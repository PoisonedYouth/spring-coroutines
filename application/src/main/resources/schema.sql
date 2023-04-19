CREATE TABLE IF NOT EXISTS Post (
    id UUID PRIMARY KEY,
    title VARCHAR(255),
    content VARCHAR(255),
    author VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
    );