CREATE TABLE Drzava
(
    drzava_id SERIAL,
    nazivDrzave VARCHAR(50) NOT NULL,
    PRIMARY KEY (drzava_id),
    UNIQUE (nazivDrzave)
);

CREATE TABLE Mjesto
(
    postBr VARCHAR(20),
    nazMjesto VARCHAR(50) NOT NULL,
    drzava_id INT NOT NULL,
    PRIMARY KEY (postBr),
    FOREIGN KEY (drzava_id) REFERENCES Drzava(drzava_id)
);

CREATE TABLE Korisnik
(
    korisnik_id SERIAL,
    imeKorisnik VARCHAR(50) NOT NULL,
    prezimeKorisnik VARCHAR(50) NOT NULL,
    emailKorisnik VARCHAR(50) NOT NULL,
    telefonKorisnik VARCHAR(20) NOT NULL,
    ovlastKorisnik uloga_korisnika NOT NULL,
    postBr VARCHAR(20) NOT NULL,
    PRIMARY KEY (korisnik_id),
    UNIQUE (emailKorisnik),
    UNIQUE (telefonKorisnik),
    FOREIGN KEY (postBr) REFERENCES Mjesto(postBr)
);

CREATE TABLE Soba
(
    soba_id SERIAL,
    broj_sobe VARCHAR(5) NOT NULL,
    vrsta VARCHAR(20) NOT NULL,
    cijena NUMERIC(10,2) NOT NULL,
    status status_sobe NOT NULL,
    PRIMARY KEY (soba_id),
    UNIQUE (broj_sobe)
);

CREATE TABLE Rezervacija
(
    rezervacija_id SERIAL,
    datumRezerviranja DATE NOT NULL DEFAULT CURRENT_DATE,
    placeno BOOLEAN NOT NULL DEFAULT FALSE,
    korisnik_id INT NOT NULL,
    iznos_rezervacije NUMERIC(10,2) NOT NULL,
    PRIMARY KEY (rezervacija_id),
    FOREIGN KEY (korisnik_id) REFERENCES Korisnik(korisnik_id)
);

CREATE TABLE Placanje
(
    placanje_id SERIAL,
    datumPlacanja DATE NOT NULL,
    nacinPlacanja VARCHAR(20) NOT NULL,
    rezervacija_id INT NOT NULL,
    PRIMARY KEY (placanje_id),
    FOREIGN KEY (rezervacija_id) REFERENCES Rezervacija(rezervacija_id)
);

CREATE TABLE DodatniSadržaj
(
    dodatniSadrzaj_id SERIAL,
    vrstaDodatniSadrzaj VARCHAR(20) NOT NULL,
    statusDodatniSadrzaj VARCHAR(20) NOT NULL,
    kapacitetDodatniSadrzaj INT NOT NULL,
    PRIMARY KEY (dodatniSadrzaj_id)
);

CREATE TABLE Upit
(
    upit_id SERIAL,
    datumUpit TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    porukaUpit TEXT NOT NULL,
    korisnik_id INT NOT NULL,
    PRIMARY KEY (upit_id),
    FOREIGN KEY (korisnik_id) REFERENCES Korisnik(korisnik_id)
);

CREATE TABLE Recenzija
(
    recenzija_id SERIAL,
    ocjenaRecenzija INT NOT NULL CHECK ( ocjenaRecenzija BETWEEN 1 AND 10 ),
    komentarRecenzija TEXT NOT NULL,
    datumRecenzija DATE NOT NULL DEFAULT CURRENT_DATE,
    korisnik_id INT NOT NULL,
    PRIMARY KEY (recenzija_id),
    FOREIGN KEY (korisnik_id) REFERENCES Korisnik(korisnik_id)
);

CREATE TABLE RezervirajSadrzaj
(
    datumOdSadrzaj DATE NOT NULL,
    datumDoSadrzaj DATE NOT NULL,
    rezervacija_id INT NOT NULL,
    dodatniSadrzaj_id INT NOT NULL,
    cijena_sadrzaj INT NOT NULL,
    PRIMARY KEY (rezervacija_id, dodatniSadrzaj_id),
    FOREIGN KEY (rezervacija_id) REFERENCES Rezervacija(rezervacija_id),
    FOREIGN KEY (dodatniSadrzaj_id) REFERENCES DodatniSadržaj(dodatniSadrzaj_id),
    CHECK (datumDoSadrzaj > datumOdSadrzaj)
);

CREATE TABLE RezervirajSobu
(
    datumOdSoba DATE NOT NULL,
    datumDoSoba DATE NOT NULL,
    rezervacija_id INT NOT NULL,
    soba_id INT NOT NULL,
    PRIMARY KEY (rezervacija_id, soba_id),
    FOREIGN KEY (rezervacija_id) REFERENCES Rezervacija(rezervacija_id),
    FOREIGN KEY (soba_id) REFERENCES Soba(soba_id),
    CHECK (datumDoSoba > datumOdSoba)
);