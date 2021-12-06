-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: projectmanage
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `material_info_lr`
--

DROP TABLE IF EXISTS `material_info_lr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material_info_lr` (
  `material_lrid` int NOT NULL AUTO_INCREMENT COMMENT '原料录入ID',
  `project_id` int NOT NULL COMMENT '项目id',
  `material_need_id` int DEFAULT NULL COMMENT '原料需求ID',
  `material_id` int DEFAULT NULL COMMENT '所需原料ID,原料表中的Material_ID（如果原料信息库有该原料，则记录原料ID，否则，不记录原料ID）\nC-1-1页面录入',
  `material_name` varchar(30) DEFAULT NULL COMMENT '原料名称(技术人员提供)',
  `cas` varchar(20) DEFAULT NULL COMMENT 'Cas号(技术人员提供)',
  `cg_sl` double DEFAULT '0' COMMENT '采购量(g)(客服人员提供)',
  `price` double DEFAULT '0' COMMENT '原料价格(g)(客服人员提供)',
  `provider` varchar(100) DEFAULT NULL COMMENT '供应商(客服人员提供)',
  `provider_phone` varchar(50) DEFAULT NULL COMMENT '供应商联系方式(客服人员提供)',
  `is_buy` varchar(10) DEFAULT NULL COMMENT '是否可以买到(“能买到”“买不到”（客服人员提供）（作为向技术人员的反馈）)',
  `not_reason` varchar(500) DEFAULT NULL COMMENT '买不到原因(客服人员提供（作为向技术人员的反馈）)',
  `delete_flag` varchar(20) DEFAULT '1' COMMENT '删除标记，删除-0，不删除-1 （默认为1）\r\n与原料需求表（Material_need）中的删除标记同步',
  `create_name` varchar(20) DEFAULT NULL COMMENT '录入人，第一次反馈的人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间，第一次反馈时间',
  `update_name` varchar(20) DEFAULT NULL COMMENT '修改人，最后一次反馈的人',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期，最后一次反馈时间（可多次反馈）',
  PRIMARY KEY (`material_lrid`),
  UNIQUE KEY `material_info_lr_material_need_material_need_id_fk` (`material_need_id`),
  CONSTRAINT `material_info_lr_material_need_material_need_id_fk` FOREIGN KEY (`material_need_id`) REFERENCES `material_need` (`material_need_id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COMMENT='原料信息录入表（客服录入）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_info_lr`
--

LOCK TABLES `material_info_lr` WRITE;
/*!40000 ALTER TABLE `material_info_lr` DISABLE KEYS */;
INSERT INTO `material_info_lr` VALUES (1,4,1,0,'苹果','12',0,0,NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL),(2,4,2,5,'2','1',0,0,NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL),(6,6,3,0,'香蕉','45',0,0,NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL),(7,6,4,7,'3','1',0,0,NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL),(21,8,5,0,'梨子','56',0,0,NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL),(22,8,6,8,'2','3',0,0,NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `material_info_lr` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-25 14:34:18
