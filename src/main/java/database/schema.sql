CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS trips;

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
);

CREATE TABLE trips (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    departure TEXT NOT NULL,
    destination TEXT NOT NULL,
    price INT NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL
);

CREATE TABLE tickets (
    id_t UUID PRIMARY KEY,
    id_u UUID REFERENCES users(id) ON DELETE CASCADE,

    departure TEXT NOT NULL,
    destination TEXT NOT NULL,
    price INT NOT NULL,

    date_departure TIMESTAMP NOT NULL,
    date_arrival TIMESTAMP NOT NULL,

    expiration_date TIMESTAMP NOT NULL,

    state VARCHAR(30) NOT NULL DEFAULT 'CREATED',

    validated_by UUID,
    validated_at TIMESTAMP
);
