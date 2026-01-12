package com.juliusmeinl.backend.service;

import com.itextpdf.text.Font;
import com.juliusmeinl.backend.dto.RezervacijaRequestDTO;
import com.juliusmeinl.backend.dto.SobaRequestDTO;
import com.juliusmeinl.backend.model.Soba;
import com.juliusmeinl.backend.model.VrstaSobe;
import com.juliusmeinl.backend.repository.RezervirajSobuRepository;
import com.juliusmeinl.backend.repository.SobaRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.core.io.ByteArrayResource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayOutputStream;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class SobaService {

    private final SobaRepository sobaRepository;
    private final RezervirajSobuRepository rezervirajSobuRepository;

    public SobaService(SobaRepository sobaRepository, RezervirajSobuRepository rezervirajSobuRepository) {
        this.sobaRepository = sobaRepository;
        this.rezervirajSobuRepository = rezervirajSobuRepository;
    }

    public List<Soba> getSveSobe() {
        return sobaRepository.findAll()
                .stream()
                .sorted((s1, s2) -> s1.getBrojSobe().compareTo(s2.getBrojSobe()))
                .toList();
    }

    public Soba getSobaById(Integer id) {
        return sobaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soba s ID " + id + " nije pronađena."));
    }

    public List<Soba> getSobePoVrsti(VrstaSobe vrsta) {
        return sobaRepository.findByVrsta(vrsta);
    }

    public Soba spremiSobu(Soba soba) {
        return sobaRepository.save(soba);
    }

    public void obrisiSobu(Integer id) {
        sobaRepository.deleteById(id);
    }
    public Optional<Soba> pronadiPoId(Integer id) {
        return sobaRepository.findById(id);
    }
    public BigDecimal izracunajCijenu(VrstaSobe vrsta, int kat, boolean balkon, boolean pogledNaMore) {
        BigDecimal cijena = BigDecimal.ZERO;

        if (vrsta == VrstaSobe.DVOKREVETNA_KING || vrsta == VrstaSobe.DVOKREVETNA_TWIN) {
            if (kat == 1) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("120.00");
                else if (balkon) cijena = new BigDecimal("105.00");
                else if (pogledNaMore) cijena = new BigDecimal("110.00");
                else cijena = new BigDecimal("100.00");
            } else if (kat == 2) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("130.00");
                else if (balkon) cijena = new BigDecimal("115.00");
                else if (pogledNaMore) cijena = new BigDecimal("120.00");
                else cijena = new BigDecimal("110.00");
            } else if (kat == 3) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("140.00");
                else if (balkon) cijena = new BigDecimal("125.00");
                else if (pogledNaMore) cijena = new BigDecimal("130.00");
                else cijena = new BigDecimal("120.00");
            }
        }

        else if (vrsta == VrstaSobe.TROKREVETNA) {
            if (kat == 1) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("150.00");
                else if (balkon) cijena = new BigDecimal("135.00");
                else if (pogledNaMore) cijena = new BigDecimal("140.00");
                else cijena = new BigDecimal("130.00");
            } else if (kat == 2) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("160.00");
                else if (balkon) cijena = new BigDecimal("145.00");
                else if (pogledNaMore) cijena = new BigDecimal("150.00");
                else cijena = new BigDecimal("140.00");
            } else if (kat == 3) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("170.00");
                else if (balkon) cijena = new BigDecimal("155.00");
                else if (pogledNaMore) cijena = new BigDecimal("160.00");
                else cijena = new BigDecimal("150.00");
            }
        }

        else if (vrsta == VrstaSobe.PENTHOUSE) {
            if (kat == 4) {
                cijena = new BigDecimal("250.00");
            }
        }

        return cijena;
    }

    public List<Map<String, Object>> dostupneSobe(LocalDate datumOd, LocalDate datumDo) {

        List<Integer> zauzeteIds = rezervirajSobuRepository.findNedostupneSobeById(datumOd, datumDo);

        if (zauzeteIds.isEmpty()) {
            zauzeteIds = List.of(-1); // ako mi vrati prazan, stavljam id -1 koji nema sig da mi ne pukne kod
        }
        List<Object[]> rezultat = sobaRepository.countDostupneSobePoVrsti(zauzeteIds);

        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] r : rezultat) {
            Map<String, Object> map = new HashMap<>();
            map.put("vrsta", r[0]);
            map.put("balkon", r[1]);
            map.put("pogledNaMore", r[2]);
            map.put("cijena", r[3]);
            map.put("brojDostupnih", r[4]);

            response.add(map);
        }
        return response;
    }

    public List<Integer> dohvatiSobe(RezervacijaRequestDTO rezervacijaRequestDTO) {
        List<Integer> sobeZaRezervacijuIds = new ArrayList<>(); // dodaj neki -1 id bezveze

        List<SobaRequestDTO> sobaRequestDTO = rezervacijaRequestDTO.getSobe();
        List<Integer> zauzeteIds = rezervirajSobuRepository.findNedostupneSobeById(rezervacijaRequestDTO.getDatumSobeOd(), rezervacijaRequestDTO.getDatumSobeDo());


        for (SobaRequestDTO s : sobaRequestDTO) {
            List<Integer> dostupneIds = sobaRepository.findDostupneSobeIds(s.getVrsta(), s.getBalkon(), s.getPogledNaMore(),zauzeteIds);

            boolean pronadena = false;
            for(Integer dostupneId : dostupneIds) {
                if(!sobeZaRezervacijuIds.contains(dostupneId)) {
                    sobeZaRezervacijuIds.add(dostupneId);
                    pronadena = true;
                    break;
                }
            }
            if (!pronadena) throw new ResponseStatusException(HttpStatus.CONFLICT, "");
        }

        return sobeZaRezervacijuIds;
    }

    //XLSX
    @Transactional
    public ByteArrayResource exportRoomsXLSX() {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Rooms");

            String[] columns = {
                    "ID", "Broj sobe", "Kat", "Vrsta",
                    "Balkon", "Pogled na more",
                    "Kapacitet", "Cijena", "Status"
            };

            Row header = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            List<Soba> sobe = sobaRepository.findAll();
            int rowIdx = 1;

            for (Soba s : sobe) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(s.getId());
                row.createCell(1).setCellValue(s.getBrojSobe());
                row.createCell(2).setCellValue(s.getKat());
                row.createCell(3).setCellValue(s.getVrsta().name());
                row.createCell(4).setCellValue(s.imaBalkon());
                row.createCell(5).setCellValue(s.imaPogledNaMore());
                row.createCell(6).setCellValue(s.getKapacitet());
                row.createCell(7).setCellValue(s.getCijena().toString());
                row.createCell(8).setCellValue(s.getStatus().name());
            }

            workbook.write(out);
            return new ByteArrayResource(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Greška pri XLSX izvozu soba", e);
        }
    }

    //XML
    @Transactional
    public ByteArrayResource exportRoomsXML() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            List<Soba> sobe = sobaRepository.findAll();
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<rooms>\n".getBytes());

            for (Soba s : sobe) {
                out.write("""
              <room>
                <id>%d</id>
                <brojSobe>%s</brojSobe>
                <kat>%d</kat>
                <vrsta>%s</vrsta>
                <balkon>%b</balkon>
                <pogledNaMore>%b</pogledNaMore>
                <kapacitet>%d</kapacitet>
                <cijena>%s</cijena>
                <status>%s</status>
              </room>
            """.formatted(
                        s.getId(),
                        s.getBrojSobe(),
                        s.getKat(),
                        s.getVrsta(),
                        s.imaBalkon(),
                        s.imaPogledNaMore(),
                        s.getKapacitet(),
                        s.getCijena(),
                        s.getStatus()
                ).getBytes());
            }

            out.write("</rooms>".getBytes());
            return new ByteArrayResource(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Greška pri XML izvozu soba", e);
        }
    }

    //PDF
    @Transactional
    public ByteArrayResource exportRoomsPDF() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);

            String[] headers = {
                    "ID", "Broj", "Kat", "Vrsta",
                    "Balkon", "More", "Kapacitet", "Cijena", "Status"
            };

            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            for (Soba s : sobaRepository.findAll()) {
                table.addCell(new Phrase(String.valueOf(s.getId()), cellFont));
                table.addCell(new Phrase(s.getBrojSobe(), cellFont));
                table.addCell(new Phrase(String.valueOf(s.getKat()), cellFont));
                table.addCell(new Phrase(s.getVrsta().name(), cellFont));
                table.addCell(new Phrase(String.valueOf(s.imaBalkon()), cellFont));
                table.addCell(new Phrase(String.valueOf(s.imaPogledNaMore()), cellFont));
                table.addCell(new Phrase(String.valueOf(s.getKapacitet()), cellFont));
                table.addCell(new Phrase(s.getCijena().toString(), cellFont));
                table.addCell(new Phrase(s.getStatus().name(), cellFont));
            }

            document.add(table);
            document.close();

            return new ByteArrayResource(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Greška pri PDF izvozu soba", e);
        }
    }


}

