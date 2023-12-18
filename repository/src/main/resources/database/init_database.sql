-- Drop tables if they exist
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS gift_certificate;
DROP TABLE IF EXISTS tag_gift_certificate;

-- Create the gift_certificate table
CREATE TABLE gift_certificate (
                                  id                   SERIAL PRIMARY KEY,
                                  name                 VARCHAR NOT NULL,
                                  description          VARCHAR NOT NULL,
                                  price                DOUBLE PRECISION NOT NULL,
                                  duration             INTEGER NOT NULL,
                                  create_date          TIMESTAMP,
                                  last_update_date     TIMESTAMP
);

-- Create the tag table
CREATE TABLE tag (
                     id                  SERIAL PRIMARY KEY,
                     name                VARCHAR UNIQUE NOT NULL
);

-- Create the join table for gift_certificate and tag
CREATE TABLE tag_gift_certificate (
                                      gift_certificate_id  INTEGER NOT NULL REFERENCES gift_certificate(id) ON UPDATE CASCADE ON DELETE CASCADE,
                                      tag_id              INTEGER NOT NULL REFERENCES tag(id) ON UPDATE CASCADE ON DELETE CASCADE
);
