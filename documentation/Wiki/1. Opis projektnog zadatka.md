# Uvod
Današnji hoteli zahtijevaju učinkovit informacijski sustav. Cilj je gostima omogućiti jednostavno i brzo rezerviranje soba, ali i dodatnog sadržaja koji hotel nudi. S druge strane, hotelu su neophodne informacije o poslovanju, mogućnost komunikacije s gostima, upravljanje smještajem te optimizacija raspodjele soba.

Digitalizacija hotelskog poslovanja ključna je za opstanak na modernom tržištu. Popularni načini rezervacija preko posrednika stavljaju naglasak na količinu različitih opcija i nemoguće je ostvariti izravan odnos s gostom i prikazati cjelokupnu ponudu.

Cilj ove aplikacije je pružiti personalizirani pristup koji objedinjuje sve funkcionalnosti hotelskog poslovanja u jedinstveni sustav. Gostima omogućuje jednostavno rezerviranje smještaja i dodatnih usluga, dok vlasnicima i osoblju pruža mogućnost upravljanja kapacitetima, praćenje statistike i donošenje poslovnih odluka na temelju podataka. Hotel dobiva dimenziju neovisnosti, smanjuju se troškovi za  provizije posrednicima te se izgrađuju dugoročni odnosi s korisnicima.

# Slične stranice

[***Villa Dubrovnik***](https://www.villa-dubrovnik.hr/hr/)

<img width="1710" height="663" alt="villa dubrovnik" src="https://github.com/user-attachments/assets/6aa5298d-439c-47c3-bc72-2a11d01dbcfd" />

<br>
Web stranica hotela Villa Dubrovnik nudi mogućnost rezervacije i biranja konkretne vrste sobe. Na primjer "Superior Sea View Room" ili "Beleca Suite". Nema prostora za iskazivanje preferencija gosta, odnosno kakvu sobu želi i što mu je bitno da soba ima pa da na osnovu toga sustav odluči koja soba je optimalna za njega te skuplja informacije na osnovu kojih bi hotel mogao promijeniti ponudu s obzirom na potražnju. Stranica ima bogatu galeriju. Fokusira se na ponudu Dubrovnika i njegovu povijest, ali pruža i informacije o vlastitim uslugama. Ne postoji izravan način komunikacije ni FAQ. Za stupanje u kontakt, gost mora poslati mail ili zvati na broj.

<br>
<br>

[***Bluesun Hotel Elaphusa***](https://www.bluesunhotels.com/hotel-elaphusa-bol-brac?utm_term=&utm_campaign=PMax+-+HR+-+CRO+MARKETS&utm_source=adwords&utm_medium=ppc&hsa_acc=5292588619&hsa_cam=22299942702&hsa_grp=&hsa_ad=&hsa_src=x&hsa_tgt=&hsa_kw=&hsa_mt=&hsa_net=adwords&hsa_ver=3&gad_source=1&gad_campaignid=22310177251&gbraid=0AAAAApFp-PgKmSjY51t7FMCosVv6fJRZN&gclid=Cj0KCQjwjL3HBhCgARIsAPUg7a5TrgDBg3UWajnjDOQvpFKWkGnJPsXh-AGwKzjvIv6CiFdGjxak2_0aAkY9EALw_wcB)

<img width="1710" height="663" alt="bluesun" src="https://github.com/user-attachments/assets/675bc633-02b6-4ef6-880f-a6aa56914808" />

Sličan princip rezervacija kao i hotel Villa Dubrovnik. Ne postoji opcija filtriranja smještaja stoga biranje željene sobe zahtijeva vrijeme i proučavanje svih ponuđenih vrsta soba, kojih je nemali broj. Prikazuje dio s posebnom ponudom gdje gosti mogu iskoristit popuste na razne aktivnosti, večere, smještaj... Postoji dio s FAQ, kao i dio gdje se izravno može prijaviti na natječaj za rad ili poslati otvorena molba za rad u hotelu.


# Korisnici i koristi

Potencijalna korist aplikacije je donošenje promjena u trenutni princip hotelskog poslovanja. Ovaj projekt integrira sve bitne funkcionalnosti u jedinstvenu aplikaciju. Hoteli mogu bolje predstaviti što nude potencijalnim gostima u vlastitoj aplikaciji nasuprot trenutnim popularnim rješenjima gdje su samo jedan od više hotela u ponudi te je količina informacija o njima ograničena. Također, aplikacija daje novu dimenziju poslovanju jer proučavanjem statističkih podataka koje skuplja o gostima i njihovim preferencijama, vlasnici mogu donositi bolje odluke za budućnost poslovanja hotela.

Korisnici koji bi mogli biti zainteresirani za aplikaciju
- Gosti <br>
  Preferiraju aplikaciju koja je fokusirana na jedan hotel i pruža sve potrebne informacije kako bi mogli biti sigurni kakav smještaj su odabrali. Žele mogućnost biranja smještaja i dodatnog sadržaja prema sklonostima. U slučaju problema mogu kontaktirati podršku pomoću chatbota ili sličnog alata.

- Vlasnici <br>
  Ne žele plaćati proviziju posrednicima u rezervaciji smještaja. Trebaju aplikaciju koja je jedinstvena za njihov hotel i pruža uvid u statistiku zauzeća, sezonske trendove i strukturu gostiju prema zemlji i/ili gradu dolaska. Identifikacija najtraženijih dodatnih usluga i mogućnost prilagodbe ponude. Mogućnost prilagodbe sustava specifičnim poslovnim potrebama i strategijama hotela.

# Opseg projektnog zadatka

Projekt obuhvaća razvoj web aplikacije koja spaja sve ključne funkcionalnosti hotelskog poslovanja u jedinstveni informacijski sustav. Sustav mora omogućiti učinkovito upravljanje rezervacijama, smještajem, korisnicima i statistikom poslovanja, uz podršku za višekorisnički rad i responzivan dizajn prilagođen različitim uređajima.

Prijava i registracija korisnika OAuth2 servisom. Upravljanje korisničkim računima te različite razine pristupa za goste, osoblje, administratore i vlasnike hotela.

Odabir i rezervacija sobe prema preferencijama i trenutnoj dostupnosti. Rezervacija dodatnog sadržaja u ponudi hotela (*nije nužna rezervacija sobe da bi se koristio dodatni sadržaj hotela*). Sam proces dodjelivanja konkretne sobe mora biti optimiziran tako da broj praznih dana u hotelu bude minimalan. Potrebna je mogućnost plaćanja internet bankarstvom i potvrda rezervacije.

Aplikacija pruža integraciju s Google Maps servisom radi prikaza točne lokacije hotela i olakšanog planiranja dolaska
gostiju. Responzivan dizajn prilagođen različitim uređajima te pregled izgleda hotela i soba putem prikaza slika i galerija.

Djelatnici hotela s potrebnim privilegijama mogu pregledavati i unositi rezervacije u ime gostiju. Upravljanje dostupnošću soba i dodatnih sadržaja trenutno ili trajno. Automatsko ažuriranje dostupnosti u stvarnom vremenu.

Pregled statistike o zauzeću smještaja, strukturi gostiju i korištenju dodatnih usluga te izvoz podataka u pdf, xml i xlsx formatu, kao i izvoz podataka statistike u pdf ili xlsx formatu. Pristup analitičkim podacima za vlasnika radi donošenja poslovnih odluka.

Korisnici imaju mogućnost kontaktiranja korisničke podrške putem integriranog chat sustava ili chatbot alata kao i mogućnost ostavljanja recenzije nakon boravka u hotelu.


# Moguće nadogradnje i prilagodbe

Aplikacija je osmišljena kao platforma za digitalno upravljanje hotelskim poslovanjem koja se može unaprjeđivati, nadograđivati i prilagođavati potražnji i potrebama korisnika, novim tehnologijama ili drugim promjenama unutar hotela ili izvan hotela koja zahtijevaju i određenu promjenu aplikacije.

- Pružanje prikaza aplikacije na više jezika kako bi pridobili više gostiju.
- Sustav nagrada za stare goste. Na primjer princip posebne ponude kao hotel Bluesun hotel Elaphusa koji nudi dio s posebnim pogodnostima i akcijama. Slična ideja bi se mogla primjeniti kao sustav nagrade za vjerne goste kako bi ih potaknuli da nastave birati hotel iznova.
- Integriranje dodatnih funkcionalnosti u aplikaciju poput digitalnog ključa za sobu, parking ili slično.
- Mogućnost izravne komunikacije s osobljem tokom radnog vremena funkcijama aplikacije za narudžbu hrane, pića, higijenskih potrepština te mogućnost prijave kvara ili za dodatna pitanja i informacije.

