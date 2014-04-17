--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.4
-- Dumped by pg_dump version 9.3.4
-- Started on 2014-04-17 13:22:17 BRT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE seguranca;
--
-- TOC entry 1967 (class 1262 OID 16893)
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
-- TOC entry 1968 (class 0 OID 0)
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
-- TOC entry 1969 (class 0 OID 0)
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
    chave_publica text NOT NULL,
    erro_qtd integer DEFAULT 0 NOT NULL,
    erro_hora timestamp without time zone
);


--
-- TOC entry 1960 (class 0 OID 16902)
-- Dependencies: 171
-- Data for Name: grupo_usuario; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO grupo_usuario (pk_codigo, nome) VALUES (0, 'Administrador');
INSERT INTO grupo_usuario (pk_codigo, nome) VALUES (1, 'Normal');


--
-- TOC entry 1961 (class 0 OID 16917)
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
-- TOC entry 1962 (class 0 OID 16922)
-- Dependencies: 173
-- Data for Name: registro; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1959 (class 0 OID 16894)
-- Dependencies: 170
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO usuario (pk_login, fk_grupo, nome, senha_hash, senha_salt, chave_publica, erro_qtd, erro_hora) VALUES ('user01', 1, 'User01', '2ca82848e422312172ecc46066d52bf6', '027163094', '30819f300d06092a864886f70d010101050003818d0030818902818100a8bf4cde94c12dea2ff7ce48204112d1c7e9965be953ac048f655ac56f30db0565e1a7de49632c5380bdc955f60674b38b0fd334482306ca8b6c0d84be07c5c91ff5a6c22920786f1d3e9cd85d2e007de859181aae0e37ac59045278f4ec06252860068ee084ef76462926d2d603b9bd1d4b60f9c901006f60c5611f1e4a05210203010001', 3, '2014-04-16 16:28:59.778745');
INSERT INTO usuario (pk_login, fk_grupo, nome, senha_hash, senha_salt, chave_publica, erro_qtd, erro_hora) VALUES ('user02', 1, 'User02', 'b59c08ee5fa48e0fae78916ff27d6bdb', '292666447', '30819f300d06092a864886f70d010101050003818d0030818902818100d047f2227ca3eb2cf900f0441b1122f7e1914d5225531719431761d1d2e4517ce303648422c49c7c364c799924439ff4b36bd9b81502f7944fd1f5c61f20d95cb30e711af28582dd7be76aef08322fd826ee9e95e1572a5502267df552d0df182c9905d8bfae7d131d4bbfa02bc3a04c30377beeff0177b2e23c7fb47a998df90203010001', 3, '2014-04-16 16:30:47.399204');
INSERT INTO usuario (pk_login, fk_grupo, nome, senha_hash, senha_salt, chave_publica, erro_qtd, erro_hora) VALUES ('admin', 0, 'Administrador', '0ce90b2b65a8048b9621993bc61872e7', '043578592', '30819f300d06092a864886f70d010101050003818d00308189028181009ba4f73616ef24c80edb8b6853502498739e847cbcf749b89159b37cea47626df58d496a93c9f1590164b74d1216066f4b66fba2db1d789a51651cac11926921d123fd99653add1e90bb9fd8274fc6b72121f60e2498312aae69eaa1162bb5f2f7b0ac02b3b768d8fcd96bd83dc655dbb91677e39479178297d13c8bbfa84d350203010001', 0, '2014-04-16 15:15:56.984597');
INSERT INTO usuario (pk_login, fk_grupo, nome, senha_hash, senha_salt, chave_publica, erro_qtd, erro_hora) VALUES ('user03', 1, 'User03', '0e4fdf9e0e89184abd2445c152007059', '040769721', '30819f300d06092a864886f70d010101050003818d00308189028181008669cb8f65be884ed06b9d1a1f61784684971d74b680ef848ba099a1cacb31396d97b72eeac01003e00ae4035491e5b1166a6e8518ba768a10bd906b4027d65efd0a9cf17656dcb39e0b3f8ac2ebb1ab09b3650dcf893e07dc2c63c57bd529df12917436c803ce6df0f61bef4623000f36e3ff8178826e6af95543c888fe7d310203010001', 0, '2014-04-16 17:01:48.182993');


--
-- TOC entry 1844 (class 2606 OID 16906)
-- Name: pk_grupo_usuario; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY grupo_usuario
    ADD CONSTRAINT pk_grupo_usuario PRIMARY KEY (pk_codigo);


--
-- TOC entry 1846 (class 2606 OID 16921)
-- Name: pk_mensagem; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mensagem
    ADD CONSTRAINT pk_mensagem PRIMARY KEY (pk_codigo);


--
-- TOC entry 1848 (class 2606 OID 16937)
-- Name: pk_registro; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY registro
    ADD CONSTRAINT pk_registro PRIMARY KEY (fk_usuario, fk_mensagem, hora);


--
-- TOC entry 1842 (class 2606 OID 16901)
-- Name: pk_usuarios; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT pk_usuarios PRIMARY KEY (pk_login);


--
-- TOC entry 1851 (class 2606 OID 16943)
-- Name: fk_registro_mensagem; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY registro
    ADD CONSTRAINT fk_registro_mensagem FOREIGN KEY (fk_mensagem) REFERENCES mensagem(pk_codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1850 (class 2606 OID 16938)
-- Name: fk_registro_usuario; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY registro
    ADD CONSTRAINT fk_registro_usuario FOREIGN KEY (fk_usuario) REFERENCES usuario(pk_login) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1849 (class 2606 OID 16912)
-- Name: fk_usuario_grupo; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk_usuario_grupo FOREIGN KEY (fk_grupo) REFERENCES grupo_usuario(pk_codigo) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2014-04-17 13:22:17 BRT

--
-- PostgreSQL database dump complete
--

