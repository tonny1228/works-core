-- 2013年12月13日11:28:41

if exists (select 1
            from  sysobjects
           where  id = object_id('fr_element')
            and   type = 'U')
   drop table fr_element
go

if exists (select 1
            from  sysobjects
           where  id = object_id('fr_form')
            and   type = 'U')
   drop table fr_form
go

if exists (select 1
            from  sysobjects
           where  id = object_id('fr_form_clobvalue')
            and   type = 'U')
   drop table fr_form_clobvalue
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('fr_form_element')
            and   name  = 'list'
            and   indid > 0
            and   indid < 255)
   drop index fr_form_element.list
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('fr_form_element')
            and   name  = 'form_id'
            and   indid > 0
            and   indid < 255)
   drop index fr_form_element.form_id
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('fr_form_element')
            and   name  = 'element'
            and   indid > 0
            and   indid < 255)
   drop index fr_form_element.element
go

if exists (select 1
            from  sysobjects
           where  id = object_id('fr_form_element')
            and   type = 'U')
   drop table fr_form_element
go

if exists (select 1
            from  sysobjects
           where  id = object_id('fr_form_tinyvalue')
            and   type = 'U')
   drop table fr_form_tinyvalue
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('fr_form_value')
            and   name  = 'item_id'
            and   indid > 0
            and   indid < 255)
   drop index fr_form_value.item_id
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('fr_form_value')
            and   name  = 'element_id'
            and   indid > 0
            and   indid < 255)
   drop index fr_form_value.element_id
go

if exists (select 1
            from  sysobjects
           where  id = object_id('fr_form_value')
            and   type = 'U')
   drop table fr_form_value
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('s_attach_reference')
            and   name  = 'FK_REFERENCE_ATTACHMENT'
            and   indid > 0
            and   indid < 255)
   drop index s_attach_reference.FK_REFERENCE_ATTACHMENT
go

if exists (select 1
            from  sysobjects
           where  id = object_id('s_attach_reference')
            and   type = 'U')
   drop table s_attach_reference
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('s_attachment')
            and   name  = 'CATALOG'
            and   indid > 0
            and   indid < 255)
   drop index s_attachment.CATALOG
go

if exists (select 1
            from  sysobjects
           where  id = object_id('s_attachment')
            and   type = 'U')
   drop table s_attachment
go

if exists (select 1
            from  sysobjects
           where  id = object_id('s_catalog')
            and   type = 'U')
   drop table s_catalog
go

if exists (select 1
            from  sysobjects
           where  id = object_id('s_log')
            and   type = 'U')
   drop table s_log
go

if exists (select 1
            from  sysobjects
           where  id = object_id('e_mail')
            and   type = 'U')
   drop table e_mail
go

/*==============================================================*/
/* Table: fr_element                                            */
/*==============================================================*/
create table fr_element (
   id                   varchar(32)          not null,
   name                 varchar(100)         not null,
   data_type            varchar(10)          not null,
   view_type            varchar(10)          null,
   options              varchar(2000)        null,
   default_value        varchar(2000)        null,
   required             int                  null,
   min_length           int                  null,
   max_length           int                  null,
   regex                varchar(100)         null,
   catelog              varchar(32)          null,
   update_time          datetime             null,
   admin                varchar(32)          null,
   constraint PK_fr_element primary key nonclustered (id)
)
go

/*==============================================================*/
/* Table: fr_form                                               */
/*==============================================================*/
create table fr_form (
   id                   varchar(32)          not null,
   name                 varchar(100)         not null,
   title                varchar(32)          null,
   info                 varchar(600)         null,
   catelog              varchar(32)          null,
   update_time          datetime             null,
   admin                varchar(32)          null,
   constraint PK_fr_form primary key nonclustered (id)
)
go

/*==============================================================*/
/* Table: fr_form_clobvalue                                     */
/*==============================================================*/
create table fr_form_clobvalue (
   id                   varchar(32)          not null,
   value                text                 null,
   constraint PK_fr_form_clobvalue primary key nonclustered (id)
)
go

/*==============================================================*/
/* Table: fr_form_element                                       */
/*==============================================================*/
create table fr_form_element (
   id                   varchar(32)          not null,
   name                 varchar(50)          null,
   form_id              varchar(32)          not null,
   foreign_form         varchar(32)          null,
   foreign_key          varchar(32)          null,
   element_id           varchar(32)          not null,
   list                 int                  null,
   search               int                  null,
   order_no             int                  null,
   update_time          datetime             null,
   admin                varchar(32)          null,
   constraint PK_fr_form_element primary key nonclustered (id)
)
go



/*==============================================================*/
/* Table: fr_form_tinyvalue                                     */
/*==============================================================*/
create table fr_form_tinyvalue (
   id                   varchar(32)          not null,
   value                varchar(1000)        null,
   constraint PK_fr_form_tinyvalue primary key nonclustered (id)
)
go

/*==============================================================*/
/* Table: fr_form_value                                         */
/*==============================================================*/
create table fr_form_value (
   id                   varchar(32)          not null,
   item_id              varchar(32)          not null,
   element_id           varchar(32)          not null,
   constraint PK_fr_form_value primary key nonclustered (id)
)
go


/*==============================================================*/
/* Table: s_attach_reference                                    */
/*==============================================================*/
create table s_attach_reference (
   ID                   varchar(32)          not null,
   ATTACH_ID            varchar(37)          null,
   OBJECT_ID            varchar(37)          null,
   CATALOG              varchar(100)         null,
   UPDATE_TIME          datetime             null,
   ADMIN                varchar(32)          null,
   constraint PK_s_attach_reference primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Index: FK_REFERENCE_ATTACHMENT                               */
/*==============================================================*/
create index FK_REFERENCE_ATTACHMENT on s_attach_reference (
ATTACH_ID ASC
)
go

/*==============================================================*/
/* Table: s_attachment                                          */
/*==============================================================*/
create table s_attachment (
   ID                   varchar(37)          not null,
   CATALOG              varchar(60)          null,
   TITLE                varchar(100)         null,
   INFO                 varchar(200)         null,
   FILENAME             varchar(300)         null,
   FILESIZE             decimal(8,0)         null,
   MIMETYPE             varchar(100)         null,
   FILEEXT              varchar(12)          null,
   PATH                 varchar(200)         null,
   UPDATE_TIME          datetime             null,
   ADMIN                varchar(32)          null,
   constraint PK_s_attachment primary key nonclustered (ID)
)
go



/*==============================================================*/
/* Table: s_catalog                                             */
/*==============================================================*/
create table s_catalog (
   ID                   varchar(40)          not null,
   NAME                 varchar(300)         not null,
   ALIAS                varchar(300)         not null,
   TYPE                 decimal(5,0)         null,
   LAYER                varchar(330)         null,
   NAME_LAYER           varchar(400)         null,
   PARENT_ID            varchar(255)         null,
   ORDER_NO             decimal(4,0)         null,
   DESCRIPTION          text                 null,
   STATUS               decimal(1,0)         null,
   DISPLAY              decimal(1,0)         null,
   UPDATE_TIME          datetime             null,
   ADMIN                varchar(32)          null,
   constraint PK_s_catalog primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table: s_log                                                 */
/*==============================================================*/
create table s_log (
   ID                   varchar(32)          not null,
   LOG_TIME             datetime             null,
   ADMIN                varchar(32)          null,
   CATALOG              varchar(100)         not null,
   ACTION               varchar(32)          not null,
   OBJECT_ID            varchar(32)          null,
   INFO                 varchar(200)         null,
   constraint PK_s_log primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table: e_mail                                                */
/*==============================================================*/
create table e_mail (
   ID                   varchar(32)          not null,
   title                varchar(200)         null,
   content              text                 null,
   mail                 varchar(2000)        null,
   CC                   varchar(2000)        null,
   BCC                  varchar(2000)        null,
   SEND_DATE            datetime             null,
   UPDATE_DATE          datetime             not null,
   UPDATE_USER          varchar(20)          not null,
   constraint PK_e_mail primary key nonclustered (ID)
)
go

alter table fr_form_clobvalue
   add constraint fr_form_clobvalue_ibfk_1 foreign key (id)
      references fr_form_value (id)
go

alter table fr_form_element
   add constraint fr_form_element_ibfk_1 foreign key (form_id)
      references fr_form (id)
go

alter table fr_form_element
   add constraint fr_form_element_ibfk_2 foreign key (element_id)
      references fr_element (id)
go

alter table fr_form_tinyvalue
   add constraint fr_form_tinyvalue_ibfk_1 foreign key (id)
      references fr_form_value (id)
go

alter table fr_form_value
   add constraint fr_form_value_ibfk_1 foreign key (element_id)
      references fr_form_element (id)
go

alter table s_attach_reference
   add constraint s_attach_reference_ibfk_1 foreign key (ATTACH_ID)
      references s_attachment (ID)
go


INSERT INTO `s_catalog` VALUES ('1083b9e8-f0f8-4e96-b294-2214c533cdf8', '小贴士', '小贴士', '0', '00010003', '资讯管理/小贴士', '11', '3', '', '0', '1', '2013-10-24 15:27:14', 'info');
INSERT INTO `s_catalog` VALUES ('11', '资讯管理', '资讯管理', '0', '0001', '资讯管理', null, '1', '', '0', '1', '2013-10-21 16:48:51', 'info');
INSERT INTO `s_catalog` VALUES ('3925bd3e-4af1-4af3-a201-a8dc9db0d6c8', '健康知识', '健康知识', '0', '00010002', '资讯管理/健康知识', '11', '2', '', '0', '1', '2013-10-24 15:27:00', 'info');
INSERT INTO `s_catalog` VALUES ('3dde3a32-eca5-4e2e-b7c4-210d2f77fddf', '酒店', '酒店', '0', '00020001', '地图分类/酒店', 'map_type', '1', '酒店', '0', '1', '2013-11-19 09:46:39', 'info');
INSERT INTO `s_catalog` VALUES ('5ac9f77e-b71d-4b9a-a9c9-3492988a7f1f', '家具', '家具', '0', '00050002', '产品管理/家具', '_product', '2', '', '0', '1', '2013-11-27 14:26:46', 'info');
INSERT INTO `s_catalog` VALUES ('68b258ab-07e1-4248-a23a-7042d478d895', '医院公告', '医院公告', '0', '00010001', '资讯管理/医院公告', '11', '1', '', '0', '1', '2013-10-21 17:10:16', 'info');
INSERT INTO `s_catalog` VALUES ('bc3c86b5-ddaa-4249-b3d1-ba73c2235d12', '家电', '家电', '0', '00050001', '产品管理/家电', '_product', '1', '', '0', '1', '2013-11-27 14:26:38', 'info');
INSERT INTO `s_catalog` VALUES ('c6bc758f-d625-4297-b36e-1b36082d3df6', '电子', '电子', '0', '00050003', '产品管理/电子', '_product', '3', '', '0', '1', '2013-11-27 14:26:55', 'info');
INSERT INTO `s_catalog` VALUES ('e41f5e7c-2f45-4e95-9011-d496f1d16c52', '内部通知', '内部通知', '0', '00010004', '资讯管理/内部通知', '11', '4', '', '0', '1', '2013-10-24 15:34:49', 'info');
INSERT INTO `s_catalog` VALUES ('map_type', '地图分类', '地图分类', '0', '0002', '地图分类', null, '2', '地图分类', '0', '1', '2013-11-19 09:40:36', 'info');
INSERT INTO `s_catalog` VALUES ('_product', '产品管理', '产品管理', '0', '0005', '产品管理', null, '5', '', '0', '1', '2013-11-26 14:16:58', 'info');
