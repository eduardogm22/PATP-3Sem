set @idusuario = 1;

insert into categorias 
values 
(default, 'Notebook'),
(default, 'Cadeira'),
(default, 'Mesa'),
(default, 'Armário'),
(default, 'Equipamentos de Informática'),
(default, 'Mobiliário'),
(default, 'Acessórios'),
(default, 'Periféricos'),
(default, 'Rede');

insert into situacoes
values
(default, 'Armazenado'),
(default, 'Ativo'),
(default, 'Inativo');

insert into setores_responsaveis (idsetor, nome)
values
(default, 'Laboratório'),
(default, 'TI'),
(default, 'Administrativo'),
(default, 'Financeiro'),
(default, 'Compras'),
(default, 'Vendas'),
(default, 'Marketing');

insert into locais (nome)
values
('Recepção'),
('Administrativo'),
('Laboratório'),
('Diretoria');

insert into fornecedores (nome, cnpj)
values
('Faculdades Ideau', '17.590.477/0001-77'),
('Deltasul', '98.102.924/0001-01');

INSERT INTO info_notas (idnota, chave_acesso, numero, serie, idfornecedor, data_aquisicao) VALUES
    (default, '4320231234567890123456789012345678901234', '001', 1, 1, '2023-01-15'),
    (default, '4306202376543210987654321098765432109876', '002', 1, 2, '2023-03-10'),
    (default, '4312202287654321098765432109876543210987', '003', 1, 3, '2022-11-05'),
    (default, '4301202398765432109876543210987654321098', '004', 1, 1, '2022-12-20'),
    (default, '4322202112345678901234567890123456789012', '005', 1, 2, '2023-05-07');

insert into cargos
values
(default, 'Admin', '1', '1', '1','1','1','1'),
(default, 'Supervisor', '0', '1', '1','1','1','1'),
(default, 'Funcionario Registrador', '0', '1', '0','0','1','1'),
(default, 'Funcionario Leitor', '0', '0', '0','0','0','1');

insert into usuarios (usuario, senha, nome, email, dt_create, idcargo) 
values
('admin', '1', 'Desenv', 'teste@teste.com', default, 1),
('Administrador', '123', 'Administrador', 'teste@teste.com', default, 1),
('Supervisor', '123', 'Supervisor', 'teste@teste.com', default, 2),
('Funcionario_R', '123', 'Funcionario_R', 'teste@teste.com', default, 3),
('Funcionario_L', '123', 'Funcionario_L', 'teste@teste.com', default, 4);
   
INSERT INTO patrimonios (nome, valor_unitario, data_recebimento, num_patrimonio, num_serie, idnota, idcategoria, idsetor, idlocal, idsituacao)
VALUES
    ('Computador Dell', 3500.00, '2023-01-15', 'PATR001', 'SN001', 1, 1, 2, 1, 1),
    ('Mesa de Escritório', 750.00, '2023-03-10', 'PATR002', 'SN002', 2, 2, 3 , 4, 1),
    ('Impressora HP LaserJet', 1200.00, '2022-11-05', 'PATR003', 'SN003', 3, 3, 4, 2, 2),
    ('Projetor Epson', 2500.00, '2022-12-20', 'PATR004', 'SN004', 4, 4, 2, 3, 1),
    ('Notebook Lenovo', 4000.00, '2023-05-07', 'PATR005', 'SN005', 5, 1, 5, 1, 1),
    ('Cadeira Ergonômica', 450.00, '2023-04-02', 'PATR006', 'SN006', 1, 2, 3, 3, 1),
    ('Telefone IP Cisco', 320.00, '2022-08-11', 'PATR007', 'SN007', 2, 3, 6, 1, 2),
    ('Monitor Samsung', 800.00, '2023-01-18', 'PATR008', 'SN008', 3, 4, 2, 3, 1),
    ('Roteador TP-Link', 250.00, '2023-02-22', 'PATR009', 'SN009', 4, 3, 7, 1, 1),
    ('Estabilizador APC', 350.00, '2023-06-30', 'PATR010', 'SN010', 5, 4, 5, 3, 2);
    
-- Fim da inserção 

