INSERT INTO ROLE (id, code, name, description, visible) VALUES (-101, 'SUPER', '超人', '系统超级管理员，能跨越一切权限，用于系统默认用户', 0);
INSERT INTO ROLE (id, code, name, description, visible) VALUES (-102, 'ADMIN', '管理员', '管理员，具有较高权限，可以做一切业务操作和系统操作', 1);
INSERT INTO ROLE (id, code, name, description, visible) VALUES (-103, 'S_ADMIN', '店家管理员', '店家管理人员，可以做一切店家相关的业务，比如修改产品', 1);
INSERT INTO ROLE (id, code, name, description, visible) VALUES (-104, 'CUSTOMER', '消费者', '消费者，可以聊天，买东西', 1);

INSERT INTO PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-101, '$uper', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);
 
INSERT INTO PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-102, 'admin', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-103, 's_admin', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-104, 'customer', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

-- 设置SUPER角色给默认的super,admin用户
INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-101, -101, -101);
INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-102, -102, -102);
INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-103, -103, -103);
INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-104, -104, -104);

-- 添加权限
INSERT INTO PERMISSION (id, code, name, description) values (-101, 'ROLE_SUPER', '超级管理员', '具有超级管理员权限');
INSERT INTO PERMISSION (id, code, name, description) values (-102, 'ROLE_ADMIN', '管理员', '具有管理员权限');
INSERT INTO PERMISSION (id, code, name, description) values (-103, 'ROLE_S_ADMIN', '店家管理员', '店家管理员');
INSERT INTO PERMISSION (id, code, name, description) values (-104, 'ROLE_CUSTOMER', '客户', '客户');

-- role super
INSERT INTO ROLE_PERMISSION (id, role_id, permission_id) values (-101, -101, -101);
INSERT INTO ROLE_PERMISSION (id, role_id, permission_id) values (-102, -101, -102);
INSERT INTO ROLE_PERMISSION (id, role_id, permission_id) values (-103, -101, -103);
INSERT INTO ROLE_PERMISSION (id, role_id, permission_id) values (-104, -101, -104);

-- role admin
INSERT INTO ROLE_PERMISSION (id, role_id, permission_id) values (-105, -102, -102);
INSERT INTO ROLE_PERMISSION (id, role_id, permission_id) values (-106, -102, -103);
INSERT INTO ROLE_PERMISSION (id, role_id, permission_id) values (-107, -102, -104);

-- role s_admin
INSERT INTO ROLE_PERMISSION (id, role_id, permission_id) values (-108, -103, -103);
INSERT INTO ROLE_PERMISSION (id, role_id, permission_id) values (-109, -103, -104);

-- role customer
INSERT INTO ROLE_PERMISSION (id, role_id, permission_id) values (-110, -104, -104); 

INSERT INTO PRODUCT
	(id, deleted, enabled, creation_date, last_modified_date, description, name, product_type, unit_price)
	values(-1, 0, 1, now(), now(), '付费消息，霸屏10秒', '消息-霸屏10秒', 1, 1);
    
INSERT INTO PRODUCT
	(id, deleted, enabled, creation_date, last_modified_date, description, name, product_type, unit_price)
	values(-2, 0, 1, now(), now(), '付费消息，霸屏20秒', '消息-霸屏20秒', 1, 2);
    
INSERT INTO PRODUCT
	(id, deleted, enabled, creation_date, last_modified_date, description, name, product_type, unit_price)
	values(-3, 0, 1, now(), now(), '付费消息，霸屏30秒', '消息-霸屏30秒', 1, 3);

insert into PAID_MSG_PRODUCT_CONFIG
	(id, deleted, enabled, creation_date, last_modified_date, hold_time, product_id)
    values(-1, 0, 1, now(),now(), 10, -1);
    
insert into PAID_MSG_PRODUCT_CONFIG
	(id, deleted, enabled, creation_date, last_modified_date, hold_time, product_id)
    values(-2, 0, 1, now(),now(), 20, -2);
    
insert into PAID_MSG_PRODUCT_CONFIG
	(id, deleted, enabled, creation_date, last_modified_date, hold_time, product_id)
    values(-3, 0, 1, now(),now(), 30, -3);
    
INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-201, 'admin1', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-201, -201, -103);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-201, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-1,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-201,'TestShop',-201);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-1,0,1,now(),now(),'test_room',-1);
