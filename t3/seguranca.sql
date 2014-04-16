--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE seguranca;
--
-- Name: seguranca; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE seguranca WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';


\connect seguranca

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- Name: grupo_usuario; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE grupo_usuario (
    pk_codigo bigint NOT NULL,
    nome character varying(20) NOT NULL
);


--
-- Name: mensagem; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE mensagem (
    pk_codigo bigint NOT NULL,
    descricao character varying(100) NOT NULL
);


--
-- Name: registro; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE registro (
    fk_usuario character varying(20) NOT NULL,
    fk_mensagem bigint NOT NULL,
    hora timestamp without time zone DEFAULT now() NOT NULL
);


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE usuario (
    pk_login character varying(20) NOT NULL,
    fk_grupo bigint NOT NULL,
    nome character varying(50) NOT NULL,
    senha_hash character varying(50) NOT NULL,
    senha_salt character varying(9) NOT NULL,
    chave_publica text NOT NULL
);


--
-- Data for Name: grupo_usuario; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO grupo_usuario (pk_codigo, nome) VALUES (0, 'Administrador');
INSERT INTO grupo_usuario (pk_codigo, nome) VALUES (1, 'Normal');


--
-- Data for Name: mensagem; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO mensagem (pk_codigo, descricao) VALUES (1001, 'Sistema iniciado.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (1002, 'Sistema encerrado.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (2001, 'Autenticação etapa 1 iniciada.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (2002, 'Autenticação etapa 1 encerrada.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (2003, 'Login name <login_name> identificado com acesso liberado.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (2004, 'Login name <login_name> identificado com acesso bloqueado.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (2005, 'Login name <login_name> não identificado.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (3001, 'Autenticação etapa 2 iniciada para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (3002, 'Autenticação etapa 2 encerrada para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (3003, 'Senha pessoal verificada positivamente para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (3004, 'Senha pessoal verificada negativamente para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (3005, 'Primeiro erro da senha pessoal contabilizado para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (3006, 'Segundo erro da senha pessoal contabilizado para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (3007, 'Terceiro erro da senha pessoal contabilizado para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (3008, 'Acesso do usuario <login_name> bloqueado pela autenticação etapa 2.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (4001, 'Autenticação etapa 3 iniciada para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (4002, 'Autenticação etapa 3 encerrada para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (4003, 'Chave privada verificada positivamente para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (4004, 'Primeiro erro de chave privada contabilizado para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (4005, 'Segundo erro de chave privada contabilizado para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (4006, 'Terceiro erro de chave privada contabilizado para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (4007, 'Acesso do usuario <login_name> bloqueado pela autenticação etapa 3.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (5001, 'Tela principal apresentada para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (5002, 'Opção 1 do menu principal selecionada por <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (5003, 'Opção 2 do menu principal selecionada por <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (5004, 'Opção 3 do menu principal selecionada por <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (5005, 'Opção 4 do menu principal selecionada por <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (6001, 'Tela de cadastro apresentada para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (6002, 'Botão cadastrar pressionado por <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (6003, 'Botão voltar de cadastrar para o menu principal pressionado por <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (7001, 'Tela de alteração apresentada para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (7002, 'Botão alterar pressionado por <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (7003, 'Botão voltar de alterar para o menu principal pressionado por <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (8001, 'Tela de consulta apresentada para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (8002, 'Botão voltar de consultar para o menu principal pressionado por <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (8003, 'Arquivo <arq_name> selecionado por <login_name> para decriptação.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (8004, 'Arquivo <arq_name> decriptado com sucesso para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (8005, 'Arquivo <arq_name> verificado com sucesso para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (8006, 'Falha na decriptação do arquivo <arq_name> para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (8007, 'Falha na verificação do arquivo <arq_name> para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (9001, 'Tela de saída apresentada para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (9002, 'Botão sair pressionado por <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (9003, 'Botão voltar de sair para o menu principal pressionado por <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (9004, 'Arquivos decriptados apagados com sucesso para <login_name>.');
INSERT INTO mensagem (pk_codigo, descricao) VALUES (9005, 'Arquivo decriptado <nome_arquivo> não pôde ser apagado para <login_name>.');


--
-- Data for Name: registro; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO usuario (pk_login, fk_grupo, nome, senha_hash, senha_salt, chave_publica) VALUES ('admin', 0, 'Administrador', '8055bdf27581584a2839a5fab4d5dc066be84076', '540654971', '30819f300d06092a864886f70d010101050003818d00308189028181009ba4f73616ef24c80edb8b6853502498739e847cbcf749b89159b37cea47626df58d496a93c9f1590164b74d1216066f4b66fba2db1d789a51651cac11926921d123fd99653add1e90bb9fd8274fc6b72121f60e2498312aae69eaa1162bb5f2f7b0ac02b3b768d8fcd96bd83dc655dbb91677e39479178297d13c8bbfa84d350203010001');


--
-- Name: pk_grupo_usuario; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY grupo_usuario
    ADD CONSTRAINT pk_grupo_usuario PRIMARY KEY (pk_codigo);


--
-- Name: pk_mensagem; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mensagem
    ADD CONSTRAINT pk_mensagem PRIMARY KEY (pk_codigo);


--
-- Name: pk_registro; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY registro
    ADD CONSTRAINT pk_registro PRIMARY KEY (fk_usuario, fk_mensagem, hora);


--
-- Name: pk_usuarios; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT pk_usuarios PRIMARY KEY (pk_login);


--
-- Name: fk_registro_mensagem; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY registro
    ADD CONSTRAINT fk_registro_mensagem FOREIGN KEY (fk_mensagem) REFERENCES mensagem(pk_codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_registro_usuario; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY registro
    ADD CONSTRAINT fk_registro_usuario FOREIGN KEY (fk_usuario) REFERENCES usuario(pk_login) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_usuario_grupo; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk_usuario_grupo FOREIGN KEY (fk_grupo) REFERENCES grupo_usuario(pk_codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

