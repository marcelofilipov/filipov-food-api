-- Script ...
alter table cidade add column estado_id bigint not null;

update cidade c set c.estado_id = (select e.id from estado e where e.nome = c.nome_estado);

alter table cidade add constraint fk_cidade_estado
    foreign key (estado_id) references estado (id);

alter table cidade drop column nome_estado;

alter table cidade change nome_cidade nome varchar(80) not null;
