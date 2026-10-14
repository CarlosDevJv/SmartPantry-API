CREATE TABLE receita(
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id VARCHAR(200) NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    preparo VARCHAR(1500) NOT NULL,
    dataCriacao TIMESTAMP with time zone DEFAULT NULL,
    CONSTRAINT fk_userId FOREIGN KEY(user_id) REFERENCES tb_users(id) ON DELETE CASCADE
);

CREATE TABLE item_estoque(
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id VARCHAR(200) NOT NULL,
    ingrediente_id INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    dataValidade TIMESTAMP with time zone DEFAULT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_userId FOREIGN KEY(user_id) REFERENCES tb_users(id) ON DELETE CASCADE,
    CONSTRAINT fk_ingredienteId FOREIGN KEY(ingrediente_id) REFERENCES ingrediente(id) ON DELETE CASCADE
);


