-- Mais dados de teste para a tela de Minhas Adoções (solicitações realizadas/recebidas)

-- Doador/adotante 3
INSERT INTO pessoa (cpf, nome, email, senha)
VALUES ('44444444444', 'Ana Adotante', 'ana@pouso.com', 'senha123');
INSERT INTO usuario (cpf, username, bio, genero, telefone)
VALUES ('44444444444', 'anaadotante', 'Apaixonada por gatos e por ajudar outros bichos', 'F', '51999990003');

-- Endereços (Ana fica sem endereço de propósito, pra testar o fallback "não informado")
INSERT INTO endereco (usuario_cpf, cep, rua, numero, complemento, bairro, cidade, uf)
VALUES ('22222222222', '90010000', 'Rua das Flores', '123', NULL, 'Centro', 'Porto Alegre', 'RS');
INSERT INTO endereco (usuario_cpf, cep, rua, numero, complemento, bairro, cidade, uf)
VALUES ('33333333642', '90020000', 'Avenida Brasil', '456', 'Apto 302', 'Floresta', 'Porto Alegre', 'RS');

-- Pet de Ana (sem foto, pra testar o placeholder de imagem)
INSERT INTO pet (nome, cpf_dono, bio, sexo, tipo_pet, data_nasc, data_cadastro, porte, is_permanente, is_castrado, status_aprovacao)
VALUES ('Luna', '44444444444', 'Gata independente, adora dormir no sol', 'F', 5, '2021-03-12', CURRENT_DATE, 'P', true, true, 'PENDENTE');

-- Mais solicitações de adoção (status EM_ANDAMENTO = aguardando resposta do responsável)
INSERT INTO adocao (data_inicio, cpf_adotante, pet_nome, pet_dono, data_fim, data_solicitacao, status, is_permanente)
VALUES (CURRENT_DATE, '44444444444', 'Bob', '33333333642', NULL, CURRENT_DATE, 'EM_ANDAMENTO', true);

INSERT INTO adocao (data_inicio, cpf_adotante, pet_nome, pet_dono, data_fim, data_solicitacao, status, is_permanente)
VALUES (CURRENT_DATE, '44444444444', 'Mel', '33333333642', NULL, CURRENT_DATE, 'EM_ANDAMENTO', true);

-- Dois adotantes disputando o mesmo pet (Mel)
INSERT INTO adocao (data_inicio, cpf_adotante, pet_nome, pet_dono, data_fim, data_solicitacao, status, is_permanente)
VALUES (CURRENT_DATE, '22222222222', 'Mel', '33333333642', NULL, CURRENT_DATE, 'EM_ANDAMENTO', true);

INSERT INTO adocao (data_inicio, cpf_adotante, pet_nome, pet_dono, data_fim, data_solicitacao, status, is_permanente)
VALUES (CURRENT_DATE, '22222222222', 'Luna', '44444444444', NULL, CURRENT_DATE, 'EM_ANDAMENTO', true);

INSERT INTO adocao (data_inicio, cpf_adotante, pet_nome, pet_dono, data_fim, data_solicitacao, status, is_permanente)
VALUES (CURRENT_DATE, '33333333642', 'Luna', '44444444444', NULL, CURRENT_DATE, 'EM_ANDAMENTO', true);
