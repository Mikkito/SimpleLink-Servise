CREATE TABLE IF NOT EXISTS links
(
    uuid TEXT,
    longlink TEXT,
    shortlink TEXT,
    transition INTEGER,
    atransition INTEGER,
    ttl DATE,
    PRIMARY KEY (uuid, shortLink)
);