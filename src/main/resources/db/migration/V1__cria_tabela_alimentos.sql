CREATE TABLE alimentos
(
    id        bigint(20) NOT NULL AUTO_INCREMENT,
    tipo      varchar(255) NOT NULL,
    valor     decimal(19,2) NOT NULL,
    nome      varchar(255) NOT NULL,
    status    boolean NOT NULL,
    PRIMARY KEY (id)
);