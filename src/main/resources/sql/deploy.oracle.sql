
CREATE TABLE db_corporation (
  id varchar(32) NOT NULL,
  name varchar(200) NOT NULL,
  host varchar(200) NOT NULL,
  schema varchar(32) NOT NULL,
  create_time date NOT NULL,
  update_time date NOT NULL,
  PRIMARY KEY (id)
) ;

create index schema on db_corporation (schema);

CREATE TABLE db_pool (
  id varchar2(50) NOT NULL,
  name varchar2(200) NOT NULL,
  host varchar2(100) DEFAULT NULL,
  port number(11) DEFAULT NULL,
  url varchar2(200) NOT NULL,
  username varchar2(50) NOT NULL,
  password varchar2(50) NOT NULL,
  initial_size number(11)  default 5 not null,
  min_size number(11)  default 1 not null,
  max_size number(11)  default 20 not null,
  create_time date DEFAULT NULL,
  update_time date DEFAULT NULL,
  PRIMARY KEY (id)
) ;



CREATE TABLE db_schema (
  id varchar2(32) NOT NULL,
  name varchar2(200) DEFAULT NULL,
  host varchar2(200) DEFAULT NULL,
  corporation varchar2(50) DEFAULT NULL,
  write_pool varchar2(37) DEFAULT NULL,
  read_pool varchar2(37) DEFAULT NULL,
  create_time date DEFAULT NULL,
  update_time date DEFAULT NULL,
  PRIMARY KEY (id)
) ;


alter table db_schema
  add constraint db_schema_ibfk_1 foreign key (write_pool)
  references db_pool (ID) on delete cascade;
alter table db_schema
  add constraint db_schema_ibfk_2 foreign key (read_pool)
  references db_pool (ID) on delete cascade;


  create index write_pool on db_schema (write_pool);
  create index read_pool on db_schema (read_pool);