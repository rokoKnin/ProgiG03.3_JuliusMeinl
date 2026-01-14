package com.juliusmeinl.backend.service;

import com.itextpdf.text.Font;
import com.juliusmeinl.backend.dto.DodatniSadrzajResponseDTO;
import com.juliusmeinl.backend.model.DodatniSadrzaj;
import com.juliusmeinl.backend.model.StatusDodatniSadrzaj;
import com.juliusmeinl.backend.repository.DodatniSadrzajRepository;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.poi.ss.usermodel.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DodatniSadrzajService {
    private final DodatniSadrzajRepository sadrzajRepository;

    public  DodatniSadrzajService(DodatniSadrzajRepository sadrzajRepository) {
        this.sadrzajRepository = sadrzajRepository;
    private final DodatniSadrzajRepository repository;

    public DodatniSadrzajService(DodatniSadrzajRepository repository) {
        this.repository = repository;
    }

    public List<DodatniSadrzaj> getAll() {
        return repository.findAll();
    }

    public DodatniSadrzaj update(Integer id, BigDecimal price, String statusStr) {
        DodatniSadrzaj content = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sadržaj nije pronađen"));
        content.setCijena(price);
        switch(statusStr) {
            case "dostupan":
                content.setStatus(StatusDodatniSadrzaj.dostupan);
                break;
            case "nedostupan":
                content.setStatus(StatusDodatniSadrzaj.nedostupan);
                break;
            default:
                throw new RuntimeException("Krivi status dodatnog sadržaja");
        }
        return repository.save(content);
    }

    public List<DodatniSadrzajResponseDTO> informacijeSadrzaj() {
        List<DodatniSadrzaj> sadrzaji = sadrzajRepository.findAll();
        List<DodatniSadrzajResponseDTO> response = new ArrayList<>();
    //XLSX
    @Transactional
    public ByteArrayResource exportXLSX() {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

        for(DodatniSadrzaj sadrzaj : sadrzaji){
            DodatniSadrzajResponseDTO sadrzajResponseDTO = new DodatniSadrzajResponseDTO();
            sadrzajResponseDTO.setVrsta(sadrzaj.getVrsta());
            sadrzajResponseDTO.setCijena(sadrzaj.getCijena());
            sadrzajResponseDTO.setDostupan(sadrzaj.getStatus() == StatusDodatniSadrzaj.dostupan);
            Sheet sheet = workbook.createSheet("Dodatni sadržaj");

            response.add(sadrzajResponseDTO);
        }
        return response;
    }
            String[] columns = {
                    "ID", "Vrsta", "Status", "Cijena"
            };

            Row header = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            List<DodatniSadrzaj> lista = repository.findAll();
            int rowIdx = 1;

            for (DodatniSadrzaj ds : lista) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(ds.getId());
                row.createCell(1).setCellValue(ds.getVrsta());
                row.createCell(2).setCellValue(ds.getStatus().name());
                row.createCell(3).setCellValue(ds.getCijena().toString());
            }

            workbook.write(out);
            return new ByteArrayResource(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Greška pri XLSX izvozu dodatnog sadržaja", e);
        }
    }

    //XML
    @Transactional
    public ByteArrayResource exportXML() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            List<DodatniSadrzaj> lista = repository.findAll();

            out.write("""
                <?xml version="1.0" encoding="UTF-8"?>
                <dodatniSadrzaji>
                """.getBytes());

            for (DodatniSadrzaj ds : lista) {
                out.write("""
                    <dodatniSadrzaj>
                        <id>%d</id>
                        <vrsta>%s</vrsta>
                        <status>%s</status>
                        <cijena>%s</cijena>
                    </dodatniSadrzaj>
                    """.formatted(
                        ds.getId(),
                        ds.getVrsta(),
                        ds.getStatus(),
                        ds.getCijena()
                ).getBytes());
            }

            out.write("</dodatniSadrzaji>".getBytes());
            return new ByteArrayResource(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Greška pri XML izvozu dodatnog sadržaja", e);
        }
    }

    //PDF
    @Transactional
    public ByteArrayResource exportPDF() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            String[] headers = { "ID", "Vrsta", "Status", "Cijena" };

            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            for (DodatniSadrzaj ds : repository.findAll()) {
                table.addCell(new Phrase(String.valueOf(ds.getId()), cellFont));
                table.addCell(new Phrase(ds.getVrsta(), cellFont));
                table.addCell(new Phrase(ds.getStatus().name(), cellFont));
                table.addCell(new Phrase(ds.getCijena().toString(), cellFont));
            }

            document.add(table);
            document.close();

            return new ByteArrayResource(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Greška pri PDF izvozu dodatnog sadržaja", e);
        }
    }

}
