-- Cargos
CREATE TABLE Cargos (
    idCargo INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    ativo TINYINT(1) DEFAULT 1    
);


CREATE TABLE Cargos_audit (     
    idResponsavelAlteracao VARCHAR(36),
    tipo_alteracao VARCHAR(20) NOT NULL,
    data_alteracao DATETIME DEFAULT CURRENT_TIMESTAMP,
    dados_novos TEXT DEFAULT NULL,
    dados_antigos TEXT DEFAULT NULL
    );

DELIMITER $$
CREATE TRIGGER tgr_insert_Cargos
    AFTER INSERT ON Cargos
    FOR EACH ROW
BEGIN
    INSERT INTO Cargos_audit (idResponsavelAlteracao, tipo_alteracao, dados_novos) 
        VALUES (
            @idUsuarioLogado,
            "insert",
            NEW.idCargo
        );
END$$

CREATE TRIGGER tgr_update_Cargos
    AFTER UPDATE ON Cargos
    FOR EACH ROW
BEGIN
    INSERT INTO Cargos_audit (idResponsavelAlteracao, tipo_alteracao, dados_novos, dados_antigos) 
        VALUES (
            @idUsuarioLogado,
            "update",
            CONCAT('idCargo: ', NEW.idCargo, ', nome: ', NEW.nome, ', ativo: ', NEW.ativo),
            CONCAT('idCargo: ', OLD.idCargo, ', nome: ', OLD.nome, ', ativo: ', OLD.ativo)
        );
END$$

CREATE TRIGGER tgr_delete_Cargos
    AFTER DELETE ON Cargos
    FOR EACH ROW
BEGIN
    INSERT INTO Cargos_audit (idResponsavelAlteracao, tipo_alteracao, dados_antigos) 
        VALUES (
            @idUsuarioLogado,
            "delete",
            CONCAT('idCargo: ', OLD.idCargo, ', nome: ', OLD.nome, ', ativo: ', OLD.ativo)
        );
END$$
DELIMITER ;
-- Usuarios
create table Usuarios (     
    idUsuario VARCHAR(36) PRIMARY KEY, 
    username VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    nomeCompleto VARCHAR(255),
    email VARCHAR(150),
    idCargo INTEGER NOT NULL,
    dataCriacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    ativo TINYINT(1) DEFAULT 1,
    CONSTRAINT fk_usr_idCargo FOREIGN KEY (idCargo) REFERENCES Cargos(idCargo)
    );

create table Usuarios_audit (     
    idResponsavelAlteracao VARCHAR(36),
    tipo_alteracao varchar(20) NOT NULL,
    data_alteracao DATETIME DEFAULT CURRENT_TIMESTAMP,
    dados_novos TEXT,
    dados_antigos TEXT
    );

DELIMITER $$
CREATE TRIGGER tgr_insert_Usuarios
    AFTER INSERT ON Usuarios
    FOR EACH ROW
BEGIN
    INSERT INTO Usuarios_audit (idResponsavelAlteracao, tipo_alteracao, dados_novos) 
        VALUES (
            @idUsuarioLogado,
            "insert",
            NEW.idUsuario
        );
END$$

CREATE TRIGGER tgr_update_Usuarios
    AFTER UPDATE ON Usuarios
    FOR EACH ROW
BEGIN
    INSERT INTO Usuarios_audit (idResponsavelAlteracao, tipo_alteracao, dados_novos, dados_antigos) 
        VALUES (
            @idUsuarioLogado,
            "update",
            CONCAT('idUsuario: ', NEW.idUsuario, ', username: ', NEW.username, ', nomeCompleto: ', NEW.nomeCompleto, 
                ', email: ', NEW.email, ', idCargo: ', NEW.idCargo, ', ativo: ', NEW.ativo),
            CONCAT('idUsuario: ', OLD.idUsuario, ', username: ', OLD.username, ', nomeCompleto: ', OLD.nomeCompleto, 
                ', email: ', OLD.email, ', idCargo: ', OLD.idCargo, ', ativo: ', OLD.ativo)
        );
END$$

CREATE TRIGGER tgr_delete_Usuarios
    AFTER DELETE ON Usuarios
    FOR EACH ROW
BEGIN
    INSERT INTO Usuarios_audit (idResponsavelAlteracao, tipo_alteracao, dados_antigos) 
        VALUES (
            @idUsuarioLogado,
            "delete",
            CONCAT('idUsuario: ', OLD.idUsuario, ', username: ', OLD.username, ', nomeCompleto: ', OLD.nomeCompleto, 
                ', email: ', OLD.email, ', idCargo: ', OLD.idCargo, ', ativo: ', OLD.ativo)
        );
END$$
DELIMITER ;