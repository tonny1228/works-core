CREATE TABLE a_system_resource (
  id varchar2(50) NOT NULL,
  admin varchar2(50) DEFAULT NULL,
  api varchar2(200) DEFAULT NULL,
  description varchar2(500) DEFAULT NULL,
  name varchar2(50) NOT NULL,
  package_name varchar2(200) DEFAULT NULL,
  type number(11) NOT NULL,
  update_time date DEFAULT NULL,
  url varchar2(300) DEFAULT NULL,
  parent_id varchar2(50) DEFAULT NULL,
  PRIMARY KEY  (ID)
);


CREATE TABLE a_privilege (
  id varchar2(50) NOT NULL,
  admin varchar2(50) DEFAULT NULL,
  name varchar2(100) NOT NULL,
  update_time date DEFAULT NULL,
  PRIMARY KEY  (ID)
);


CREATE TABLE a_privilege_resource (
  privilege_id varchar2(50) NOT NULL,
  resource_id varchar2(50) NOT NULL,
  PRIMARY KEY  (RESOURCE_ID,PRIVILEGE_ID)
);


alter table a_privilege_resource
  add constraint a_privilege_resource_ibfk_1 foreign key (RESOURCE_ID)
  references a_system_resource (ID) on delete cascade;

alter table a_privilege_resource
  add constraint a_privilege_resource_ibfk_2 foreign key (PRIVILEGE_ID)
  references a_privilege (ID) on delete cascade;

  create index privilege_resource_id on a_privilege_resource (PRIVILEGE_ID);


CREATE TABLE u_department (
  id varchar2(50) NOT NULL,
  code varchar2(50) DEFAULT NULL,
  position_id varchar2(50) DEFAULT NULL,
  description varchar2(500) DEFAULT NULL,
  name varchar2(100) DEFAULT NULL,
  properties varchar2(500) DEFAULT NULL,
  type varchar2(50) DEFAULT NULL,
  dp_level number(5) DEFAULT 0,
  parent_id varchar2(50) DEFAULT NULL,
  PRIMARY KEY (id)
);

alter table u_department
  add constraint FKF79D2D5C45CF1FEA foreign key (parent_id)
  references u_department (id) on delete cascade;

  create index FKF79D2D5C45CF1FEA on u_department (parent_id);


CREATE TABLE u_department_tree (
  id varchar2(50) NOT NULL,
  depth number(11) NOT NULL,
  id_layer varchar2(2000) DEFAULT NULL,
  order_no number(11) DEFAULT NULL,
  parent_id varchar2(50) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE u_business_unit (
  id varchar2(50) NOT NULL,
  ID_LAYER varchar2(300) DEFAULT NULL,
  type varchar2(2) DEFAULT NULL,
  dept_id varchar2(50) NOT NULL,
  parent_id varchar2(50) DEFAULT NULL,
  PRIMARY KEY (id)
);

alter table u_business_unit
  add constraint FK8240C592711806F foreign key (dept_id)
  references u_department (id) on delete cascade;

alter table u_business_unit
  add constraint FK8240C5945CF1FEA foreign key (parent_id)
  references u_department (id) on delete cascade;

  create index FK8240C592711806F on u_business_unit (dept_id);

  create index FK8240C5945CF1FEA on u_business_unit (parent_id);




CREATE TABLE u_job (
  ID varchar2(50) NOT NULL,
  NAME varchar2(200) NOT NULL,
  INFO varchar2(500),
  ORDER_NO number(11),
  PRIMARY KEY  (ID)
);


CREATE TABLE u_job_level (
  ID varchar2(50) NOT NULL,
  JOB_ID varchar2(50) NOT NULL,
  NAME varchar2(200) NOT NULL,
  INFO varchar2(500),
  JLEVEL number(11),
  PRIMARY KEY  (ID)
);

alter table u_job_level
  add constraint u_job_level_ibfk_1 foreign key (JOB_ID)
  references u_job (id) on delete cascade;

  create index FK_REFERENCE_8 on u_job_level (JOB_ID);


CREATE TABLE u_member (
  id varchar2(50) NOT NULL,
  address varchar2(200) DEFAULT NULL,
  birthday date DEFAULT NULL,
  email varchar2(100) DEFAULT NULL,
  info varchar2(1000) DEFAULT NULL,
  mobile_no varchar2(100) DEFAULT NULL,
  name varchar2(50) DEFAULT NULL,
  order_no number(11) DEFAULT NULL,
  password varchar2(50) DEFAULT NULL,
  reg_time date DEFAULT NULL,
  sex varchar2(3) DEFAULT NULL,
  status varchar2(20) DEFAULT NULL,
  tel_no varchar2(100) DEFAULT NULL,
  username varchar2(50) DEFAULT NULL,
  zip varchar2(50) DEFAULT NULL,
  answer varchar2(300) DEFAULT NULL,
  id_no varchar2(50) DEFAULT NULL,
  no varchar2(50) DEFAULT NULL,
  question varchar2(300) DEFAULT NULL,
  score number(11) NOT NULL,
  user_id varchar2(50) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE u_position (
  id varchar2(50) NOT NULL,
  description varchar2(500) DEFAULT NULL,
  name varchar2(100) NOT NULL,
  dept_id varchar2(50) DEFAULT NULL,
  job_level_id varchar2(50) DEFAULT NULL,
  PRIMARY KEY (id)
);

alter table u_position
  add constraint FKD7887DB32711806F foreign key (dept_id)
  references u_department (id) on delete cascade;
alter table u_position
  add constraint FKD7887DB39658CD87 foreign key (job_level_id)
  references u_job_level (id) on delete cascade;

  create index FKD7887DB32711806F on u_position (dept_id);
  create index FKD7887DB39658CD87 on u_position (job_level_id);


CREATE TABLE u_position_tree (
  id varchar2(50) NOT NULL,
  depth number(11) NOT NULL,
  id_layer varchar2(2000) DEFAULT NULL,
  order_no number(11) DEFAULT NULL,
  parent_id varchar2(50) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE u_role (
  ID varchar2(50) NOT NULL,
  NAME varchar2(100) NOT NULL,
  DESCRIPTION varchar2(500),
  PRIMARY KEY  (ID)
);






CREATE TABLE u_title (
  ID varchar2(50) NOT NULL,
  NAME varchar2(200) NOT NULL,
  INFO varchar2(500),
  ORDER_NO number(11),
  PRIMARY KEY  (ID)
);




CREATE TABLE u_user (
  id varchar2(50) NOT NULL,
  address varchar2(200) DEFAULT NULL,
  birthday date DEFAULT NULL,
  email varchar2(100) DEFAULT NULL,
  info varchar2(1000) DEFAULT NULL,
  mobile_no varchar2(100) DEFAULT NULL,
  name varchar2(50) DEFAULT NULL,
  order_no number(11) DEFAULT NULL,
  password varchar2(50) DEFAULT NULL,
  reg_time date DEFAULT NULL,
  sex varchar2(3) DEFAULT NULL,
  status varchar2(20) DEFAULT NULL,
  tel_no varchar2(100) DEFAULT NULL,
  username varchar2(50) DEFAULT NULL,
  zip varchar2(50) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE u_user_info (
  user_id varchar2(50) NOT NULL,
  position_id varchar2(50) DEFAULT NULL,
  title_id varchar2(50) DEFAULT NULL,
  PRIMARY KEY (user_id)
);


alter table u_user_info
  add constraint FKC7FE85583BEE2A3D foreign key (user_id)
  references u_user (id) on delete cascade;
alter table u_user_info
  add constraint FKC7FE855852E3F2E2 foreign key (position_id)
  references u_position (id) on delete cascade;
alter table u_user_info
  add constraint FKC7FE85588B79C9F2 foreign key (title_id)
  references u_title (id) on delete cascade;

  create index FKC7FE855852E3F2E2 on u_user_info (position_id);
  create index FKC7FE85588B79C9F2 on u_user_info (title_id);




CREATE TABLE u_user_position (
  user_id varchar2(50) NOT NULL,
  position_id varchar2(50) NOT NULL,
  PRIMARY KEY (user_id,position_id)
);


alter table u_user_position
  add constraint FK122B54D315D652F0 foreign key (user_id)
  references u_user_info (user_id) on delete cascade;
alter table u_user_position
  add constraint FK122B54D33BEE2A3D foreign key (user_id)
  references u_user (id) on delete cascade;
alter table u_user_position
  add constraint FK122B54D352E3F2E2 foreign key (position_id)
  references u_position (id) on delete cascade;


  create index FK122B54D352E3F2E2 on u_user_position (position_id);
  create index FK122B54D315D652F0 on u_user_position (user_id);






CREATE TABLE u_user_role (
  USER_ID varchar2(50) NOT NULL,
  ROLE_ID varchar2(50) NOT NULL,
  PRIMARY KEY  (USER_ID,ROLE_ID)
);


alter table u_user_role
  add constraint u_user_role_ibfk_1 foreign key (ROLE_ID)
  references u_role (ID) on delete cascade;
alter table u_user_role
  add constraint u_user_role_ibfk_2 foreign key (USER_ID)
  references u_user (ID) on delete cascade;

  create index FK_USERROLE_ROLE on u_user_role (ROLE_ID);


CREATE TABLE a_department_privilege (
  id varchar2(50) NOT NULL,
  privilege_id varchar2(50) NOT NULL,
  dept_id varchar2(50) NOT NULL,
  PRIMARY KEY (id)
);

alter table a_department_privilege
  add constraint FK53C287222711806F foreign key (dept_id)
  references u_department (id) on delete cascade;
alter table a_department_privilege
  add constraint FK53C28722CB8B9D97 foreign key (privilege_id)
  references a_privilege (id) on delete cascade;

  create index FK53C287222711806F on a_department_privilege (dept_id);
  create index FK53C28722CB8B9D97 on a_department_privilege (privilege_id);

CREATE TABLE a_position_privilege (
  id varchar2(50) NOT NULL,
  POSITION_ID varchar2(50) NOT NULL,
  PRIVILEGE_ID varchar2(50) NOT NULL,
  PRIMARY KEY  (id)
);

alter table a_position_privilege
  add constraint a_position_privilege_ibfk_1 foreign key (POSITION_ID)
  references u_position (id) on delete cascade;
alter table a_position_privilege
  add constraint a_position_privilege_ibfk_2 foreign key (PRIVILEGE_ID)
  references a_privilege (id) on delete cascade;

  create index a_position_position_id on a_position_privilege (POSITION_ID);
  create index a_position_privilege_id on a_position_privilege (PRIVILEGE_ID);


CREATE TABLE a_role_privilege (
  id varchar2(50) NOT NULL,
  ROLE_ID varchar2(50) NOT NULL,
  PRIVILEGE_ID varchar2(50) NOT NULL,
  PRIMARY KEY  (id)
);


alter table a_role_privilege
  add constraint a_role_privilege_ibfk_1 foreign key (ROLE_ID)
  references u_role (id) on delete cascade;
alter table a_role_privilege
  add constraint a_role_privilege_ibfk_2 foreign key (PRIVILEGE_ID)
  references a_privilege (id) on delete cascade;

  create index role_privilege_role_id on a_role_privilege (ROLE_ID);
  create index role_privilege_id on a_role_privilege (PRIVILEGE_ID);



CREATE TABLE a_user_privilege (
  id varchar2(50) NOT NULL,
  USER_ID varchar2(50) NOT NULL,
  PRIVILEGE_ID varchar2(50) NOT NULL,
  PRIMARY KEY  (id)
);


alter table a_user_privilege
  add constraint a_user_privilege_ibfk_1 foreign key (USER_ID)
  references u_user (id) on delete cascade;
alter table a_user_privilege
  add constraint a_user_privilege_ibfk_2 foreign key (PRIVILEGE_ID)
  references a_privilege (id) on delete cascade;

  create index user_privilege_user_id on a_user_privilege (USER_ID);
  create index user_privilege_id on a_user_privilege (PRIVILEGE_ID);


 CREATE TABLE s_catalog_auth (
  id varchar2(50) NOT NULL,
  catalog_layer varchar2(1000) DEFAULT NULL,
  edit number(1) NOT NULL,
  extend number(1) NOT NULL,
  operator varchar2(50) DEFAULT NULL,
  reading number(1) DEFAULT NULL,
  remove number(1) NOT NULL,
  update_time date DEFAULT NULL,
  verify number(1) NOT NULL,
  catalog_id varchar2(50) DEFAULT NULL,
  role_id varchar2(50) DEFAULT NULL,
  PRIMARY KEY (id)
);

alter table s_catalog_auth
  add constraint FKB4CF5A5A64102FAB foreign key (catalog_id)
  references s_catalog (id) on delete cascade;
alter table s_catalog_auth
  add constraint FKB4CF5A5A96C3665D foreign key (role_id)
  references u_role (id) on delete cascade;

  create index FKB4CF5A5A96C3665D on s_catalog_auth (role_id);
  create index FKB4CF5A5A64102FAB on s_catalog_auth (catalog_id);



