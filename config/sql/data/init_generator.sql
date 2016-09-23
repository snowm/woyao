TRUNCATE TABLE id_generator;

--Init profile
INSERT INTO id_generator (gen_name,gen_value) VALUES('role',1);
INSERT INTO id_generator (gen_name,gen_value) VALUES('permission',1);
INSERT INTO id_generator (gen_name,gen_value) VALUES('rolePermission',1);
INSERT INTO id_generator (gen_name,gen_value) VALUES('profile',1);
INSERT INTO id_generator (gen_name,gen_value) VALUES('profileRole',1);

