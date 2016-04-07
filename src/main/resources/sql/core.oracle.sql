
 CREATE TABLE  fr_element (
  id varchar2(32) NOT NULL,
  name varchar2(100) NOT NULL,
  data_type varchar2(10) NOT NULL,
  view_type varchar2(10),
  options varchar2(2000),
  default_value varchar2(2000),
  required number(11),
  min_length number(11),
  max_length number(11),
  regex varchar2(100),
  catelog varchar2(32),
  update_time date,
  admin varchar2(32),
  PRIMARY KEY  (id)
) ;


 CREATE TABLE  fr_form (
  id varchar2(32) NOT NULL,
  name varchar2(100) NOT NULL,
  title varchar2(32),
  info varchar2(600),
  catelog varchar2(32),
  update_time date,
  admin varchar2(32),
  PRIMARY KEY  (id)
) ;


 CREATE TABLE  fr_form_element (
  id varchar2(32) NOT NULL,
  name varchar2(50),
  form_id varchar2(32) NOT NULL,
  foreign_form varchar2(32),
  foreign_key varchar2(32),
  element_id varchar2(32) NOT NULL,
  list number(11),
  search number(11),
  order_no number(11),
  update_time date,
  admin varchar2(32),
  PRIMARY KEY  (id)
) ;


alter table fr_form_element
  add constraint fr_form_element_ibfk_1 foreign key (form_id)
  references fr_form (ID) on delete cascade;
alter table fr_form_element
  add constraint fr_form_element_ibfk_2 foreign key (element_id)
  references fr_element (ID) on delete cascade;

create index list on FR_FORM_ELEMENT (id, list);
create index element on FR_FORM_ELEMENT (element_id);
create index form_id on FR_FORM_ELEMENT (form_id);



 CREATE TABLE  fr_form_value (
  id varchar2(32) NOT NULL,
  item_id varchar2(32) NOT NULL,
  element_id varchar2(32) NOT NULL,
  PRIMARY KEY  (id)
) ;

alter table fr_form_value
  add constraint fr_form_value_ibfk_1 foreign key (element_id)
  references fr_form_element (id) on delete cascade;

create index item_id on fr_form_value (item_id);
create index element_id on fr_form_value (element_id);





 CREATE TABLE  fr_form_tinyvalue (
  id varchar2(32) NOT NULL,
  value varchar2(1000),
  PRIMARY KEY  (id)
) ;


alter table fr_form_tinyvalue
  add constraint fr_form_tinyvalue_ibfk_1 foreign key (id)
  references fr_form_value (id) on delete cascade;


 CREATE TABLE  fr_form_clobvalue (
  id varchar2(32) NOT NULL,
  value clob,
  PRIMARY KEY  (id)
) ;

alter table fr_form_clobvalue
  add constraint fr_form_clobvalue_ibfk_1 foreign key (id)
  references fr_form_value (id) on delete cascade;





 CREATE TABLE  s_attachment (
  ID varchar2(50) NOT NULL,
  CATALOG varchar2(60),
  TITLE varchar2(100),
  INFO varchar2(200),
  FILENAME varchar2(300),
  FILESIZE decimal(8,0),
  MIMETYPE varchar2(100),
  FILEEXT varchar2(12),
  PATH varchar2(200),
  UPDATE_TIME date,
  ADMIN varchar2(32),
  PRIMARY KEY  (ID)
) ;
create index idx_CATALOG_id on s_attachment (CATALOG);

 CREATE TABLE  s_attach_reference (
  ID varchar2(50) NOT NULL,
  ATTACH_ID varchar2(50),
  OBJECT_ID varchar2(50),
  CATALOG varchar2(100),
  UPDATE_TIME date,
  ADMIN varchar2(32),
  PRIMARY KEY  (ID)
) ;

alter table s_attach_reference
  add constraint s_attach_reference_ibfk_1 foreign key (ATTACH_ID)
  references s_attachment (id) on delete cascade;


 CREATE TABLE  s_catalog (
  id varchar2(50) NOT NULL,
  admin varchar2(50) DEFAULT NULL,
  alias varchar2(300) DEFAULT NULL,
  description clob,
  display number(11) NOT NULL,
  name varchar2(300) DEFAULT NULL,
  status number(11) NOT NULL,
  type number(11) NOT NULL,
  update_time date DEFAULT NULL,
  PRIMARY KEY (id)
) ;

CREATE TABLE  s_catalog_tree (
  id varchar2(50) NOT NULL,
  depth number(11) NOT NULL,
  id_layer varchar2(2000) DEFAULT NULL,
  order_no number(11) DEFAULT NULL,
  parent_id varchar2(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ;




 CREATE TABLE  s_log (
  ID varchar2(32) NOT NULL,
  LOG_TIME date,
  ADMIN varchar2(32),
  CATALOG varchar2(100) NOT NULL,
  ACTION varchar2(32) NOT NULL,
  OBJECT_ID varchar2(32),
  INFO varchar2(200),
  PRIMARY KEY  (ID)
) ;






 CREATE TABLE  s_mail (
  ID varchar2(50) NOT NULL,
  title varchar2(200),
  content clob,
  mail varchar2(2000),
  CC varchar2(2000),
  BCC varchar2(2000),
  SEND_DATE date,
  UPDATE_DATE date NOT NULL,
  UPDATE_USER varchar2(20) NOT NULL,
  PRIMARY KEY  (ID)
) ;



CREATE TABLE  s_id_generator(
  id varchar2(50) NOT NULL,
  description varchar2(500) DEFAULT NULL,
  expression varchar2(200) DEFAULT NULL,
  name varchar2(150) DEFAULT NULL,
  section varchar2(300) DEFAULT NULL,
  spliter varchar2(1) DEFAULT NULL,
  PRIMARY KEY (id)
) ;



CREATE TABLE  s_id_sequence(
  id varchar2(50) NOT NULL,
  next_val number(20) DEFAULT NULL,
  step number(11) NOT NULL,
  PRIMARY KEY (id)
) ;




CREATE TABLE  s_owner_config (
  id varchar2(50) NOT NULL,
  module varchar2(50) NOT NULL,
  entity varchar2(50) NOT NULL,
  default_owner varchar2(50) NOT NULL,
  owner_view number(11) NOT NULL,
  owner_edit number(11) NOT NULL,
  parent_view number(11) NOT NULL,
  parent_edit number(11) NOT NULL,
  child_view number(11) NOT NULL,
  bu_role_id varchar2(50) DEFAULT NULL,
  org_role_id varchar2(50) DEFAULT NULL,
  PRIMARY KEY (id)
);






CREATE TABLE s_catalog_owner (
  id varchar2(50) NOT NULL,
  data_id varchar2(50) NOT NULL,
  creator_id varchar2(50) DEFAULT NULL,
  owner_id varchar2(100) DEFAULT NULL,
  owner_name varchar2(100) DEFAULT NULL,
  owner_type varchar2(100) DEFAULT NULL,
  writable number(11) NOT NULL,
  auth_time date DEFAULT NULL,
  auth_begin_time date DEFAULT NULL,
  auth_end_time date DEFAULT NULL,
  PRIMARY KEY (id)
);
alter table s_catalog_owner
  add constraint FKE5E20C4137E42A7A foreign key (data_id)
  references s_catalog (id) on delete cascade;

