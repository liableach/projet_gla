CREATE TABLE users (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL
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
