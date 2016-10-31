INSERT INTO PRODUCT
	(id, deleted, enabled, creation_date, last_modified_date, description, name, product_type, unit_price)
	values(-1, 0, 1, now(), now(), '付费消息，霸屏10秒', '消息-霸屏10秒', 1, 1);
    
INSERT INTO PRODUCT
	(id, deleted, enabled, creation_date, last_modified_date, description, name, product_type, unit_price)
	values(-2, 0, 1, now(), now(), '付费消息，霸屏20秒', '消息-霸屏20秒', 1, 2);
    
INSERT INTO PRODUCT
	(id, deleted, enabled, creation_date, last_modified_date, description, name, product_type, unit_price)
	values(-3, 0, 1, now(), now(), '付费消息，霸屏30秒', '消息-霸屏30秒', 1, 3);

INSERT INTO PRODUCT
	(id, deleted, enabled, creation_date, last_modified_date, description, name, product_type, unit_price)
	values(-4, 0, 1, now(), now(), '礼物1 描述', '礼物1', 1, 1);
    
INSERT INTO PRODUCT
	(id, deleted, enabled, creation_date, last_modified_date, description, name, product_type, unit_price)
	values(-5, 0, 1, now(), now(), '礼物2 描述', '礼物2', 1, 2);
    
INSERT INTO PRODUCT
	(id, deleted, enabled, creation_date, last_modified_date, description, name, product_type, unit_price)
	values(-6, 0, 1, now(), now(), '礼物3 描述', '礼物3', 1, 3);

	
insert into MSG_PRODUCT
	(id, hold_time)
    values(-1, 10);
    
insert into MSG_PRODUCT
	(id, hold_time)
    values(-2, 20);
    
insert into MSG_PRODUCT
	(id, hold_time)
    values(-3, 30);

insert into MSG_PRODUCT
	(id, code, hold_time)
    values(-4, 'e1', 10);
    
insert into MSG_PRODUCT
	(id, code, hold_time)
    values(-5, 'e2', 20);
    
insert into MSG_PRODUCT
	(id, code, hold_time)
    values(-6, 'e3', 30);
    
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


INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-202, 'admin2', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-202, -202, -103);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-202, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-2,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-202,'TestShop2',-202);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-2,0,1,now(),now(),'test_room2',-2);
	


INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-203, 'admin3', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-203, -203, -103);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-203, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-3,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-203,'TestShop3',-203);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-3,0,1,now(),now(),'test_room3',-3);
	


INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-204, 'admin4', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-204, -204, -103);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-204, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-4,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-204,'TestShop4',-204);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-4,0,1,now(),now(),'test_room4',-4);
	
	

INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-205, 'admin5', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-205, -205, -103);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-205, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-5,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-205,'TestShop5',-205);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-5,0,1,now(),now(),'test_room5',-5);
	
	

INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-206, 'admin6', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-206, -206, -103);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-206, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-6,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-206,'TestShop6',-206);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-6,0,1,now(),now(),'test_room6',-6);

	

INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-207, 'admin7', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-207, -207, -103);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-207, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-7,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-207,'TestShop7',-207);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-7,0,1,now(),now(),'test_room7',-7);
	


INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-208, 'admin8', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-208, -208, -103);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-208, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-8,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-208,'TestShop8',-208);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-8,0,1,now(),now(),'test_room8',-8);
	
	

INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-209, 'admin9', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-209, -209, -103);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-209, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-9,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-209,'TestShop9',-209);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-9,0,1,now(),now(),'test_room9',-9);
	
	
INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-210, 'admin10', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO PROFILE_ROLE (id, profile_id, role_id) VALUES (-210, -210, -103);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-210, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-10,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-210,'TestShop10',-210);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-10,0,1,now(),now(),'test_room10',-10);
	


INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-211, 'admin11', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-211, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-11,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-211,'TestShop11',-211);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-11,0,1,now(),now(),'test_room11',-11);

	

INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-212, 'admin12', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-212, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-12,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-212,'TestShop12',-212);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-12,0,1,now(),now(),'test_room12',-12);
	
	

INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-213, 'admin13', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-213, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-13,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-213,'TestShop13',-213);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-13,0,1,now(),now(),'test_room13',-13);

	


INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-214, 'admin14', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-214, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-14,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-214,'TestShop14',-214);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-14,0,1,now(),now(),'test_room14',-14);
	



INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-215, 'admin15', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-215, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-15,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-215,'TestShop15',-215);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-15,0,1,now(),now(),'test_room15',-15);
	
	
	

INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-216, 'admin16', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-216, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-16,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-216,'TestShop16',-216);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-16,0,1,now(),now(),'test_room16',-16);

	
	

INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-217, 'admin17', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-217, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-17,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-217,'TestShop17',-217);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-17,0,1,now(),now(),'test_room17',-17);

	

INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-218, 'admin18', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-218, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-18,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-218,'TestShop18',-218);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-18,0,1,now(),now(),'test_room18',-18);

	

INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-219, 'admin19', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-219, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-19,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-219,'TestShop19',-219);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-19,0,1,now(),now(),'test_room11',-19);



INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-220, 'admin20', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-220, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-20,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-220,'TestShop11',-220);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-20,0,1,now(),now(),'test_room11',-20);


