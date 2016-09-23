CREATE TABLE `ID_GENERATOR` (
  `GEN_NAME` varchar(255) DEFAULT NULL,
  `GEN_VALUE` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ID_GENERATOR_ALI` (
  `GEN_NAME` varchar(255) DEFAULT NULL,
  `GEN_VALUE` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ID_GENERATOR_CHANNEL` (
  `GEN_NAME` varchar(255) DEFAULT NULL,
  `GEN_VALUE` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ID_GENERATOR_LOCAL` (
  `GEN_NAME` varchar(255) DEFAULT NULL,
  `GEN_VALUE` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ID_GENERATOR_LOG` (
  `GEN_NAME` varchar(255) DEFAULT NULL,
  `GEN_VALUE` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ID_GENERATOR_STATISTIC` (
  `GEN_NAME` varchar(255) DEFAULT NULL,
  `GEN_VALUE` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `TIME_LIMIT_CONFIG` (
  `ID` bigint(20) NOT NULL,
  `CHOOSE_PROD_LEFT` int(11) DEFAULT NULL,
  `COMPLETE_LEFT` int(11) DEFAULT NULL,
  `MAP_CHANNEL_LEFT` int(11) DEFAULT NULL,
  `MAP_LOCAL_LEFT` int(11) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `SAVE_LEFT` int(11) DEFAULT NULL,
  `SEND_CHANNEL_LEFT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `MISP` (
  `ID` bigint(20) NOT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `PROVINCE` (
  `ID` bigint(20) NOT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `CHANNEL_CONFIG` (
  `ID` bigint(20) NOT NULL,
  `CONN_TIMEOUT` int(11) NOT NULL,
  `FLOW_ID` varchar(255) DEFAULT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `MANUALLY` bit(1) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `REQ_TIMEOUT` int(11) NOT NULL,
  `SOCKET_TIMEOUT` int(11) NOT NULL,
  `URL` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `SHDHST_CHANNEL_CONFIG` (
  `ACCOUNT` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_ow4v2hkojos02n1lqtep11t8t` FOREIGN KEY (`ID`) REFERENCES `CHANNEL_CONFIG` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `YITUKE_CHANNEL_CONFIG` (
  `APP_KEY` varchar(255) DEFAULT NULL,
  `APP_SECRET` varchar(255) DEFAULT NULL,
  `ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_30264a8c6pj8cosjdffhg8th2` FOREIGN KEY (`ID`) REFERENCES `CHANNEL_CONFIG` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `YITUO_CHANNEL_CONFIG` (
  `LOGIN_NAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_a41gpirvtxg3ihcuia8vh2bee` FOREIGN KEY (`ID`) REFERENCES `CHANNEL_CONFIG` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `CHANNEL` (
  `ID` bigint(20) NOT NULL,
  `CHANNEL_SERVICE_NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `SUPPORT_BUY_NUM` bit(1) NOT NULL,
  `TYPE` varchar(20) NOT NULL,
  `CONFIG_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `TYPE_UNIQUE` (`TYPE`),
  KEY `FK_kkwvfhgp1mxq2lkgnvb9l7y29` (`CONFIG_ID`),
  CONSTRAINT `FK_kkwvfhgp1mxq2lkgnvb9l7y29` FOREIGN KEY (`CONFIG_ID`) REFERENCES `CHANNEL_CONFIG` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `CHANNEL_DAILY_STATISTIC` (
  `ID` bigint(20) NOT NULL,
  `COUNT` int(11) NOT NULL,
  `END_DATE` datetime NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `START_DATE` datetime NOT NULL,
  `STATISTIC_INTERVAL` int(11) NOT NULL,
  `STATISTIC_TYPE` int(11) DEFAULT NULL,
  `CHANNEL_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_sbdloomuubwvjcnhg70pqtdc4` (`CHANNEL_ID`),
  CONSTRAINT `FK_sbdloomuubwvjcnhg70pqtdc4` FOREIGN KEY (`CHANNEL_ID`) REFERENCES `CHANNEL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `CHANNEL_REALTIME_STATISTIC` (
  `ID` bigint(20) NOT NULL,
  `COUNT` int(11) NOT NULL,
  `END_DATE` datetime NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `START_DATE` datetime NOT NULL,
  `STATISTIC_INTERVAL` int(11) NOT NULL,
  `STATISTIC_TYPE` int(11) DEFAULT NULL,
  `CHANNEL_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_92qroihhpam4lcqy2upjepx6c` (`CHANNEL_ID`),
  CONSTRAINT `FK_92qroihhpam4lcqy2upjepx6c` FOREIGN KEY (`CHANNEL_ID`) REFERENCES `CHANNEL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `CHANNEL_PRODUCT` (
  `ID` bigint(20) NOT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `REGION` int(11) DEFAULT NULL,
  `SIZE` float DEFAULT NULL,
  `SIZE_UNIT` int(11) DEFAULT NULL,
  `SUPPROT_BUY_NUM` bit(1) DEFAULT NULL,
  `CHANNEL_ID` bigint(20) NOT NULL,
  `MISP_ID` bigint(20) DEFAULT NULL,
  `PROVINCE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_2kuwors26bdcw2laym5bxk977` (`CHANNEL_ID`),
  KEY `FK_10vgaqy1tjlo8ad912m0pw84l` (`MISP_ID`),
  KEY `FK_s5sn8xa8i0d3md3exrs3oxs04` (`PROVINCE_ID`),
  CONSTRAINT `FK_10vgaqy1tjlo8ad912m0pw84l` FOREIGN KEY (`MISP_ID`) REFERENCES `MISP` (`ID`),
  CONSTRAINT `FK_2kuwors26bdcw2laym5bxk977` FOREIGN KEY (`CHANNEL_ID`) REFERENCES `CHANNEL` (`ID`),
  CONSTRAINT `FK_s5sn8xa8i0d3md3exrs3oxs04` FOREIGN KEY (`PROVINCE_ID`) REFERENCES `PROVINCE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `YITUKE_CHANNEL_PRODUCT` (
  `PKG_NO` varchar(255) NOT NULL,
  `ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_hqfbyr811p2u1a0mjafh0i8ub` FOREIGN KEY (`ID`) REFERENCES `CHANNEL_PRODUCT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `YITUO_CHANNEL_PRODUCT` (
  `PRODUCT_CODE` varchar(255) NOT NULL,
  `ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_tnm0it4226gmsgw5o3vtb2f41` FOREIGN KEY (`ID`) REFERENCES `CHANNEL_PRODUCT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `LOCAL_PRODUCT` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(20) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `GENERATION` int(11) DEFAULT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `NAME` varchar(100) NOT NULL,
  `REGION` int(11) DEFAULT NULL,
  `SIZE` int(11) DEFAULT NULL,
  `SIZE_UNIT` int(11) DEFAULT NULL,
  `MISP_ID` bigint(20) NOT NULL,
  `PROVINCE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_3fudb7wxxmvuoid4rysgl18bf` (`MISP_ID`),
  KEY `FK_lvsg5g4amm55y3bdyu5q610s2` (`PROVINCE_ID`),
  CONSTRAINT `FK_3fudb7wxxmvuoid4rysgl18bf` FOREIGN KEY (`MISP_ID`) REFERENCES `MISP` (`ID`),
  CONSTRAINT `FK_lvsg5g4amm55y3bdyu5q610s2` FOREIGN KEY (`PROVINCE_ID`) REFERENCES `PROVINCE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ALI_PRODUCT` (
  `ID` bigint(20) NOT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `MAX_PRICE` bigint(20) DEFAULT NULL,
  `MIN_PRICE` bigint(20) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `PRODUCT_ID` varchar(20) NOT NULL,
  `REGION` int(11) DEFAULT NULL,
  `SIZE` float DEFAULT NULL,
  `SIZE_UNIT` int(11) DEFAULT NULL,
  `MISP_ID` bigint(20) DEFAULT NULL,
  `PROVINCE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_8po6px4wh96ej3lh7f361g8xd` (`MISP_ID`),
  KEY `FK_krmk9t6ppvwt818t1imq6go5g` (`PROVINCE_ID`),
  KEY `IDX_ALI_PROD_SPUID` (`PRODUCT_ID`),
  CONSTRAINT `FK_8po6px4wh96ej3lh7f361g8xd` FOREIGN KEY (`MISP_ID`) REFERENCES `MISP` (`ID`),
  CONSTRAINT `FK_krmk9t6ppvwt818t1imq6go5g` FOREIGN KEY (`PROVINCE_ID`) REFERENCES `PROVINCE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ALI_LOCAL_PRODUCT` (
  `ID` bigint(20) NOT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ALI_PRODUCT_ID` bigint(20) NOT NULL,
  `LOCAL_PRODUCT_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_o0w8vvwgefcnwhb9fa44kc4rv` (`ALI_PRODUCT_ID`),
  KEY `FK_5q5kne56kwgsmhbf1sm1lndka` (`LOCAL_PRODUCT_ID`),
  KEY `IDX_ALI_LC_PROD_ENABLED` (`ENABLED`) USING BTREE,
  CONSTRAINT `FK_5q5kne56kwgsmhbf1sm1lndka` FOREIGN KEY (`LOCAL_PRODUCT_ID`) REFERENCES `LOCAL_PRODUCT` (`ID`),
  CONSTRAINT `FK_o0w8vvwgefcnwhb9fa44kc4rv` FOREIGN KEY (`ALI_PRODUCT_ID`) REFERENCES `ALI_PRODUCT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `LOCAL_CHANNEL_PRODUCT` (
  `ID` bigint(20) NOT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `SEQ` int(11) DEFAULT NULL,
  `CHANNEL_PRODUCT_ID` bigint(20) NOT NULL,
  `LOCAL_PRODUCT_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_5q75w6tmxm8p332oqkxxf3wqd` (`CHANNEL_PRODUCT_ID`),
  KEY `FK_mcb2pp5pos03plwetl0557wbd` (`LOCAL_PRODUCT_ID`),
  CONSTRAINT `FK_5q75w6tmxm8p332oqkxxf3wqd` FOREIGN KEY (`CHANNEL_PRODUCT_ID`) REFERENCES `CHANNEL_PRODUCT` (`ID`),
  CONSTRAINT `FK_mcb2pp5pos03plwetl0557wbd` FOREIGN KEY (`LOCAL_PRODUCT_ID`) REFERENCES `LOCAL_PRODUCT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ALI_ORDER` (
  `ID` bigint(20) NOT NULL,
  `ACCOUNT_VAL` varchar(20) NOT NULL,
  `BIZ_TYPE` int(11) DEFAULT NULL,
  `BUY_NUM` int(11) DEFAULT NULL,
  `BUYER_ID` varchar(50) DEFAULT NULL,
  `BUYER_IP` varchar(50) DEFAULT NULL,
  `ERROR_CODE` int(11) DEFAULT NULL,
  `FAIL_REASON` varchar(500) DEFAULT NULL,
  `IN_CORRECT_ORDER` bit(1) DEFAULT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `NOTIFY_URL` varchar(255) DEFAULT NULL,
  `SELLER_ID` varchar(20) DEFAULT NULL,
  `SIGN` varchar(50) DEFAULT NULL,
  `SPU_ID` varchar(20) NOT NULL,
  `SUPPLIER_ID` varchar(20) NOT NULL,
  `TB_ORDER_NO` varchar(20) NOT NULL,
  `TB_PRICE` int(11) DEFAULT NULL,
  `TIME_LIMIT` int(11) DEFAULT NULL,
  `TIME_START` datetime DEFAULT NULL,
  `TIME_DUE` datetime DEFAULT NULL,
  `TOTAL_PRICE` int(11) DEFAULT NULL,
  `TS` bigint(20) DEFAULT NULL,
  `UNIT_PRICE` int(11) DEFAULT NULL,
  `ALI_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `ERROR_PROCESSED` bit(1) DEFAULT NULL,
  `IN_CORRECT_REASON` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_ALI_ORD_TOORDNO` (`TB_ORDER_NO`),
  KEY `FK_ka3xnxvwcsy79crjjfnoo2yiv` (`ALI_PRODUCT_ID`),
  KEY `IDX_ALI_ORD_SPU_ID` (`SPU_ID`) USING BTREE,
  KEY `IDX_ALI_ORD_MB` (`ACCOUNT_VAL`),
  KEY `IDX_ALI_ORD_CRDT` (`CREATION_DATE`),
  KEY `IDX_ALI_ORD_LMDT` (`LAST_MODIFIED_DATE`),
  CONSTRAINT `FK_ka3xnxvwcsy79crjjfnoo2yiv` FOREIGN KEY (`ALI_PRODUCT_ID`) REFERENCES `ALI_PRODUCT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `LOCAL_ORDER` (
  `ID` bigint(20) NOT NULL,
  `ERROR_CODE` int(11) DEFAULT NULL,
  `FAIL_REASON` varchar(500) DEFAULT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ORDER_STATUS` int(11) DEFAULT NULL,
  `SENT_CB_TO_ALI` bit(1) DEFAULT NULL,
  `SENT_CB_TO_ALI_DATE` datetime DEFAULT NULL,
  `STEP` int(11) NOT NULL,
  `TIME_DUE` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `ALI_ORDER_ID` bigint(20) NOT NULL,
  `LOCAL_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `MANUALLY` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_i20584wdn2q4ejcoixtrsd08u` (`ALI_ORDER_ID`),
  KEY `FK_ivxwfay0kgt8qt45cq3tgbiv5` (`LOCAL_PRODUCT_ID`),
  KEY `IDX_LC_ORD_ERR_CODE` (`ERROR_CODE`) USING BTREE,
  KEY `IDX_LC_ORD_SENT_FLAG` (`SENT_CB_TO_ALI`) USING BTREE,
  KEY `IDX_LC_ORD_STEP` (`STEP`) USING BTREE,
  KEY `IDX_LC_ORD_STATUS` (`ORDER_STATUS`) USING BTREE,
  KEY `IDX_LC_ORD_TIMEDUE` (`TIME_DUE`),
  CONSTRAINT `FK_i20584wdn2q4ejcoixtrsd08u` FOREIGN KEY (`ALI_ORDER_ID`) REFERENCES `ALI_ORDER` (`ID`),
  CONSTRAINT `FK_ivxwfay0kgt8qt45cq3tgbiv5` FOREIGN KEY (`LOCAL_PRODUCT_ID`) REFERENCES `LOCAL_PRODUCT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `MAPPED_CHANNEL_PRODUCT` (
  `ID` bigint(20) NOT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `SEQ` int(11) DEFAULT NULL,
  `TRIED` bit(1) DEFAULT NULL,
  `CHANNEL_PRODUCT_ID` bigint(20) NOT NULL,
  `LOCAL_ORDER_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_o3wb3lv025649v5mga7o0muee` (`CHANNEL_PRODUCT_ID`),
  KEY `FK_4v27go89b4mm7uvkfwkaion55` (`LOCAL_ORDER_ID`),
  CONSTRAINT `FK_4v27go89b4mm7uvkfwkaion55` FOREIGN KEY (`LOCAL_ORDER_ID`) REFERENCES `LOCAL_ORDER` (`ID`),
  CONSTRAINT `FK_o3wb3lv025649v5mga7o0muee` FOREIGN KEY (`CHANNEL_PRODUCT_ID`) REFERENCES `CHANNEL_PRODUCT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `CHANNEL_ORDER` (
  `ID` bigint(20) NOT NULL,
  `BUY_NUM` int(11) DEFAULT NULL,
  `CALLBACK_VALIDATION_CODE` varchar(100) DEFAULT NULL,
  `COMPLETE_DATE` datetime DEFAULT NULL,
  `FAIL_REASON` varchar(500) DEFAULT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `ORDER_STATUS` int(11) DEFAULT NULL,
  `SENT_DATE` datetime DEFAULT NULL,
  `THIRD_CHANNEL_ORDER_ID` varchar(100) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CHANNEL_PRODUCT_ID` bigint(20) NOT NULL,
  `LOCAL_ORDER_ID` bigint(20) NOT NULL,
  `MAPPED_CHANNEL_PRODUCT_ID` bigint(20) NOT NULL,
  `MANUALLY` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_r30q7d7ql56tl5dpmgii3b881` (`CHANNEL_PRODUCT_ID`),
  KEY `FK_syhbil2ofqukr9f3vyh15ho0w` (`LOCAL_ORDER_ID`),
  KEY `FK_ssc3i4uua4scf0x5hb1c593ja` (`MAPPED_CHANNEL_PRODUCT_ID`),
  KEY `IDX_CORD_STATUS` (`ORDER_STATUS`) USING BTREE,
  KEY `IDX_CORD_3RD_ORDID` (`THIRD_CHANNEL_ORDER_ID`),
  CONSTRAINT `FK_r30q7d7ql56tl5dpmgii3b881` FOREIGN KEY (`CHANNEL_PRODUCT_ID`) REFERENCES `CHANNEL_PRODUCT` (`ID`),
  CONSTRAINT `FK_ssc3i4uua4scf0x5hb1c593ja` FOREIGN KEY (`MAPPED_CHANNEL_PRODUCT_ID`) REFERENCES `MAPPED_CHANNEL_PRODUCT` (`ID`),
  CONSTRAINT `FK_syhbil2ofqukr9f3vyh15ho0w` FOREIGN KEY (`LOCAL_ORDER_ID`) REFERENCES `LOCAL_ORDER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ORDER_PROCESS_LOG` (
  `ID` bigint(20) NOT NULL,
  `CODE` int(11) NOT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `END_DATE` datetime NOT NULL,
  `LOCAL_ORDER_ID` bigint(20) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `START_DATE` datetime NOT NULL,
  `STATUS` int(11) NOT NULL,
  `STEP` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `PROFILE` (
  `ID` bigint(20) NOT NULL,
  `CREDENTIALS_EXPIRED` bit(1) DEFAULT NULL,
  `EMAIL` varchar(200) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `EXPIRED` bit(1) DEFAULT NULL,
  `GENDER` int(11) DEFAULT NULL,
  `LOCKED` bit(1) DEFAULT NULL,
  `MOBILE_NUMBER` varchar(20) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `NICKNAME` varchar(50) DEFAULT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `TYPE` int(11) NOT NULL,
  `USERNAME` varchar(50) NOT NULL,
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

CREATE TABLE `PERMISSION` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `PROFILE_ROLE` (
  `ID` bigint(20) NOT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `PROFILE_ID` bigint(20) DEFAULT NULL,
  `ROLE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_fv5l1pbb2u5mkuicpj9udk0lk` (`PROFILE_ID`),
  KEY `FK_ofvbhn5g14udp3bausuqauv8s` (`ROLE_ID`),
  CONSTRAINT `FK_fv5l1pbb2u5mkuicpj9udk0lk` FOREIGN KEY (`PROFILE_ID`) REFERENCES `PROFILE` (`ID`),
  CONSTRAINT `FK_ofvbhn5g14udp3bausuqauv8s` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLE` (`ID`)
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
