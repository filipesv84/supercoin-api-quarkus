-- 1. Limpeza (Ordem correta para evitar erro de chave estrangeira)
DELETE FROM transacao;
DELETE FROM conta;
DELETE FROM cliente;
DELETE FROM funcionario;

-- 2. Gerente Mestre da CAIXA (O único que pode criar Atendentes)
INSERT INTO funcionario (id, nome, email, senha, cargo, matricula)
VALUES (1, 'Felipe Gerente', 'felipe@caixa.com', 'admin123', 'GERENTE', 'G001');

-- 3. Clientes da CAIXA (Domínios externos como @gmail, @hotmail, etc)
INSERT INTO cliente (id, nome, cpf, email, senha) VALUES (1, 'Felipe Oliveira', '11122233301', 'felipe@gmail.com', '123');
INSERT INTO cliente (id, nome, cpf, email, senha) VALUES (2, 'Ana Silva', '22233344402', 'ana@gmail.com', '123');
INSERT INTO cliente (id, nome, cpf, email, senha) VALUES (3, 'Carlos Souza', '33344455503', 'carlos@gmail.com', '123');
INSERT INTO cliente (id, nome, cpf, email, senha) VALUES (4, 'Beatriz Santos', '44455566604', 'beatriz@gmail.com', '123');
INSERT INTO cliente (id, nome, cpf, email, senha) VALUES (5, 'Ricardo Lima', '55566677705', 'ricardo@gmail.com', '123');

-- 4. Contas da CAIXA (Vinculadas aos IDs de cliente acima)
-- Coloquei a conta 2 como DIGITAL (valor 2) para testarmos os bloqueios
INSERT INTO conta (id, numero, saldo, cliente_id, tipo) VALUES (1, '104001', 1000.0, 1, 0); -- CORRENTE
INSERT INTO conta (id, numero, saldo, cliente_id, tipo) VALUES (2, '104002', 1000.0, 2, 2); -- DIGITAL
INSERT INTO conta (id, numero, saldo, cliente_id, tipo) VALUES (3, '104003', 1000.0, 3, 0);
INSERT INTO conta (id, numero, saldo, cliente_id, tipo) VALUES (4, '104004', 1000.0, 4, 1); -- POUPANCA
INSERT INTO conta (id, numero, saldo, cliente_id, tipo) VALUES (5, '104005', 1000.0, 5, 0);


-- 5. Sincroniza os contadores de ID do Hibernate
ALTER SEQUENCE IF EXISTS hibernate_sequence RESTART WITH 10;
ALTER SEQUENCE IF EXISTS Cliente_SEQ RESTART WITH 10;
ALTER SEQUENCE IF EXISTS Conta_SEQ RESTART WITH 10;
ALTER SEQUENCE IF EXISTS Funcionario_SEQ RESTART WITH 10;
