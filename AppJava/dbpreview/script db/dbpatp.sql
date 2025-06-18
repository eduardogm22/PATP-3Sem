-- Recriação do banco
DROP DATABASE IF EXISTS patp;
CREATE DATABASE patp;
USE patp;

-- Tabela de usuários
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_usuario VARCHAR(50) NOT NULL UNIQUE, -- nome de login
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(100),                        -- nome real
    sobrenome VARCHAR(100),
    cpf VARCHAR(14) UNIQUE,
    data_nascimento DATE,
    email VARCHAR(100),
    telefone VARCHAR(20),

    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Tabela de cargos
CREATE TABLE cargos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT
);

-- Relacionamento usuários-cargos (muitos para muitos)
CREATE TABLE usuarios_cargos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    cargo_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (cargo_id) REFERENCES cargos(id) ON DELETE CASCADE,
    UNIQUE (usuario_id, cargo_id)
);

-- Tabela de permissões
CREATE TABLE permissoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT
);

-- Relacionamento cargos-permissoes (muitos para muitos)
CREATE TABLE cargos_permissoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cargo_id INT NOT NULL,
    permissao_id INT NOT NULL,
    FOREIGN KEY (cargo_id) REFERENCES cargos(id) ON DELETE CASCADE,
    FOREIGN KEY (permissao_id) REFERENCES permissoes(id) ON DELETE CASCADE,
    UNIQUE (cargo_id, permissao_id)
);

-- Tabela de fornecedores
CREATE TABLE fornecedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(18) UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);


-- Tabela de categorias
CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT
);

-- Tabela de situações
CREATE TABLE situacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);


-- Tabela de patrimônio (documento de aquisição)
CREATE TABLE patrimonio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    chave_acesso VARCHAR(50) UNIQUE,
    numero_documento VARCHAR(30),
    data_aquisicao DATE,
    data_recebimento DATE,
    fornecedor_id INT,
    recebido_por VARCHAR(100),
    serie VARCHAR(50),
    nome_produto VARCHAR(100) NOT NULL,
    categoria_id INT,
    setor_responsavel VARCHAR(100),
    situacao_id INT,
    valor_total DECIMAL(10, 2) NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id),
    FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    FOREIGN KEY (situacao_id) REFERENCES situacoes(id)
);

-- Itens individuais derivados do patrimônio
CREATE TABLE itens_patrimonio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patrimonio_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    situacao_id INT,
    categoria_id INT,
    setor VARCHAR(100),
    
    valor_unitario DECIMAL(10,2),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (patrimonio_id) REFERENCES patrimonio(id) ON DELETE CASCADE,
    FOREIGN KEY (situacao_id) REFERENCES situacoes(id),
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

-- Tabela de logs de ações
CREATE TABLE log_usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    acao VARCHAR(50) NOT NULL, -- INSERIR, ALTERAR, EXCLUIR
    tabela_afetada VARCHAR(100) NOT NULL,
    descricao TEXT,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Inserção inicial de admin
INSERT INTO usuarios (nome, senha, email)
VALUES ('admin', 'admin', 'admin@sistema.com');
