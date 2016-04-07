CREATE TABLE if not exists a_system_resource (
  id varchar(50) NOT NULL,
  admin varchar(50) DEFAULT NULL,
  api varchar(200) DEFAULT NULL,
  description varchar(500) DEFAULT NULL,
  name varchar(50) NOT NULL,
  package_name varchar(200) DEFAULT NULL,
  type int(11) NOT NULL,
  update_time datetime DEFAULT NULL,
  url varchar(300) DEFAULT NULL,
  parent_id varchar(50) DEFAULT NULL,
  PRIMARY KEY  (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists a_privilege (
  id varchar(50) NOT NULL,
  admin varchar(50) DEFAULT NULL,
  name varchar(100) NOT NULL,
  update_time datetime DEFAULT NULL,
  PRIMARY KEY  (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists a_privilege_resource (
  privilege_id varchar(50) NOT NULL,
  resource_id varchar(50) NOT NULL,
  PRIMARY KEY  (RESOURCE_ID,PRIVILEGE_ID),
  KEY PRIVILEGE_ID (PRIVILEGE_ID),
  CONSTRAINT a_privilege_resource_ibfk_1 FOREIGN KEY (RESOURCE_ID) REFERENCES a_system_resource (ID) ON DELETE CASCADE,
  CONSTRAINT a_privilege_resource_ibfk_2 FOREIGN KEY (PRIVILEGE_ID) REFERENCES a_privilege (ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists u_department (
  id varchar(50) NOT NULL,
  code varchar(50) DEFAULT NULL,
  position_id varchar(50) DEFAULT NULL,
  description varchar(500) DEFAULT NULL,
  name varchar(100) DEFAULT NULL,
  properties varchar(500) DEFAULT NULL,
  type varchar(50) DEFAULT NULL,
  dp_level int DEFAULT 0,
  parent_id varchar(50) DEFAULT NULL,
  create_time datetime default null,
  admin varchar(50) default null,
  PRIMARY KEY (id),
  KEY FKF79D2D5C45CF1FEA (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE if not exists u_department_tree (
  id varchar(50) NOT NULL,
  depth int(11) NOT NULL,
  id_layer varchar(2000) DEFAULT NULL,
  order_no int(11) DEFAULT NULL,
  parent_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists u_business_unit (
  id varchar(50) NOT NULL,
  ID_LAYER varchar(300) DEFAULT NULL,
  type varchar(2) DEFAULT NULL,
  dept_id varchar(50) NOT NULL,
  parent_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK8240C592711806F (dept_id),
  KEY FK8240C5945CF1FEA (parent_id),
  CONSTRAINT FK8240C592711806F FOREIGN KEY (dept_id) REFERENCES u_department (id),
  CONSTRAINT FK8240C5945CF1FEA FOREIGN KEY (parent_id) REFERENCES u_department (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE if not exists u_job (
  ID varchar(50) NOT NULL,
  NAME varchar(200) NOT NULL,
  INFO varchar(500),
  ORDER_NO int(11),
  PRIMARY KEY  (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists u_job_level (
  ID varchar(50) NOT NULL,
  JOB_ID varchar(50) NOT NULL,
  NAME varchar(200) NOT NULL,
  INFO varchar(500),
  JLEVEL int(11),
  PRIMARY KEY  (ID),
  KEY FK_REFERENCE_8 (JOB_ID),
  CONSTRAINT u_job_level_ibfk_1 FOREIGN KEY (JOB_ID) REFERENCES u_job (ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists u_member (
  id varchar(50) NOT NULL,
  address varchar(200) DEFAULT NULL,
  birthday date DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  info varchar(1000) DEFAULT NULL,
  mobile_no varchar(100) DEFAULT NULL,
  name varchar(50) DEFAULT NULL,
  order_no int(11) DEFAULT NULL,
  password varchar(50) DEFAULT NULL,
  reg_time datetime DEFAULT NULL,
  sex varchar(2) DEFAULT NULL,
  status varchar(20) DEFAULT NULL,
  tel_no varchar(100) DEFAULT NULL,
  username varchar(50) DEFAULT NULL,
  zip varchar(50) DEFAULT NULL,
  answer varchar(300) DEFAULT NULL,
  id_no varchar(50) DEFAULT NULL,
  no varchar(50) DEFAULT NULL,
  question varchar(300) DEFAULT NULL,
  score int(11) NOT NULL,
  user_id varchar(50) DEFAULT NULL,
  nickname varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists u_position (
  id varchar(50) NOT NULL,
  description varchar(500) DEFAULT NULL,
  name varchar(100) NOT NULL,
  dept_id varchar(50) DEFAULT NULL,
  job_level_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKD7887DB32711806F (dept_id),
  KEY FKD7887DB39658CD87 (job_level_id),
  CONSTRAINT FKD7887DB32711806F FOREIGN KEY (dept_id) REFERENCES u_department (id),
  CONSTRAINT FKD7887DB39658CD87 FOREIGN KEY (job_level_id) REFERENCES u_job_level (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists u_position_tree (
  id varchar(50) NOT NULL,
  depth int(11) NOT NULL,
  id_layer varchar(2000) DEFAULT NULL,
  order_no int(11) DEFAULT NULL,
  parent_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists u_role (
  ID varchar(50) NOT NULL COMMENT '角色唯一编号',
  NAME varchar(100) NOT NULL COMMENT '角色名',
  DESCRIPTION varchar(500) COMMENT '角色描述',
  PRIMARY KEY  (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






CREATE TABLE if not exists u_title (
  ID varchar(50) NOT NULL,
  NAME varchar(200) NOT NULL,
  INFO varchar(500),
  ORDER_NO int(11),
  PRIMARY KEY  (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE if not exists u_user (
  id varchar(50) NOT NULL,
  address varchar(200) DEFAULT NULL,
  birthday date DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  info varchar(1000) DEFAULT NULL,
  mobile_no varchar(100) DEFAULT NULL,
  name varchar(50) DEFAULT NULL,
  order_no int(11) DEFAULT NULL,
  password varchar(50) DEFAULT NULL,
  reg_time datetime DEFAULT NULL,
  sex varchar(2) DEFAULT NULL,
  status varchar(20) DEFAULT NULL,
  tel_no varchar(100) DEFAULT NULL,
  username varchar(50) DEFAULT NULL,
  zip varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists u_user_info (
  user_id varchar(50) NOT NULL,
  position_id varchar(50) DEFAULT NULL,
  title_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (user_id),
  KEY FKC7FE855852E3F2E2 (position_id),
  KEY FKC7FE85583BEE2A3D (user_id),
  KEY FKC7FE85588B79C9F2 (title_id),
  CONSTRAINT FKC7FE85583BEE2A3D FOREIGN KEY (user_id) REFERENCES u_user (id),
  CONSTRAINT FKC7FE855852E3F2E2 FOREIGN KEY (position_id) REFERENCES u_position (id),
  CONSTRAINT FKC7FE85588B79C9F2 FOREIGN KEY (title_id) REFERENCES u_title (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists u_user_position (
  user_id varchar(50) NOT NULL,
  position_id varchar(50) NOT NULL,
  PRIMARY KEY (user_id,position_id),
  KEY FK122B54D352E3F2E2 (position_id),
  KEY FK122B54D315D652F0 (user_id),
  CONSTRAINT FK122B54D315D652F0 FOREIGN KEY (user_id) REFERENCES u_user_info (user_id),
  CONSTRAINT FK122B54D33BEE2A3D FOREIGN KEY (user_id) REFERENCES u_user (id),
  CONSTRAINT FK122B54D352E3F2E2 FOREIGN KEY (position_id) REFERENCES u_position (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists u_user_role (
  USER_ID varchar(50) NOT NULL,
  ROLE_ID varchar(50) NOT NULL,
  PRIMARY KEY  (USER_ID,ROLE_ID),
  KEY FK_USERROLE_ROLE (ROLE_ID),
  CONSTRAINT u_user_role_ibfk_1 FOREIGN KEY (ROLE_ID) REFERENCES u_role (ID) ON DELETE CASCADE,
  CONSTRAINT u_user_role_ibfk_2 FOREIGN KEY (USER_ID) REFERENCES u_user (ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists a_department_privilege (
  id varchar(50) NOT NULL,
  privilege_id varchar(50) NOT NULL,
  dept_id varchar(50) NOT NULL,
  PRIMARY KEY (id),
  KEY FK53C287222711806F (dept_id),
  KEY FK53C28722CB8B9D97 (privilege_id),
  CONSTRAINT FK53C287222711806F FOREIGN KEY (dept_id) REFERENCES u_department (id),
  CONSTRAINT FK53C28722CB8B9D97 FOREIGN KEY (privilege_id) REFERENCES a_privilege (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists a_position_privilege (
  id varchar(50) NOT NULL,
  POSITION_ID varchar(50) NOT NULL,
  PRIVILEGE_ID varchar(50) NOT NULL,
  PRIMARY KEY  (id),
  KEY POSITION_ID (POSITION_ID),
  KEY PRIVILEGE_ID (PRIVILEGE_ID),
  CONSTRAINT a_position_privilege_ibfk_1 FOREIGN KEY (POSITION_ID) REFERENCES u_position (ID) ON DELETE CASCADE,
  CONSTRAINT a_position_privilege_ibfk_2 FOREIGN KEY (PRIVILEGE_ID) REFERENCES a_privilege (ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists a_role_privilege (
  id varchar(50) NOT NULL,
  ROLE_ID varchar(50) NOT NULL,
  PRIVILEGE_ID varchar(50) NOT NULL,
  PRIMARY KEY  (id),
  KEY ROLE_ID (ROLE_ID),
  KEY PRIVILEGE_ID (PRIVILEGE_ID),
  CONSTRAINT a_role_privilege_ibfk_1 FOREIGN KEY (ROLE_ID) REFERENCES u_role (ID) ON DELETE CASCADE,
  CONSTRAINT a_role_privilege_ibfk_2 FOREIGN KEY (PRIVILEGE_ID) REFERENCES a_privilege (ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists a_user_privilege (
  id varchar(50) NOT NULL,
  USER_ID varchar(50) NOT NULL,
  PRIVILEGE_ID varchar(50) NOT NULL,
  PRIMARY KEY  (id),
  KEY USER_ID (USER_ID),
  KEY PRIVILEGE_ID (PRIVILEGE_ID),
  CONSTRAINT a_user_privilege_ibfk_1 FOREIGN KEY (USER_ID) REFERENCES u_user (ID) ON DELETE CASCADE,
  CONSTRAINT a_user_privilege_ibfk_2 FOREIGN KEY (PRIVILEGE_ID) REFERENCES a_privilege (ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




 CREATE TABLE if not exists s_catalog_auth (
  id varchar(50) NOT NULL,
  catalog_layer varchar(1000) DEFAULT NULL,
  edit bit(1) NOT NULL,
  extend bit(1) NOT NULL,
  operator varchar(50) DEFAULT NULL,
  reading bit(1) DEFAULT NULL,
  remove bit(1) NOT NULL,
  update_time datetime DEFAULT NULL,
  verify bit(1) NOT NULL,
  catalog_id varchar(50) DEFAULT NULL,
  role_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKB4CF5A5A96C3665D (role_id),
  KEY FKB4CF5A5A64102FAB (catalog_id),
  CONSTRAINT FKB4CF5A5A64102FAB FOREIGN KEY (catalog_id) REFERENCES s_catalog (id),
  CONSTRAINT FKB4CF5A5A96C3665D FOREIGN KEY (role_id) REFERENCES u_role (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





create table if not exists ue_login
(
   id                   varchar(50) not null,
   username             varchar(50) not null,
   user_id              varchar(50),
   user_type            varchar(20),
   user_agent           varchar(200),
   os                   varchar(50),
   device_id            varchar(100),
   network_mode         varchar(20),
   ip                   varchar(20),
   event_time           datetime not null,
   logout_time          datetime,
   logout_type          int,
   info                 varchar(500),
   name                 varchar(50),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE if not exists ue_operating (
  id varchar(50) NOT NULL,
  title varchar(255) DEFAULT NULL,
  type varchar(255) DEFAULT NULL,
  detail varchar(255) DEFAULT NULL,
  data_id varchar(255) DEFAULT NULL,
  username varchar(50) DEFAULT NULL,
  name varchar(50) DEFAULT NULL,
  user_id varchar(50) DEFAULT NULL,
  user_type varchar(50) DEFAULT NULL,
  event_time datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE if not exists ue_reading_count (
  id varchar(50) NOT NULL,
  main_function varchar(50) DEFAULT NULL,
  sub_function varchar(50) DEFAULT NULL,
  data_id varchar(50) NOT NULL,
  num int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id (id),
  UNIQUE KEY id_2 (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists u_user_owner (
  id varchar(50) NOT NULL,
  data_id varchar(50) NOT NULL,
  creator_id varchar(50) DEFAULT NULL,
  owner_id varchar(100) DEFAULT NULL,
  owner_name varchar(100) DEFAULT NULL,
  owner_type varchar(100) DEFAULT NULL,
  writable int(11) NOT NULL,
  auth_begin_ime datetime DEFAULT NULL,
  auth_end_time datetime DEFAULT NULL,
  auth_time datetime DEFAULT NULL,
  auth_begin_time datetime DEFAULT NULL,
  authn_time datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKE5E20C4137E42A7A (data_id),
  CONSTRAINT FK382AE7499AB8A5FE FOREIGN KEY (data_id) REFERENCES u_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
