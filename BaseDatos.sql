create table cuenta (estado bit not null, numero_cuenta integer not null, saldo_inicial float(53) not null, cliente_id bigint, id bigint not null auto_increment, tipo_cuenta varchar(255) not null, primary key (id)) engine=InnoDB;
create table movimiento (saldo float(53) not null, valor float(53) not null, cuenta_id bigint, fecha datetime(6), movimiento_id bigint not null auto_increment, tipo_movimiento varchar(255) not null, primary key (movimiento_id)) engine=InnoDB;
create table persona (edad integer not null, estado bit not null, id bigint not null auto_increment, identificacion varchar(10), telefono varchar(10), clave varchar(20) not null, dtype varchar(31) not null, nombre varchar(50) not null, direccion varchar(255), genero varchar(255) not null, primary key (id)) engine=InnoDB;
alter table cuenta add constraint UKpj7ncg765kt4klndu25bwbwe4 unique (numero_cuenta);
alter table persona add constraint UKr5vsms84ih2viwd6tatk9o5pq unique (identificacion);
alter table cuenta add constraint FKlxjuylbfwqesghtnemd5dbldj foreign key (cliente_id) references persona (id);
alter table movimiento add constraint FK4ea11fe7p3xa1kwwmdgi9f2fi foreign key (cuenta_id) references cuenta (id);
