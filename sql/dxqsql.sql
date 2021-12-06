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
-- Table structure for table `account_information`
--

DROP TABLE IF EXISTS `account_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_information` (
  `account_id` int NOT NULL AUTO_INCREMENT COMMENT '账号ID',
  `account_name` varchar(20) DEFAULT NULL COMMENT '账号，根据人员类型加前缀,例如“GW-1234”',
  `keywords` varchar(20) NOT NULL COMMENT '密码',
  `name` varchar(20) DEFAULT NULL COMMENT '员工姓名',
  `type_id` int NOT NULL COMMENT '身份类型ID',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`account_id`),
  KEY `account_information_identity_type_id_fk` (`type_id`),
  CONSTRAINT `account_information_identity_type_id_fk` FOREIGN KEY (`type_id`) REFERENCES `identity` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='账号信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_information`
--

LOCK TABLES `account_information` WRITE;
/*!40000 ALTER TABLE `account_information` DISABLE KEYS */;
INSERT INTO `account_information` VALUES (1,'ADMIN管理员','123456','管理员',1,'2021-01-26 00:00:00',NULL),(2,'SW商务','000000','商务',2,'2021-01-26 00:00:00',NULL),(3,'JS技术','000000','技术',3,'2021-01-26 00:00:00',NULL),(4,'KF客服','000000','客服',4,'2021-01-26 00:00:00',NULL),(7,'KF小明','111111','小明',4,'2021-01-28 22:22:44',NULL),(9,'KF小红','111111','小红',4,'2021-01-28 22:22:44',NULL),(10,'ADMIN小蓝','111111','小蓝',1,'2021-02-28 08:43:01',NULL);
/*!40000 ALTER TABLE `account_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `b2_ypg`
--

DROP TABLE IF EXISTS `b2_ypg`;
/*!50001 DROP VIEW IF EXISTS `b2_ypg`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `b2_ypg` AS SELECT 
 1 AS `bh`,
 1 AS `xdrq`,
 1 AS `hs`,
 1 AS `pgzt`,
 1 AS `cjjg`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `feasible_project`
--

DROP TABLE IF EXISTS `feasible_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feasible_project` (
  `feasible_project_id` int NOT NULL AUTO_INCREMENT COMMENT '可行项目ID',
  `project_id` int DEFAULT NULL COMMENT '项目ID,项目概况表中的Project_ID',
  `h_sl` varchar(20) DEFAULT NULL COMMENT '氢谱数量',
  `c_sl` varchar(20) DEFAULT NULL COMMENT '碳谱数量',
  `m_sl` varchar(20) DEFAULT NULL COMMENT '质谱数量',
  `dtlx_sj_cs` double DEFAULT NULL COMMENT '打通路线时间,大于或等于零的数字，可输入小数',
  `jll_sj_cs` double DEFAULT NULL COMMENT '积累量时间,大于或等于零的数字，可输入小数',
  `zsj_cs` double DEFAULT NULL COMMENT '测试总时间(打通路线时间+积累量时间)',
  `is_difficult_js` varchar(10) DEFAULT NULL COMMENT '技术难度,“难”、“一般”、“简单”',
  `papers_js` varchar(500) DEFAULT NULL COMMENT '上传文献,技术判断查阅的相关文献',
  `time_needed` varchar(20) DEFAULT NULL COMMENT '所需总时间，用于客服人员拟合同（比完成项目时间适当多几天）',
  `bz` varchar(500) DEFAULT NULL COMMENT '备注,在此输入完成项目的特殊要求，例如：需要客户提供特定的原料信息，项目的技术难点',
  `evaluation_js` varchar(20) DEFAULT '未评估' COMMENT '“已评估-可行”、“未评估（默认）”（技术人员填写）',
  `evaluation_name` varchar(20) DEFAULT NULL COMMENT '评估人',
  `evaluation_date` datetime DEFAULT NULL COMMENT '评估时间,用于T-1页面（二）完成时间：xx，耗时：x小时,\nB-2页面（二）计算耗时（已评估-可行，未成交）',
  PRIMARY KEY (`feasible_project_id`),
  KEY `project_test_project_overview_project_id_fk` (`project_id`),
  CONSTRAINT `project_test_project_overview_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project_overview` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='可行项目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feasible_project`
--

LOCK TABLES `feasible_project` WRITE;
/*!40000 ALTER TABLE `feasible_project` DISABLE KEYS */;
INSERT INTO `feasible_project` VALUES (1,2,'2','1','4',12,13,25,'难','2/wjhl1wi7g.csv','30','无','已评估-可行','管理员 工号:1','2021-03-24 16:09:21'),(2,5,'1','3','2',13.2,15.7,28.9,'一般','5/wk52f7b3t.pdf','30','无','已评估-可行','技术 工号:3','2021-03-24 16:59:06');
/*!40000 ALTER TABLE `feasible_project` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS insert_pgzt */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `insert_pgzt` AFTER INSERT ON `feasible_project` FOR EACH ROW begin
    -- todo: implement
    if new.evaluation_js = '已评估-可行' then
        update project_zt t
        set t.project_zt_js  ='已评估-可行',
            t.evaluation_name=new.evaluation_name,
            t.evaluation_name=new.evaluation_date
        where t.project_id = new.project_id;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS update_pgzt */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `update_pgzt` AFTER UPDATE ON `feasible_project` FOR EACH ROW begin
    -- todo: implement
    if new.evaluation_js = '已评估-可行' then
        update project_zt t
        set t.project_zt_js  ='已评估-可行',
            t.evaluation_name=new.evaluation_name,
            t.evaluation_name=new.evaluation_date
        where t.project_id = new.project_id;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `final_price`
--

DROP TABLE IF EXISTS `final_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `final_price` (
  `p_final_price_id` int NOT NULL AUTO_INCREMENT COMMENT '最终报价ID',
  `project_id` int DEFAULT NULL COMMENT '项目ID，项目概况表中的Project_ID',
  `material_cost` double DEFAULT NULL COMMENT '原料成本',
  `cs_material_cost` double DEFAULT NULL COMMENT '测试成本',
  `rj_material_cost` double DEFAULT NULL COMMENT '溶剂耗材成本',
  `work_cost` double DEFAULT NULL COMMENT '劳动成本',
  `price_model` varchar(5) DEFAULT NULL COMMENT '报价模式，A/B/C/D/E',
  `wb_price` double DEFAULT NULL COMMENT '外包报价',
  `final_price` double DEFAULT NULL COMMENT '最终价格',
  `create_name` varchar(20) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_name` varchar(20) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期',
  `all_cost` double DEFAULT NULL COMMENT '总成本 = 原料成本 + 测试成本+ 溶剂耗材成本 + 劳动成本',
  PRIMARY KEY (`p_final_price_id`),
  KEY `FINAL_PRICE_project_overview_project_id_fk` (`project_id`),
  CONSTRAINT `FINAL_PRICE_project_overview_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project_overview` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='报价表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `final_price`
--

LOCK TABLES `final_price` WRITE;
/*!40000 ALTER TABLE `final_price` DISABLE KEYS */;
INSERT INTO `final_price` VALUES (1,2,113,7,7,6097.560975609756,'B',8892.229965156794,8892.229965156794,'管理员 工号:1','2021-03-24 16:09:22',NULL,NULL,6224.560975609756),(2,5,126,6,6,7048.780487804877,'C',10266.829268292682,10266.829268292682,'技术 工号:3','2021-03-24 16:59:07',NULL,NULL,7186.780487804877);
/*!40000 ALTER TABLE `final_price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `identity`
--

DROP TABLE IF EXISTS `identity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `identity` (
  `type_id` int NOT NULL AUTO_INCREMENT COMMENT '身份ID',
  `type` varchar(20) DEFAULT NULL COMMENT '身份类型',
  `rank` varchar(10) DEFAULT NULL COMMENT '身份级别',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_date` datetime DEFAULT NULL COMMENT '最后修改日期',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='身份类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `identity`
--

LOCK TABLES `identity` WRITE;
/*!40000 ALTER TABLE `identity` DISABLE KEYS */;
INSERT INTO `identity` VALUES (1,'系统管理员','高级','2021-01-18 00:00:00','2021-01-18 00:00:00'),(2,'商务人员','高级','2021-01-18 00:00:00','2021-01-18 00:00:00'),(3,'技术人员','高级','2021-01-18 00:00:00','2021-01-18 00:00:00'),(4,'客服人员','高级','2021-01-18 00:00:00','2021-01-18 00:00:00');
/*!40000 ALTER TABLE `identity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kh_model_judgment`
--

DROP TABLE IF EXISTS `kh_model_judgment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kh_model_judgment` (
  `kh_model_id` int NOT NULL AUTO_INCREMENT COMMENT '客户报价模式ID',
  `kh_type` varchar(20) DEFAULT NULL COMMENT '客户类型,“企业”或“高校”',
  `is_money` varchar(10) DEFAULT NULL COMMENT '是否有钱,“有钱”、“一般”、“没钱”',
  `is_difficult` varchar(10) DEFAULT NULL COMMENT '项目难度,“难”、“一般”、“简单”',
  `model_name` varchar(5) DEFAULT NULL COMMENT '报价模式，五种模式“A,B,C,D,E”，E自定义',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`kh_model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='客户报价模式判断表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kh_model_judgment`
--

LOCK TABLES `kh_model_judgment` WRITE;
/*!40000 ALTER TABLE `kh_model_judgment` DISABLE KEYS */;
INSERT INTO `kh_model_judgment` VALUES (6,'企业','有钱','难','A','2021-02-27 00:03:56','2021-02-27 00:03:58'),(7,'企业','有钱','一般','A','2021-02-27 00:03:59','2021-02-27 00:03:58'),(8,'企业','有钱','容易','A','2021-02-27 00:04:00','2021-02-27 00:04:01'),(9,'企业','一般','难','A','2021-02-27 00:04:02','2021-02-27 00:04:01'),(10,'企业','一般','一般','A','2021-02-27 00:04:03','2021-02-27 00:04:03'),(11,'企业','一般','容易','A','2021-02-27 00:04:05','2021-02-27 00:04:04'),(12,'高校','有钱','难','B','2021-02-27 00:04:05','2021-02-27 00:04:06'),(13,'高校','一般','难','B','2021-02-27 00:04:07','2021-02-27 00:04:07'),(14,'高校','有钱','一般','C','2021-02-27 00:04:08','2021-02-27 00:04:09'),(15,'高校','一般','一般','C','2021-02-27 00:04:10','2021-02-27 00:04:09'),(16,'高校','有钱','容易','D','2021-02-27 00:04:11','2021-02-27 00:04:11'),(17,'高校','一般','容易','D','2021-02-27 00:04:13','2021-02-27 00:04:12');
/*!40000 ALTER TABLE `kh_model_judgment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_info`
--

DROP TABLE IF EXISTS `material_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material_info` (
  `material_id` int NOT NULL AUTO_INCREMENT COMMENT '原料ID',
  `material_name` varchar(30) DEFAULT NULL COMMENT '原料名称',
  `cas` varchar(20) DEFAULT NULL COMMENT 'Cas号',
  `structure` varchar(100) DEFAULT NULL COMMENT '结构式',
  `quantities` double DEFAULT NULL COMMENT '规格(g)，比如某原料包装规格：一包是100g，价格（price）是50元',
  `qurity` double DEFAULT NULL COMMENT '纯度',
  `price` double DEFAULT NULL COMMENT '原料价格',
  `provider` varchar(100) DEFAULT NULL COMMENT '供应商',
  `provider_phone` varchar(50) DEFAULT NULL COMMENT '供应商联系方式',
  `bz` varchar(500) DEFAULT NULL COMMENT '备注，录入是否提供发票，是否有现货等信息',
  `create_name` varchar(20) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_name` varchar(20) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期',
  `select_date` datetime DEFAULT NULL COMMENT ' 最后查询日期',
  `select_name` varchar(20) DEFAULT NULL COMMENT '最后查询人',
  PRIMARY KEY (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='原料基本表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_info`
--

LOCK TABLES `material_info` WRITE;
/*!40000 ALTER TABLE `material_info` DISABLE KEYS */;
INSERT INTO `material_info` VALUES (1,'1','1',NULL,1,0.1,12,'TRE','1122','SS','管理员 工号:1','2021-02-21 01:04:16',NULL,NULL,NULL,NULL),(4,'2','1',NULL,1,0.1,12,'TRE','2211','ww','管理员 工号:1','2021-02-21 01:07:16',NULL,NULL,NULL,NULL),(5,'2','1',NULL,1,0.1,14,'qwe','5734','99','管理员 工号:1','2021-02-22 17:35:15',NULL,NULL,NULL,NULL),(6,'3','1',NULL,23,0.12,12,'egt','222','89j','管理员 工号:1','2021-02-23 17:19:11','1','2021-02-23 20:24:42',NULL,NULL),(8,'2','3',NULL,45,0.87,24,'HSD','653','QAZ','管理员 工号:1','2021-02-23 20:28:26','管理员 工号:1','2021-02-23 20:28:26',NULL,NULL),(9,'香蕉','45',NULL,32,0.4,13,'tewfe',NULL,NULL,'客服 工号:4','2021-02-26 14:23:09','客服 工号:4','2021-02-26 14:23:09',NULL,NULL),(10,'梨','76',NULL,7,0,3,'543','123',NULL,'管理员 工号:1','2021-02-26 20:12:24','管理员 工号:1','2021-02-26 22:17:26',NULL,NULL),(11,'桃子','98',NULL,3,0,12,'rfv',NULL,NULL,'管理员 工号:1','2021-02-26 20:20:53','管理员 工号:1','2021-02-26 20:20:53',NULL,NULL),(12,'2','34',NULL,12,0,50,'[]',NULL,NULL,'管理员 工号:1','2021-02-27 20:13:57','管理员 工号:1','2021-02-27 20:13:57',NULL,NULL),(13,'苹果','2351',NULL,45,0,100,'akt',NULL,NULL,'管理员 工号:1','2021-03-24 15:56:10','管理员 工号:1','2021-03-24 15:56:10',NULL,NULL),(14,'li','32453',NULL,50,0,100,'wewdfca',NULL,NULL,'客服 工号:4','2021-03-24 16:54:47','客服 工号:4','2021-03-24 16:54:47',NULL,NULL),(15,'li','32453',NULL,100,95,180,'sakdfjl','','','客服 工号:4','2021-03-24 16:56:35','客服 工号:4','2021-03-24 16:56:35',NULL,NULL);
/*!40000 ALTER TABLE `material_info` ENABLE KEYS */;
UNLOCK TABLES;

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
  `cg_sl` varchar(20) DEFAULT '0' COMMENT '采购量(g)(客服人员提供)',
  `price` double DEFAULT '0' COMMENT '原料价格(g)(客服人员提供)',
  `provider` varchar(100) DEFAULT NULL COMMENT '供应商(客服人员提供)',
  `provider_phone` varchar(50) DEFAULT NULL COMMENT '供应商联系方式(客服人员提供)',
  `is_buy` varchar(10) DEFAULT NULL COMMENT '是否可以买到(“能买到”“买不到”（客服人员提供）（作为向技术人员的反馈）)',
  `not_reason` varchar(500) DEFAULT NULL COMMENT '买不到原因(客服人员提供（作为向技术人员的反馈）)',
  `delete_flag` varchar(20) DEFAULT '1' COMMENT '删除标记，删除-0，不删除-1 （默认为1）\n与原料需求表（Material_need）中的删除标记同步',
  `create_name` varchar(20) DEFAULT NULL COMMENT '录入人，第一次反馈的人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间，第一次反馈时间',
  `update_name` varchar(20) DEFAULT NULL COMMENT '修改人，最后一次反馈的人',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期，最后一次反馈时间（可多次反馈）',
  PRIMARY KEY (`material_lrid`),
  UNIQUE KEY `material_info_lr_material_need_material_need_id_fk` (`material_need_id`),
  CONSTRAINT `material_info_lr_material_need_material_need_id_fk` FOREIGN KEY (`material_need_id`) REFERENCES `material_need` (`material_need_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='原料信息录入表（客服录入）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_info_lr`
--

LOCK TABLES `material_info_lr` WRITE;
/*!40000 ALTER TABLE `material_info_lr` DISABLE KEYS */;
INSERT INTO `material_info_lr` VALUES (1,2,1,0,'苹果','2351','45',100,'akt',NULL,'能买到',NULL,'1','管理员 工号:1','2021-03-24 15:56:06','管理员 工号:1','2021-03-24 15:56:06'),(2,2,2,9,'香蕉','45','32',13,'tewfe',NULL,'能买到',NULL,'1','管理员 工号:1','2021-03-24 15:56:06','管理员 工号:1','2021-03-24 15:56:06'),(3,5,3,0,'li','32453','50',100,'wewdfca',NULL,'能买到',NULL,'1','客服 工号:4','2021-03-24 16:54:40','客服 工号:4','2021-03-24 16:54:40'),(4,5,4,9,'香蕉','45','64',26,'tewfe',NULL,'能买到',NULL,'1','客服 工号:4','2021-03-24 16:54:40','客服 工号:4','2021-03-24 16:54:40'),(5,3,5,0,'氯气','2541264','0',0,NULL,NULL,'买不到','爆炸管制','0','管理员 工号:1','2021-03-24 17:10:17','管理员 工号:1','2021-03-24 17:10:17');
/*!40000 ALTER TABLE `material_info_lr` ENABLE KEYS */;
UNLOCK TABLES;

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
  `material_sl` varchar(20) DEFAULT '0' COMMENT '需要原料数量',
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='原料需求表（技术录入）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_need`
--

LOCK TABLES `material_need` WRITE;
/*!40000 ALTER TABLE `material_need` DISABLE KEYS */;
INSERT INTO `material_need` VALUES (1,2,0,'苹果','2351','43',NULL,'要红的','1','管理员 工号:1','2021-03-24 15:51:56','管理员 工号:1','2021-03-24 15:51:56'),(2,2,9,'香蕉','45','23',NULL,'要黄的','1','管理员 工号:1','2021-03-24 15:51:57','管理员 工号:1','2021-03-24 15:51:57'),(3,5,0,'li','32453','45',NULL,'','1','技术 工号:3','2021-03-24 16:52:38','技术 工号:3','2021-03-24 16:52:38'),(4,5,9,'香蕉','45','45',NULL,'wer','1','技术 工号:3','2021-03-24 16:52:38','技术 工号:3','2021-03-24 16:52:38'),(5,3,0,'氯气','2541264','10',NULL,'','0','管理员 工号:1','2021-03-24 17:09:11','管理员 工号:1','2021-03-24 17:09:11');
/*!40000 ALTER TABLE `material_need` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `no_feasible_project`
--

DROP TABLE IF EXISTS `no_feasible_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `no_feasible_project` (
  `no_feasible_project_id` int NOT NULL AUTO_INCREMENT COMMENT '不可行项目ID',
  `project_id` int DEFAULT NULL COMMENT '项目ID',
  `evaluation_js` varchar(20) DEFAULT '未评估' COMMENT '评估状态,“已评估-不可行”、“未评估（默认）”（技术人员填写）',
  `reasons_js` varchar(500) DEFAULT NULL COMMENT '技术原因,可行或不可行的原因',
  `evaluation_name` varchar(20) DEFAULT NULL COMMENT '评估人',
  `evaluation_date` datetime DEFAULT NULL COMMENT '评估时间;用于T-1页面（二）完成时间：xx，耗时：x小时;B-2页面(二)计算耗时（已评估-不可行，未成交）',
  PRIMARY KEY (`no_feasible_project_id`),
  KEY `Accepted_project_project_overview_project_id_fk` (`project_id`),
  CONSTRAINT `Accepted_project_project_overview_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project_overview` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='不可行项目表（技术人员填写）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `no_feasible_project`
--

LOCK TABLES `no_feasible_project` WRITE;
/*!40000 ALTER TABLE `no_feasible_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `no_feasible_project` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS insert_pgzt_no */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `insert_pgzt_no` AFTER INSERT ON `no_feasible_project` FOR EACH ROW begin
    -- todo: implement
    if new.evaluation_js = '已评估-不可行' then
        update project_zt t
        set t.project_zt_js  ='已评估-不可行',
            t.evaluation_date=new.evaluation_date,
            t.evaluation_name=new.evaluation_name
        where t.project_id = new.project_id;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS update_pgzt_no */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `update_pgzt_no` AFTER UPDATE ON `no_feasible_project` FOR EACH ROW begin
    -- todo: implement
    if not exists(select * from project_zt t where t.project_id = new.project_id) then
        if new.evaluation_js = '已评估-不可行' then
            update project_zt t
            set t.project_zt_js  ='已评估-不可行',
                t.evaluation_date=new.evaluation_date,
                t.evaluation_name=new.evaluation_name
            where t.project_id = new.project_id;
        end if;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `project_overview`
--

DROP TABLE IF EXISTS `project_overview`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_overview` (
  `project_id` int NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `project_name` varchar(20) NOT NULL COMMENT '产品名称',
  `project_details` varchar(100) NOT NULL COMMENT '产品内容，产品结构式图片等',
  `project_sl` varchar(20) NOT NULL COMMENT '需求量',
  `cas` varchar(20) NOT NULL COMMENT 'Cas号',
  `kh_name` varchar(50) NOT NULL COMMENT '询单单位名称',
  `khry_name` varchar(20) NOT NULL COMMENT '询单人员姓名',
  `khry_type` varchar(20) NOT NULL COMMENT '询单人员身份',
  `khry_contact` varchar(20) NOT NULL COMMENT '询单人联系方式',
  `khry_Isreal` varchar(5) NOT NULL COMMENT '询单人身份是否真实',
  `kh_type` varchar(20) NOT NULL COMMENT '客户类型,“企业”或“高校”',
  `co_history` varchar(20) NOT NULL COMMENT '客户合作历史,“新客户”or“老客户”',
  `is_money` varchar(10) NOT NULL COMMENT '是否有钱,“有钱”、“一般”、“没钱”',
  `is_deal` varchar(5) DEFAULT NULL COMMENT '有成交可能',
  `is_accept_sw` varchar(5) NOT NULL COMMENT '有无成交机会,“有”“无”（商务人员填写）',
  `bz` varchar(500) DEFAULT NULL COMMENT '备注,客户对产品纯度，货期等方面的特殊要求',
  `model_name` varchar(20) DEFAULT NULL COMMENT '报价模式',
  `create_name` varchar(20) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '询单日期',
  `update_name` varchar(20) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='项目概况表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_overview`
--

LOCK TABLES `project_overview` WRITE;
/*!40000 ALTER TABLE `project_overview` DISABLE KEYS */;
INSERT INTO `project_overview` VALUES (2,'abc','2/w3x57aqq4.png','23.51','3525','一','张三','老师','1111','真实','高校','新客户','有钱','有','有','34234sdfad；asdfasfds',NULL,'管理员 工号:1','2021-03-24 15:10:29','管理员 工号:1','2021-03-24 15:10:29'),(3,'wer','3/wcagr2pe9.png','43.6','9786','企业一','李四','公司职员','2222','真实','企业','新客户','一般','有','有','asdfashdvpa',NULL,'管理员 工号:1','2021-03-24 15:14:09','管理员 工号:1','2021-03-24 15:14:09'),(4,'poi','4/i3t79i60q.png','64.78','45629','企业二','王五','公司职员','8888','真实','企业','老客户','有钱','有','有','jklkjhafds',NULL,'管理员 工号:1','2021-03-24 15:19:25','管理员 工号:1','2021-03-24 15:19:25'),(5,'adfsaf','5/s11nrm1er.png','34.45','3525','学校三','张三','老师','235211264','真实','高校','新客户','有钱','有','有','无',NULL,'商务 工号:2','2021-03-24 16:49:12','商务 工号:2','2021-03-24 16:49:12');
/*!40000 ALTER TABLE `project_overview` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS insert_accepted_project */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `insert_accepted_project` AFTER INSERT ON `project_overview` FOR EACH ROW begin
    -- todo: implement
    -- 有成交机会，则将项目id插入 项目状态表（project_zt）
    if new.is_accept_sw = '有' then
        insert into project_zt(project_id) values(new.project_id);
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS update_accepted_project */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `update_accepted_project` AFTER UPDATE ON `project_overview` FOR EACH ROW begin
    -- todo: implement
    -- 有成交机会，则将项目id插入 项目状态表（project_zt）
    if not exists(select t.project_id from project_zt t where t.project_id = new.project_id) then
        if new.is_accept_sw = '有' then
            insert into project_zt(project_id) values (new.project_id);
        end if;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `project_zt`
--

DROP TABLE IF EXISTS `project_zt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_zt` (
  `project_id` int NOT NULL COMMENT '项目ID',
  `project_zt_js` varchar(20) DEFAULT '未评估' COMMENT '项目状态,“已评估”、“未评估（默认）”（技术人员填写）\nT-1-1-2-No页面，点击【提交完成评估】，更新Project_ZT为‘已评估-不可行’\nT-1-1-2-Yes-2页面，点击【完成项目评估】，更新Project_ZT_js为‘已评估-可行’\n【这两个界面是二选一】',
  `evaluation_name` varchar(20) DEFAULT NULL COMMENT '评估人',
  `evaluation_date` datetime DEFAULT NULL COMMENT '评估时间',
  `is_task_js` varchar(20) DEFAULT '未下达' COMMENT '原料任务是否下达，默认未下达，T-1-1-1页面，点击【确认原料信息】Is_task变为“已下达”',
  `task_name` varchar(20) DEFAULT NULL COMMENT '下达人',
  `task_date` datetime DEFAULT NULL COMMENT '下达时间',
  `fk_zt_kf` varchar(20) DEFAULT '未反馈' COMMENT '反馈状态,已反馈，未反馈（默认）\nC-1-1页面，点击【提交原料信息】或【买不到原料反馈】，将反馈状态修改为“已反馈”；\n如果是“已反馈”状态，T-1-1-1页面 可以查到反馈信息',
  `reason` varchar(250) DEFAULT NULL COMMENT '原料不可行原因',
  `fk_name` varchar(20) DEFAULT NULL COMMENT '反馈人',
  `fk_date` datetime DEFAULT NULL COMMENT '反馈时间',
  `project_result_KF` varchar(20) DEFAULT '待定' COMMENT '项目成交结果,“待定”“成交”“未成交（默认）”(客服人员填写)\nC-3-1页面选择的成交状态结果来确定',
  `project_result_name` varchar(20) DEFAULT NULL COMMENT '项目成交录入人',
  `Project_result_date` datetime DEFAULT NULL COMMENT '项目成交时间,	B-2页面(二)计算耗时',
  `kh_feedback` varchar(200) DEFAULT NULL COMMENT '客户反馈	,下单客户对项目报价、时间等的反馈信息\nC-3-1页面反馈',
  `kh_feedback_name` varchar(20) DEFAULT NULL COMMENT '客户反馈录入人',
  `kh_feedback_date` datetime DEFAULT NULL COMMENT '客户反馈时间',
  `timed` int DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  CONSTRAINT `project_zt_project_overview_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project_overview` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_zt`
--

LOCK TABLES `project_zt` WRITE;
/*!40000 ALTER TABLE `project_zt` DISABLE KEYS */;
INSERT INTO `project_zt` VALUES (1,'未评估',NULL,NULL,'未下达',NULL,NULL,'未反馈',NULL,NULL,NULL,'待定',NULL,NULL,NULL,NULL,NULL,0),(2,'已评估-可行','2021-03-24 16:09:21','2021-03-24 16:09:21','已下达','管理员 工号:1','2021-03-24 15:51:57','已反馈-能买到',NULL,'管理员 工号:1','2021-03-24 15:56:06','成交','管理员 工号:1','2021-03-24 16:28:26','好','管理员 工号:1','2021-03-24 16:28:22',2),(3,'未评估',NULL,NULL,'未下达','管理员 工号:1','2021-03-24 17:09:11','已反馈-买不到','爆炸管制','管理员 工号:1','2021-03-24 17:10:17','待定',NULL,NULL,NULL,NULL,NULL,1),(4,'未评估',NULL,NULL,'未下达',NULL,NULL,'未反馈',NULL,NULL,NULL,'待定',NULL,NULL,NULL,NULL,NULL,1),(5,'已评估-可行','技术 工号:3','2021-03-24 16:59:06','已下达','技术 工号:3','2021-03-24 16:52:38','已反馈-能买到',NULL,'客服 工号:4','2021-03-24 16:54:40','成交','客服 工号:4','2021-03-24 17:02:04','ajdfklsfjaskgqwh','客服 工号:4','2021-03-24 17:01:57',0);
/*!40000 ALTER TABLE `project_zt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quoting_model`
--

DROP TABLE IF EXISTS `quoting_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quoting_model` (
  `model_id` int NOT NULL AUTO_INCREMENT COMMENT '报价模式ID',
  `model_name` varchar(5) DEFAULT NULL COMMENT '报价模式,五种模式“A,B,C,D,E”，E自定义',
  `h_price` double NOT NULL COMMENT '氢谱单价，用于测试成本计算',
  `c_price` double NOT NULL COMMENT '碳谱单价，用于测试成本计算',
  `m_price` double DEFAULT NULL COMMENT '质谱单价，用于测试成本计算',
  `rate` double NOT NULL DEFAULT '1' COMMENT '(测试/溶剂)比率，用于溶剂成本计算。大于等于0的数',
  `firm_wage` double DEFAULT NULL COMMENT '企业月工资，用于企业成本计算（只有模式A用）',
  `college_wage` double DEFAULT NULL COMMENT '高校月工资，用于高校成本计算',
  `firm_coefficient` double DEFAULT '0.7' COMMENT '企业报价系数，“大于0且小于1”的数（只有模式A用这个字段）',
  `college_coefficient` double DEFAULT '0.7' COMMENT '高校报价系数,用于计算报价。“大于0且小于1”的数',
  `product_coefficient` double DEFAULT '0.7' COMMENT '产品报价系数,用于计算外包价。“大于0且小于1”的数（模式A中不用这个字段）',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='报价模式表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quoting_model`
--

LOCK TABLES `quoting_model` WRITE;
/*!40000 ALTER TABLE `quoting_model` DISABLE KEYS */;
INSERT INTO `quoting_model` VALUES (1,'A',1,1,1,1,5000,5000,0.7,0.7,0.6,'2021-02-02 00:14:30','2021-02-02 00:14:10'),(2,'B',1,1,1,1,5000,5000,0.7,0.7,0.7,'2021-02-02 00:14:31',NULL),(3,'C',1,1,1,1,5000,5000,0.7,0.7,0.7,'2021-02-02 00:14:32',NULL),(4,'D',1,1,1,1,5000,5000,0.7,0.7,0.7,'2021-02-02 00:14:33',NULL),(5,'E',1,1,1,1,5000,5000,0.7,0.7,0.7,'2021-02-02 00:14:33',NULL);
/*!40000 ALTER TABLE `quoting_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receive_kh`
--

DROP TABLE IF EXISTS `receive_kh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receive_kh` (
  `receive_kh_id` int NOT NULL AUTO_INCREMENT COMMENT '接单客户沟通任务id',
  `project_id` int NOT NULL COMMENT '项目id',
  `receive_kh_name` varchar(20) DEFAULT NULL COMMENT '接单客户名称',
  `receive_kh_phone` varchar(20) DEFAULT NULL COMMENT '接单客户联系方式',
  `receive_intention` varchar(20) DEFAULT NULL COMMENT '接单意向',
  `is_final_receive` varchar(20) DEFAULT NULL COMMENT '是否为最终接单客户',
  `receive_price` double DEFAULT NULL COMMENT '接单价格',
  `needed_time` double DEFAULT NULL COMMENT '所需时间',
  `test_result` varchar(20) DEFAULT NULL COMMENT '实验情况(“成功” ”失败”)',
  `create_name` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_name` varchar(20) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`receive_kh_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='接单客户沟通任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receive_kh`
--

LOCK TABLES `receive_kh` WRITE;
/*!40000 ALTER TABLE `receive_kh` DISABLE KEYS */;
INSERT INTO `receive_kh` VALUES (1,2,'a','1111','再贵点','否',0,0,NULL,'客服 工号:4','2021-03-24 17:02:53',NULL,NULL),(2,2,'b','1111',NULL,'是',9000,35,'成功','客服 工号:4','2021-03-24 17:03:22','客服 工号:4','2021-03-24 17:03:31');
/*!40000 ALTER TABLE `receive_kh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `b2_ypg`
--

/*!50001 DROP VIEW IF EXISTS `b2_ypg`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `b2_ypg` AS select `t`.`project_id` AS `bh`,`t`.`create_date` AS `xdrq`,timestampdiff(HOUR,`t`.`create_date`,`a`.`evaluation_date`) AS `hs`,`a`.`project_zt_js` AS `pgzt`,`a`.`project_result_KF` AS `cjjg` from (`project_overview` `t` join `project_zt` `a`) where ((`t`.`project_id` = `a`.`project_id`) and (`t`.`project_id` = '1') and (`a`.`project_zt_js` in ('已评估-可行','已评估-不可行')) and (`t`.`create_date` > (now() - interval 3 day)) and (`t`.`create_date` < now())) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-02 21:16:45
