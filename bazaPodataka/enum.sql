-- Tipovi soba
    -- enum koji cu mozda trebat kasnije
-- CREATE TYPE tip_sobe AS ENUM ('jednokrevetna', 'dvokrevetna', 'suite', 'apartman');

-- Status sobe
CREATE TYPE status_sobe AS ENUM ('slobodna', 'zauzeta', 'nedostupna');

-- Ovlasti korisnika
CREATE TYPE uloga_korisnika AS ENUM ('gost', 'zaposlenik', 'vlasnik');
