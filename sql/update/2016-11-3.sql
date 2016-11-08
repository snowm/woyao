CREATE TABLE `SHOP_MSG_PRODUCT_CONF` (
  `ID` bigint(20) NOT NULL,
  `REF_PRODUCT_ID` bigint(20) NOT NULL,
  `UNIT_PRICE` int(11) NOT NULL,
  `SHOP_ID` bigint(20) NOT NULL,
  `DELETED` bit(1) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SHOP_MSG_SHOP_ID__idx` (`SHOP_ID`),
  KEY `FK_SHOP_MSG_REF_PROD_ID` (`REF_PRODUCT_ID`),
  CONSTRAINT `FK_SHOP_MSG_PROD_ID_PROD_ID` FOREIGN KEY (`REF_PRODUCT_ID`) REFERENCES `PRODUCT` (`ID`),
  CONSTRAINT `FK_SHOP_MSG_SHOP_ID` FOREIGN KEY (`SHOP_ID`) REFERENCES `SHOP` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


ALTER TABLE `PURCHASE_ORDER` 
ADD COLUMN `SHOP_ID` BIGINT(20) NULL DEFAULT NULL AFTER `TO_PROFILE_WX_ID`,
ADD INDEX `FK_ORD_SHOP_ID_idx` (`SHOP_ID` ASC);

ALTER TABLE `PURCHASE_ORDER` 
ADD CONSTRAINT `FK_ORD_SHOP_ID`
  FOREIGN KEY (`SHOP_ID`)
  REFERENCES `woyao`.`SHOP` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;