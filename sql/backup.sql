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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='账号信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_information`
--

LOCK TABLES `account_information` WRITE;
/*!40000 ALTER TABLE `account_information` DISABLE KEYS */;
INSERT INTO `account_information` VALUES (1,'ADMIN001','123456','管理员',1,'2021-01-26 00:00:00',NULL),(2,'SW002','000000','商务',2,'2021-01-26 00:00:00',NULL),(3,'JS003','000000','技术',3,'2021-01-26 00:00:00',NULL),(4,'KF004','000000','客服',4,'2021-01-26 00:00:00',NULL),(7,'','111111','小明',3,'2021-01-28 22:22:44',NULL),(8,'','111111','小红',4,'2021-01-28 22:22:44',NULL);
/*!40000 ALTER TABLE `account_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feasible_project`
--

DROP TABLE IF EXISTS `feasible_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feasible_project` (
  `feasible_project_id` int NOT NULL AUTO_INCREMENT COMMENT '可行项目ID',
  `project_id` int DEFAULT NULL COMMENT '项目ID,项目概况表中的Project_ID',
  `h_sl` double DEFAULT NULL COMMENT '氢谱数量',
  `c_sl` double DEFAULT NULL COMMENT '碳谱数量',
  `m_sl` double DEFAULT NULL COMMENT '质谱数量',
  `dtlx_sj_cs` double DEFAULT NULL COMMENT '打通路线时间,大于或等于零的数字，可输入小数',
  `jll_sj_cs` double DEFAULT NULL COMMENT '积累量时间,大于或等于零的数字，可输入小数',
  `zsj_cs` double DEFAULT NULL COMMENT '测试总时间(打通路线时间+积累量时间)',
  `is_difficult_js` varchar(10) DEFAULT NULL COMMENT '技术难度,“难”、“一般”、“简单”',
  `papers_js` varchar(500) DEFAULT NULL COMMENT '上传文献,技术判断查阅的相关文献',
  `time_needed` varchar(20) DEFAULT NULL COMMENT '所需总时间，用于客服人员拟合同（比完成项目时间适当多几天）',
  `bz` varchar(500) DEFAULT NULL COMMENT '备注,在此输入完成项目的特殊要求，例如：需要客户提供特定的原料信息，项目的技术难点',
  `evaluation_js` varchar(20) DEFAULT '未评估' COMMENT '“已评估-可行”、“未评估（默认）”（技术人员填写）',
  `create_name` varchar(20) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `evaluation_name` varchar(20) DEFAULT NULL COMMENT '评估人',
  `evaluation_date` datetime DEFAULT NULL COMMENT '评估时间,用于T-1页面（二）完成时间：xx，耗时：x小时,\r\nB-2页面（二）计算耗时（已评估-可行，未成交）',
  PRIMARY KEY (`feasible_project_id`),
  KEY `project_test_project_overview_project_id_fk` (`project_id`),
  CONSTRAINT `project_test_project_overview_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project_overview` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='可行项目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feasible_project`
--

LOCK TABLES `feasible_project` WRITE;
/*!40000 ALTER TABLE `feasible_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `feasible_project` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报价表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `final_price`
--

LOCK TABLES `final_price` WRITE;
/*!40000 ALTER TABLE `final_price` DISABLE KEYS */;
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
  `kh_modle_id` int NOT NULL AUTO_INCREMENT COMMENT '客户报价模式ID',
  `kh_type` varchar(20) DEFAULT NULL COMMENT '客户类型,“企业”或“高校”',
  `is_money` varchar(10) DEFAULT NULL COMMENT '是否有钱,“有钱”、“一般”、“没钱”',
  `is_difficult` varchar(10) DEFAULT NULL COMMENT '项目难度,“难”、“一般”、“简单”',
  `modle_name` varchar(5) DEFAULT NULL COMMENT '报价模式，五种模式“A,B,C,D,E”，E自定义',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`kh_modle_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='客户报价模式判断表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kh_model_judgment`
--

LOCK TABLES `kh_model_judgment` WRITE;
/*!40000 ALTER TABLE `kh_model_judgment` DISABLE KEYS */;
INSERT INTO `kh_model_judgment` VALUES (1,'企业',NULL,NULL,'A',NULL,NULL),(2,'高校',NULL,'难','B',NULL,NULL),(3,'高校',NULL,'一般','C',NULL,NULL),(4,'高校','有钱','简单','C',NULL,NULL),(5,'高校','一般','简单','D',NULL,NULL);
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
  `quantities` double DEFAULT NULL COMMENT '原料库存(g)',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='原料基本表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_info`
--

LOCK TABLES `material_info` WRITE;
/*!40000 ALTER TABLE `material_info` DISABLE KEYS */;
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
  `cg_sl` double DEFAULT NULL COMMENT '采购量(g)(客服人员提供)',
  `price` double DEFAULT NULL COMMENT '原料价格(g)(客服人员提供)',
  `provider` varchar(100) DEFAULT NULL COMMENT '供应商(客服人员提供)',
  `provider_phone` varchar(50) DEFAULT NULL COMMENT '供应商联系方式(客服人员提供)',
  `is_buy` varchar(10) DEFAULT NULL COMMENT '是否可以买到(“能买到”“买不到”（客服人员提供）（作为向技术人员的反馈）)',
  `not_reason` varchar(500) DEFAULT NULL COMMENT '买不到原因(客服人员提供（作为向技术人员的反馈）)',
  `fk_zt` varchar(20) DEFAULT '未反馈' COMMENT '反馈状态,已反馈，未反馈（默认）\nC-1-1页面，点击【提交原料信息】或【买不到原料反馈】，将反馈状态修改为“已反馈”；\n如果是“已反馈”状态，T-1-1-1页面 可以查到反馈信息',
  `create_name` varchar(20) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '录入时间',
  `update_name` varchar(20) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`material_lrid`),
  KEY `material_info_lr_material_need_material_need_id_fk` (`material_need_id`),
  CONSTRAINT `material_info_lr_material_need_material_need_id_fk` FOREIGN KEY (`material_need_id`) REFERENCES `material_need` (`material_need_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='原料信息录入表（客服录入）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_info_lr`
--

LOCK TABLES `material_info_lr` WRITE;
/*!40000 ALTER TABLE `material_info_lr` DISABLE KEYS */;
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
  `material_sl` double DEFAULT NULL COMMENT '需要原料数量',
  `picture` varchar(100) DEFAULT NULL COMMENT '原料图片（记录路径）',
  `bz` varchar(500) DEFAULT NULL COMMENT '备注',
  `is_task` varchar(10) DEFAULT '未下达' COMMENT '任务是否下达，“已下达”“未下达”',
  `create_name` varchar(20) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_name` varchar(20) DEFAULT NULL COMMENT 'varchar(20)',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`material_need_id`),
  UNIQUE KEY `project_feasible_Project_ID_uindex` (`project_id`),
  CONSTRAINT `project_feasible_project_overview_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project_overview` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='原料需求表（技术录入）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_need`
--

LOCK TABLES `material_need` WRITE;
/*!40000 ALTER TABLE `material_need` DISABLE KEYS */;
INSERT INTO `material_need` VALUES (3,1,NULL,NULL,NULL,NULL,NULL,NULL,'未下达',NULL,'2021-01-23 22:49:52',NULL,NULL),(6,2,NULL,NULL,NULL,NULL,NULL,NULL,'未下达',NULL,NULL,NULL,NULL),(10,3,NULL,NULL,NULL,NULL,NULL,NULL,'未下达',NULL,NULL,NULL,NULL),(14,6,NULL,NULL,NULL,NULL,NULL,NULL,'未下达',NULL,NULL,NULL,NULL),(15,5,NULL,NULL,NULL,NULL,NULL,NULL,'未下达',NULL,NULL,NULL,NULL),(16,7,NULL,NULL,NULL,NULL,NULL,NULL,'未下达',NULL,NULL,NULL,NULL),(17,4,NULL,NULL,NULL,NULL,NULL,NULL,'未下达',NULL,NULL,NULL,NULL),(18,8,NULL,NULL,NULL,NULL,NULL,NULL,'未下达',NULL,NULL,NULL,NULL);
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
  `experiment` varchar(10) DEFAULT '进行中' COMMENT '实验情况,“进行中”“成功”“不成功”',
  `evaluation_js` varchar(20) DEFAULT '未评估' COMMENT '评估状态,“已评估-不可行”、“未评估（默认）”（技术人员填写）',
  `reasons_js` varchar(500) DEFAULT NULL COMMENT '技术原因,可行或不可行的原因',
  `create_name` varchar(20) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
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
  `project_sl` int NOT NULL COMMENT '需求量',
  `cas` varchar(20) NOT NULL COMMENT 'Cas号',
  `kh_name` varchar(50) NOT NULL COMMENT '询单单位名称',
  `khry_name` varchar(20) NOT NULL COMMENT '询单人员姓名',
  `khry_type` varchar(20) NOT NULL COMMENT '询单人员身份',
  `khry_contact` varchar(20) NOT NULL COMMENT '询单人联系方式',
  `khry_Isreal` varchar(5) NOT NULL COMMENT '询单人身份是否真实',
  `kh_type` varchar(20) NOT NULL COMMENT '客户类型,“企业”或“高校”',
  `co_history` varchar(20) NOT NULL COMMENT '客户合作历史,“新客户”or“老客户”',
  `is_money` varchar(10) NOT NULL COMMENT '是否有钱,“有钱”、“一般”、“没钱”',
  `is_accect_sw` varchar(5) NOT NULL COMMENT '有无成交机会,“有”“无”（商务人员填写）',
  `bz` varchar(500) DEFAULT NULL COMMENT '备注,客户对产品纯度，货期等方面的特殊要求',
  `project_zt_js` varchar(20) DEFAULT '未评估' COMMENT '项目状态,“已评估”、“未评估（默认）”（技术人员填写）',
  `project_result_kf` varchar(20) DEFAULT '未成交' COMMENT '项目成交结果,“待定”“成交”“未成交（默认）”(客服人员填写)',
  `project_result_date` datetime DEFAULT NULL COMMENT '项目成交时间,B-2页面(二)计算耗时',
  `kh_feedback` varchar(200) DEFAULT NULL COMMENT '下单客户对项目报价、时间等的反馈信息',
  `modle_name` varchar(20) DEFAULT NULL COMMENT '报价模式',
  `create_name` varchar(20) DEFAULT NULL COMMENT '录入人',
  `create_date` datetime DEFAULT NULL COMMENT '询单日期',
  `update_name` varchar(20) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='项目概况表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_overview`
--

LOCK TABLES `project_overview` WRITE;
/*!40000 ALTER TABLE `project_overview` DISABLE KEYS */;
INSERT INTO `project_overview` VALUES (1,'123','123',12,'22','22','22','2','22','22','22','2','是','有',NULL,'未评估','未成交',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'1','321',10,'1','学校一','张三','老师','1111','真实','高校','老客户','一般','有',NULL,'未评估','未成交',NULL,NULL,NULL,NULL,'2021-01-29 19:30:43',NULL,'2021-01-29 19:30:43'),(3,'a','234',5,'23','学校二','李四','学生','2222','真实','高校','老客户','有钱','有',NULL,'未评估','未成交',NULL,NULL,NULL,NULL,'2021-01-29 20:42:47',NULL,'2021-01-29 20:42:49'),(4,'a','234',5,'23','学校二','李四','学生','2222','真实','高校','老客户','有钱','有',NULL,'未评估','未成交',NULL,NULL,NULL,NULL,'2021-01-28 20:44:44',NULL,NULL),(5,'a','234',5,'23','学校二','李四','学生','2222','真实','高校','老客户','有钱','有',NULL,'未评估','未成交',NULL,NULL,NULL,NULL,'2021-01-27 20:44:48',NULL,NULL),(6,'a','234',5,'23','学校二','李四','学生','2222','真实','高校','老客户','有钱','有',NULL,'未评估','未成交',NULL,NULL,NULL,NULL,'2021-01-26 20:44:51',NULL,NULL),(7,'a','234',5,'23','学校二','李四','学生','2222','真实','高校','老客户','有钱','有',NULL,'未评估','未成交',NULL,NULL,NULL,NULL,'2021-01-25 20:44:54',NULL,NULL),(8,'a','234',5,'23','学校二','李四','学生','2222','真实','高校','老客户','有钱','有',NULL,'未评估','未成交',NULL,NULL,NULL,NULL,'2021-01-19 20:44:57',NULL,NULL);
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `insert_accepted_project` AFTER UPDATE ON `project_overview` FOR EACH ROW begin
    -- todo: implement
    if new.is_accect_sw = '有' then
        insert into material_need(project_id) values (new.project_id);
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `quoting_model`
--

DROP TABLE IF EXISTS `quoting_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quoting_model` (
  `modle_id` int NOT NULL AUTO_INCREMENT COMMENT '报价模式ID',
  `modle_name` varchar(5) DEFAULT NULL COMMENT '报价模式,五种模式“A,B,C,D,E”，E自定义',
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
  PRIMARY KEY (`modle_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='报价模式表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quoting_model`
--

LOCK TABLES `quoting_model` WRITE;
/*!40000 ALTER TABLE `quoting_model` DISABLE KEYS */;
INSERT INTO `quoting_model` VALUES (1,'A',1,1,NULL,1,NULL,NULL,0.7,0.7,0.7,NULL,NULL),(2,'B',1,1,NULL,1,NULL,NULL,0.7,0.7,0.7,NULL,NULL),(3,'C',1,1,NULL,1,NULL,NULL,0.7,0.7,0.7,NULL,NULL),(4,'D',1,1,NULL,1,NULL,NULL,0.7,0.7,0.7,NULL,NULL),(5,'E',1,1,NULL,1,NULL,NULL,0.7,0.7,0.7,NULL,NULL);
/*!40000 ALTER TABLE `quoting_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recieve_kh`
--

DROP TABLE IF EXISTS `recieve_kh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recieve_kh` (
  `recieve_kh_id` int NOT NULL AUTO_INCREMENT COMMENT '接单客户沟通任务id',
  `project_id` int NOT NULL COMMENT '项目id',
  `recieve_kh_name` varchar(20) DEFAULT NULL COMMENT '接单客户名称',
  `recieve_kh_phone` varchar(20) DEFAULT NULL COMMENT '接单客户联系方式',
  `recieve_intention` varchar(20) DEFAULT NULL COMMENT '接单意向',
  `is_final_receive` varchar(20) DEFAULT NULL COMMENT '是否为最终接单客户',
  `receive_price` double DEFAULT NULL COMMENT '接单价格',
  `needed_time` double DEFAULT NULL COMMENT '所需时间',
  `test_result` varchar(20) DEFAULT NULL COMMENT '实验情况(“成功” ”失败”)',
  `create_name` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_name` varchar(20) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`recieve_kh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接单客户沟通任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recieve_kh`
--

LOCK TABLES `recieve_kh` WRITE;
/*!40000 ALTER TABLE `recieve_kh` DISABLE KEYS */;
/*!40000 ALTER TABLE `recieve_kh` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-30 14:33:22
