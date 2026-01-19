CREATE TABLE turma (
    codigo BIGSERIAL PRIMARY KEY,
    inicio DATE NOT NULL,
    fim DATE NOT NULL,
    local VARCHAR(200),
    curso_id BIGINT NOT NULL,

    CONSTRAINT fk_turma_curso
        FOREIGN KEY (curso_id) REFERENCES curso(codigo)
);