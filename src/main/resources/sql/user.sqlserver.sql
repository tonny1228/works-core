if exists (select 1
            from  sysindexes
           where  id    = object_id('a_position_privilege')
            and   name  = 'PRIVILEGE_ID'
            and   indid > 0
            and   indid < 255)
   drop index a_position_privilege.PRIVILEGE_ID
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('a_position_privilege')
            and   name  = 'POSITION_ID'
            and   indid > 0
            and   indid < 255)
   drop index a_position_privilege.POSITION_ID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('a_position_privilege')
            and   type = 'U')
   drop table a_position_privilege
go

if exists (select 1
            from  sysobjects
           where  id = object_id('a_privilege')
            and   type = 'U')
   drop table a_privilege
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('a_privilege_resource')
            and   name  = 'PRIVILEGE_ID'
            and   indid > 0
            and   indid < 255)
   drop index a_privilege_resource.PRIVILEGE_ID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('a_privilege_resource')
            and   type = 'U')
   drop table a_privilege_resource
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('a_role_privilege')
            and   name  = 'ROLE_ID'
            and   indid > 0
            and   indid < 255)
   drop index a_role_privilege.ROLE_ID
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('a_role_privilege')
            and   name  = 'PRIVILEGE_ID'
            and   indid > 0
            and   indid < 255)
   drop index a_role_privilege.PRIVILEGE_ID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('a_role_privilege')
            and   type = 'U')
   drop table a_role_privilege
go

if exists (select 1
            from  sysobjects
           where  id = object_id('a_system_resource')
            and   type = 'U')
   drop table a_system_resource
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('a_user_privilege')
            and   name  = 'USER_ID'
            and   indid > 0
            and   indid < 255)
   drop index a_user_privilege.USER_ID
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('a_user_privilege')
            and   name  = 'PRIVILEGE_ID'
            and   indid > 0
            and   indid < 255)
   drop index a_user_privilege.PRIVILEGE_ID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('a_user_privilege')
            and   type = 'U')
   drop table a_user_privilege
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('u_business_unit')
            and   name  = 'PARENT_ID'
            and   indid > 0
            and   indid < 255)
   drop index u_business_unit.PARENT_ID
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('u_business_unit')
            and   name  = 'DEPT_ID'
            and   indid > 0
            and   indid < 255)
   drop index u_business_unit.DEPT_ID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_business_unit')
            and   type = 'U')
   drop table u_business_unit
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('u_department')
            and   name  = 'u_department_ibfk_1'
            and   indid > 0
            and   indid < 255)
   drop index u_department.u_department_ibfk_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_department')
            and   type = 'U')
   drop table u_department
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_job')
            and   type = 'U')
   drop table u_job
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('u_job_level')
            and   name  = 'FK_REFERENCE_8'
            and   indid > 0
            and   indid < 255)
   drop index u_job_level.FK_REFERENCE_8
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_job_level')
            and   type = 'U')
   drop table u_job_level
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_member')
            and   type = 'U')
   drop table u_member
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('u_position')
            and   name  = 'FK_ROLE_DEPT'
            and   indid > 0
            and   indid < 255)
   drop index u_position.FK_ROLE_DEPT
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('u_position')
            and   name  = 'FK_REFERENCE_7'
            and   indid > 0
            and   indid < 255)
   drop index u_position.FK_REFERENCE_7
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_position')
            and   type = 'U')
   drop table u_position
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_role')
            and   type = 'U')
   drop table u_role
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_title')
            and   type = 'U')
   drop table u_title
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_user')
            and   type = 'U')
   drop table u_user
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('u_user_info')
            and   name  = 'FK_REFERENCE_11'
            and   indid > 0
            and   indid < 255)
   drop index u_user_info.FK_REFERENCE_11
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('u_user_info')
            and   name  = 'FK_REFERENCE_10'
            and   indid > 0
            and   indid < 255)
   drop index u_user_info.FK_REFERENCE_10
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_user_info')
            and   type = 'U')
   drop table u_user_info
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('u_user_position')
            and   name  = 'FK_POSITION'
            and   indid > 0
            and   indid < 255)
   drop index u_user_position.FK_POSITION
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_user_position')
            and   type = 'U')
   drop table u_user_position
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('u_user_role')
            and   name  = 'FK_USERROLE_ROLE'
            and   indid > 0
            and   indid < 255)
   drop index u_user_role.FK_USERROLE_ROLE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('u_user_role')
            and   type = 'U')
   drop table u_user_role
go

/*==============================================================*/
/* Table: a_position_privilege                                  */
/*==============================================================*/
create table a_position_privilege (
   id                   varchar(32)          not null,
   POSITION_ID          varchar(32)          not null,
   PRIVILEGE_ID         varchar(32)          not null,
   constraint PK_a_position_privilege primary key nonclustered (id)
)
go



/*==============================================================*/
/* Table: a_privilege                                           */
/*==============================================================*/
create table a_privilege (
   ID                   varchar(32)          not null,
   NAME                 varchar(100)         not null,
   UPDATE_TIME          datetime             null,
   ADMIN                varchar(50)          null,
   constraint PK_a_privilege primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table: a_privilege_resource                                  */
/*==============================================================*/
create table a_privilege_resource (
   RESOURCE_ID          varchar(32)          not null,
   PRIVILEGE_ID         varchar(32)          not null,
   constraint PK_a_privilege_resource primary key nonclustered (RESOURCE_ID, PRIVILEGE_ID)
)
go


/*==============================================================*/
/* Table: a_role_privilege                                      */
/*==============================================================*/
create table a_role_privilege (
   id                   varchar(32)          not null,
   ROLE_ID              varchar(32)          not null,
   PRIVILEGE_ID         varchar(32)          not null,
   constraint PK_a_role_privilege primary key nonclustered (id)
)
go


/*==============================================================*/
/* Table: a_system_resource                                     */
/*==============================================================*/
create table a_system_resource (
   ID                   varchar(32)          not null,
   NAME                 varchar(50)          not null,
   package_name         varchar(200)         null,
   API                  varchar(200)         null,
   URL                  varchar(300)         null,
   DESCRIPTION          varchar(500)         null,
   TYPE                 int                  null,
   PARENT_ID            varchar(32)          null,
   UPDATE_TIME          datetime             null,
   ADMIN                varchar(32)          null,
   constraint PK_a_system_resource primary key nonclustered (ID)
)
go


/*==============================================================*/
/* Table: a_user_privilege                                      */
/*==============================================================*/
create table a_user_privilege (
   id                   varchar(32)          not null,
   USER_ID              varchar(32)          not null,
   PRIVILEGE_ID         varchar(32)          not null,
   constraint PK_a_user_privilege primary key nonclustered (id)
)
go



/*==============================================================*/
/* Table: u_business_unit                                       */
/*==============================================================*/
create table u_business_unit (
   ID                   varchar(32)          not null,
   DEPT_ID              varchar(32)          null,
   TYPE                 varchar(50)          null,
   ID_LAYER             varchar(300)         null,
   PARENT_ID            varchar(32)          null,
   constraint PK_u_business_unit primary key nonclustered (ID)
)
go


/*==============================================================*/
/* Table: u_department                                          */
/*==============================================================*/
create table u_department (
   ID                   varchar(32)          not null,
   NAME                 varchar(100)         not null,
   DESCRIPTION          varchar(500)         null,
   ID_LAYER             varchar(300)         null,
   PARENT_ID            varchar(32)          null,
   TYPE                 int                  null,
   PROPERTIES           varchar(50)          null,
   POSITION_ID          varchar(37)          null,
   ORDER_NO             int                  null,
   constraint PK_u_department primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Index: u_department_ibfk_1                                   */
/*==============================================================*/
create index u_department_ibfk_1 on u_department (
POSITION_ID ASC
)
go

/*==============================================================*/
/* Table: u_job                                                 */
/*==============================================================*/
create table u_job (
   ID                   varchar(32)          not null,
   NAME                 varchar(200)         not null,
   INFO                 varchar(500)         null,
   ORDER_NO             int                  null,
   constraint PK_u_job primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table: u_job_level                                           */
/*==============================================================*/
create table u_job_level (
   ID                   varchar(32)          not null,
   JOB_ID               varchar(32)          not null,
   NAME                 varchar(200)         not null,
   INFO                 varchar(500)         null,
   JLEVEL               int                  null,
   constraint PK_u_job_level primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Index: FK_REFERENCE_8                                        */
/*==============================================================*/
create index FK_REFERENCE_8 on u_job_level (
JOB_ID ASC
)
go

/*==============================================================*/
/* Table: u_member                                              */
/*==============================================================*/
create table u_member (
   id                   varchar(32)          not null,
   username             varchar(20)          not null,
   name                 varchar(30)          null,
   password             varchar(32)          not null,
   user_id              varchar(32)          null,
   id_no                varchar(50)          null,
   reg_date             datetime             not null,
   status               int                  null,
   tel                  varchar(20)          null,
   mobile               varchar(25)          null,
   email                varchar(60)          null,
   question             varchar(300)         null,
   answer               varchar(300)         null,
   gender               varchar(3)           null,
   birth                datetime             null,
   score                int                  null,
   address              varchar(200)         null,
   zip                  varchar(10)          null,
   no                   varchar(12)          null,
   constraint PK_u_member primary key nonclustered (id)
)
go

/*==============================================================*/
/* Table: u_position                                            */
/*==============================================================*/
create table u_position (
   ID                   varchar(32)          not null,
   DEPT_ID              varchar(32)          null,
   JOB_LEVEL_ID         varchar(32)          null,
   PARENT_ID            varchar(32)          null,
   ID_LAYER             varchar(300)         null,
   NAME                 varchar(100)         not null,
   DESCRIPTION          varchar(500)         null,
   ORDER_NO             int                  null,
   constraint PK_u_position primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Index: FK_REFERENCE_7                                        */
/*==============================================================*/
create index FK_REFERENCE_7 on u_position (
JOB_LEVEL_ID ASC
)
go

/*==============================================================*/
/* Index: FK_ROLE_DEPT                                          */
/*==============================================================*/
create index FK_ROLE_DEPT on u_position (
DEPT_ID ASC
)
go

/*==============================================================*/
/* Table: u_role                                                */
/*==============================================================*/
create table u_role (
   ID                   varchar(32)          not null,
   NAME                 varchar(100)         not null,
   DESCRIPTION          varchar(500)         null,
   constraint PK_u_role primary key nonclustered (ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色唯一编号',
   'user', @CurrentUser, 'table', 'u_role', 'column', 'ID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色名',
   'user', @CurrentUser, 'table', 'u_role', 'column', 'NAME'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色描述',
   'user', @CurrentUser, 'table', 'u_role', 'column', 'DESCRIPTION'
go

/*==============================================================*/
/* Table: u_title                                               */
/*==============================================================*/
create table u_title (
   ID                   varchar(32)          not null,
   NAME                 varchar(200)         not null,
   INFO                 varchar(500)         null,
   ORDER_NO             int                  null,
   constraint PK_u_title primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table: u_user                                                */
/*==============================================================*/
create table u_user (
   ID                   varchar(32)          not null,
   USERNAME             varchar(50)          not null,
   PASSWORD             varchar(50)          null,
   NAME                 varchar(50)          null,
   BIRTHDAY             datetime             null,
   TEL_NO               varchar(50)          null,
   MOBILE_NO            varchar(50)          null,
   EMAIL                varchar(50)          null,
   ADDRESS              varchar(200)         null,
   ZIP                  varchar(10)          null,
   SEX                  varchar(1)           null,
   STATUS               int                  null,
   INFO                 varchar(1000)        null,
   ORDER_NO             int                  null,
   REG_TIME             datetime             null,
   constraint PK_u_user primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table: u_user_info                                           */
/*==============================================================*/
create table u_user_info (
   USER_ID              varchar(32)          not null,
   TITLE_ID             varchar(32)          null,
   POSITION_ID          varchar(32)          null,
   constraint PK_u_user_info primary key nonclustered (USER_ID)
)
go

/*==============================================================*/
/* Index: FK_REFERENCE_10                                       */
/*==============================================================*/
create index FK_REFERENCE_10 on u_user_info (
TITLE_ID ASC
)
go

/*==============================================================*/
/* Index: FK_REFERENCE_11                                       */
/*==============================================================*/
create index FK_REFERENCE_11 on u_user_info (
POSITION_ID ASC
)
go

/*==============================================================*/
/* Table: u_user_position                                       */
/*==============================================================*/
create table u_user_position (
   USER_ID              varchar(32)          not null,
   POSITION_ID          varchar(32)          not null,
   constraint PK_u_user_position primary key nonclustered (USER_ID, POSITION_ID)
)
go

/*==============================================================*/
/* Index: FK_POSITION                                           */
/*==============================================================*/
create index FK_POSITION on u_user_position (
POSITION_ID ASC
)
go

/*==============================================================*/
/* Table: u_user_role                                           */
/*==============================================================*/
create table u_user_role (
   USER_ID              varchar(32)          not null,
   ROLE_ID              varchar(32)          not null,
   constraint PK_u_user_role primary key nonclustered (USER_ID, ROLE_ID)
)
go

/*==============================================================*/
/* Index: FK_USERROLE_ROLE                                      */
/*==============================================================*/
create index FK_USERROLE_ROLE on u_user_role (
ROLE_ID ASC
)
go

alter table a_position_privilege
   add constraint a_position_privilege_ibfk_1 foreign key (POSITION_ID)
      references u_position (ID)
go

alter table a_position_privilege
   add constraint a_position_privilege_ibfk_2 foreign key (PRIVILEGE_ID)
      references a_privilege (ID)
go

alter table a_privilege_resource
   add constraint a_privilege_resource_ibfk_1 foreign key (RESOURCE_ID)
      references a_system_resource (ID)
go

alter table a_privilege_resource
   add constraint a_privilege_resource_ibfk_2 foreign key (PRIVILEGE_ID)
      references a_privilege (ID)
go

alter table a_role_privilege
   add constraint a_role_privilege_ibfk_1 foreign key (ROLE_ID)
      references u_role (ID)
go

alter table a_role_privilege
   add constraint a_role_privilege_ibfk_2 foreign key (PRIVILEGE_ID)
      references a_privilege (ID)
go

alter table a_user_privilege
   add constraint a_user_privilege_ibfk_1 foreign key (USER_ID)
      references u_user (ID)
go

alter table a_user_privilege
   add constraint a_user_privilege_ibfk_2 foreign key (PRIVILEGE_ID)
      references a_privilege (ID)
go

alter table u_business_unit
   add constraint u_business_unit_ibfk_1 foreign key (PARENT_ID)
      references u_department (ID)
go

alter table u_business_unit
   add constraint u_business_unit_ibfk_2 foreign key (DEPT_ID)
      references u_department (ID)
go

alter table u_job_level
   add constraint u_job_level_ibfk_1 foreign key (JOB_ID)
      references u_job (ID)
go

alter table u_position
   add constraint u_position_ibfk_1 foreign key (DEPT_ID)
      references u_department (ID)
go

alter table u_position
   add constraint u_position_ibfk_2 foreign key (JOB_LEVEL_ID)
      references u_job_level (ID)
go

alter table u_user_info
   add constraint u_user_info_ibfk_1 foreign key (POSITION_ID)
      references u_position (ID)
go

alter table u_user_info
   add constraint u_user_info_ibfk_2 foreign key (USER_ID)
      references u_user (ID)
go

alter table u_user_info
   add constraint u_user_info_ibfk_3 foreign key (TITLE_ID)
      references u_title (ID)
go

alter table u_user_position
   add constraint u_user_position_ibfk_1 foreign key (USER_ID)
      references u_user (ID)
go

alter table u_user_position
   add constraint u_user_position_ibfk_2 foreign key (POSITION_ID)
      references u_position (ID)
go

alter table u_user_role
   add constraint u_user_role_ibfk_1 foreign key (ROLE_ID)
      references u_role (ID)
go

alter table u_user_role
   add constraint u_user_role_ibfk_2 foreign key (USER_ID)
      references u_user (ID)
go








INSERT INTO a_system_resource VALUES ('attach.list', '上传附件', 'AttachService', 'list', '', '', '0', 'attach.manage', '2013-02-15 11:46:36', 'system');
INSERT INTO a_system_resource VALUES ('attach.manage', ' 附件管理', 'AttachService', '', '', '', '1', null, '2013-02-15 11:43:27', 'system');
INSERT INTO a_system_resource VALUES ('attach.upload', '附件上传', 'AttachService', 'create', '', '', '0', 'attach.manage', '2013-02-15 11:47:08', 'system');

INSERT INTO a_system_resource VALUES ('catalog.add', '添加分类', 'CatalogService', 'create', '', '', '3', 'catalog.package', '2013-02-05 13:38:52', 'system');
INSERT INTO a_system_resource VALUES ('catalog.delete', '删除分类', 'CatalogService', 'delete', '', '', '3', 'catalog.package', '2013-02-05 13:48:11', 'system');
INSERT INTO a_system_resource VALUES ('catalog.edit', '编辑分类', 'CatalogService', 'update', '/catalog/edit.action', '', '3', 'catalog.package', '2013-02-05 13:47:12', 'system');
INSERT INTO a_system_resource VALUES ('catalog.list', '分类列表', 'CatalogService', 'list', '/catalog/list.action', '', '2', 'catalog.package', '2013-02-05 16:10:00', 'system');
INSERT INTO a_system_resource VALUES ('catalog.package', '分类管理', 'CatalogService', '', '', '', '1', null, '2013-02-05 16:09:27', 'system');
INSERT INTO a_system_resource VALUES ('catalog.view', '查看分类', 'CatalogService', 'read', '/catalog/view.action', '', '3', 'catalog.package', '2013-02-05 14:38:40', 'system');


INSERT INTO a_privilege VALUES ('pri.catalog', '目录管理', '2013-02-05 16:12:06', null);
INSERT INTO a_privilege VALUES ('pri.cataloglist', '目录查看', '2012-06-25 11:45:10', null);

INSERT INTO a_privilege_resource VALUES ('catalog.add', 'pri.catalog');
INSERT INTO a_privilege_resource VALUES ('catalog.delete', 'pri.catalog');
INSERT INTO a_privilege_resource VALUES ('catalog.edit', 'pri.catalog');
INSERT INTO a_privilege_resource VALUES ('catalog.package', 'pri.catalog');
INSERT INTO a_privilege_resource VALUES ('catalog.view', 'pri.catalog');


INSERT INTO a_system_resource VALUES ('form.add', '添加表单', 'FormService', 'create', '', '', '3', 'form.manage', '2013-02-05 13:38:52', 'system');
INSERT INTO a_system_resource VALUES ('form.delete', '删除表单', 'FormService', 'delete', '', '', '3', 'form.manage', '2013-02-05 13:48:11', 'system');
INSERT INTO a_system_resource VALUES ('form.edit', '编辑表单', 'FormService', 'update', '/form/edit.action', '', '3', 'form.manage', '2013-02-05 13:47:12', 'system');
INSERT INTO a_system_resource VALUES ('form.list', '表单列表', 'FormService', 'list', '/form/list.action', '', '2', 'form.manage', '2013-02-05 16:10:00', 'system');
INSERT INTO a_system_resource VALUES ('form.manage', '表单管理', 'FormService', '', '', '', '1', null, '2013-02-05 16:09:27', 'system');
INSERT INTO a_system_resource VALUES ('form.view', '查看表单信息', 'FormService', 'read', '/form/view.action', '', '3', 'form.managee', '2013-02-05 14:38:40', 'system');

INSERT INTO a_system_resource VALUES ('element.add', '添加元数据', 'ElementService', 'create', '', '', '3', 'element.manage', '2013-02-05 13:38:52', 'system');
INSERT INTO a_system_resource VALUES ('element.delete', '删除元数据', 'ElementService', 'delete', '', '', '3', 'element.manage', '2013-02-05 13:48:11', 'system');
INSERT INTO a_system_resource VALUES ('element.edit', '编辑元数据', 'ElementService', 'update', '/form/edit.action', '', '3', 'element.manage', '2013-02-05 13:47:12', 'system');
INSERT INTO a_system_resource VALUES ('element.list', '元数据列表', 'ElementService', 'list', '/form/list.action', '', '2', 'element.manage', '2013-02-05 16:10:00', 'system');
INSERT INTO a_system_resource VALUES ('element.manage', '元数据管理', 'ElementService', '', '', '', '1', null, '2013-02-05 16:09:27', 'system');
INSERT INTO a_system_resource VALUES ('element.view', '查看元数据信息', 'ElementService', 'read', '/form/view.action', '', '3', 'element.managee', '2013-02-05 14:38:40', 'system');

INSERT INTO a_system_resource VALUES ('formelement.add', '添加表单元数据', 'ElementService', 'create', '', '', '3', 'element.manage', '2013-02-05 13:38:52', 'system');
INSERT INTO a_system_resource VALUES ('formelement.delete', '删除表单元数据', 'ElementService', 'delete', '', '', '3', 'element.manage', '2013-02-05 13:48:11', 'system');
INSERT INTO a_system_resource VALUES ('formelement.edit', '编辑表单元数据', 'ElementService', 'update', '/form/edit.action', '', '3', 'element.manage', '2013-02-05 13:47:12', 'system');
INSERT INTO a_system_resource VALUES ('formelement.list', '表单元数据列表', 'ElementService', 'list', '/form/list.action', '', '2', 'element.manage', '2013-02-05 16:10:00', 'system');
INSERT INTO a_system_resource VALUES ('formelement.manage', '表单元数据管理', 'ElementService', '', '', '', '1', null, '2013-02-05 16:09:27', 'system');
INSERT INTO a_system_resource VALUES ('formelement.view', '查看表单元数据信息', 'ElementService', 'read', '/form/view.action', '', '3', 'element.managee', '2013-02-05 14:38:40', 'system');



INSERT INTO a_system_resource VALUES ('formvalue.add', '添加元数据', 'ElementService', 'create', '', '', '3', 'element.manage', '2013-02-05 13:38:52', 'system');
INSERT INTO a_system_resource VALUES ('formvalue.delete', '删除元数据', 'ElementService', 'delete', '', '', '3', 'element.manage', '2013-02-05 13:48:11', 'system');
INSERT INTO a_system_resource VALUES ('formvalue.edit', '编辑元数据', 'ElementService', 'update', '/form/edit.action', '', '3', 'element.manage', '2013-02-05 13:47:12', 'system');
INSERT INTO a_system_resource VALUES ('formvalue.view', '查看元数据信息', 'ElementService', 'read', '/form/view.action', '', '3', 'element.managee', '2013-02-05 14:38:40', 'system');

INSERT INTO a_privilege VALUES ('pri.form', '表单定义', '2012-07-18 11:55:58', null);
INSERT INTO a_privilege VALUES ('pri.mailservice', '邮件管理', '2012-06-26 14:09:37', null);




INSERT INTO a_system_resource VALUES ('auth.list', '权限管理', 'AuthEntityService', 'list', '/user/listPrivilege.action', '', '2', 'auth.package', '2013-02-15 09:37:48', 'admin');
INSERT INTO a_system_resource VALUES ('auth.package', '权限管理', 'AuthEntityService', '', '', '', '1', null, '2013-02-05 11:19:27', 'admin');
INSERT INTO a_system_resource VALUES ('auth.resource.list', '资源管理', 'AuthEntityService', 'list', '/user/listSystemResource.action', '', '2', 'auth.package', '2013-02-15 09:38:28', 'admin');

INSERT INTO a_system_resource VALUES ('job.list', '职务管理', 'JobService', 'list', '/user/listJob.action', '', '2', 'user.manage', '2013-02-15 10:23:51', 'admin');
INSERT INTO a_system_resource VALUES ('member.manage', '会员管理', 'MemberEntityService', 'list', '/member/list.action', '', '2', 'user.manage', '2013-02-15 10:25:40', 'admin');
INSERT INTO a_system_resource VALUES ('org.manage', '组织机构', 'DepartmentEntityService', 'list', '/user/orgnization.action', '', '2', 'user.manage', '2013-02-15 10:21:49', 'admin');
INSERT INTO a_system_resource VALUES ('role.list', '角色管理', 'RoleEntityService', 'list', '/user/listRole.action', '', '2', 'user.manage', '2013-02-15 10:19:52', 'admin');
INSERT INTO a_system_resource VALUES ('title.list', '职称管理', 'TitleService', 'list', '/user/listTitle.action', '', '2', 'user.manage', '2013-02-15 10:23:27', 'admin');
INSERT INTO a_system_resource VALUES ('user.add', '添加用户', 'UserEntityService', 'create', '', '', '3', 'user.manage', '2013-02-07 11:18:43', 'admin');
INSERT INTO a_system_resource VALUES ('user.delete', '删除用户', 'UserEntityService', 'delete', '', '', '3', 'user.manage', '2013-02-15 09:53:11', 'admin');
INSERT INTO a_system_resource VALUES ('user.edit', '修改用户', 'UserEntityService', 'update', '', '', '3', 'user.manage', '2013-02-07 11:19:02', 'admin');
INSERT INTO a_system_resource VALUES ('user.list', '用户管理', 'UserEntityService', 'list', '/user/userList.action', '', '2', 'user.manage', '2013-02-15 11:29:28', 'admin');
INSERT INTO a_system_resource VALUES ('user.manage', '用户管理', 'UserEntityService', '', '', '', '1', null, '2013-02-07 11:18:16', 'admin');
INSERT INTO a_system_resource VALUES ('user.me', '修改个人信息', 'UserService', 'special', '', '', '1', 'user.manage', '2013-02-07 11:18:16', 'admin');





INSERT INTO a_privilege VALUES ('pri.auth', '权限管理', '2013-02-15 09:40:00', null);
INSERT INTO a_privilege VALUES ('pri.user', '用户管理', '2013-02-15 11:24:22', null);
INSERT INTO a_privilege VALUES ('pri.me', '修改用户信息', '2013-02-05 16:08:31', null);




INSERT INTO a_privilege_resource VALUES ('auth.list', 'pri.auth');
INSERT INTO a_privilege_resource VALUES ('auth.package', 'pri.auth');
INSERT INTO a_privilege_resource VALUES ('auth.resource.list', 'pri.auth');
INSERT INTO a_privilege_resource VALUES ('job.list', 'pri.user');
INSERT INTO a_privilege_resource VALUES ('member.manage', 'pri.user');
INSERT INTO a_privilege_resource VALUES ('org.manage', 'pri.user');
INSERT INTO a_privilege_resource VALUES ('role.list', 'pri.user');
INSERT INTO a_privilege_resource VALUES ('title.list', 'pri.user');
INSERT INTO a_privilege_resource VALUES ('user.add', 'pri.user');
INSERT INTO a_privilege_resource VALUES ('user.delete', 'pri.user');
INSERT INTO a_privilege_resource VALUES ('user.edit', 'pri.user');
INSERT INTO a_privilege_resource VALUES ('user.list', 'pri.user');
INSERT INTO a_privilege_resource VALUES ('user.manage', 'pri.user');
INSERT INTO a_privilege_resource VALUES ('attach.list', 'pri.me');
INSERT INTO a_privilege_resource VALUES ('attach.upload', 'pri.me');
INSERT INTO a_privilege_resource VALUES ('user.me', 'pri.me');





INSERT INTO u_user VALUES ('anonymous', 'anonymous', 'anonymous_!@#$%^&*()', 'anonymous', '2013-02-15', null, null, null, null, null, '0', '100', null, '1', null);
INSERT INTO u_user VALUES ('member', 'member', 'member_!@#$%^&*()', '免费会员', '2013-02-15', null, null, null, null, null, '0', '100', null, '1', null);
INSERT INTO u_user VALUES ('admin', 'admin', '1', 'admin', '2012-12-04', '1', '1', '1', '', null, '0', '100', '', '1', '2012-12-04');
INSERT INTO u_user VALUES ('vip', 'vip', 'member_!@#$%^&*()', '付费会员', '2013-02-15', null, null, null, null, null, '0', '100', null, '1', null);






INSERT INTO a_user_privilege VALUES ('1', 'admin', 'pri.auth');
INSERT INTO a_user_privilege VALUES ('2', 'admin', 'pri.user');
insert into u_role values('everyone','所有人',null);




