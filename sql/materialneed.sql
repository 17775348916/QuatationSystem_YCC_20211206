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
-- Table structure for table `material_need`
--

DROP TABLE IF EXISTS `material_need`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material_need` (
  `material_need_id` int NOT NULL AUTO_INCREMENT COMMENT '原料需求ID',
  `project_id` int DEFAULT NULL COMMENT '项目ID，项目概况表中的Project_ID',
  `material_id` int DEFAULT NULL COMMENT '所需原料ID，原料表中的Material_ID',
  `material_name` varchar(20) DEFAULT NULL COMMENT '所需原料名称',
  `cas` varchar(20) DEFAULT NULL COMMENT 'Cas号',
  `material_sl` double DEFAULT NULL COMMENT '需要原料数量',
  `picture` varchar(100) DEFAULT NULL COMMENT '原料图片（记录路径）',
  `bz` varchar(500) DEFAULT NULL COMMENT '备注',
  `delete_flag` varchar(5) DEFAULT '1' COMMENT '删除标记,删除-0，不删除-1 （默认为1）',
  `create_name` varchar(20) DEFAULT NULL COMMENT '录入人，第一次下达的人',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期，第一次下达时间',
  `update_name` varchar(20) DEFAULT NULL COMMENT '修改人，最后一次下达的人',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期，最后一次下达时间（可多次下达）',
  PRIMARY KEY (`material_need_id`),
  KEY `material_need_project_overview_project_id_fk` (`project_id`),
  CONSTRAINT `material_need_project_overview_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project_overview` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='原料需求表（技术录入）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_need`
--

LOCK TABLES `material_need` WRITE;
/*!40000 ALTER TABLE `material_need` DISABLE KEYS */;
INSERT INTO `material_need` VALUES (1,4,0,'苹果','12',32,NULL,'要红一点','0','管理员 工号:1','2021-02-24 22:09:04','管理员 工号:1','2021-02-24 22:09:04'),(2,4,5,'2','1',54,NULL,'99','0','管理员 工号:1','2021-02-24 22:09:04','管理员 工号:1','2021-02-24 22:09:04'),(3,6,0,'香蕉','45',1,NULL,'要熟了的','1','管理员 工号:1','2021-02-24 22:11:06','管理员 工号:1','2021-02-24 22:11:06'),(4,6,7,'3','1',12,NULL,'89j','1','管理员 工号:1','2021-02-24 22:11:06','管理员 工号:1','2021-02-24 22:11:06'),(5,8,0,'梨子','56',12.6,NULL,'今年成熟的','1','管理员 工号:1','2021-02-24 22:12:00','管理员 工号:1','2021-02-24 22:12:00'),(6,8,8,'2','3',14,NULL,'QAZ','1','管理员 工号:1','2021-02-24 22:12:00','管理员 工号:1','2021-02-24 22:12:00');
/*!40000 ALTER TABLE `material_need` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-25 14:34:03
