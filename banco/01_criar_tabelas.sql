CREATE SEQUENCE seq_cc_usuario START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_cc_local START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_cc_trecho START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_cc_relato START WITH 1 INCREMENT BY 1 NOCACHE;

CREATE TABLE t_cc_usuario (
    id_usuario   NUMBER(8)     NOT NULL,
    nome         VARCHAR2(100) NOT NULL,
    email        VARCHAR2(120) NOT NULL,
    perfil       VARCHAR2(20)  NOT NULL,
    dt_cadastro  DATE          NOT NULL,
    CONSTRAINT pk_cc_usuario PRIMARY KEY (id_usuario),
    CONSTRAINT uq_cc_usuario_email UNIQUE (email),
    CONSTRAINT ck_cc_usuario_perfil CHECK (perfil IN ('PADRAO','IDOSO','CADEIRANTE','GESTANTE','CARRINHO_BEBE'))
);

CREATE TABLE t_cc_local (
    id_local   NUMBER(6)     NOT NULL,
    nome       VARCHAR2(100) NOT NULL,
    tipo       VARCHAR2(20)  NOT NULL,
    bairro     VARCHAR2(80)  NOT NULL,
    latitude   NUMBER(9,6)   NOT NULL,
    longitude  NUMBER(9,6)   NOT NULL,
    CONSTRAINT pk_cc_local PRIMARY KEY (id_local),
    CONSTRAINT ck_cc_local_tipo CHECK (tipo IN ('ESTACAO','EDUCACAO','PONTO_ONIBUS','PRACA','SAUDE','COMERCIO'))
);

CREATE TABLE t_cc_trecho (
    id_trecho         NUMBER(6) NOT NULL,
    id_local_origem   NUMBER(6) NOT NULL,
    id_local_destino  NUMBER(6) NOT NULL,
    distancia_m       NUMBER(7) NOT NULL,
    tem_escada        CHAR(1)   DEFAULT 'N' NOT NULL,
    nivel_calcada     NUMBER(1) DEFAULT 0 NOT NULL,
    risco_alagamento  NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT pk_cc_trecho PRIMARY KEY (id_trecho),
    CONSTRAINT uq_cc_trecho_locais UNIQUE (id_local_origem, id_local_destino),
    CONSTRAINT ck_cc_trecho_distancia CHECK (distancia_m > 0),
    CONSTRAINT ck_cc_trecho_escada CHECK (tem_escada IN ('S','N')),
    CONSTRAINT ck_cc_trecho_calcada CHECK (nivel_calcada BETWEEN 0 AND 3),
    CONSTRAINT ck_cc_trecho_alagamento CHECK (risco_alagamento BETWEEN 0 AND 3),
    CONSTRAINT ck_cc_trecho_locais CHECK (id_local_origem <> id_local_destino)
);

CREATE TABLE t_cc_relato (
    id_relato   NUMBER(8)     NOT NULL,
    id_usuario  NUMBER(8)     NOT NULL,
    id_trecho   NUMBER(6)     NOT NULL,
    tipo        VARCHAR2(20)  NOT NULL,
    descricao   VARCHAR2(200) NOT NULL,
    status      VARCHAR2(20)  DEFAULT 'ABERTO' NOT NULL,
    dt_relato   DATE          NOT NULL,
    CONSTRAINT pk_cc_relato PRIMARY KEY (id_relato),
    CONSTRAINT ck_cc_relato_tipo CHECK (tipo IN ('BURACO','SEM_RAMPA','ALAGAMENTO','ILUMINACAO','OBSTRUCAO')),
    CONSTRAINT ck_cc_relato_status CHECK (status IN ('ABERTO','EM_ANALISE','RESOLVIDO'))
);

ALTER TABLE t_cc_trecho ADD CONSTRAINT fk_cc_trecho_origem
    FOREIGN KEY (id_local_origem) REFERENCES t_cc_local (id_local);

ALTER TABLE t_cc_trecho ADD CONSTRAINT fk_cc_trecho_destino
    FOREIGN KEY (id_local_destino) REFERENCES t_cc_local (id_local);

ALTER TABLE t_cc_relato ADD CONSTRAINT fk_cc_relato_usuario
    FOREIGN KEY (id_usuario) REFERENCES t_cc_usuario (id_usuario);

ALTER TABLE t_cc_relato ADD CONSTRAINT fk_cc_relato_trecho
    FOREIGN KEY (id_trecho) REFERENCES t_cc_trecho (id_trecho);
