CREATE TABLE turma_participante (
    codigo BIGSERIAL PRIMARY KEY,
    turma_id BIGINT NOT NULL,
    funcionario_id BIGINT NOT NULL,

    CONSTRAINT fk_tp_turma
        FOREIGN KEY (turma_id) REFERENCES turma(codigo),

    CONSTRAINT fk_tp_funcionario
        FOREIGN KEY (funcionario_id) REFERENCES funcionario(codigo),

    CONSTRAINT uk_turma_funcionario
        UNIQUE (turma_id, funcionario_id)
);