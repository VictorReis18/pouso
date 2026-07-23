-- Solicitações de adoção de teste (aguardando resposta do doador)
INSERT INTO adocao (data_inicio, cpf_adotante, pet_nome, pet_dono, data_fim, data_solicitacao, status, is_permanente)
VALUES (CURRENT_DATE, '22222222222', 'Thor', '33333333642', NULL, CURRENT_DATE, 'EM_ANDAMENTO', true);

INSERT INTO adocao (data_inicio, cpf_adotante, pet_nome, pet_dono, data_fim, data_solicitacao, status, is_permanente)
VALUES (CURRENT_DATE, '33333333642', 'Rex', '22222222222', NULL, CURRENT_DATE, 'EM_ANDAMENTO', true);
