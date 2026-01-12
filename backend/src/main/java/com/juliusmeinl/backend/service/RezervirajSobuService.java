package com.juliusmeinl.backend.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.juliusmeinl.backend.model.RezervirajSobu;
import com.juliusmeinl.backend.model.VrstaSobe;
import com.juliusmeinl.backend.repository.RezervirajSobuRepository;
import com.juliusmeinl.backend.repository.SobaRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.util.List;
import java.util.Map;
@Service
public class RezervirajSobuService {

    private final SobaRepository sobaRepository;
    private final RezervirajSobuRepository rezervirajSobuRepository;

    public RezervirajSobuService(SobaRepository sobaRepository, RezervirajSobuRepository rezervirajSobuRepository) {
        this.sobaRepository = sobaRepository;
        this.rezervirajSobuRepository = rezervirajSobuRepository;
    }
    public List<Map<String, Object>> dostupneSobe(LocalDate datumOd, LocalDate datumDo) {
        List<Integer> zauzeteIds = rezervirajSobuRepository.findNedostupneSobeById(datumOd, datumDo);
        if (zauzeteIds.isEmpty()) zauzeteIds = List.of(-1);

        List<Object[]> rezultat = sobaRepository.countDostupneSobePoVrsti(zauzeteIds);
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] r : rezultat) {
            Map<String, Object> map = new HashMap<>();
            map.put("vrsta", r[0]);
            map.put("balkon", r[1]);
            map.put("pogledNaMore", r[2]);
            map.put("brojDostupnih", r[3]);
            response.add(map);
        }
        return response;
    }
    // ===========================
    // GENERATE STATISTICS
    // ===========================
    public Map<String, Object> generateStatistics() {

        Map<String, Object> stats = new HashMap<>();
        List<RezervirajSobu> rezervacije = rezervirajSobuRepository.findAll();

        Map<String, Long> country = new HashMap<>();
        Map<String, Long> county = new HashMap<>();

        Map<Integer, Map<String, Integer>> monthlyPieCounter = new HashMap<>();

        Map<Integer, Integer> yearlyTotal = new HashMap<>();
        Map<Integer, Integer> yearlyKing = new HashMap<>();
        Map<Integer, Integer> yearlyTwin = new HashMap<>();
        Map<Integer, Integer> yearlyTriple = new HashMap<>();
        Map<Integer, Integer> yearlyPent = new HashMap<>();

        Map<Integer, List<VrstaSobe>> monthlyDays = new HashMap<>();

        for (RezervirajSobu rs : rezervacije) {
            if (rs.getRezervacija() == null || rs.getSoba() == null) continue;

            String drzava = rs.getRezervacija()
                    .getKorisnik().getMjesto().getDrzava().getNazivDrzave();

            String zupanija = rs.getRezervacija()
                    .getKorisnik().getMjesto().getId().getNazMjesto();

            country.merge(drzava, 1L, Long::sum);
            county.merge(zupanija, 1L, Long::sum);

            LocalDate datum = rs.getDatumOd();
            int month = datum.getMonthValue();
            int day = datum.getDayOfMonth();
            VrstaSobe tip = rs.getSoba().getVrsta();

            monthlyPieCounter
                    .computeIfAbsent(month, m -> new HashMap<>())
                    .merge(tip.name(), 1, Integer::sum);

            yearlyTotal.merge(month, 1, Integer::sum);

            switch (tip) {
                case DVOKREVETNA_KING -> yearlyKing.merge(month, 1, Integer::sum);
                case DVOKREVETNA_TWIN -> yearlyTwin.merge(month, 1, Integer::sum);
                case TROKREVETNA -> yearlyTriple.merge(month, 1, Integer::sum);
                case PENTHOUSE -> yearlyPent.merge(month, 1, Integer::sum);
            }

            monthlyDays.computeIfAbsent(day, d -> new ArrayList<>()).add(tip);
        }

        // COUNTRY
        stats.put("country", Map.of(
                "name", new ArrayList<>(country.keySet()),
                "data", new ArrayList<>(country.values())
        ));

        // COUNTY
        stats.put("county", Map.of(
                "name", new ArrayList<>(county.keySet()),
                "data", new ArrayList<>(county.values())
        ));

        // MONTHLY PIE
        int currentMonth = LocalDate.now().getMonthValue();
        Map<String, Integer> pie = monthlyPieCounter.getOrDefault(currentMonth, new HashMap<>());
        stats.put("monthlyPie", Map.of(
                "DVOKREVETNA_KING", pie.getOrDefault("DVOKREVETNA_KING", 0),
                "DVOKREVETNA_TWIN", pie.getOrDefault("DVOKREVETNA_TWIN", 0),
                "TROKREVETNA", pie.getOrDefault("TROKREVETNA", 0),
                "PENTHOUSE", pie.getOrDefault("PENTHOUSE", 0)
        ));

        // MONTHLY LINE
        List<Integer> days = new ArrayList<>();
        List<Integer> total = new ArrayList<>();
        List<Integer> king = new ArrayList<>();
        List<Integer> twin = new ArrayList<>();
        List<Integer> triple = new ArrayList<>();
        List<Integer> pent = new ArrayList<>();

        for (int d = 1; d <= 31; d++) {
            List<VrstaSobe> list = monthlyDays.getOrDefault(d, List.of());
            days.add(d);
            total.add(list.size());
            king.add((int) list.stream().filter(v -> v == VrstaSobe.DVOKREVETNA_KING).count());
            twin.add((int) list.stream().filter(v -> v == VrstaSobe.DVOKREVETNA_TWIN).count());
            triple.add((int) list.stream().filter(v -> v == VrstaSobe.TROKREVETNA).count());
            pent.add((int) list.stream().filter(v -> v == VrstaSobe.PENTHOUSE).count());
        }

        stats.put("monthly", Map.of(
                "day", days,
                "total", total,
                "DVOKREVETNA_KING", king,
                "DVOKREVETNA_TWIN", twin,
                "TROKREVETNA", triple,
                "PENTHOUSE", pent
        ));

        // YEARLY LINE
        List<Integer> months = new ArrayList<>();
        List<Integer> yTotal = new ArrayList<>();
        List<Integer> yKing = new ArrayList<>();
        List<Integer> yTwin = new ArrayList<>();
        List<Integer> yTriple = new ArrayList<>();
        List<Integer> yPent = new ArrayList<>();

        for (int m = 1; m <= 12; m++) {
            months.add(m);
            yTotal.add(yearlyTotal.getOrDefault(m, 0));
            yKing.add(yearlyKing.getOrDefault(m, 0));
            yTwin.add(yearlyTwin.getOrDefault(m, 0));
            yTriple.add(yearlyTriple.getOrDefault(m, 0));
            yPent.add(yearlyPent.getOrDefault(m, 0));
        }

        stats.put("yearly", Map.of(
                "month", months,
                "total", yTotal,
                "DVOKREVETNA_KING", yKing,
                "DVOKREVETNA_TWIN", yTwin,
                "TROKREVETNA", yTriple,
                "PENTHOUSE", yPent
        ));

        return stats;
    }

    // ===========================
    // EXPORT
    // ===========================
    public ByteArrayResource exportStatistics(String format) {

        Map<String, Object> stats = generateStatistics();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            if ("xlsx".equalsIgnoreCase(format)) {
                exportXlsx(stats, out);
            } else if ("pdf".equalsIgnoreCase(format)) {
                exportPdf(stats, out);
            } else {
                throw new RuntimeException("Nepodržan format");
            }

            return new ByteArrayResource(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Greška pri exportu", e);
        }
    }

    // ===========================
    // PDF EXPORT
    // ===========================
    private void exportPdf(Map<String, Object> stats, OutputStream out) {

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("STATISTICS REPORT").setBold().setFontSize(18));

        addYearlyTable(document, stats);
        addMonthlyTable(document, stats);
        addMonthlyPieTable(document, stats);
        addCountryTable(document, stats);
        addCountyTable(document, stats);

        document.close();
    }

    private void addYearlyTable(Document doc, Map<String, Object> stats) {
        Map<String, Object> y = (Map<String, Object>) stats.get("yearly");

        doc.add(new Paragraph("\nGodišnji broj rezervacija").setBold());

        Table t = new Table(6);
        t.addCell("Mjesec"); t.addCell("Ukupno"); t.addCell("King");
        t.addCell("Twin"); t.addCell("Trokrevetna"); t.addCell("Penthouse");

        List<Integer> months = (List<Integer>) y.get("month");
        for (int i = 0; i < months.size(); i++) {
            t.addCell(months.get(i).toString());
            t.addCell(((List<?>) y.get("total")).get(i).toString());
            t.addCell(((List<?>) y.get("DVOKREVETNA_KING")).get(i).toString());
            t.addCell(((List<?>) y.get("DVOKREVETNA_TWIN")).get(i).toString());
            t.addCell(((List<?>) y.get("TROKREVETNA")).get(i).toString());
            t.addCell(((List<?>) y.get("PENTHOUSE")).get(i).toString());
        }
        doc.add(t);
    }

    private void addMonthlyTable(Document doc, Map<String, Object> stats) {
        Map<String, Object> m = (Map<String, Object>) stats.get("monthly");

        doc.add(new Paragraph("\nMjesečni broj rezervacija").setBold());

        Table t = new Table(6);
        t.addCell("Dan"); t.addCell("Ukupno"); t.addCell("King");
        t.addCell("Twin"); t.addCell("Trokrevetna"); t.addCell("Penthouse");

        List<Integer> days = (List<Integer>) m.get("day");
        for (int i = 0; i < days.size(); i++) {
            t.addCell(days.get(i).toString());
            t.addCell(((List<?>) m.get("total")).get(i).toString());
            t.addCell(((List<?>) m.get("DVOKREVETNA_KING")).get(i).toString());
            t.addCell(((List<?>) m.get("DVOKREVETNA_TWIN")).get(i).toString());
            t.addCell(((List<?>) m.get("TROKREVETNA")).get(i).toString());
            t.addCell(((List<?>) m.get("PENTHOUSE")).get(i).toString());
        }
        doc.add(t);
    }

    private void addMonthlyPieTable(Document doc, Map<String, Object> stats) {
        Map<String, Integer> pie = (Map<String, Integer>) stats.get("monthlyPie");

        doc.add(new Paragraph("\nPostotak po tipu sobe").setBold());

        Table t = new Table(2);
        t.addCell("Tip sobe");
        t.addCell("Broj");

        pie.forEach((k, v) -> {
            t.addCell(k);
            t.addCell(v.toString());
        });

        doc.add(t);
    }

    private void addCountryTable(Document doc, Map<String, Object> stats) {
        Map<String, Object> c = (Map<String, Object>) stats.get("country");

        doc.add(new Paragraph("\nRezervacije po državama").setBold());

        Table t = new Table(2);
        t.addCell("Država");
        t.addCell("Broj");

        List<String> names = (List<String>) c.get("name");
        List<Long> data = (List<Long>) c.get("data");

        for (int i = 0; i < names.size(); i++) {
            t.addCell(names.get(i));
            t.addCell(data.get(i).toString());
        }

        doc.add(t);
    }

    private void addCountyTable(Document doc, Map<String, Object> stats) {
        Map<String, Object> c = (Map<String, Object>) stats.get("county");

        doc.add(new Paragraph("\nRezervacije po županijama").setBold());

        Table t = new Table(2);
        t.addCell("Županija");
        t.addCell("Broj");

        List<String> names = (List<String>) c.get("name");
        List<Long> data = (List<Long>) c.get("data");

        for (int i = 0; i < names.size(); i++) {
            t.addCell(names.get(i));
            t.addCell(data.get(i).toString());
        }

        doc.add(t);
    }
    private void exportXlsx(Map<String, Object> stats, OutputStream out) throws Exception {

        Workbook workbook = new XSSFWorkbook();

        createYearlySheet(workbook, stats);
        createMonthlySheet(workbook, stats);
        createMonthlyPieSheet(workbook, stats);
        createCountrySheet(workbook, stats);
        createCountySheet(workbook, stats);

        workbook.write(out);
        workbook.close();
    }
    private void createYearlySheet(Workbook workbook, Map<String, Object> stats) {

        Sheet sheet = workbook.createSheet("Yearly");
        Map<String, Object> yearly = (Map<String, Object>) stats.get("yearly");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Mjesec");
        header.createCell(1).setCellValue("Ukupno");
        header.createCell(2).setCellValue("Dvokrevetna King");
        header.createCell(3).setCellValue("Dvokrevetna Twin");
        header.createCell(4).setCellValue("Trokrevetna");
        header.createCell(5).setCellValue("Penthouse");

        List<Integer> months = (List<Integer>) yearly.get("month");

        for (int i = 0; i < months.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(months.get(i));
            row.createCell(1).setCellValue(((List<Integer>) yearly.get("total")).get(i));
            row.createCell(2).setCellValue(((List<Integer>) yearly.get("DVOKREVETNA_KING")).get(i));
            row.createCell(3).setCellValue(((List<Integer>) yearly.get("DVOKREVETNA_TWIN")).get(i));
            row.createCell(4).setCellValue(((List<Integer>) yearly.get("TROKREVETNA")).get(i));
            row.createCell(5).setCellValue(((List<Integer>) yearly.get("PENTHOUSE")).get(i));
        }

        for (int i = 0; i <= 5; i++) sheet.autoSizeColumn(i);
    }
    private void createMonthlySheet(Workbook workbook, Map<String, Object> stats) {

        Sheet sheet = workbook.createSheet("Monthly");
        Map<String, Object> monthly = (Map<String, Object>) stats.get("monthly");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Dan");
        header.createCell(1).setCellValue("Ukupno");
        header.createCell(2).setCellValue("Dvokrevetna King");
        header.createCell(3).setCellValue("Dvokrevetna Twin");
        header.createCell(4).setCellValue("Trokrevetna");
        header.createCell(5).setCellValue("Penthouse");

        List<Integer> days = (List<Integer>) monthly.get("day");

        for (int i = 0; i < days.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(days.get(i));
            row.createCell(1).setCellValue(((List<Integer>) monthly.get("total")).get(i));
            row.createCell(2).setCellValue(((List<Integer>) monthly.get("DVOKREVETNA_KING")).get(i));
            row.createCell(3).setCellValue(((List<Integer>) monthly.get("DVOKREVETNA_TWIN")).get(i));
            row.createCell(4).setCellValue(((List<Integer>) monthly.get("TROKREVETNA")).get(i));
            row.createCell(5).setCellValue(((List<Integer>) monthly.get("PENTHOUSE")).get(i));
        }

        for (int i = 0; i <= 5; i++) sheet.autoSizeColumn(i);
    }
    private void createMonthlyPieSheet(Workbook workbook, Map<String, Object> stats) {

        Sheet sheet = workbook.createSheet("Monthly Pie");
        Map<String, Integer> pie = (Map<String, Integer>) stats.get("monthlyPie");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Tip sobe");
        header.createCell(1).setCellValue("Broj rezervacija");

        int rowIdx = 1;
        for (Map.Entry<String, Integer> entry : pie.entrySet()) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }
    private void createCountrySheet(Workbook workbook, Map<String, Object> stats) {

        Sheet sheet = workbook.createSheet("Country");
        Map<String, Object> country = (Map<String, Object>) stats.get("country");

        List<String> names = (List<String>) country.get("name");
        List<Long> values = (List<Long>) country.get("data");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Država");
        header.createCell(1).setCellValue("Broj rezervacija");

        for (int i = 0; i < names.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(names.get(i));
            row.createCell(1).setCellValue(values.get(i));
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }
    private void createCountySheet(Workbook workbook, Map<String, Object> stats) {

        Sheet sheet = workbook.createSheet("County");
        Map<String, Object> county = (Map<String, Object>) stats.get("county");

        List<String> names = (List<String>) county.get("name");
        List<Long> values = (List<Long>) county.get("data");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Županija");
        header.createCell(1).setCellValue("Broj rezervacija");

        for (int i = 0; i < names.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(names.get(i));
            row.createCell(1).setCellValue(values.get(i));
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }



}
