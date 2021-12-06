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
-- Table structure for table `project_zt`
--

DROP TABLE IF EXISTS `project_zt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_zt` (
  `project_id` int NOT NULL COMMENT '项目ID',
  `project_zt_js` varchar(20) DEFAULT '未评估' COMMENT '项目状态，“已评估”、“未评估（默认）”（技术人员填写）\r\nT-1-1-2-No页面，点击【提交完成评估】，更新Project_ZT为‘已评估-不可行’\r\nT-1-1-2-Yes-2页面，点击【完成项目评估】，更新Project_ZT_js为‘已评估-可行’\r\n【这两个界面是二选一】',
  `evaluation_name` varchar(20) DEFAULT NULL COMMENT '评估人',
  `evaluation_date` datetime DEFAULT NULL COMMENT '评估时间',
  `is_task_js` varchar(20) DEFAULT '未下达' COMMENT '原料任务是否下达，默认未下达，T-1-1-1页面，点击【确认原料信息】Is_task变为“已下达”',
  `task_name` varchar(20) DEFAULT NULL COMMENT '下达人',
  `task_date` datetime DEFAULT NULL COMMENT '下达时间',
  `fk_zt_kf` varchar(20) DEFAULT '未反馈' COMMENT '反馈状态,已反馈，未反馈（默认）\r\nC-1-1页面，点击【提交原料信息】或【买不到原料反馈】，将反馈状态修改为“已反馈”；\r\n如果是“已反馈”状态，T-1-1-1页面 可以查到反馈信息',
  `fk_name` varchar(20) DEFAULT NULL COMMENT '反馈人',
  `fk_date` datetime DEFAULT NULL COMMENT '反馈时间',
  `project_result_KF` varchar(20) DEFAULT '未成交' COMMENT '项目成交结果,“待定”“成交”“未成交（默认）”(客服人员填写)\r\nC-3-1页面选择的成交状态结果来确定',
  `project_result_name` varchar(20) DEFAULT NULL COMMENT '项目成交录入人',
  `Project_result_date` datetime DEFAULT NULL COMMENT '项目成交时间,	B-2页面(二)计算耗时',
  `kh_feedback` varchar(200) DEFAULT NULL COMMENT '客户反馈	,下单客户对项目报价、时间等的反馈信息\r\nC-3-1页面反馈',
  `kh_feedback_name` varchar(20) DEFAULT NULL COMMENT '客户反馈录入人',
  `kh_feedback_date` datetime DEFAULT NULL COMMENT '客户反馈时间',
  `timed` int DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  CONSTRAINT `project_zt_project_overview_project_id_fk` FOREIGN KEY (`project_id`) REFERENCES `project_overview` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='项目状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_zt`
--

LOCK TABLES `project_zt` WRITE;
/*!40000 ALTER TABLE `project_zt` DISABLE KEYS */;
INSERT INTO `project_zt` VALUES (1,'已评估-可行',NULL,NULL,'未下达',NULL,NULL,'未反馈',NULL,NULL,'未成交',NULL,NULL,NULL,NULL,NULL,7),(2,'已评估-不可行',NULL,NULL,'未下达',NULL,NULL,'未反馈',NULL,NULL,'未成交',NULL,NULL,NULL,NULL,NULL,7),(3,'已评估-可行',NULL,NULL,'未下达',NULL,NULL,'未反馈',NULL,NULL,'未成交',NULL,NULL,NULL,NULL,NULL,56),(4,'未评估',NULL,NULL,'已下达','管理员 工号:1','2021-02-24 22:09:04','未反馈',NULL,NULL,'未成交',NULL,NULL,NULL,NULL,NULL,49),(5,'未评估',NULL,NULL,'未下达',NULL,NULL,'未反馈',NULL,NULL,'未成交',NULL,NULL,NULL,NULL,NULL,50),(6,'未评估',NULL,NULL,'已下达','管理员 工号:1','2021-02-24 22:11:06','未反馈',NULL,NULL,'未成交',NULL,NULL,NULL,NULL,NULL,50),(7,'未评估',NULL,NULL,'未下达',NULL,NULL,'未反馈',NULL,NULL,'未成交',NULL,NULL,NULL,NULL,NULL,50),(8,'未评估',NULL,NULL,'已下达','管理员 工号:1','2021-02-24 22:12:00','未反馈',NULL,NULL,'未成交',NULL,NULL,NULL,NULL,NULL,49),(9,'未评估',NULL,NULL,'未下达',NULL,NULL,'未反馈',NULL,NULL,'未成交',NULL,NULL,NULL,NULL,NULL,49),(11,'未评估',NULL,NULL,'未下达',NULL,NULL,'未反馈',NULL,NULL,'未成交',NULL,NULL,NULL,NULL,NULL,35);
/*!40000 ALTER TABLE `project_zt` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS insert_Material_info_LR */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `insert_Material_info_LR` AFTER UPDATE ON `project_zt` FOR EACH ROW begin
    -- todo: implement
    if new.is_task_js = '已下达' then
        insert into material_info_lr(project_id, material_need_id, material_id, material_name, cas)
            (select t.project_id, t.material_need_id, t.material_id, t.material_name, t.cas
             from material_need t
             where t.material_need_id not in
                   (select a.material_need_id from material_info_lr a where a.project_id = new.project_id));
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-25 14:33:42
