
 CREATE TABLE if not exists fr_element (
  id varchar(32) NOT NULL,
  name varchar(100) NOT NULL,
  data_type varchar(10) NOT NULL,
  view_type varchar(10),
  options varchar(2000),
  default_value varchar(2000),
  required int(11),
  min_length int(11),
  max_length int(11),
  regex varchar(100),
  catelog varchar(32),
  update_time datetime,
  admin varchar(32),
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 CREATE TABLE if not exists fr_form (
  id varchar(32) NOT NULL,
  name varchar(100) NOT NULL,
  title varchar(32),
  info varchar(600),
  catelog varchar(32),
  update_time datetime,
  admin varchar(32),
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 CREATE TABLE if not exists fr_form_element (
  id varchar(32) NOT NULL,
  name varchar(50),
  form_id varchar(32) NOT NULL,
  foreign_form varchar(32),
  foreign_key varchar(32),
  element_id varchar(32) NOT NULL,
  list int(11),
  search int(11),
  order_no int(11),
  update_time datetime,
  admin varchar(32),
  PRIMARY KEY  (id),
  KEY list (id,list),
  KEY element (element_id),
  KEY form_id (form_id),
  CONSTRAINT fr_form_element_ibfk_1 FOREIGN KEY (form_id) REFERENCES fr_form (id) ON DELETE CASCADE,
  CONSTRAINT fr_form_element_ibfk_2 FOREIGN KEY (element_id) REFERENCES fr_element (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 CREATE TABLE if not exists fr_form_value (
  id varchar(32) NOT NULL,
  item_id varchar(32) NOT NULL,
  element_id varchar(32) NOT NULL,
  PRIMARY KEY  (id),
  KEY item_id (item_id),
  KEY element_id (element_id),
  CONSTRAINT fr_form_value_ibfk_1 FOREIGN KEY (element_id) REFERENCES fr_form_element (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 CREATE TABLE if not exists fr_form_tinyvalue (
  id varchar(32) NOT NULL,
  value varchar(1000),
  PRIMARY KEY  (id),
  CONSTRAINT fr_form_tinyvalue_ibfk_1 FOREIGN KEY (id) REFERENCES fr_form_value (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 CREATE TABLE if not exists fr_form_clobvalue (
  id varchar(32) NOT NULL,
  value longtext,
  PRIMARY KEY  (id),
  CONSTRAINT fr_form_clobvalue_ibfk_1 FOREIGN KEY (id) REFERENCES fr_form_value (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;










 CREATE TABLE if not exists s_attachment (
  ID varchar(50) NOT NULL,
  CATALOG varchar(60),
  TITLE varchar(100),
  INFO varchar(200),
  FILENAME varchar(300),
  FILESIZE decimal(8,0),
  MIMETYPE varchar(100),
  FILEEXT varchar(12),
  PATH varchar(200),
  UPDATE_TIME datetime,
  ADMIN varchar(50),
  PRIMARY KEY  (ID),
  KEY CATALOG (CATALOG)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 CREATE TABLE if not exists s_attach_reference (
  ID varchar(50) NOT NULL,
  ATTACH_ID varchar(50),
  OBJECT_ID varchar(50),
  CATALOG varchar(100),
  UPDATE_TIME datetime,
  ADMIN varchar(50),
  PRIMARY KEY  (ID),
  KEY FK_REFERENCE_ATTACHMENT (ATTACH_ID),
  CONSTRAINT s_attach_reference_ibfk_1 FOREIGN KEY (ATTACH_ID) REFERENCES s_attachment (ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 CREATE TABLE if not exists s_catalog (
  id varchar(50) NOT NULL,
  admin varchar(50) DEFAULT NULL,
  alias varchar(300) DEFAULT NULL,
  description longtext,
  display int(11) NOT NULL,
  name varchar(300) DEFAULT NULL,
  status int(11) NOT NULL,
  type int(11) NOT NULL,
  update_time datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists s_catalog_tree (
  id varchar(50) NOT NULL,
  depth int(11) NOT NULL,
  id_layer varchar(2000) DEFAULT NULL,
  order_no int(11) DEFAULT NULL,
  parent_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




 CREATE TABLE if not exists s_log (
  ID varchar(50) NOT NULL,
  LOG_TIME datetime,
  ADMIN varchar(50),
  CATALOG varchar(100) NOT NULL,
  ACTION varchar(50) NOT NULL,
  OBJECT_ID varchar(100),
  INFO varchar(1000),
  PRIMARY KEY  (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






 CREATE TABLE if not exists s_mail (
  ID varchar(50) NOT NULL,
  title varchar(200),
  content mediumtext,
  mail varchar(2000),
  CC varchar(2000),
  BCC varchar(2000),
  SEND_DATE datetime,
  UPDATE_DATE datetime NOT NULL,
  UPDATE_USER varchar(20) NOT NULL,
  PRIMARY KEY  (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE if not exists s_id_generator(
  id varchar(50) NOT NULL,
  description varchar(500) DEFAULT NULL,
  expression varchar(200) DEFAULT NULL,
  name varchar(150) DEFAULT NULL,
  section varchar(300) DEFAULT NULL,
  spliter varchar(1) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE if not exists s_id_sequence(
  id varchar(50) NOT NULL,
  next_val bigint(20) DEFAULT NULL,
  step int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE if not exists s_id_generator(
  id varchar(50) NOT NULL,
  description varchar(500) DEFAULT NULL,
  expression varchar(200) DEFAULT NULL,
  name varchar(150) DEFAULT NULL,
  section varchar(300) DEFAULT NULL,
  spliter varchar(1) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists s_setting (
  id varchar(50) NOT NULL,
  name varchar(50) DEFAULT NULL,
  catalog varchar(50) NOT NULL,
  setting_key varchar(50) DEFAULT NULL,
  stringValue varchar(500) DEFAULT NULL,
  numberValue decimal(20,7) DEFAULT NULL,
  objectBytes blob,
  type varchar(100) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists s_owner_config (
  id varchar(50) NOT NULL,
  module varchar(50) NOT NULL,
  entity varchar(50) NOT NULL,
  default_owner varchar(50) NOT NULL,
  owner_view int(11) NOT NULL,
  owner_edit int(11) NOT NULL,
  parent_view int(11) NOT NULL,
  parent_edit int(11) NOT NULL,
  child_view int(11) NOT NULL,
  bu_role_id varchar(50) DEFAULT NULL,
  org_role_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE  if not exists s_catalog_owner (
  id varchar(50) NOT NULL,
  data_id varchar(50) NOT NULL,
  creator_id varchar(50) DEFAULT NULL,
  owner_id varchar(100) DEFAULT NULL,
  owner_name varchar(100) DEFAULT NULL,
  owner_type varchar(100) DEFAULT NULL,
  writable int(11) NOT NULL,
  auth_time datetime DEFAULT NULL,
  auth_begin_time datetime DEFAULT NULL,
  auth_end_time datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKE5E20C4137E42A7A (data_id),
  CONSTRAINT FKE5E20C4137E42A7A FOREIGN KEY (data_id) REFERENCES s_catalog (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE  if not exists  s_idg_owner (
  id varchar(50) NOT NULL,
  data_id varchar(50) NOT NULL,
  creator_id varchar(50) DEFAULT NULL,
  owner_id varchar(100) DEFAULT NULL,
  owner_name varchar(100) DEFAULT NULL,
  owner_type varchar(100) DEFAULT NULL,
  writable int(11) NOT NULL,
  auth_time datetime DEFAULT NULL,
  auth_begin_time datetime DEFAULT NULL,
  auth_end_time datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKE5E20C4137E42A7B (data_id),
  CONSTRAINT FKE5E20C4137E42A7B FOREIGN KEY (data_id) REFERENCES s_id_generator (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
