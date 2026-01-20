package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.dto.*;
import com.juliusmeinl.backend.model.*;
import com.juliusmeinl.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.io.ByteArrayOutputStream;


@Service
public class RezervacijaService {
    private final SobaRepository sobaRepository;
    private final RezervirajSobuRepository rezervirajSobuRepository;
    private final RezervacijaRepository rezervacijaRepository;
    private final KorisnikRepository korisnikRepository;
    private final DodatniSadrzajRepository dodatniSadrzajRepository;
    private final RezervirajSadrzajRepository rezervirajSadrzajRepository;

    public RezervacijaService(SobaRepository sobaRepository, RezervirajSobuRepository rezervirajSobuRepository, RezervacijaRepository rezervacijaRepository, KorisnikRepository korisnikRepository, DodatniSadrzajRepository dodatniSadrzajRepository, RezervirajSadrzajRepository rezervirajSadrzajRepository) {
        this.sobaRepository = sobaRepository;
        this.rezervirajSobuRepository = rezervirajSobuRepository;
        this.rezervacijaRepository = rezervacijaRepository;
        this.korisnikRepository = korisnikRepository;
        this.dodatniSadrzajRepository = dodatniSadrzajRepository;
        this.rezervirajSadrzajRepository = rezervirajSadrzajRepository;
    }



    public Integer kreirajRezervaciju(Integer id) {
        Optional<Korisnik> korisnik = korisnikRepository.findById(id);

        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setKorisnik(korisnik.get());
        rezervacija.setIznosRezervacije(BigDecimal.ZERO);
        rezervacija.setPlaceno(true);

        return rezervacijaRepository.save(rezervacija).getId();
    }

    @Transactional
    public void rezervirajSobe(Integer rezervacijaId, List<Integer> dodijeljeneSobeId, LocalDate datumOd, LocalDate datumDo) {
        BigDecimal rezervacijaSobeCijena = BigDecimal.ZERO;
        long brojNocenja = ChronoUnit.DAYS.between(datumOd, datumDo);

        Rezervacija rezervacija = rezervacijaRepository.findById(rezervacijaId).orElseThrow(() -> new IllegalArgumentException("Rezervacija ne postoji"));

        for (Integer sobaId : dodijeljeneSobeId) {

            Soba soba = sobaRepository.findById(sobaId).orElseThrow(() -> new IllegalArgumentException("Soba ne postoji"));

            rezervacijaSobeCijena = rezervacijaSobeCijena.add(soba.getCijena().multiply(BigDecimal.valueOf(brojNocenja)));

            RezervirajSobu rs = new RezervirajSobu();

            RezervirajSobuId id = new RezervirajSobuId(rezervacijaId, sobaId);
            rs.setId(id);

            rs.setRezervacija(rezervacija);
            rs.setSoba(soba);
            rs.setDatumOd(datumOd);
            rs.setDatumDo(datumDo);

            rezervirajSobuRepository.save(rs);
        }
        rezervacija.setIznosRezervacije(rezervacijaSobeCijena);
        rezervacijaRepository.save(rezervacija);

    }

    @Transactional
    public void rezervirajSadrzaj(Integer rezervacijaId, RezervacijaRequestDTO rezervacijaRequestDTO) {
        List<DodatniSadrzajRequestDTO> dodatniSadrzajRequestDTO = rezervacijaRequestDTO.getDodatniSadrzaji();

        BigDecimal cijenaRezervacijaSadrzaj = BigDecimal.ZERO;

        Rezervacija rezervacija = rezervacijaRepository.findById(rezervacijaId).orElseThrow(() -> new IllegalArgumentException("Rezervacija ne postoji"));

        for(DodatniSadrzajRequestDTO d : dodatniSadrzajRequestDTO) {
            DodatniSadrzaj dodatniSadrzaj = dodatniSadrzajRepository.findByVrsta(d.getVrstaDodatnogSadrzaja()).orElseThrow(() -> new IllegalArgumentException("Dodatni sadrzaj ne postoji"));

            cijenaRezervacijaSadrzaj = cijenaRezervacijaSadrzaj.add(dodatniSadrzaj.getCijena());

            RezervirajSadrzaj rs = new RezervirajSadrzaj();
            rs.setRezervacija(rezervacija);
            rs.setDodatniSadrzaj(dodatniSadrzaj);

            RezervirajSadrzajId id = new RezervirajSadrzajId(rezervacijaId,dodatniSadrzaj.getId(),d.getDatum());
            rs.setId(id);

            rezervirajSadrzajRepository.save(rs);
        }
        rezervacija.setIznosRezervacije(rezervacija.getIznosRezervacije().add(cijenaRezervacijaSadrzaj));
        rezervacijaRepository.save(rezervacija);
    }


    @Transactional(readOnly = true)
    public List<Rezervacija> dohvatiSveRezervacije() {
        return rezervacijaRepository.findAll();
    }

    @Transactional
    public Rezervacija azurirajRezervaciju(Integer rezervacijaId, Rezervacija input) {
        Rezervacija rezervacija = rezervacijaRepository.findById(rezervacijaId)
                .orElseThrow(() -> new IllegalArgumentException("Rezervacija ne postoji"));

        // Update polja rezervacije
        rezervacija.setPlaceno(input.isPlaceno());
        rezervacija.setDatumRezerviranja(input.getDatumRezerviranja());

        // Update soba
        if (input.getSobe() != null) {
            rezervirajSobuRepository.deleteAll(rezervacija.getSobe());
            for (RezervirajSobu rs : input.getSobe()) {
                rs.setRezervacija(rezervacija);
                rezervirajSobuRepository.save(rs);
            }
        }

        // Update dodatni sadržaj
        if (input.getSadrzaji() != null) {
            rezervirajSadrzajRepository.deleteAll(rezervacija.getSadrzaji());
            for (RezervirajSadrzaj rs : input.getSadrzaji()) {
                rs.setRezervacija(rezervacija);
                rezervirajSadrzajRepository.save(rs);
            }
        }

        // Update ukupnog iznosa rezervacije (sobe + dodatni sadržaj)
        BigDecimal iznos = BigDecimal.ZERO;
        if (rezervacija.getSobe() != null) {
            for (RezervirajSobu rs : rezervacija.getSobe()) {
                long brojNocenja = ChronoUnit.DAYS.between(rs.getDatumOd(), rs.getDatumDo());
                iznos = iznos.add(rs.getSoba().getCijena().multiply(BigDecimal.valueOf(brojNocenja)));
            }
        }
        if (rezervacija.getSadrzaji() != null) {
            for (RezervirajSadrzaj rs : rezervacija.getSadrzaji()) {
                iznos = iznos.add(rs.getDodatniSadrzaj().getCijena());
            }
        }
        rezervacija.setIznosRezervacije(iznos);

        return rezervacijaRepository.save(rezervacija);
    }

    @Transactional(readOnly = true)
    public List<RezervacijaResponseDTO> dohvatiSveRezervacijeDTO() {
        List<Rezervacija> rezervacije = rezervacijaRepository.findAll(); // možeš dodati fetch joins ako želiš

        List<RezervacijaResponseDTO> result = new ArrayList<>();

        for (Rezervacija r : rezervacije) {
            RezervacijaResponseDTO dto = new RezervacijaResponseDTO(
                    r.getId(),
                    r.getDatumRezerviranja(),
                    r.isPlaceno(),
                    r.getIznosRezervacije(),
                    r.getKorisnik().getIme(),
                    r.getKorisnik().getPrezime(),
                    r.getKorisnik().getEmail()
                   // r.getKorisnik().getKorisnikId()
            );

            // dodaj sobe
            if (r.getSobe() != null) {
                for (RezervirajSobu rs : r.getSobe()) {
                    dto.getSobe().add(new SobaDTO(
                            rs.getSoba().getId(),
                            rs.getSoba().getBrojSobe(),
                            rs.getSoba().getVrsta(),
                            rs.getDatumOd(),
                            rs.getDatumDo()
                    ));
                }
            }

            // dodaj dodatke
            if (r.getSadrzaji() != null) {
                for (RezervirajSadrzaj rd : r.getSadrzaji()) {
                    dto.getSadrzaji().add(new DodatakDTO(
                            rd.getDodatniSadrzaj().getId(),
                            rd.getDodatniSadrzaj().getVrsta(),
                            rd.getId().getDatumSadrzaj() // pretpostavljam da id sadrži datum
                    ));
                }
            }

            result.add(dto);
        }

        return result;
    }
    @Transactional(readOnly = true)
    public byte[] exportPdf() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(doc, out);
            doc.open();

            doc.add(new com.itextpdf.text.Paragraph("Lista rezervacija"));
            doc.add(com.itextpdf.text.Chunk.NEWLINE);

            // Definiraj kolone tabele
            String[] columns = {"ID", "Ime", "Prezime", "Email", "Plaćeno", "Iznos", "Soba", "Vrsta sobe", "Datum od", "Datum do"};
            com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(columns.length);
            table.setWidthPercentage(100);

            // Dodaj header
            for (String col : columns) {
                com.itextpdf.text.pdf.PdfPCell cell = new com.itextpdf.text.pdf.PdfPCell(new com.itextpdf.text.Paragraph(col));
                cell.setBackgroundColor(com.itextpdf.text.BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            // Popuni tabelu sa podacima
            List<RezervacijaResponseDTO> rezervacije = dohvatiSveRezervacijeDTO();
            for (RezervacijaResponseDTO r : rezervacije) {
                if (r.getSobe().isEmpty()) {
                    // Ako nema soba, dodaj praznu
                    table.addCell(r.getId().toString());
                    table.addCell(r.getIme());
                    table.addCell(r.getPrezime());
                    table.addCell(r.getEmail());
                    table.addCell(r.isPlaceno() ? "DA" : "NE");
                    table.addCell(r.getIznos().toString());
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                } else {
                    for (SobaDTO s : r.getSobe()) {
                        table.addCell(r.getId().toString());
                        table.addCell(r.getIme());
                        table.addCell(r.getPrezime());
                        table.addCell(r.getEmail());
                        table.addCell(r.isPlaceno() ? "DA" : "NE");
                        table.addCell(r.getIznos().toString());
                        table.addCell(s.getRoomNumber());
                        table.addCell(s.getRoomType() != null ? s.getRoomType().name() : "");
                        table.addCell(s.getDatumOd() != null ? s.getDatumOd().toString() : "");
                        table.addCell(s.getDatumDo() != null ? s.getDatumDo().toString() : "");
                    }
                }
            }

            doc.add(table);
            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Transactional(readOnly = true)
    public byte[] exportXml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<reservations>\n");

        for (RezervacijaResponseDTO r : dohvatiSveRezervacijeDTO()) {
            sb.append("  <reservation>\n");
            sb.append("    <id>").append(r.getId()).append("</id>\n");
            sb.append("    <ime>").append(r.getIme()).append("</ime>\n");
            sb.append("    <prezime>").append(r.getPrezime()).append("</prezime>\n");
            sb.append("    <email>").append(r.getEmail()).append("</email>\n");
            sb.append("    <placeno>").append(r.isPlaceno()).append("</placeno>\n");
            sb.append("    <sobe>\n");

            for (SobaDTO s : r.getSobe()) {
                sb.append("      <soba>\n");
                sb.append("        <roomId>").append(s.getRoomId()).append("</roomId>\n");
                sb.append("        <roomNumber>").append(s.getRoomNumber()).append("</roomNumber>\n");
                sb.append("        <roomType>").append(s.getRoomType()!=null ? s.getRoomType().name() : "").append("</roomType>\n");
                sb.append("        <datumOd>").append(s.getDatumOd() != null ? s.getDatumOd().toString() : "").append("</datumOd>\n");
                sb.append("        <datumDo>").append(s.getDatumDo() != null ? s.getDatumDo().toString() : "").append("</datumDo>\n");
                sb.append("      </soba>\n");
            }

            sb.append("    </sobe>\n");
            sb.append("  </reservation>\n");
        }

        sb.append("</reservations>");
        return sb.toString().getBytes();
    }
    @Transactional(readOnly = true)
    public byte[] exportXlsx() {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Rezervacije");

            // Header
            String[] columns = {"ID", "Ime", "Prezime", "Email", "Tip", "Naziv", "Datum od", "Datum do", "Plaćeno", "Iznos"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                headerRow.createCell(i).setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (RezervacijaResponseDTO r : dohvatiSveRezervacijeDTO()) {

                // Ako ima sobe
                for (SobaDTO s : r.getSobe()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(r.getId());
                    row.createCell(1).setCellValue(r.getIme());
                    row.createCell(2).setCellValue(r.getPrezime());
                    row.createCell(3).setCellValue(r.getEmail());
                    row.createCell(4).setCellValue("Soba");
                    row.createCell(5).setCellValue(s.getRoomNumber());
                    row.createCell(6).setCellValue(s.getDatumOd() != null ? s.getDatumOd().toString() : "");
                    row.createCell(7).setCellValue(s.getDatumDo() != null ? s.getDatumDo().toString() : "");
                    row.createCell(8).setCellValue(r.isPlaceno() ? "PAID" : "UNPAID");
                    row.createCell(9).setCellValue(r.getIznos().toString());
                }

                // Ako ima dodatni sadržaj
                for (DodatakDTO d : r.getSadrzaji()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(r.getId());
                    row.createCell(1).setCellValue(r.getIme());
                    row.createCell(2).setCellValue(r.getPrezime());
                    row.createCell(3).setCellValue(r.getEmail());
                    row.createCell(4).setCellValue("Dodatak");
                    row.createCell(5).setCellValue(d.getVrsta());
                    row.createCell(6).setCellValue(d.getDatum() != null ? d.getDatum().toString() : "");
                    row.createCell(7).setCellValue(""); // Datum do ne postoji za dodatni sadržaj
                    row.createCell(8).setCellValue(r.isPlaceno() ? "PAID" : "UNPAID");
                    row.createCell(9).setCellValue(r.getIznos().toString());
                }

                // Ako nema ni sobe ni sadržaja, dodaj prazni red samo s rezervacijom
                if (r.getSobe().isEmpty() && r.getSadrzaji().isEmpty()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(r.getId());
                    row.createCell(1).setCellValue(r.getIme());
                    row.createCell(2).setCellValue(r.getPrezime());
                    row.createCell(3).setCellValue(r.getEmail());
                    row.createCell(4).setCellValue("Nema podataka");
                    row.createCell(5).setCellValue("");
                    row.createCell(6).setCellValue("");
                    row.createCell(7).setCellValue("");
                    row.createCell(8).setCellValue(r.isPlaceno() ? "PAID" : "UNPAID");
                    row.createCell(9).setCellValue(r.getIznos().toString());
                }
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error exporting XLSX", e);
        }
    }

    @Transactional(readOnly = true)
    public List<AvailableRoomDTO> getAvailableRooms(LocalDate dateFrom, LocalDate dateTo, Integer currentRoomId) {
        try {
            // Get all rooms
            List<Soba> allRooms = sobaRepository.findAll();
            
            // Filter available rooms for the given dates
            List<Soba> availableRooms = allRooms.stream()
                .filter(room -> isRoomAvailable(room, dateFrom, dateTo))
                .collect(Collectors.toList());
            
            // Include current room if specified (so user can keep the same room)
            if (currentRoomId != null) {
                Soba currentRoom = sobaRepository.findById(currentRoomId).orElse(null);
                if (currentRoom != null && !availableRooms.contains(currentRoom)) {
                    availableRooms.add(currentRoom);
                }
            }
            
            // Convert to DTOs
            return availableRooms.stream()
                .map(room -> new AvailableRoomDTO(
                    room.getId(),
                    room.getBrojSobe(),
                    room.getVrsta(),
                    room.getCijena()
                ))
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            throw new RuntimeException("Greška prilikom dohvaćanja dostupnih soba", e);
        }
    }

    private boolean isRoomAvailable(Soba room, LocalDate dateFrom, LocalDate dateTo) {
        try {
            // Get all reservations for this room
            List<RezervirajSobu> roomReservations = rezervirajSobuRepository.findBySobaId(room.getId());
            
            // Check if room is available for the given dates
            return roomReservations.stream()
                .noneMatch(reservation -> 
                    !(dateTo.isBefore(reservation.getDatumOd()) || 
                    dateFrom.isAfter(reservation.getDatumDo()))
                );
        } catch (Exception e) {
            // If there's an error, assume room is available
            return true;
        }
    }

    @Transactional
    public boolean updateReservationRoom(Integer reservationId, int roomIndex, Integer newRoomId, 
                                        String newRoomNumber, String newRoomType) {
        try {
            // Get the reservation
            Rezervacija reservation = rezervacijaRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Rezervacija ne postoji"));
            
            // Get all room reservations for this reservation
            List<RezervirajSobu> roomReservations = new ArrayList<>(reservation.getSobe());
            
            if (roomIndex >= roomReservations.size()) {
                throw new IllegalArgumentException("Neispravan indeks sobe");
            }
            
            // Get the specific room reservation to update
            RezervirajSobu roomToUpdate = roomReservations.get(roomIndex);
            
            // Get the new room
            Soba newRoom = sobaRepository.findById(newRoomId)
                .orElseThrow(() -> new IllegalArgumentException("Nova soba ne postoji"));
            
            // Check if the new room is available for these dates
            if (!isRoomAvailable(newRoom, roomToUpdate.getDatumOd(), roomToUpdate.getDatumDo())) {
                throw new IllegalArgumentException("Soba nije dostupna za odabrane datume");
            }
            
            // Delete the old room reservation
            rezervirajSobuRepository.delete(roomToUpdate);
            
            // Create new room reservation
            RezervirajSobu newRoomReservation = new RezervirajSobu();
            RezervirajSobuId newId = new RezervirajSobuId(reservationId, newRoomId);
            newRoomReservation.setId(newId);
            newRoomReservation.setRezervacija(reservation);
            newRoomReservation.setSoba(newRoom);
            newRoomReservation.setDatumOd(roomToUpdate.getDatumOd());
            newRoomReservation.setDatumDo(roomToUpdate.getDatumDo());
            
            // Save the new room reservation
            rezervirajSobuRepository.save(newRoomReservation);
            
            // Recalculate total price
            recalculateReservationPrice(reservationId);
            
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Greška pri ažuriranju sobe: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void recalculateReservationPrice(Integer reservationId) {
        Rezervacija reservation = rezervacijaRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("Rezervacija ne postoji"));
        
        BigDecimal total = BigDecimal.ZERO;
        
        // Calculate room prices
        for (RezervirajSobu rs : reservation.getSobe()) {
            long brojNocenja = ChronoUnit.DAYS.between(rs.getDatumOd(), rs.getDatumDo());
            total = total.add(rs.getSoba().getCijena().multiply(BigDecimal.valueOf(brojNocenja)));
        }
        
        // Calculate additional content prices
        for (RezervirajSadrzaj rs : reservation.getSadrzaji()) {
            total = total.add(rs.getDodatniSadrzaj().getCijena());
        }
        
        reservation.setIznosRezervacije(total);
        rezervacijaRepository.save(reservation);
    }



}
