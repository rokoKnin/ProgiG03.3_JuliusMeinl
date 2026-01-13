INSERT INTO mjesto (postBr, nazMjesto, drzava_id) VALUES
                                                      ('10000', 'Zagreb', 61),       -- 61 je Croatia u tablici drzava
                                                      ('21000', 'Split', 61),
                                                      ('51000', 'Rijeka', 61),
                                                      ('31000', 'Osijek', 61),
                                                      ('20000', 'Dubrovnik', 61);
INSERT INTO korisnik (imeKorisnik, prezimeKorisnik, emailKorisnik, telefonKorisnik, ovlastKorisnik, mjesto_id) VALUES
                                                                                                                   ('Ivan', 'Ivić', 'ivan@example.com', '0911111111', 'VLASNIK', 1),
                                                                                                                   ('Ana', 'Anić', 'ana@example.com', '0912222222', 'REGISTRIRAN', 2),
                                                                                                                   ('Marko', 'Marković', 'marko@example.com', '0913333333', 'ZAPOSLENIK', 3),
                                                                                                                   ('Petra', 'Petrović', 'petra@example.com', '0914444444', 'REGISTRIRAN', 4),
                                                                                                                   ('Luka', 'Lukić', 'luka@example.com', '0915555555', 'REGISTRIRAN', 5);


INSERT INTO rezervacija (datumRezerviranja, placeno, iznos_rezervacije, korisnik_id) VALUES
                                                                                         ('2026-01-10', true, 240.00, 6),  -- Ivan
                                                                                         ('2026-01-11', false, 210.00, 7), -- Ana
                                                                                         ('2026-01-12', true, 300.00, 8),  -- Marko
                                                                                         ('2026-01-13', false, 150.00, 9), -- Petra
                                                                                         ('2026-01-14', true, 120.00, 10); -- Luka
INSERT INTO rezervirajSobu (rezervacija_id, soba_id, datumOdSoba, datumDoSoba) VALUES
                                                                                   (1, 101, '2026-01-15', '2026-01-17'), -- Ivan
                                                                                   (2, 102, '2026-01-16', '2026-01-18'), -- Ana
                                                                                   (3, 103, '2026-01-17', '2026-01-19'), -- Marko
                                                                                   (4, 104, '2026-01-18', '2026-01-20'), -- Petra
                                                                                   (5, 105, '2026-01-19', '2026-01-21'); -- Luka