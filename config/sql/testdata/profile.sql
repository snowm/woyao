INSERT INTO profile (USERNAME, type, gender, PASSWORD, ID, creation_date, last_modified_date) 
	VALUES ('admin', 0, 0, '1509dcf1b2d67df38f142b6b8fb171080b7491bde4d9691590586bb993c7649adfead826ad3121b3',-1, now(),now());
INSERT INTO employee (id,title,rank,employee_number,abn,gst)
	VALUES (-1,'O',5,'000','abn','gst');

INSERT INTO profile (USERNAME, type, gender, PASSWORD, ID, creation_date, last_modified_date) 
	VALUES ('jadey', 0, 0, '1509dcf1b2d67df38f142b6b8fb171080b7491bde4d9691590586bb993c7649adfead826ad3121b3',-2, now(),now());
INSERT INTO employee (id,title,rank,employee_number,abn,gst)
	VALUES (-2,'O',5,'001','abn','gst');

INSERT INTO profile (USERNAME, type, gender, PASSWORD, ID, creation_date, last_modified_date) 
	VALUES ('ryan', 0, 1,'1509dcf1b2d67df38f142b6b8fb171080b7491bde4d9691590586bb993c7649adfead826ad3121b3',-3, now(),now());
INSERT INTO employee (id,title,rank,employee_number,abn,gst)
	VALUES (-3,'O',5,'002','abn','gst');

INSERT INTO profile (USERNAME, type, gender, PASSWORD, ID, creation_date, last_modified_date) 
	VALUES ('huisan', 0, 2,'1509dcf1b2d67df38f142b6b8fb171080b7491bde4d9691590586bb993c7649adfead826ad3121b3',-4, now(),now());
INSERT INTO employee (id,title,rank,employee_number,abn,gst)
	VALUES (-4,'O',5,'003','abn','gst');

INSERT INTO profile (USERNAME, type, gender, PASSWORD, ID, creation_date, last_modified_date) 
	VALUES ('testDataAdmin', 0, 1,'1509dcf1b2d67df38f142b6b8fb171080b7491bde4d9691590586bb993c7649adfead826ad3121b3',-5, now(),now());
INSERT INTO employee (id,title,rank,employee_number,abn,gst)
	VALUES (-5,'O',5,'004','abn','gst');

-- employee
INSERT INTO profile (USERNAME, type, gender, PASSWORD, ID, creation_date, last_modified_date) 
	VALUES ('testManager1', 1, 0,'1509dcf1b2d67df38f142b6b8fb171080b7491bde4d9691590586bb993c7649adfead826ad3121b3',-10000, now(),now());
INSERT INTO employee (id,title,rank,employee_number,abn,gst)
	VALUES (-10000,'Sale Manager',4,'004','abn','gst');

INSERT INTO profile (USERNAME, type, gender, PASSWORD, ID, creation_date, last_modified_date) 
	VALUES ('testManager2', 1, 1,'1509dcf1b2d67df38f142b6b8fb171080b7491bde4d9691590586bb993c7649adfead826ad3121b3',-10001, now(),now());
INSERT INTO employee (id,title,rank,employee_number,abn,gst)
	VALUES (-10001,'Sale Manager',3,'005','abn','gst');

INSERT INTO profile (USERNAME, type, gender, PASSWORD, ID, creation_date, last_modified_date) 
	VALUES ('testSale1', 1, 0,'1509dcf1b2d67df38f142b6b8fb171080b7491bde4d9691590586bb993c7649adfead826ad3121b3',-10002, now(),now());
INSERT INTO employee (id,title,rank,employee_number,abn,gst)
	VALUES (-10002,'Junior Sale',1,'006','abn','gst');

INSERT INTO profile (USERNAME, type, gender, PASSWORD, ID, creation_date, last_modified_date) 
	VALUES ('testSale2', 1, 0,'1509dcf1b2d67df38f142b6b8fb171080b7491bde4d9691590586bb993c7649adfead826ad3121b3',-10003, now(),now());
INSERT INTO employee (id,title,rank,employee_number,abn,gst)
	VALUES (-10003,'Senior Sale',2,'007','abn','gst');

INSERT INTO profile (USERNAME, type, gender, PASSWORD, ID, creation_date, last_modified_date) 
	VALUES ('testSale3', 1, 1,'1509dcf1b2d67df38f142b6b8fb171080b7491bde4d9691590586bb993c7649adfead826ad3121b3',-10004, now(),now());
INSERT INTO employee (id,title,rank,employee_number,abn,gst)
	VALUES (-10004,'Junior Sale',1,'008','abn','gst');

INSERT INTO profile (USERNAME, type, gender, PASSWORD, ID, creation_date, last_modified_date) 
	VALUES ('testSale4', 1, 2,'1509dcf1b2d67df38f142b6b8fb171080b7491bde4d9691590586bb993c7649adfead826ad3121b3',-10005, now(),now());
INSERT INTO employee (id,title,rank,employee_number,abn,gst)
	VALUES (-10005,'Senior Sale',2,'009','abn','gst');

-- client
INSERT INTO profile (USERNAME, type, gender, ID, creation_date, last_modified_date) 
	VALUES ('testClient1', 4, 0,-20000, now(),now());
INSERT INTO client (id,current_employee,last_active_date)
	VALUES (-20000,-10002,now());

INSERT INTO profile (USERNAME, type, gender,  ID, creation_date, last_modified_date) 
	VALUES ('testClient2', 4, 0,-20001, now(),now());
INSERT INTO client (id,current_employee,last_active_date)
	VALUES (-20001,-10003,now());

INSERT INTO profile (USERNAME, type, gender, ID, creation_date, last_modified_date) 
	VALUES ('testClient3', 4, 1,-20002, now(),now());
INSERT INTO client (id,current_employee,last_active_date)
	VALUES (-20002,-10003,now());

INSERT INTO profile (USERNAME, type, gender,  ID, creation_date, last_modified_date) 
	VALUES ('testClient4', 4, 2,-20003, now(),now());
INSERT INTO client (id,current_employee,last_active_date)
	VALUES (-20003,-10003,now());

INSERT INTO profile (USERNAME, type, gender, ID, creation_date, last_modified_date) 
	VALUES ('testClient5', 4, 0,-20004, now(),now());
INSERT INTO client (id,current_employee,last_active_date)
	VALUES (-20004,-10004,now());

INSERT INTO profile (USERNAME, type, gender,  ID, creation_date, last_modified_date) 
	VALUES ('testClient6', 4, 0,-20005, now(),now());
INSERT INTO client (id,current_employee,last_active_date)
	VALUES (-20005,-10004,now());

INSERT INTO profile (USERNAME, type, gender, ID, creation_date, last_modified_date) 
	VALUES ('testClient7', 4, 1,-20006, now(),now());
INSERT INTO client (id,current_employee,last_active_date)
	VALUES (-20006,-10005,now());

INSERT INTO profile (USERNAME, type, gender,  ID, creation_date, last_modified_date) 
	VALUES ('testClient8', 4, 2,-20007, now(),now());
INSERT INTO client (id,current_employee,last_active_date)
	VALUES (-20007,-10005,now());

-- referral
INSERT INTO profile (USERNAME, type, gender, ID, creation_date, last_modified_date) 
	VALUES ('testReferral', 5, 0,-30000, now(),now());

INSERT INTO profile_role (id, profile_id, role_id) VALUES (-1,-1,-1);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-2,-2,-1);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-3,-3,-1);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-4,-4,-1);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-5,-5,-2);

INSERT INTO profile_role (id, profile_id, role_id) VALUES (-100,-10000,-3);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-101,-10001,-3);

INSERT INTO profile_role (id, profile_id, role_id) VALUES (-200,-10002,-4);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-201,-10005,-2);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-202,-10003,-4);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-203,-10004,-4);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-204,-10005,-4);

INSERT INTO profile_role (id, profile_id, role_id) VALUES (-300,-20000,-5);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-301,-20001,-5);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-302,-20002,-5);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-303,-20003,-5);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-304,-20004,-5);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-305,-20005,-5);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-306,-20006,-5);
INSERT INTO profile_role (id, profile_id, role_id) VALUES (-307,-20007,-5);

INSERT INTO profile_role (id, profile_id, role_id) VALUES (-407,-30000,-6);


INSERT INTO profile_department (id, profile_id, department_id) VALUES (-1,-1,-500);
INSERT INTO profile_department (id, profile_id, department_id) VALUES (-2,-2,-100);
INSERT INTO profile_department (id, profile_id, department_id) VALUES (-3,-3,-100);
INSERT INTO profile_department (id, profile_id, department_id) VALUES (-4,-4,-100);
INSERT INTO profile_department (id, profile_id, department_id) VALUES (-5,-5,-500);

INSERT INTO profile_department (id, profile_id, department_id) VALUES (-100,-10000,-400);
INSERT INTO profile_department (id, profile_id, department_id) VALUES (-101,-10001,-400);

INSERT INTO profile_department (id, profile_id, department_id) VALUES (-200,-10002,-400);
INSERT INTO profile_department (id, profile_id, department_id) VALUES (-201,-10003,-400);
INSERT INTO profile_department (id, profile_id, department_id) VALUES (-202,-10004,-400);
INSERT INTO profile_department (id, profile_id, department_id) VALUES (-203,-10005,-400);
