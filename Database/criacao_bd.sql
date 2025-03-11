drop database db_assets_control;
create database if not exists db_assets_control;
use db_assets_control;

-- criando as tabelas 

create table categorias(
	idcategoria integer primary key auto_increment,
    nome varchar(30) not null unique
);

create table info_notas( 
	idnota integer not null primary key auto_increment,  
	chave_acesso varchar(44) not null unique,  
	numero integer not null,  
	serie integer not null,
    idfornecedor integer not null,
	data_aquisicao date not null
); 

create  table setores_responsaveis (
	idsetor integer not null primary key auto_increment,
    nome varchar(30) not null
);

create table locais(
	idlocal integer primary key auto_increment,
    nome varchar(255) unique
);

create table situacoes (
	idsituacao integer not null auto_increment primary key,
    nome varchar(30) not null
);

create table patrimonios (
	idpatrimonio integer not null auto_increment primary key,
	nome varchar(100) not null,
	valor_unitario decimal(10,2) not null,
    data_recebimento date,
    num_patrimonio varchar(30),
    num_serie varchar(30),
	idnota integer,
	idcategoria integer not null,
    idsetor integer not null,
    idlocal integer,
	idsituacao integer not null,
    
    constraint fk_ptr_info_notas foreign key (idnota) references info_notas (idnota),
    constraint fk_ptr_categorias foreign key (idcategoria) references categorias (idcategoria),
    constraint fk_ptr_setores_responsaveis foreign key (idsetor) references setores_responsaveis (idsetor),
    constraint fk_ptr_locais foreign key (idlocal) references locais (idlocal),
    constraint fk_ptr_situacoes foreign key (idsituacao) references situacoes (idsituacao)
);

create table fornecedores (
	idfornecedor integer not null auto_increment primary key,
    nome varchar(50) not null,
    cnpj varchar(18)
);

create table cargos ( 
	idcargo int primary key auto_increment,  
	nome varchar(30) not null,  
	acesso_geral char(1) not null,  
	pode_registrar char(1) not null,  
	controle_adm char(1) not null,  
	controle_usuario char(1) not null,  
	pode_modificar char(1) not null,  
	pode_visualizar char(1) not null  
);

create table users (
	userid integer not null auto_increment primary key,
	username varchar(30) not null unique,  
	passwd varchar(20) not null,
	personname varchar(30) not null,  
	email varchar(40),  
	created_at timestamp default current_timestamp,
	roleid integer not null,
    
    constraint fk_usr_roles foreign key (roleid) references roles (roleid)
); 

-- stored procedures

-- procedure que recebe a quantidade e cadastra 1 produto para cada distinto vezes o valor da quantidade
 -- drop procedure cadastra_quantidade;
delimiter $$
create procedure cadastra_quantidade (in nome varchar(100), in valor_unitario decimal(10, 2), in data_recebimento date, in idnota integer, in idcategoria integer, 
in idsetor integer, in idlocal integer, in idsituacao integer, in quantidade integer)
begin
	declare contador integer default 1;
    while 
		contador <= quantidade do
				insert into patrimonios (idpatrimonio, nome, valor_unitario, num_patrimonio, num_serie, data_recebimento, idnota, idcategoria, idsetor, idlocal, idsituacao)
				values
                (default, nome, valor_unitario, null, null, data_recebimento, idnota, idcategoria, idsetor, idlocal, idsituacao);
			set contador = contador + 1;
	end while;
end 
$$ delimiter ;

-- procedure que faz o cadastro das notas
-- drop procedure cadastra_nota;
delimiter $$
create procedure cadastra_nota(in e_chave_acesso varchar(44), in e_numero integer, in e_serie integer, in e_idfornecedor integer,in e_data_aquisicao date, out erro integer, out s_idnota integer)    
begin
	set @s_idnota = null;
    
	select idnota 
	into s_idnota 
	from info_notas 
    where 
		chave_acesso = e_chave_acesso;
    
    if s_idnota is null then
		insert into info_notas (chave_acesso, numero, serie, idfornecedor, data_aquisicao)
        values
        (e_chave_acesso, e_numero, e_serie, e_idfornecedor, e_data_aquisicao);
        
        set @s_idnota = last_insert_id();
    end if;
end
$$ delimiter ;

-- views 

-- drop procedure st_select_editar;

delimiter $$
create procedure st_select_editar (in idpatrimonio_in integer)
begin
select 
	nts.chave_acesso, 
	fcd.nome as "fornecedor", 
    ptr.num_serie, 
    ptr.num_patrimonio, 
    nts.data_aquisicao,
    ptr.data_recebimento, 
    lcl.nome as "local", 
    ptr.nome as "patrimonio", 
    ptr.valor_unitario, 
    sit.nome as "situacao", 
    srp.nome as "setor", 
    cat.nome as "categoria" 
from 
	patrimonios as ptr
left outer join
	info_notas as nts on ptr.idnota = nts.idnota
left outer join
	fornecedores as fcd on nts.idfornecedor = fcd.idfornecedor
left outer join
	locais as lcl on ptr.idlocal = lcl.idlocal
left outer join
	situacoes as sit on ptr.idsituacao = sit.idsituacao
left outer join
	setores_responsaveis as srp on ptr.idsetor = srp.idsetor
left outer join
	categorias as cat on ptr.idcategoria = cat.idcategoria
where 
	idpatrimonio = idpatrimonio_in;
end;
$$ delimiter;

-- drop view principal_patrimonio_view; -- descomentar caso fizer alteração
create view principal_patrimonio_view as
select
	ptr.idpatrimonio as "ID",
    ptr.nome as "Patrimônio",
    cat.nome as "Categoria",
    ptr.valor_unitario as "Valor Unitário",
    ptr.num_patrimonio as "Núm. Patrimônio",
    nta.data_aquisicao as "Data de Aquisição",
    srp.nome as "Setor Resp.",
    sit.nome as "Situação"
from
	patrimonios as ptr
inner join
	categorias as cat on ptr.idcategoria = cat.idcategoria
left outer join
	info_notas as nta on ptr.idnota = nta.idnota
left outer join
	setores_responsaveis as srp on ptr.idsetor = srp.idsetor
left outer join situacoes as sit on ptr.idsituacao = sit.idsituacao;

-- drop procedure st_pesquisar; descomentar caso fizer alteração
delimiter $$
create procedure st_pesquisar (in pesquisado varchar(30))
begin
select
	ptr.idpatrimonio as "ID",
    ptr.nome as "Patrimônio",
    cat.nome as "Categoria",
    ptr.valor_unitario as "Valor Unitário",
    ptr.data_recebimento as "Data Receb.",
    srp.nome as "Setor Resp.",
    sit.nome as "Situação"
from
	patrimonios as ptr
inner join
	categorias as cat on ptr.idcategoria = cat.idcategoria
left outer join
	info_notas as nta on ptr.idnota = nta.idnota
left outer join
	setores_responsaveis as srp on ptr.idsetor = srp.idsetor
left outer join situacoes as sit on ptr.idsituacao = sit.idsituacao
where ptr.nome like concat('%', pesquisado, '%') or cat.nome like concat('%', pesquisado, '%') or srp.nome like concat('%', pesquisado, '%');
end;
$$ delimiter ;

-- Criação tabela auditoria para verificar logs

-- drop table patrimonios_audit;
create table patrimonios_audit (
    idusuario integer,
    tipo_alteracao varchar(10),
    data_alteracao timestamp,
    idpatrimonio integer not null,
	nome varchar(100) default null,
	valor_unitario decimal(10,2) default null,
    data_recebimento date default null,
    num_patrimonio varchar(30) default null,
    num_serie varchar(30) default null,
	idnota integer default null,
	idcategoria integer default null,
    idsetor integer default null,
    idlocal integer default null,
	idsituacao integer default null   
);


create table categorias_audit(
	idusuario integer,
    tipo_alteracao varchar(10),
    data_alteracao timestamp,
    idcategoria integer,
    nome varchar(30) default null
);

create table info_notas_audit(
	idusuario integer,
    tipo_alteracao varchar(10),
    data_alteracao timestamp,
	idnota integer,  
	chave_acesso varchar(44) default null,  
	numero integer default null,  
	serie integer default null,
    idfornecedor integer default null,
	data_aquisicao date default null
); 

create table setores_responsaveis_audit (
	idusuario integer,
    tipo_alteracao varchar(10),
    data_alteracao timestamp,
	idsetor integer ,
    nome varchar(30) default null
);

create table locais_audit(
	idusuario integer,
    tipo_alteracao varchar(10),
    data_alteracao timestamp,
	idlocal integer ,
    nome varchar(255) default null
);

create table situacoes_audit (
	idusuario integer,
    tipo_alteracao varchar(10),
    data_alteracao timestamp,
	idsituacao integer ,
    nome varchar(30) default null
);

create table fornecedores_audit (
	idusuario integer,
    tipo_alteracao varchar(10),
    data_alteracao timestamp,
	idfornecedor integer,
    nome varchar(50) default null,
    cnpj varchar(18) default null
);

create table cargos_audit ( 
	idusuario integer,
    tipo_alteracao varchar(10),
    data_alteracao timestamp,
	idcargo integer,  
	nome varchar(30) default null,  
	acesso_geral char(1) default null,  
	pode_registrar char(1) default null,  
	controle_adm char(1) default null,  
	controle_usuario char(1) default null,  
	pode_modificar char(1) default null,  
	pode_visualizar char(1) default null  
);

create table usuarios_audit (
	idusuario integer,
	tipo_alteracao varchar(10),
    data_alteracao timestamp,
    idusuario_modificou integer,
	usuario varchar(30) default null,  
	senha varchar(20) default null,
	nome varchar(30) default null,  
	email varchar(40) default null,  
	dt_create date default null,
	idcargo integer default null
    ); 

-- triggers auditoria

-- patrimonios
delimiter $$
create trigger patrimonios_trigger_insert 
after insert on patrimonios 
for each row
begin
	insert into patrimonios_audit (idusuario, tipo_alteracao, data_alteracao, idpatrimonio)
	values
	(@idusuario, 'insert', current_timestamp(), new.idpatrimonio);
end $$
delimiter ;

delimiter $$
create trigger patrimonios_trigger_update 
after update on patrimonios 
for each row
begin
	insert into patrimonios_audit (idusuario, tipo_alteracao, data_alteracao, idpatrimonio, nome, valor_unitario, data_recebimento, num_patrimonio, num_serie, idnota, idcategoria, idsetor, idlocal, idsituacao)
	values
	(@idusuario, 'update', current_timestamp(), old.idpatrimonio, old.nome, old.valor_unitario, old.data_recebimento, old.num_patrimonio, old.num_serie, old.idnota, old.idcategoria, old.idsetor, old.idlocal, old.idsituacao);
end $$
delimiter ;

delimiter $$
create trigger patrimonios_trigger_delete
before delete on patrimonios 
for each row
begin
	insert into patrimonios_audit (idusuario, tipo_alteracao, data_alteracao, idpatrimonio, nome, valor_unitario, data_recebimento, num_patrimonio, num_serie, idnota, idcategoria, idsetor, idlocal, idsituacao)
	values
	(@idusuario, 'delete', current_timestamp(), old.idpatrimonio, old.nome, old.valor_unitario, old.data_recebimento, old.num_patrimonio, old.num_serie, old.idnota, old.idcategoria, old.idsetor, old.idlocal, old.idsituacao);
end $$
delimiter ;

-- categorias

delimiter $$
create trigger categorias_trigger_insert 
after insert on categorias
for each row
begin
	insert into categorias_audit (idusuario, tipo_alteracao, data_alteracao, idcategoria)
	values
	(@idusuario, 'insert', current_timestamp(), new.idcategoria);
end $$
delimiter ;

delimiter $$
create trigger categorias_trigger_update 
after update on categorias 
for each row
begin
	insert into categorias_audit (idusuario, tipo_alteracao, data_alteracao, idcategoria, nome)
	values
	(@idusuario, 'update', current_timestamp(), old.idcategoria, old.nome);
end $$
delimiter ;

delimiter $$
create trigger categorias_trigger_delete
before delete on categorias
for each row
begin
	insert into categorias_audit (idusuario, tipo_alteracao, data_alteracao, idcategoria, nome)
	values
	(@idusuario, 'delete', current_timestamp(), old.idcategoria, old.nome);
end $$
delimiter ;

-- info notas
delimiter $$
create trigger info_notas_trigger_insert 
after insert on info_notas
for each row
begin
	insert into info_notas_audit (idusuario, tipo_alteracao, data_alteracao, idnota)
	values
	(@idusuario, 'insert', current_timestamp(), new.idnota);
end $$
delimiter ;

delimiter $$
create trigger info_notas_trigger_update 
after update on info_notas 
for each row
begin
	insert into info_notas_audit (idusuario, tipo_alteracao, data_alteracao, idnota, chave_acesso, numero, serie, idfornecedor, data_aquisicao)
	values
	(@idusuario, 'update', current_timestamp(), old.idnota, old.chave_acesso, old.numero, old.serie, old.idfornecedor, old.data_aquisicao);
end $$
delimiter ;

delimiter $$
create trigger info_notas_trigger_delete
before delete on info_notas
for each row
begin
	insert into info_notas_audit (idusuario, tipo_alteracao, data_alteracao, idnota, chave_acesso, numero, serie, idfornecedor, data_aquisicao)
	values
	(@idusuario, 'delete', current_timestamp(), old.idnota, old.chave_acesso, old.numero, old.serie, old.idfornecedor, old.data_aquisicao);
end $$
delimiter ;

-- setores responsaveis drop trigger setores_responsaveis_trigger_insert 
delimiter $$
create trigger setores_responsaveis_trigger_insert 
after insert on setores_responsaveis
for each row
begin
	insert into setores_responsaveis_audit (idusuario, tipo_alteracao, data_alteracao, idsetor)
	values
	(@idusuario, 'insert', current_timestamp(), new.idsetor);
end $$
delimiter ;

delimiter $$
create trigger setores_responsaveis_trigger_update 
after update on setores_responsaveis 
for each row
begin
	insert into setores_responsaveis_audit (idusuario, tipo_alteracao, data_alteracao, idsetor, nome)
	values
	(@idusuario, 'update', current_timestamp(), old.idsetor, old.nome);
end $$
delimiter ;

delimiter $$
create trigger setores_responsaveis_trigger_delete
before delete on setores_responsaveis
for each row
begin
	insert into setores_responsaveis_audit (idusuario, tipo_alteracao, data_alteracao, idsetor, nome)
	values
	(@idusuario, 'delete', current_timestamp(), old.idsetor, old.nome);
end $$
delimiter ;

-- locais
delimiter $$
create trigger locais_trigger_insert 
after insert on locais
for each row
begin
	insert into locais_audit (idusuario, tipo_alteracao, data_alteracao, idlocal)
	values
	(@idusuario, 'insert', current_timestamp(), new.idlocal);
end $$
delimiter ;

delimiter $$
create trigger locais_trigger_update 
after update on locais
for each row
begin
	insert into locais_audit (idusuario, tipo_alteracao, data_alteracao, idlocal, nome)
	values
	(@idusuario, 'update', current_timestamp(), old.idlocal, old.nome);
end $$
delimiter ;

delimiter $$
create trigger locais_trigger_delete
before delete on locais
for each row
begin
	insert into locais_audit (idusuario, tipo_alteracao, data_alteracao, idlocal, nome)
	values
	(@idusuario, 'delete', current_timestamp(), old.idlocal, old.nome);
end $$
delimiter ;

-- situacoes
delimiter $$
create trigger situacoes_trigger_insert 
after insert on situacoes
for each row
begin
	insert into situacoes_audit (idusuario, tipo_alteracao, data_alteracao, idsituacao)
	values
	(@idusuario, 'insert', current_timestamp(), new.idsituacao);
end $$
delimiter ;

delimiter $$
create trigger situacoes_trigger_update 
after update on situacoes
for each row
begin
	insert into situacoes_audit (idusuario, tipo_alteracao, data_alteracao, idsituacao, nome)
	values
	(@idusuario, 'update', current_timestamp(), old.idsituacao, old.nome);
end $$
delimiter ;

delimiter $$
create trigger situacoes_trigger_delete
before delete on situacoes
for each row
begin
	insert into situacoes_audit (idusuario, tipo_alteracao, data_alteracao, idsituacao, nome)
	values
	(@idusuario, 'delete', current_timestamp(), old.idsituacao, old.nome);
end $$
delimiter ;

-- fornecedores
delimiter $$
create trigger fornecedores_trigger_insert 
after insert on fornecedores
for each row
begin
	insert into fornecedores_audit (idusuario, tipo_alteracao, data_alteracao, idfornecedor)
	values
	(@idusuario, 'insert', current_timestamp(), new.idfornecedor);
end $$
delimiter ;

delimiter $$
create trigger fornecedores_trigger_update 
after update on fornecedores 
for each row
begin
	insert into fornecedores_audit (idusuario, tipo_alteracao, data_alteracao, idfornecedor, nome, cnpj)
	values
	(@idusuario, 'update', current_timestamp(), old.idfornecedor, old.nome, old.cnpj);
end $$
delimiter ;

delimiter $$
create trigger fornecedores_trigger_delete
before delete on fornecedores
for each row
begin
	insert into fornecedores_audit (idusuario, tipo_alteracao, data_alteracao, idfornecedor, nome, cnpj)
	values
	(@idusuario, 'delete', current_timestamp(), old.idfornecedor, old.nome, old.cnpj);
end $$
delimiter ;

-- cargos
delimiter $$
create trigger cargos_trigger_insert 
after insert on cargos
for each row
begin
	insert into cargos_audit (idusuario, tipo_alteracao, data_alteracao, idcargo)
	values
	(@idusuario, 'insert', current_timestamp(), new.idcargo);
end $$
delimiter ;

delimiter $$
create trigger cargos_trigger_update 
after update on cargos
for each row
begin
	insert into cargos_audit (idusuario, tipo_alteracao, data_alteracao, idcargo, nome, acesso_geral, pode_registrar, controle_adm, controle_usuario, pode_modificar, pode_visualizar)
	values
	(@idusuario, 'update', current_timestamp(), old.idcargo, old.nome, old.acesso_geral, old.pode_registrar, old.controle_adm, old.controle_usuario, old.pode_modificar, old.pode_visualizar);
end $$
delimiter ;

delimiter $$
create trigger cargos_trigger_delete
before delete on cargos
for each row
begin
	insert into cargos_audit (idusuario, tipo_alteracao, data_alteracao, idcargo, nome, acesso_geral, pode_registrar, controle_adm, controle_usuario, pode_modificar, pode_visualizar)
	values
	(@idusuario, 'delete', current_timestamp(), old.idcargo, old.nome, old.acesso_geral, old.pode_registrar, old.controle_adm, old.controle_usuario, old.pode_modificar, old.pode_visualizar);
end $$
delimiter ;

-- usuarios
delimiter $$
create trigger usuarios_trigger_insert 
after insert on usuarios
for each row
begin
	insert into usuarios_audit (idusuario_modificou, tipo_alteracao, data_alteracao, idusuario)
	values
	(@idusuario, 'insert', current_timestamp(), new.idusuario);
end $$
delimiter ;

	idusuario integer,
	tipo_alteracao varchar(10),
    data_alteracao timestamp,
	usuario varchar(30) not null unique,  
	senha varchar(20) not null,
	nome varchar(30) not null,  
	email varchar(40),  
	dt_create date,
	idcargo integer not null
delimiter $$

create trigger usuarios_trigger_update 
after update on usuarios 
for each row
begin
	insert into usuarios_audit (idusuario_modificou, tipo_alteracao, data_alteracao, idusuario, usuario, senha, nome, email, dt_create, idcargo)
	values
	(@idusuario, 'update', current_timestamp(), old.idusuario, old.usuario, old.senha, old.nome, old.email, old.dt_create, old.idcargo);
end $$
delimiter ;

delimiter $$
create trigger usuarios_trigger_delete
before delete on usuarios
for each row
begin
	insert into usuarios_audit (idusuario_modificou, tipo_alteracao, data_alteracao, idusuario, usuario, senha, nome, email, dt_create, idcargo)
	values
	(@idusuario, 'delete', current_timestamp(), old.idusuario, old.usuario, old.senha, old.nome, old.email, old.dt_create, old.idcargo);
end $$
delimiter ; 