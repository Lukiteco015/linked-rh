CREATE TABLE curso (
    codigo BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(4000) NOT NULL,
    duracao INT NOT NULL
);
