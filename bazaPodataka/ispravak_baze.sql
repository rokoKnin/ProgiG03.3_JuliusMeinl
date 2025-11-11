ALTER TABLE soba
ALTER COLUMN vrsta TYPE VARCHAR(50)
        USING vrsta::text,
    ALTER COLUMN status TYPE VARCHAR(20)
        USING status::text;