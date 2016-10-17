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