CREATE TABLE `PERMISSION` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ROLE` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VISIBLE` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ROLE_PERMISSION` (
  `ID` bigint(20) NOT NULL,
  `PERMISSION_ID` bigint(20) DEFAULT NULL,
  `ROLE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_m2r1sqbtxt5fo39ax55fiq4l0` (`PERMISSION_ID`),
  KEY `FK_fn5mhmaqw72j8387h40mmj8sn` (`ROLE_ID`),
  CONSTRAINT `FK_fn5mhmaqw72j8387h40mmj8sn` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLE` (`ID`),
  CONSTRAINT `FK_m2r1sqbtxt5fo39ax55fiq4l0` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `PERMISSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `PROFILE` (
  `ID` bigint(20) NOT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `CREDENTIALS_EXPIRED` bit(1) DEFAULT NULL,
  `EMAIL` varchar(200) DEFAULT NULL,
  `EXPIRED` bit(1) DEFAULT NULL,
  `GENDER` int(11) DEFAULT NULL,
  `LOCKED` bit(1) DEFAULT NULL,
  `MOBILE_NUMBER` varchar(20) DEFAULT NULL,
  `NICKNAME` varchar(50) DEFAULT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `TYPE` int(11) NOT NULL,
  `USERNAME` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME_UNIQUE` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `PROFILE_ROLE` (
  `ID` bigint(20) NOT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `PROFILE_ID` bigint(20) DEFAULT NULL,
  `ROLE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PROFILE_ID` (`PROFILE_ID`),
  KEY `FK_ROLE_ID` (`ROLE_ID`),
  CONSTRAINT `FK_fv5l1pbb2u5mkuicpj9udk0lk` FOREIGN KEY (`PROFILE_ID`) REFERENCES `PROFILE` (`ID`),
  CONSTRAINT `FK_ofvbhn5g14udp3bausuqauv8s` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
