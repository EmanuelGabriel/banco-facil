
CREATE SEQUENCE oportunidade_seq;

CREATE TABLE oportunidade(

  id bigint default nextval ('oportunidade_seq') not null,
  nome_oportunidade varchar(100) not null,
  descricao varchar(200) not null,
  valor decimal(10,2),

   primary key(id)

);