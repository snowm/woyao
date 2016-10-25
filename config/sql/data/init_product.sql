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