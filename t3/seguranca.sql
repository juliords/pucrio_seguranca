--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.4
-- Dumped by pg_dump version 9.3.4
-- Started on 2014-04-16 09:27:10 BRT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE seguranca;
--
-- TOC entry 1966 (class 1262 OID 16893)
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
-- TOC entry 5 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 1967 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 174 (class 3079 OID 11757)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1968 (class 0 OID 0)
-- Dependencies: 174
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 16902)
-- Name: grupo_usuario; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE grupo_usuario (
    pk_codigo bigint NOT NULL,
    nome character varying(20) NOT NULL
);


--
-- TOC entry 172 (class 1259 OID 16917)
-- Name: mensagem; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE mensagem (
    pk_codigo bigint NOT NULL,
    descricao character varying(100) NOT NULL
);


--
-- TOC entry 173 (class 1259 OID 16922)
-- Name: registro; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE registro (
    fk_usuario character varying(20) NOT NULL,
    fk_mensagem bigint NOT NULL,
    hora timestamp without time zone DEFAULT now() NOT NULL
);


--
-- TOC entry 170 (class 1259 OID 16894)
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
-- TOC entry 1959 (class 0 OID 16902)
-- Dependencies: 171
-- Data for Name: grupo_usuario; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO grupo_usuario (pk_codigo, nome) VALUES (0, 'Administrador');
INSERT INTO grupo_usuario (pk_codigo, nome) VALUES (1, 'Normal');


--
-- TOC entry 1960 (class 0 OID 16917)
-- Dependencies: 172
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
-- TOC entry 1961 (class 0 OID 16922)
-- Dependencies: 173
-- Data for Name: registro; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1958 (class 0 OID 16894)
-- Dependencies: 170
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1843 (class 2606 OID 16906)
-- Name: pk_grupo_usuario; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY grupo_usuario
    ADD CONSTRAINT pk_grupo_usuario PRIMARY KEY (pk_codigo);


--
-- TOC entry 1845 (class 2606 OID 16921)
-- Name: pk_mensagem; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mensagem
    ADD CONSTRAINT pk_mensagem PRIMARY KEY (pk_codigo);


--
-- TOC entry 1847 (class 2606 OID 16937)
-- Name: pk_registro; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY registro
    ADD CONSTRAINT pk_registro PRIMARY KEY (fk_usuario, fk_mensagem, hora);


--
-- TOC entry 1841 (class 2606 OID 16901)
-- Name: pk_usuarios; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT pk_usuarios PRIMARY KEY (pk_login);


--
-- TOC entry 1850 (class 2606 OID 16943)
-- Name: fk_registro_mensagem; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY registro
    ADD CONSTRAINT fk_registro_mensagem FOREIGN KEY (fk_mensagem) REFERENCES mensagem(pk_codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1849 (class 2606 OID 16938)
-- Name: fk_registro_usuario; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY registro
    ADD CONSTRAINT fk_registro_usuario FOREIGN KEY (fk_usuario) REFERENCES usuario(pk_login) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1848 (class 2606 OID 16912)
-- Name: fk_usuario_grupo; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk_usuario_grupo FOREIGN KEY (fk_grupo) REFERENCES grupo_usuario(pk_codigo) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2014-04-16 09:27:10 BRT

--
-- PostgreSQL database dump complete
--

