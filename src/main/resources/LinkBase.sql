CREATE TABLE links
(
    uuid TEXT,
    longlink TEXT,
    shortlink TEXT,
    transition INTEGER,
    atransition INTEGER,
    ttl DATE,
    PRIMARY KEY (uuid, shortLink)
);