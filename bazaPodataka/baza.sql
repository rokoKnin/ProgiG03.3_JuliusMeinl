 CREATE TABLE drzava
    (
        drzava_id SERIAL,
        nazivDrzave VARCHAR(50) NOT NULL,
        PRIMARY KEY (drzava_id),
        UNIQUE (nazivDrzave)
    );

    CREATE TABLE mjesto
    (
        id SERIAL,
        postBr VARCHAR(20),
        nazMjesto VARCHAR(50),
        drzava_id INT NOT NULL,
        PRIMARY KEY (id),
        UNIQUE (postBr, nazMjesto),
        FOREIGN KEY (drzava_id) REFERENCES drzava(drzava_id)
    );

    CREATE TABLE korisnik
    (
        korisnik_id SERIAL,
        imeKorisnik VARCHAR(50) NOT NULL,
        prezimeKorisnik VARCHAR(50) NOT NULL,
        emailKorisnik VARCHAR(50) NOT NULL,
        telefonKorisnik VARCHAR(20) NOT NULL,
        ovlastKorisnik VARCHAR(20),
        mjesto_id INT NOT NULL,
        PRIMARY KEY (korisnik_id),
        UNIQUE (emailKorisnik),
        UNIQUE (telefonKorisnik),
        FOREIGN KEY (mjesto_id) REFERENCES mjesto(id)
    );

    CREATE TABLE rezervacija
    (
        rezervacija_id SERIAL,
        datumRezerviranja DATE NOT NULL DEFAULT CURRENT_DATE,
        placeno BOOLEAN NOT NULL DEFAULT FALSE,
        iznos_rezervacije NUMERIC(10,2) NOT NULL,
        korisnik_id INT NOT NULL,
        PRIMARY KEY (rezervacija_id),
        FOREIGN KEY (korisnik_id) REFERENCES korisnik(korisnik_id)
    );

    CREATE TABLE soba (
        soba_id SERIAL,
        broj_sobe VARCHAR(5) NOT NULL UNIQUE,
        kat INTEGER NOT NULL CHECK (kat BETWEEN 1 AND 4),
        vrsta VARCHAR(20) NOT NULL,
        kapacitet INTEGER NOT NULL CHECK (kapacitet BETWEEN 2 AND 3),
        balkon BOOLEAN NOT NULL,
        pogled_na_more BOOLEAN NOT NULL DEFAULT FALSE,
        cijena NUMERIC(10,2) NOT NULL,
        status VARCHAR(20) NOT NULL DEFAULT 'DOSTUPNA',
        PRIMARY KEY (soba_id)
    );

    CREATE TABLE dodatniSadrzaj
    (
        dodatniSadrzaj_id SERIAL,
        vrstaDodatniSadrzaj VARCHAR(20) NOT NULL,
        statusDodatniSadrzaj VARCHAR(20) NOT NULL,
        cijena_sadrzaj NUMERIC(10,2) NOT NULL,
        PRIMARY KEY (dodatniSadrzaj_id)
    );

    CREATE TABLE upit
    (
        upit_id SERIAL,
        datumUpit TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        porukaUpit TEXT NOT NULL,
        korisnik_id INT NOT NULL,
        PRIMARY KEY (upit_id),
        FOREIGN KEY (korisnik_id) REFERENCES korisnik(korisnik_id)
    );

    CREATE TABLE recenzija
    (
        recenzija_id SERIAL,
        ocjenaRecenzija INT NOT NULL CHECK ( ocjenaRecenzija BETWEEN 1 AND 10 ),
        komentarRecenzija TEXT NOT NULL,
        datumRecenzija DATE NOT NULL DEFAULT CURRENT_DATE,
        korisnik_id INT NOT NULL,
        PRIMARY KEY (recenzija_id),
        FOREIGN KEY (korisnik_id) REFERENCES korisnik(korisnik_id)
    );

    CREATE TABLE rezervirajSadrzaj
    (
        rezervacija_id INT NOT NULL,
        dodatniSadrzaj_id INT NOT NULL,
        datum_sadrzaj DATE NOT NULL,
        PRIMARY KEY (rezervacija_id, dodatniSadrzaj_id, datum_sadrzaj),
        FOREIGN KEY (rezervacija_id) REFERENCES rezervacija(rezervacija_id),
        FOREIGN KEY (dodatniSadrzaj_id) REFERENCES dodatniSadrzaj(dodatniSadrzaj_id)
    );

    CREATE TABLE rezervirajSobu
    (
        rezervacija_id INT NOT NULL,
        soba_id INT NOT NULL,
        datumOdSoba DATE NOT NULL,
        datumDoSoba DATE NOT NULL,
        PRIMARY KEY (rezervacija_id, soba_id),
        FOREIGN KEY (rezervacija_id) REFERENCES rezervacija(rezervacija_id),
        FOREIGN KEY (soba_id) REFERENCES soba(soba_id),
        CHECK (datumDoSoba > datumOdSoba)
    );