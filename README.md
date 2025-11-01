# Programsko inženjerstvo - Julius Meinl

# Opis projekta
Ovaj projekt je reultat timskog rada u sklopu projeknog zadatka kolegija [Programsko inženjerstvo](https://www.fer.unizg.hr/predmet/proinz) na Fakultetu elektrotehnike i računarstva Sveučilišta u Zagrebu. 

Današnji hoteli zahtijevaju učinkovit informacijski sustav. Cilj je gostima omogućiti jednostavno i brzo rezerviranje soba, ali i dodatnog sadržaja koji hotel nudi. S druge strane, hotelu su neophodne informacije o poslovanju, mogućnost komunikacije s gostima, upravljanje smještajem te optimizacija raspodjele soba.

Cilj ove aplikacije je pružiti personalizirani pristup koji objedinjuje sve funkcionalnosti hotelskog poslovanja u jedinstveni sustav. Gostima omogućuje jednostavno rezerviranje smještaja i dodatnih usluga, dok vlasnicima i osoblju pruža mogućnost upravljanja kapacitetima, praćenje statistike i donošenje poslovnih odluka na temelju podataka. Hotel dobiva dimenziju neovisnosti, smanjuju se troškovi za provizije posrednicima te se izgrađuju dugoročni odnosi s korisnicima.

> Obzirom da je ovo zadani projekt navedite i što želite/jeste novo  naučili.

> Dobro izrađen opis omogućuje vam da pokažete svoj rad drugim programerima, kao i potencijalnim poslodavcima. Ne samo da prvi dojam na stranici opisa često razlikuje dobar projekt od lošeg projekta već i predstavlja dobru praksu koju morate savladati.

# Funkcijski zahtjevi
|ID zahtjeva|Opis|Prioritet|Izvor|Kriterij prihvacanja|
|-|-|-|-|-|
|F-001|Sustav omogućuje korisnicima prijavu u sustav pomoću OAuth 2.0 standarda.|Visok|Zahtjev dionika|Korisnik se može prijaviti putem Google računa.|
|F-002|Sustav omogućuje odabir i rezervaciju dostupnih soba.|Visok|Zahtjev dionika|Korisnik može odabrati i rezervirati sobu ovisno o dostupnosti.|
|F-003|Sustav omogućuje rezervaciju dostupnog dodatnog sadržaja.|Srednji|Zahtjev dionika|Korisnik može rezervirati dodatni sadržaj samostalno ili uz rezervaciju sobe, a sustav šalje potvrdu rezervacije.|
|F-004|Sustav omogućuje plaćanje rezervacije sobe putem interneta.|Srednji|Zahtjev dionika|Korisnik može odabrati plaćanje putem online bankarstva ili pri prijavi u hotel.|
|F-005|Sustav omogućuje pregled lokacije i izgleda hotela.|Visok|Postojeći sustav|Korisnik može vidjeti lokaciju hotela putem integrirane karte i pregledavati slike hotela i soba u aplikaciji.|
|F-006|Sustav omogućuje kontaktiranje korisničke podrške.|Srednji|Zahtjev dionika|Korisnik može kontaktirati korisničku podršku putem chatbota ili drugog sličnog alata.|
|F-007|Sustav omogućuje pregled i rezervacije soba.|Visok|Zahtjev dionika|Korisnik s privilegijama može rezervirati sobu i pregledavati sve rezervacije i podatke o gostima.|
|F-008|Sustav omogućuje pregled statistike.|Visok|Zahtjev dionika|Korisnik s privilegijama može pristupiti i pregledavati sve statističke podatke hotela.|
|F-009|Sustav omogućuje izvoz statistike i podataka.|Visok|Zahtjev dionika|Korisnik s privilegijama može izvesti sve potrebne podatke i statistike hotela  u obliku pdf-a, csv-a, cml-a i xsvx-a.|
|F-010|Sustav omogućuje upravljanje dostupnošću soba i dodatnog sadržaja.|Visok|Zahtjev dionika|Korisnik s privilegijama može postaviti dostupnost soba i dodatnog sadržaja za određeni period ili neodređeno vrijeme.|
|F-011|Sustav omogućuje upravljanje korisničkim računima.|Srednji|Zahtjev dionika|Korisnik s privilegijama može upravljati korisničkim računima, uključujući brisanje računa i pregled povijesti rezervacija.|
|F-012|Sustav omogućuje ostavljanje recenzije hotela.|Srednji|Zahtjev dionika|Korisnik može ostaviti recenziju nakon završetka boravka u hotelu ili korištenja dodatne usluge.|
|F-013|Sustav omogućuje pregled osobnih podataka i profila.|Srednji|Zahtjev dionika|Korisnik može pregledati svoje podatke (ime, kontakt, rezervacije).|
|F-014|Sustav omogućuje uređivanje osobnih podataka i profila.|Srednji|Zahtjev dionika|Korisnik može urediti svoje podatke, a promjene se uspješno spremaju i prikazuju.|


# Tehnologije

**Frontend:** React (JavaScript, HTML, CSS)
**Backend:** Spring
**Baza podataka:** postgreSQL

# Instalcija

# Članovi tima
 - Roko Tojčić (voditelj): https://github.com/rokoKnin
 - Andrija Rebić (Frontend): https://github.com/AndrijaRebic
 - Gracia Hlobik (Frontend): https://github.com/GraciaHlobik
 - Luka Omrčen (Backend): https://github.com/LukaOmrcen
 - Nino Ljubas (Backend): https://github.com/nino55534
 - Leni Bosnić (Dokumentacija): https://github.com/LeniBosnic
 - Leona Križanac (Dokumentacija): https://github.com/LeonaKrizanac

# Kontribucije
>Pravila ovise o organizaciji tima i su često izdvojena u CONTRIBUTING.md



# 📝 Kodeks ponašanja [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)
Kao studenti sigurno ste upoznati s minimumom prihvatljivog ponašanja definiran u [KODEKS PONAŠANJA STUDENATA FAKULTETA ELEKTROTEHNIKE I RAČUNARSTVA SVEUČILIŠTA U ZAGREBU](https://www.fer.hr/_download/repository/Kodeks_ponasanja_studenata_FER-a_procisceni_tekst_2016%5B1%5D.pdf), te dodatnim naputcima za timski rad na predmetu [Programsko inženjerstvo](https://wwww.fer.hr).
Očekujemo da ćete poštovati [etički kodeks IEEE-a](https://www.ieee.org/about/corporate/governance/p7-8.html) koji ima važnu obrazovnu funkciju sa svrhom postavljanja najviših standarda integriteta, odgovornog ponašanja i etičkog ponašanja u profesionalnim aktivnosti. Time profesionalna zajednica programskih inženjera definira opća načela koja definiranju  moralni karakter, donošenje važnih poslovnih odluka i uspostavljanje jasnih moralnih očekivanja za sve pripadnike zajenice.

Kodeks ponašanja skup je provedivih pravila koja služe za jasnu komunikaciju očekivanja i zahtjeva za rad zajednice/tima. Njime se jasno definiraju obaveze, prava, neprihvatljiva ponašanja te  odgovarajuće posljedice (za razliku od etičkog kodeksa). U ovom repozitoriju dan je jedan od široko prihvačenih kodeks ponašanja za rad u zajednici otvorenog koda.
>### Poboljšajte funkcioniranje tima:
>* definirajte načina na koji će rad biti podijeljen među članovima grupe
>* dogovorite kako će grupa međusobno komunicirati.
>* ne gubite vrijeme na dogovore na koji će grupa rješavati sporove primjenite standarde!
>* implicitno podrazmijevamo da će svi članovi grupe slijediti kodeks ponašanja.
 
>###  Prijava problema
>Najgore što se može dogoditi je da netko šuti kad postoje problemi. Postoji nekoliko stvari koje možete učiniti kako biste najbolje riješili sukobe i probleme:
>* Obratite mi se izravno [e-pošta](mailto:vlado.sruk@fer.hr) i  učinit ćemo sve što je u našoj moći da u punom povjerenju saznamo koje korake trebamo poduzeti kako bismo riješili problem.
>* Razgovarajte s vašim asistentom jer ima najbolji uvid u dinamiku tima. Zajedno ćete saznati kako riješiti sukob i kako izbjeći daljnje utjecanje u vašem radu.
>* Ako se osjećate ugodno neposredno razgovarajte o problemu. Manje incidente trebalo bi rješavati izravno. Odvojite vrijeme i privatno razgovarajte s pogođenim članom tima te vjerujte u iskrenost.

# 📝 Licenca
Važeča (1)
[![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

Ovaj repozitorij sadrži otvoreni obrazovni sadržaji (eng. Open Educational Resources)  i licenciran je prema pravilima Creative Commons licencije koja omogućava da preuzmete djelo, podijelite ga s drugima uz 
uvjet da navođenja autora, ne upotrebljavate ga u komercijalne svrhe te dijelite pod istim uvjetima [Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License HR][cc-by-nc-sa].
>
> ### Napomena:
>
> Svi paketi distribuiraju se pod vlastitim licencama.
> Svi upotrijebleni materijali  (slike, modeli, animacije, ...) distribuiraju se pod vlastitim licencama.

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: https://creativecommons.org/licenses/by-nc/4.0/deed.hr 
[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png
[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg

Orginal [![cc0-1.0][cc0-1.0-shield]][cc0-1.0]
>
>COPYING: All the content within this repository is dedicated to the public domain under the CC0 1.0 Universal (CC0 1.0) Public Domain Dedication.
>
[![CC0-1.0][cc0-1.0-image]][cc0-1.0]

[cc0-1.0]: https://creativecommons.org/licenses/by/1.0/deed.en
[cc0-1.0-image]: https://licensebuttons.net/l/by/1.0/88x31.png
[cc0-1.0-shield]: https://img.shields.io/badge/License-CC0--1.0-lightgrey.svg

### Reference na licenciranje repozitorija
