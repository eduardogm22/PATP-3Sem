select * from usuarios;
select * from usuarios_audit;
update usuarios set
usuario = 'teste', senha = '1', nome = 'Desenv', email = 'teste@teste.com'
where idusuario = 5;
delete from usuarios where idusuario = 5;

select * from patrimonios where idpatrimonio = 2 order by nome;
select * from patrimonios_audit;
select * from situacoes;
SELECT senha FROM usuarios WHERE idusuario = 1;
call st_select_editar(2);
set @idusuario = 1;
UPDATE pessoas 
                SET nome = 'eduardo', email = 'teste' 
                WHERE idpessoa = 6;

select * from categorias_audit;
insert into categorias
values
(default, 'Teste2');
update categorias set nome = 'testando' where idcategoria = 10;
update patrimonios set idsituacao = '3' where idpatrimonio = 11;
delete from categorias where idcategoria = 13;

select * from info_notas_audit;
insert into info_notas
values
(default, '43191234567890123456789012345678901234567890', 111, 11, 1, current_date());
update info_notas set chave_acesso = '111111111' where idnota = 6;
delete from info_notas where idnota = 6;

select * from setores_responsaveis_audit;
insert into setores_responsaveis
values
(default, 'teste2');
update setores_responsaveis set nome = 'testando' where idsetor = 8;
delete from setores_responsaveis where idsetor = 8;

select * from locais_audit;
insert into locais
values
(default, 'Teste2');
update locais set nome = 'testando' where idlocal = 5;
delete from locais where idlocal = 6;

select * from situacoes_audit;
insert into situacoes
values
(default, 'Teste2');
update situacoes set nome = 'testando' where idsituacao = 4;
delete from situacoes where idsituacao = 5;

select * from fornecedores_audit;
insert into fornecedores
values
(default, 'Teste2', '12345678901234');
update fornecedores set nome = 'testando' where idfornecedor = 3;
delete from fornecedores where idfornecedor = 3;

select * from pessoas_audit;
insert into pessoas
values
(default, 'Teste2', 'teste@teste.com', current_date());
update pessoas set nome = 'testando' where idpessoa = 4;
delete from pessoas where idpessoa = 6;

select * from cargos_audit;
insert into cargos
values
(default, 'Teste2', 1, 1, 1, 1, 1, 1);
update cargos set nome = 'testando' where idcargo = 5;
delete from cargos where idcargo = 5;

select * from pessoas_audit;
select * from usuarios_audit;
insert into usuarios
values
(1, 'Teste2', 'senha', 1);
update usuarios set usuario = 'testando' where idpessoa = 1;
delete from usuarios where idpessoa = 1;

call cadastra_quantidade('Monitor Samsung', 800.00, '2023-01-18', 3, 4, 2, 3, 1, 1);

select * from usuarios;

-- executar isso
alter table info_notas
drop index chave_acesso;

alter table info_notas
drop index numero;

-- Final teste 1
-- Teste busca
CALL st_pesquisar('Computador Dell');


SELECT
    ptr.idpatrimonio AS "ID",
    ptr.nome AS "Patrimônio",
    cat.nome AS "Categoria",
    ptr.valor_unitario AS "Valor Unitário",
    ptr.num_patrimonio AS "Núm. Patrimônio",
    nta.data_aquisicao AS "Data de Aquisição",
    srp.nome AS "Setor Resp.",
    sit.nome AS "Situação"
FROM
    patrimonios AS ptr
INNER JOIN
    categorias AS cat ON ptr.idcategoria = cat.idcategoria
LEFT OUTER JOIN
    info_notas AS nta ON ptr.idnota = nta.idnota
LEFT OUTER JOIN
    setores_responsaveis AS srp ON ptr.idsetor_responsavel = srp.idsetor_responsavel
LEFT OUTER JOIN
    situacoes AS sit ON ptr.idsituacao = sit.idsituacao
WHERE
    ptr.nome LIKE CONCAT('%', 'Computador Dell', '%');




-- Teste 2
SELECT 
    ptr.idpatrimonio AS "ID",
    ptr.nome AS "Patrimônio",
    cat.nome AS "Categoria",
    ptr.valor_unitario AS "Valor Unitário",
    ptr.num_patrimonio AS "Núm. Patrimônio",
    nta.data_aquisicao AS "Data de Aquisição",
    srp.nome AS "Setor Resp.",
    sit.nome AS "Situação"
FROM
    patrimonios AS ptr
INNER JOIN
    categorias AS cat ON ptr.idcategoria = cat.idcategoria
LEFT OUTER JOIN
    info_notas AS nta ON ptr.idnota = nta.idnota
LEFT OUTER JOIN
    setores_responsaveis AS srp ON ptr.idsetor_responsavel = srp.idsetor_responsavel
LEFT OUTER JOIN
    situacoes AS sit ON ptr.idsituacao = sit.idsituacao
WHERE
    ptr.nome;
-- Final Teste 2

-- Teste
DELIMITER $$
CREATE PROCEDURE st_pesquisar(IN pesquisado VARCHAR(30))
BEGIN
    SELECT
        ptr.idpatrimonio AS "ID",
        ptr.nome AS "Patrimônio",
        cat.nome AS "Categoria",
        ptr.valor_unitario AS "Valor Unitário",
        ptr.num_patrimonio AS "Núm. Patrimônio",
        nta.data_aquisicao AS "Data de Aquisição",
        srp.nome AS "Setor Resp.",
        sit.nome AS "Situação"
    FROM
        patrimonios AS ptr
    INNER JOIN
        categorias AS cat ON ptr.idcategoria = cat.idcategoria
    LEFT OUTER JOIN
        info_notas AS nta ON ptr.idnota = nta.idnota
    LEFT OUTER JOIN
        setores_responsaveis AS srp ON ptr.idsetor_responsavel = srp.idsetor_responsavel
    LEFT OUTER JOIN
        situacoes AS sit ON ptr.idsituacao = sit.idsituacao
    WHERE
        ptr.nome LIKE CONCAT('%', pesquisado, '%')
        OR ptr.idpatrimonio LIKE CONCAT('%', pesquisado, '%')
        OR ptr.num_patrimonio LIKE CONCAT('%', pesquisado, '%');
END $$

DELIMITER ;


#testando cadastro pela procedure

create table patrimonios_teste (
	idpatrimonio integer auto_increment primary key,
	nome varchar(100),
	valor_unitario decimal(10,2),
    data_recebimento date,
    num_patrimonio varchar(30),
    num_serie varchar(30),
	idnota integer,
	idcategoria integer,
    idsetor_responsavel integer,
	idsituacao integer
);
drop procedure cadastra_quantidade_teste;
delimiter $$
create procedure cadastra_quantidade_teste (in nome varchar(100), in valor_unitario decimal(10, 2), in categoria integer, in quantidade integer)
begin
	declare contador integer default 1;
    while 
		contador <= quantidade do
				insert into patrimonios_teste (idpatrimonio, nome, valor_unitario, num_patrimonio, num_serie, data_recebimento, idnota, idcategoria, idsetor_responsavel, idsituacao)
				values
                (default, nome, valor_unitario, null, null, current_date(), null, categoria, null, null);
			set contador = contador + 1;
	end while;
end 
$$ delimiter ;

call cadastra_quantidade_teste('testecat1', 50, 1, 1);
select * from patrimonios_teste;
