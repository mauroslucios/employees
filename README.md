# employeess
## Tecnologia usadas:
- java
- jpa
- spring
- postgresql
- docker(container postgresql - localhost)
- spring security(encriptar senha)
- lombok
- swaggerFox
- swaggerUI
- postgresql(heroku)
- git

## Endpoints funcionarios
### localhost:8080/swagger-ui.html

- /api/v1/funcionarios
- /api/v1/funcionarios/{id}

## Endpoints departamentos
### localhost:8080
- /api/v1/departamentos
- /api/v1/departamentos/{id}

## Endpoints posts
### localhost:8080
- /api/v1/posts
- /api/v1/posts/{id}


### Deve-se atualizar a classe principal para evitar o login via web
```
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class EmployeesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeesApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}```

# SQL das tabelas
```
-- Table: public.tb_departamento

-- DROP TABLE IF EXISTS public.tb_departamento;

CREATE TABLE IF NOT EXISTS public.tb_departamento
(
    id bigint NOT NULL DEFAULT nextval('tb_departamento_id_seq'::regclass),
    nome character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tb_departamento_pkey PRIMARY KEY (id),
    CONSTRAINT uk_ayglrjjqocpl5sucr4d0y1910 UNIQUE (nome)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_departamento
    OWNER to postgres;
```

```
-- Table: public.tb_funcionario

-- DROP TABLE IF EXISTS public.tb_funcionario;

CREATE TABLE IF NOT EXISTS public.tb_funcionario
(
    id bigint NOT NULL DEFAULT nextval('tb_funcionario_id_seq'::regclass),
    cpf character varying(11) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    nome character varying(30) COLLATE pg_catalog."default",
    senha character varying(255) COLLATE pg_catalog."default",
    tipo character varying(255) COLLATE pg_catalog."default" NOT NULL,
    departamento_id bigint,
    CONSTRAINT tb_funcionario_pkey PRIMARY KEY (id),
    CONSTRAINT uk_h483q8xwbxkhk56ceeww2pvhw UNIQUE (cpf),
    CONSTRAINT uk_nt71h6xkn60cxsh8q58iyk7jr UNIQUE (senha),
    CONSTRAINT uk_onjc1xoei8x59dt76x2xnd3wc UNIQUE (email),
    CONSTRAINT fkatmyvejd2vst2t9pbfdd89cm3 FOREIGN KEY (departamento_id)
        REFERENCES public.tb_departamento (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_funcionario
    OWNER to postgres;
```

```
-- Table: public.tb_post

-- DROP TABLE IF EXISTS public.tb_post;

CREATE TABLE IF NOT EXISTS public.tb_post
(
    id bigint NOT NULL DEFAULT nextval('tb_post_id_seq'::regclass),
    descricao character varying(255) COLLATE pg_catalog."default",
    titulo character varying(255) COLLATE pg_catalog."default",
    funcionario_id bigint,
    CONSTRAINT tb_post_pkey PRIMARY KEY (id),
    CONSTRAINT uk_fyqo7f8r1tiriyq05fpkh20ls UNIQUE (descricao),
    CONSTRAINT uk_po36ffb4juc91t8oxquk3vnh4 UNIQUE (titulo),
    CONSTRAINT fke6hfbpa6uxtd59wi00ymrm5kd FOREIGN KEY (funcionario_id)
        REFERENCES public.tb_funcionario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_post
    OWNER to postgres;
```
