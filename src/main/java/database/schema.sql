CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE tickets (
    id_t UUID PRIMARY KEY,
    id_u UUID REFERENCES users(id),
    departure TEXT,
    destination TEXT,
    price INT,
    date_departure TIMESTAMP,
    date_destination TIMESTAMP,
    validated BOOLEAN,
    valid BOOLEAN,
    validated_by UUID,
    validated_at TIMESTAMP
);
