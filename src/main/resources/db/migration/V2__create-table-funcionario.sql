CREATE TABLE funcionario (
    codigo BIGSERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    nascimento DATE NOT NULL,
    cargo VARCHAR(200) NOT NULL,
    admissao DATE NOT NULL,
    status BIT NOT NULL
);