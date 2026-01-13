package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.model.*;
import com.itextpdf.text.Font;
import com.juliusmeinl.backend.model.Drzava;
import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.Mjesto;
//import com.juliusmeinl.backend.model.MjestoId;
import com.juliusmeinl.backend.repository.DrzavaRepository;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

import org.springframework.core.io.ByteArrayResource;
import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;
    private final DrzavaRepository drzavaRepository;

    @Transactional
    public Korisnik spremiKorisnika(Korisnik korisnik) {
        String imeIzJsona = korisnik.getMjesto().getDrzava().getNazivDrzave();

        Drzava drzava = drzavaRepository.findByNazivDrzave(imeIzJsona);

        korisnik.getMjesto().setDrzava(drzava);

        //TODO: dodati da se nazMjesta formatira

        korisnik.setOvlast(UlogaKorisnika.REGISTRIRAN);
        return korisnikRepository.save(korisnik);
    }

    // Provjerava postojili li korisnik prema mailu
    public boolean existsByEmail(String email) {
        return korisnikRepository.existsByEmail(email);
    }

    // Dohvat korisnika po emailu
    public Optional<Korisnik> findByEmail(String email) {
        return korisnikRepository.findByEmail(email);
    }

    //Provjerava je li korisnik vlasnik
    public boolean korisnikJeVlasnik(String email) {
        Optional<Korisnik> korisnik = korisnikRepository.findByEmailAndOvlast(email, UlogaKorisnika.VLASNIK);
        return korisnik.isPresent();
    }

    //TODO: Rafaktorizirati za novi nacin formiranja SecurityContexta
    @Transactional
    public Integer trenutniKorisnikId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauthUser = (OAuth2User) auth.getPrincipal();
        String email = oauthUser.getAttribute("email");
        Optional<Korisnik> korisnik = korisnikRepository.findByEmail(email);

        return korisnik.get().getId();
    }
    @Transactional
    public ByteArrayResource exportUsersXLSX() {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Users");
            Row header = sheet.createRow(0);
            String[] columns = {"Ime","Prezime","Email","Telefon","Uloga","Drzava","Mjesto","PostanskiBroj"};
            for(int i=0;i<columns.length;i++) header.createCell(i).setCellValue(columns[i]);

            Iterable<Korisnik> iterableUsers = korisnikRepository.findAll();
            List<Korisnik> users = new ArrayList<>();
            iterableUsers.forEach(users::add);
            int rowIdx=1;
            for(Korisnik k : users){
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(k.getIme());
                row.createCell(1).setCellValue(k.getPrezime());
                row.createCell(2).setCellValue(k.getEmail());
                row.createCell(3).setCellValue(k.getTelefon());
                row.createCell(4).setCellValue(k.getOvlast().name());

                if (k.getMjesto() != null) {
                    row.createCell(5).setCellValue(k.getMjesto().getDrzava() != null
                            ? k.getMjesto().getDrzava().getNazivDrzave()
                            : "");
                    row.createCell(6).setCellValue(k.getMjesto().getNazMjesto() != null
                            ? k.getMjesto().getNazMjesto()
                            : "");
                    row.createCell(7).setCellValue(k.getMjesto().getPostBr() != null
                            ? k.getMjesto().getPostBr()
                            : "");
                } else {
                    row.createCell(5).setCellValue("");
                    row.createCell(6).setCellValue("");
                    row.createCell(7).setCellValue("");
                }
            }

            workbook.write(out);
            return new ByteArrayResource(out.toByteArray());
        } catch(Exception e){
            throw new RuntimeException("Greška pri XLSX izvozu korisnika", e);
        }
    }

    @Transactional
    public ByteArrayResource exportUsersXML() {
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Iterable<Korisnik> iterableUsers = korisnikRepository.findAll();
            List<Korisnik> users = new ArrayList<>();
            iterableUsers.forEach(users::add);
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<users>\n".getBytes());

            for(Korisnik k : users){
                out.write(("  <user>\n").getBytes());
                out.write(("    <ime>"+k.getIme()+"</ime>\n").getBytes());
                out.write(("    <prezime>"+k.getPrezime()+"</prezime>\n").getBytes());
                out.write(("    <email>"+k.getEmail()+"</email>\n").getBytes());
                out.write(("    <telefon>"+k.getTelefon()+"</telefon>\n").getBytes());
                out.write(("    <uloga>"+k.getOvlast().name()+"</uloga>\n").getBytes());

                if (k.getMjesto() != null) {
                    out.write(("    <drzava>"+(k.getMjesto().getDrzava() != null
                            ? k.getMjesto().getDrzava().getNazivDrzave() : "")+"</drzava>\n").getBytes());
                    out.write(("    <mjesto>"+(k.getMjesto().getNazMjesto() != null
                            ? k.getMjesto().getNazMjesto() : "")+"</mjesto>\n").getBytes());
                    out.write(("    <postanskiBroj>"+(k.getMjesto().getPostBr() != null
                            ? k.getMjesto().getPostBr() : "")+"</postanskiBroj>\n").getBytes());
                } else {
                    out.write(("    <drzava></drzava>\n").getBytes());
                    out.write(("    <mjesto></mjesto>\n").getBytes());
                    out.write(("    <postanskiBroj></postanskiBroj>\n").getBytes());
                }

                out.write(("  </user>\n").getBytes());
            }

            out.write("</users>".getBytes());
            return new ByteArrayResource(out.toByteArray());
        } catch(Exception e){
            throw new RuntimeException("Greška pri XML izvozu korisnika", e);
        }
    }

    @Transactional
    public ByteArrayResource exportUsersPDF() {
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{3,3,5,3,2,3,3,2});

            String[] columns = {"Ime","Prezime","Email","Telefon","Uloga","Drzava","Mjesto","PostanskiBroj"};
            for(String col : columns){
                PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            Iterable<Korisnik> iterableUsers = korisnikRepository.findAll();
            List<Korisnik> users = new ArrayList<>();
            iterableUsers.forEach(users::add);
            for(Korisnik k : users){
                table.addCell(new Phrase(k.getIme(), cellFont));
                table.addCell(new Phrase(k.getPrezime(), cellFont));
                table.addCell(new Phrase(k.getEmail(), cellFont));
                table.addCell(new Phrase(k.getTelefon(), cellFont));
                table.addCell(new Phrase(k.getOvlast().name(), cellFont));

                String drzava = (k.getMjesto() != null && k.getMjesto().getDrzava() != null)
                        ? k.getMjesto().getDrzava().getNazivDrzave() : "";
                String mjesto = (k.getMjesto() != null && k.getMjesto().getNazMjesto() != null)
                        ? k.getMjesto().getNazMjesto() : "";
                String postBr = (k.getMjesto() != null && k.getMjesto().getPostBr() != null)
                        ? k.getMjesto().getPostBr() : "";

                table.addCell(new Phrase(drzava, cellFont));
                table.addCell(new Phrase(mjesto, cellFont));
                table.addCell(new Phrase(postBr, cellFont));
            }

            document.add(table);
            document.close();
            return new ByteArrayResource(out.toByteArray());

        } catch(Exception e){
            throw new RuntimeException("Greška pri PDF izvozu korisnika", e);
        }
    }


    public List<Map<String, Object>> getAllUsersForFrontend() {
        Iterable<Korisnik> iterableUsers = korisnikRepository.findAll();
        List<Korisnik> users = new ArrayList<>();
        iterableUsers.forEach(users::add);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Korisnik k : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", k.getId());
            map.put("ime", k.getIme());
            map.put("prezime", k.getPrezime());
            map.put("email", k.getEmail());
            map.put("telefon", k.getTelefon());

            String drzava = "";
            String mjesto = "";
            String postanskiBroj = "";

            if (k.getMjesto() != null) {
                if (k.getMjesto().getDrzava() != null) {
                    drzava = k.getMjesto().getDrzava().getNazivDrzave();
                }
                if (k.getMjesto().getId() != null) {
                    if (k.getMjesto() != null) {
                        if (k.getMjesto().getDrzava() != null) {
                            drzava = k.getMjesto().getDrzava().getNazivDrzave();
                        }
                        if (k.getMjesto().getNazMjesto() != null) {
                            mjesto = k.getMjesto().getNazMjesto();
                        }
                        if (k.getMjesto().getPostBr() != null) {
                            postanskiBroj = k.getMjesto().getPostBr();
                        }
                    }

                }
            }

            map.put("drzava", drzava);
            map.put("mjesto", mjesto);
            map.put("postanskiBroj", postanskiBroj);

            // Normalizacija role za frontend
            map.put("uloga", normalizeUloga(k.getOvlast()));
            result.add(map);
        }

        return result;
    }

    private String normalizeUloga(UlogaKorisnika uloga) {
        if (uloga == null) return "GOST";

        return switch (uloga) {
            case VLASNIK -> "KORISNIK";      // ako želiš da vlasnik bude "Korisnik" frontend label
            case ZAPOSLENIK -> "RECEPCIONIST";
            case REGISTRIRAN -> "KORISNIK";
            case NEREGISTRIRAN -> "GOST";
        };
    }






    public Korisnik updateRole(Integer userId, String novaUloga) {
        UlogaKorisnika ulogaEnum;
        try {
            ulogaEnum = UlogaKorisnika.valueOf(novaUloga.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Nepoznata uloga: " + novaUloga);
        }

        int updated = korisnikRepository.updateRoleOnly(userId, ulogaEnum);
        if (updated == 0) {
            throw new RuntimeException("Korisnik s ID " + userId + " ne postoji");
        }

        // Vrati korisnika s novom ulogom
        return korisnikRepository.findById(userId).orElseThrow();
    }

    private UlogaKorisnika parseUloga(String uloga) {
        if (uloga == null) return UlogaKorisnika.NEREGISTRIRAN;

        return switch (uloga.toUpperCase()) {
            case "KORISNIK" -> UlogaKorisnika.REGISTRIRAN;
            case "RECEPCIONIST" -> UlogaKorisnika.ZAPOSLENIK;
            case "ADMIN" -> UlogaKorisnika.VLASNIK;  // mapiranje frontend "ADMIN" na backend VLASNIK
            default -> UlogaKorisnika.NEREGISTRIRAN;
        };
    }



}
