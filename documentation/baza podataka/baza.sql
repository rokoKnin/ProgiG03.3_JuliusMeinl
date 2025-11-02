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
  ovlastKorisnik VARCHAR(20) NOT NULL,
  postBr INT NOT NULL,
  PRIMARY KEY (korisnik_id),
  FOREIGN KEY (postBr) REFERENCES Mjesto(postBr),
  UNIQUE (emailKorisnik),
  UNIQUE (telefonKorisnik)
);

CREATE TABLE Soba
(
  broj_sobe VARCHAR(5) NOT NULL,
  vrsta VARCHAR(20) NOT NULL,
  cijena INT NOT NULL,
  status VARCHAR(20) NOT NULL,
  PRIMARY KEY (broj_sobe),
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

CREATE TABLE Placanje
(
  placanje_id VARCHAR(10) NOT NULL,
  datumPlacanja DATE NOT NULL,
  nacinPlacanja VARCHAR(20) NOT NULL,
  rezervacija_id INT NOT NULL,
  PRIMARY KEY (placanje_id),
  FOREIGN KEY (rezervacija_id) REFERENCES Rezervacija(rezervacija_id)
);

CREATE TABLE DodatniSadrzaj
(
  dodatniSadrzaj_id VARCHAR(10) NOT NULL,
  vrstaDodatniSadrzaj VARCHAR(20) NOT NULL,
  cijenaDodatniSadrzaj INT NOT NULL,
  statusDodatniSadrzaj VARCHAR(20) NOT NULL,
  kapacitetDodatniSadrzaj INT NOT NULL,
  PRIMARY KEY (dodatniSadrzaj_id)
);

CREATE TABLE Upit
(
  upit_id VARCHAR(10) NOT NULL,
  datumUpit DATE NOT NULL,
  porukaUpit VARCHAR(500) NOT NULL,
  korisnik_id INT NOT NULL,
  zaposlenik_id VARCHAR(10) NOT NULL,
  PRIMARY KEY (upit_id),
  FOREIGN KEY (korisnik_id) REFERENCES Korisnik(korisnik_id),
  FOREIGN KEY (zaposlenik_id) REFERENCES Korisnik(korisnik_id)
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
  FOREIGN KEY (dodatniSadrzaj_id) REFERENCES DodatniSadrzaj(dodatniSadrzaj_id)
);

CREATE TABLE RezervirajSobu
(
  datumOdSoba DATE NOT NULL,
  datumDoSoba DATE NOT NULL,
  rezervacija_id INT NOT NULL,
  broj_sobe VARCHAR(5) NOT NULL,
  PRIMARY KEY (rezervacija_id, broj_sobe),
  FOREIGN KEY (rezervacija_id) REFERENCES Rezervacija(rezervacija_id),
  FOREIGN KEY (broj_sobe) REFERENCES Soba(broj_sobe)
);