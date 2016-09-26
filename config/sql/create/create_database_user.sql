CREATE SCHEMA `woyao` DEFAULT CHARACTER SET utf8mb4 ;

CREATE USER 'woyao'@'localhost' IDENTIFIED BY 'woyao_003';
CREATE USER 'woyao'@'%' IDENTIFIED BY 'w0ya0_oo3!@';

GRANT ALL PRIVILEGES ON `woyao`.* TO 'woyao'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON `woyao`.* TO 'woyao'@'%';