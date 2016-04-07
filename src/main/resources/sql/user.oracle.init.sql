INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('attach.manage', '附件管理', 'AttachService', '', '', '', '1', null,sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('attach.upload', '附件上传', 'AttachService', 'create', '', '', '0', 'attach.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('attach.list', '上传附件', 'AttachService', 'list', '', '', '0', 'attach.manage', sysdate, 'system');

INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('catalog.package', '目录管理', 'CatalogService', '', '', '', '1', null, sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('catalog.add', '添加目录', 'CatalogService', 'create', '', '', '3', 'catalog.package', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('catalog.delete', '删除目录', 'CatalogService', 'delete', '', '', '3', 'catalog.package',sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('catalog.edit', '编辑目录', 'CatalogService', 'update', '/catalog/edit.action', '', '3', 'catalog.package', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('catalog.list', '目录列表', 'CatalogService', 'list', '/catalog/list.action', '', '2', 'catalog.package', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('catalog.view', '查看目录', 'CatalogService', 'read', '/catalog/view.action', '', '3', 'catalog.package',sysdate, 'system');


INSERT INTO a_privilege(ID,NAME,UPDATE_TIME,ADMIN) VALUES ('pri.catalog', '目录管理', sysdate, null);
INSERT INTO a_privilege(ID,NAME,UPDATE_TIME,ADMIN) VALUES ('pri.cataloglist', '目录查看', sysdate, null);


INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('catalog.add', 'pri.catalog');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('catalog.delete', 'pri.catalog');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('catalog.edit', 'pri.catalog');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('catalog.package', 'pri.catalog');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('catalog.view', 'pri.catalog');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('catalog.list', 'pri.catalog');

INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('form.manage', '表单管理', 'FormService', '', '', '', '1', null, sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('form.add', '添加表单', 'FormService', 'create', '', '', '3', 'form.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('form.delete', '删除表单', 'FormService', 'delete', '', '', '3', 'form.manage',sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('form.edit', '编辑表单', 'FormService', 'update', '/form/edit.action', '', '3', 'form.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('form.list', '表单列表', 'FormService', 'list', '/form/list.action', '', '2', 'form.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('form.view', '查看表单信息', 'FormService', 'read', '/form/view.action', '', '3', 'form.manage',sysdate, 'system');

INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('element.manage', '元数据管理', 'ElementService', '', '', '', '1', null, sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('element.add', '添加元数据', 'ElementService', 'create', '', '', '3', 'element.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('element.delete', '删除元数据', 'ElementService', 'delete', '', '', '3', 'element.manage',sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('element.edit', '编辑元数据', 'ElementService', 'update', '/form/edit.action', '', '3', 'element.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('element.list', '元数据列表', 'ElementService', 'list', '/form/list.action', '', '2', 'element.manage',sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('element.view', '查看元数据信息', 'ElementService', 'read', '/form/view.action', '', '3', 'element.manage', sysdate, 'system');

INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('formelement.manage', '表单元数据管理', 'ElementService', '', '', '', '1', null, sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('formelement.add', '添加表单元数据', 'ElementService', 'create', '', '', '3', 'element.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('formelement.delete', '删除表单元数据', 'ElementService', 'delete', '', '', '3', 'element.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('formelement.edit', '编辑表单元数据', 'ElementService', 'update', '/form/edit.action', '', '3', 'element.manage',sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('formelement.list', '表单元数据列表', 'ElementService', 'list', '/form/list.action', '', '2', 'element.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('formelement.view', '查看表单元数据信息', 'ElementService', 'read', '/form/view.action', '', '3', 'element.manage', sysdate, 'system');


INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('formvalue.add', '添加元数据', 'ElementService', 'create', '', '', '3', 'element.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('formvalue.delete', '删除元数据', 'ElementService', 'delete', '', '', '3', 'element.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('formvalue.edit', '编辑元数据', 'ElementService', 'update', '/form/edit.action', '', '3', 'element.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('formvalue.view', '查看元数据信息', 'ElementService', 'read', '/form/view.action', '', '3', 'element.manage', sysdate, 'system');

INSERT INTO a_privilege(ID,NAME,UPDATE_TIME,ADMIN) VALUES ('pri.form', '表单定义',sysdate, null);
INSERT INTO a_privilege(ID,NAME,UPDATE_TIME,ADMIN) VALUES ('pri.fillform', '表单填写', sysdate, null);
INSERT INTO a_privilege(ID,NAME,UPDATE_TIME,ADMIN) VALUES ('pri.mailservice', '邮件管理',sysdate, null);


INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('form.manage', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('form.add', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('form.delete', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('form.edit', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('form.list', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('form.view', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('element.manage', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('element.add', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('element.delete', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('element.edit', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('element.list', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('element.view', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formelement.manage', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formelement.add', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formelement.delete', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formelement.edit', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formelement.list', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formelement.view', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formvalue.add', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formvalue.delete', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formvalue.edit', 'pri.form');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formvalue.view', 'pri.form');

INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formvalue.add', 'pri.fillform');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formvalue.delete', 'pri.fillform');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formvalue.edit', 'pri.fillform');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('formvalue.view', 'pri.fillform');


INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('auth.package', '权限管理', 'AuthEntityService', '', '', '', '1', null, sysdate, 'admin');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('auth.list', '权限管理', 'AuthEntityService', 'list', '/user/listPrivilege.action', '', '2', 'auth.package', sysdate, 'admin');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('auth.resource.list', '资源管理', 'AuthEntityService', 'list', '/user/listSystemResource.action', '', '2', 'auth.package', sysdate, 'admin');

INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('user.manage', '用户管理', 'UserEntityService', '', '', '', '1', null, sysdate, 'admin');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('user.add', '添加用户', 'UserEntityService', 'create', '', '', '3', 'user.manage', sysdate, 'admin');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('user.delete', '删除用户', 'UserEntityService', 'delete', '', '', '3', 'user.manage',sysdate, 'admin');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('user.edit', '修改用户', 'UserEntityService', 'update', '', '', '3', 'user.manage', sysdate, 'admin');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('user.list', '用户管理', 'UserEntityService', 'list', '/user/userList.action', '', '2', 'user.manage', sysdate, 'admin');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('user.me', '修改个人信息', 'UserService', 'special', '', '', '1', 'user.manage', sysdate, 'admin');

INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('job.list', '职务管理', 'JobService', 'list', '/user/listJob.action', '', '2', 'user.manage', sysdate, 'admin');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('member.manage', '会员管理', 'MemberEntityService', 'list', '/member/list.action', '', '2', 'user.manage', sysdate, 'admin');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('org.manage', '组织机构', 'DepartmentEntityService', 'list', '/user/orgnization.action', '', '2', 'user.manage', sysdate, 'admin');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('role.list', '角色管理', 'RoleEntityService', 'list', '/user/listRole.action', '', '2', 'user.manage',sysdate, 'admin');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('title.list', '职称管理', 'TitleService', 'list', '/user/listTitle.action', '', '2', 'user.manage', sysdate, 'admin');

INSERT INTO a_privilege(ID,NAME,UPDATE_TIME,ADMIN) VALUES ('pri.auth', '权限管理', sysdate, null);
INSERT INTO a_privilege(ID,NAME,UPDATE_TIME,ADMIN) VALUES ('pri.user', '用户管理', sysdate, null);
INSERT INTO a_privilege(ID,NAME,UPDATE_TIME,ADMIN) VALUES ('pri.me', '修改用户信息', sysdate, null);

INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('auth.list', 'pri.auth');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('auth.package', 'pri.auth');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('auth.resource.list', 'pri.auth');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('job.list', 'pri.user');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('member.manage', 'pri.user');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('org.manage', 'pri.user');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('role.list', 'pri.user');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('title.list', 'pri.user');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('user.add', 'pri.user');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('user.delete', 'pri.user');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('user.edit', 'pri.user');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('user.list', 'pri.user');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('user.manage', 'pri.user');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('attach.list', 'pri.me');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('attach.upload', 'pri.me');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('user.me', 'pri.me');


INSERT INTO u_user(ID,USERNAME,PASSWORD,NAME,BIRTHDAY,TEL_NO,MOBILE_NO,EMAIL,ADDRESS,ZIP,SEX,status,INFO,ORDER_NO,REG_TIME) VALUES ('anonymous', 'anonymous', 'anonymous_!@#$%^&*()', 'anonymous', sysdate, null, null, null, null, null, '0', 'ACTIVE', null, '1', sysdate);
INSERT INTO u_user(ID,USERNAME,PASSWORD,NAME,BIRTHDAY,TEL_NO,MOBILE_NO,EMAIL,ADDRESS,ZIP,SEX,status,INFO,ORDER_NO,REG_TIME) VALUES ('member', 'member', 'member_!@#$%^&*()', '免费会员', sysdate, null, null, null, null, null, '0', 'ACTIVE', null, '1', sysdate);
INSERT INTO u_user(ID,USERNAME,PASSWORD,NAME,BIRTHDAY,TEL_NO,MOBILE_NO,EMAIL,ADDRESS,ZIP,SEX,status,INFO,ORDER_NO,REG_TIME) VALUES ('admin', 'admin', '1', 'admin',sysdate, '1', '1', '1', '', null, '0', 'ACTIVE', '', '1', sysdate);
INSERT INTO u_user(ID,USERNAME,PASSWORD,NAME,BIRTHDAY,TEL_NO,MOBILE_NO,EMAIL,ADDRESS,ZIP,SEX,status,INFO,ORDER_NO,REG_TIME) VALUES ('vip', 'vip', 'member_!@#$%^&*()', '付费会员', sysdate, null, null, null, null, null, '0', 'ACTIVE', null, '1', sysdate);


INSERT INTO a_user_privilege(id,USER_ID,PRIVILEGE_ID) VALUES ('auth', 'admin', 'pri.auth');
INSERT INTO a_user_privilege(id,USER_ID,PRIVILEGE_ID) VALUES ('user', 'admin', 'pri.user');
INSERT INTO a_user_privilege(id,USER_ID,PRIVILEGE_ID) VALUES ('admincatalog', 'admin', 'pri.catalog');
insert into u_role(ID,NAME,DESCRIPTION) values('everyone','所有人',null);

INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('idgenerator.manage', 'ID生成器管理', 'IDGeneratorService', 'read', '/idg/list.action', '', '1', null, sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('idgenerator.add', '添加ID生成器', 'IDGeneratorEntityService', 'create', '/idg/add.action', '', '3', 'idgenerator.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('idgenerator.delete', '删除ID生成器', 'IDGeneratorEntityService', 'delete', '/idg/delete.action', '', '3', 'idgenerator.manage', sysdate, 'system');
INSERT INTO a_system_resource(ID,NAME,package_name,API,URL,DESCRIPTION,TYPE,PARENT_ID,UPDATE_TIME,ADMIN) VALUES ('idgenerator.edit', '编辑ID生成器', 'IDGeneratorEntityService', 'update', '/idg/edit.action', '', '3', 'idgenerator.manage',sysdate, 'system');

INSERT INTO a_privilege(ID,NAME,UPDATE_TIME,ADMIN) VALUES ('pri.idgenerator', 'ID生成器管理', sysdate, null);
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('idgenerator.manage', 'pri.idgenerator');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('idgenerator.add', 'pri.idgenerator');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('idgenerator.delete', 'pri.idgenerator');
INSERT INTO a_privilege_resource(RESOURCE_ID,PRIVILEGE_ID) VALUES ('idgenerator.edit', 'pri.idgenerator');
INSERT INTO a_user_privilege(id,USER_ID,PRIVILEGE_ID) VALUES ('idgenerator', 'admin', 'pri.idgenerator');
