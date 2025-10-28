CREATE TABLE Drzava
(
    drzava_id VARCHAR(10) NOT NULL,
    nazivDrzave VARCHAR(50) NOT NULL,
    PRIMARY KEY (drzava_id),
    UNIQUE (nazivDrzave)
);

CREATE TABLE Mjesto
(
    postBr VARCHAR(20) NOT NULL,
    nazMjesto VARCHAR(50) NOT NULL,
    drzava_id INT NOT NULL,
    PRIMARY KEY (postBr),
    FOREIGN KEY (drzava_id) REFERENCES Drzava(drzava_id)
);

CREATE TABLE Korisnik
(
    korisnik_id VARCHAR(10) NOT NULL,
    imeKorisnik VARCHAR(50) NOT NULL,
    prezimeKorisnik VARCHAR(50) NOT NULL,
    emailKorisnik VARCHAR(50) NOT NULL,
    telefonKorisnik VARCHAR(20) NOT NULL,
    postBr INT NOT NULL,
    PRIMARY KEY (korisnik_id),
    FOREIGN KEY (postBr) REFERENCES Mjesto(postBr),
    UNIQUE (emailKorisnik),
    UNIQUE (telefonKorisnik)
);

CREATE TABLE Soba
(
    soba_id VARCHAR(10) NOT NULL,
    broj_sobe VARCHAR(5) NOT NULL,
    vrsta VARCHAR(20) NOT NULL,
    cijena INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    PRIMARY KEY (soba_id),
    UNIQUE (broj_sobe)
);

CREATE TABLE Rezervacija
(
    rezervacija_id VARCHAR(10) NOT NULL,
    datumRezerviranja DATE NOT NULL,
    placeno? VARCHAR(20) NOT NULL,
    korisnik_id INT NOT NULL,
    PRIMARY KEY (rezervacija_id),
    FOREIGN KEY (korisnik_id) REFERENCES Korisnik(korisnik_id)
);

CREATE TABLE Plaćanje
(
    placanje_id VARCHAR(10) NOT NULL,
    datumPlacanja DATE NOT NULL,
    nacinPlacanja VARCHAR(20) NOT NULL,
    rezervacija_id INT NOT NULL,
    PRIMARY KEY (placanje_id),
    FOREIGN KEY (rezervacija_id) REFERENCES Rezervacija(rezervacija_id)
);

CREATE TABLE DodatniSadržaj
(
    dodatniSadrzaj_id VARCHAR(10) NOT NULL,
    vrstaDodatniSadrzaj VARCHAR(20) NOT NULL,
    statusDodatniSadrzaj VARCHAR(20) NOT NULL,
    kapacitetDodatniSadrzaj INT NOT NULL,
    PRIMARY KEY (dodatniSadrzaj_id)
);

CREATE TABLE Zaposlenik
(
    zaposlenik_id VARCHAR(10) NOT NULL,
    imeZaposlenik VARCHAR(50) NOT NULL,
    prezimeZaposlenik VARCHAR(50) NOT NULL,
    emailZaposlenik VARCHAR(50) NOT NULL,
    telefonZaposlenik VARCHAR(20) NOT NULL,
    pozicijaZaposlenik VARCHAR(20) NOT NULL,
    PRIMARY KEY (zaposlenik_id),
    UNIQUE (emailZaposlenik),
    UNIQUE (telefonZaposlenik)
);

CREATE TABLE Upit
(
    upit_id VARCHAR(10) NOT NULL,
    datumUpit DATE NOT NULL,
    porukaUpit VARCHAR(500) NOT NULL,
    korisnik_id INT NOT NULL,
    zaposlenik_id INT NOT NULL,
    PRIMARY KEY (upit_id),
    FOREIGN KEY (korisnik_id) REFERENCES Korisnik(korisnik_id),
    FOREIGN KEY (zaposlenik_id) REFERENCES Zaposlenik(zaposlenik_id)
);

CREATE TABLE Recenzija
(
    recenzija_id VARCHAR(10) NOT NULL,
    ocjenaRecenzija INT NOT NULL,
    komentarRecenzija VARCHAR(500) NOT NULL,
    datumRecenzija DATE NOT NULL,
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
    PRIMARY KEY (rezervacija_id, dodatniSadrzaj_id),
    FOREIGN KEY (rezervacija_id) REFERENCES Rezervacija(rezervacija_id),
    FOREIGN KEY (dodatniSadrzaj_id) REFERENCES DodatniSadržaj(dodatniSadrzaj_id)
);

CREATE TABLE RezervirajSobu
(
    datumOdSoba DATE NOT NULL,
    datumDoSoba DATE NOT NULL,
    rezervacija_id INT NOT NULL,
    soba_id INT NOT NULL,
    PRIMARY KEY (rezervacija_id, soba_id),
    FOREIGN KEY (rezervacija_id) REFERENCES Rezervacija(rezervacija_id),
    FOREIGN KEY (soba_id) REFERENCES Soba(soba_id)
);