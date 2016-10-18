INSERT INTO `woyao`.PROFILE (id, username, type, gender, password, enabled, expired, credentials_expired, locked, creation_date, last_modified_date, deleted)
	VALUES (-201, 'admin1', 1, 1, '44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884', 1, 0, 0, 0, now(), now(), 0);

INSERT INTO `woyao`.PICTURE (`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,PATH, URL)
	VALUES (-201, 0,1,now(),now(),'..', '/pic/asf-feather.png');
	
INSERT INTO `woyao`.`SHOP`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`ADDRESS`,`DESCRIPTION`,
	`LATITUDE`,`LONGITUDE`,`MANAGER_PROFILE_ID`,`NAME`,`PIC_ID`)
	VALUES (-1,0,1,now(),now(),'test_addr','test_desc',30.663456,104.072227,-201,'TestShop',-201);

INSERT INTO `woyao`.`CHAT_ROOM`
	(`ID`,`DELETED`,`ENABLED`,`CREATION_DATE`,`LAST_MODIFIED_DATE`,`NAME`,`SHOP_ID`)
	VALUES (-1,0,1,now(),now(),'test_room',-1);
