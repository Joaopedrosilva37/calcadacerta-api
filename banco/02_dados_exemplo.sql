INSERT INTO t_cc_usuario VALUES (seq_cc_usuario.NEXTVAL, 'Usuario Exemplo 01', 'usuario01@exemplo.com.br', 'CADEIRANTE', TO_DATE('2026-09-20', 'YYYY-MM-DD'));
INSERT INTO t_cc_usuario VALUES (seq_cc_usuario.NEXTVAL, 'Usuario Exemplo 02', 'usuario02@exemplo.com.br', 'IDOSO', TO_DATE('2026-09-22', 'YYYY-MM-DD'));
INSERT INTO t_cc_usuario VALUES (seq_cc_usuario.NEXTVAL, 'Usuario Exemplo 03', 'usuario03@exemplo.com.br', 'PADRAO', TO_DATE('2026-09-25', 'YYYY-MM-DD'));
INSERT INTO t_cc_usuario VALUES (seq_cc_usuario.NEXTVAL, 'Usuario Exemplo 04', 'usuario04@exemplo.com.br', 'GESTANTE', TO_DATE('2026-09-28', 'YYYY-MM-DD'));
INSERT INTO t_cc_usuario VALUES (seq_cc_usuario.NEXTVAL, 'Usuario Exemplo 05', 'usuario05@exemplo.com.br', 'CARRINHO_BEBE', TO_DATE('2026-10-01', 'YYYY-MM-DD'));

INSERT INTO t_cc_local VALUES (seq_cc_local.NEXTVAL, 'FIAP Paulista', 'EDUCACAO', 'Bela Vista', -23.564500, -46.652700);
INSERT INTO t_cc_local VALUES (seq_cc_local.NEXTVAL, 'Metro Trianon-MASP', 'ESTACAO', 'Cerqueira Cesar', -23.561400, -46.655900);
INSERT INTO t_cc_local VALUES (seq_cc_local.NEXTVAL, 'Metro Consolacao', 'ESTACAO', 'Consolacao', -23.555800, -46.662000);
INSERT INTO t_cc_local VALUES (seq_cc_local.NEXTVAL, 'Metro Brigadeiro', 'ESTACAO', 'Paraiso', -23.567100, -46.648600);
INSERT INTO t_cc_local VALUES (seq_cc_local.NEXTVAL, 'Ponto de Onibus Paulista', 'PONTO_ONIBUS', 'Bela Vista', -23.563000, -46.654000);
INSERT INTO t_cc_local VALUES (seq_cc_local.NEXTVAL, 'Praca do Bairro', 'PRACA', 'Consolacao', -23.558000, -46.659000);
INSERT INTO t_cc_local VALUES (seq_cc_local.NEXTVAL, 'UBS Bela Vista', 'SAUDE', 'Bela Vista', -23.566000, -46.650000);
INSERT INTO t_cc_local VALUES (seq_cc_local.NEXTVAL, 'Feira Livre', 'COMERCIO', 'Liberdade', -23.559000, -46.635000);

INSERT INTO t_cc_trecho VALUES (seq_cc_trecho.NEXTVAL, 1, 2, 300, 'N', 0, 0);
INSERT INTO t_cc_trecho VALUES (seq_cc_trecho.NEXTVAL, 1, 3, 700, 'N', 1, 0);
INSERT INTO t_cc_trecho VALUES (seq_cc_trecho.NEXTVAL, 1, 4, 650, 'N', 0, 0);
INSERT INTO t_cc_trecho VALUES (seq_cc_trecho.NEXTVAL, 2, 5, 200, 'N', 1, 0);
INSERT INTO t_cc_trecho VALUES (seq_cc_trecho.NEXTVAL, 2, 7, 450, 'S', 2, 1);
INSERT INTO t_cc_trecho VALUES (seq_cc_trecho.NEXTVAL, 3, 6, 350, 'N', 0, 1);
INSERT INTO t_cc_trecho VALUES (seq_cc_trecho.NEXTVAL, 6, 7, 500, 'N', 2, 3);
INSERT INTO t_cc_trecho VALUES (seq_cc_trecho.NEXTVAL, 4, 5, 400, 'S', 1, 0);
INSERT INTO t_cc_trecho VALUES (seq_cc_trecho.NEXTVAL, 4, 8, 600, 'N', 2, 2);
INSERT INTO t_cc_trecho VALUES (seq_cc_trecho.NEXTVAL, 8, 7, 300, 'N', 3, 3);
INSERT INTO t_cc_trecho VALUES (seq_cc_trecho.NEXTVAL, 5, 8, 800, 'N', 1, 1);

INSERT INTO t_cc_relato VALUES (seq_cc_relato.NEXTVAL, 1, 5, 'SEM_RAMPA', 'Escada sem rampa alternativa', 'ABERTO', TO_DATE('2026-10-02', 'YYYY-MM-DD'));
INSERT INTO t_cc_relato VALUES (seq_cc_relato.NEXTVAL, 1, 7, 'ALAGAMENTO', 'Agua acumula apos chuva forte', 'ABERTO', TO_DATE('2026-10-03', 'YYYY-MM-DD'));
INSERT INTO t_cc_relato VALUES (seq_cc_relato.NEXTVAL, 2, 7, 'BURACO', 'Buracos na calcada', 'EM_ANALISE', TO_DATE('2026-10-05', 'YYYY-MM-DD'));
INSERT INTO t_cc_relato VALUES (seq_cc_relato.NEXTVAL, 2, 10, 'ALAGAMENTO', 'Alagamento frequente no trecho', 'ABERTO', TO_DATE('2026-10-07', 'YYYY-MM-DD'));
INSERT INTO t_cc_relato VALUES (seq_cc_relato.NEXTVAL, 3, 2, 'ILUMINACAO', 'Poste apagado a noite', 'RESOLVIDO', TO_DATE('2026-10-09', 'YYYY-MM-DD'));

COMMIT;
