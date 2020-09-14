-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: conference
-- ------------------------------------------------------
-- Server version	5.7.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `conference`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `conference` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `conference`;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `memberId` varchar(32) NOT NULL,
  `totalAmount` decimal(18,0) DEFAULT NULL,
  `canWithdrawAmount` decimal(18,0) DEFAULT NULL,
  `freezedAmount` decimal(18,0) DEFAULT NULL,
  `withdrawTotalAmount` decimal(18,0) DEFAULT NULL,
  `changedTime` datetime DEFAULT NULL,
  PRIMARY KEY (`memberId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clock_in`
--

DROP TABLE IF EXISTS `clock_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clock_in` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberId` varchar(32) NOT NULL,
  `attend` int(11) DEFAULT '1' COMMENT '连续打卡次数',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clock_in`
--

LOCK TABLES `clock_in` WRITE;
/*!40000 ALTER TABLE `clock_in` DISABLE KEYS */;
/*!40000 ALTER TABLE `clock_in` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conference`
--

DROP TABLE IF EXISTS `conference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conference` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial_number` varchar(32) DEFAULT NULL COMMENT '会议编号',
  `banner` varchar(512) DEFAULT NULL COMMENT '主题banner',
  `topic` varchar(512) DEFAULT NULL COMMENT '主题',
  `brief` varchar(1024) DEFAULT NULL COMMENT '会议概要',
  `introduction` text COMMENT '会议介绍',
  `location` varchar(512) DEFAULT NULL COMMENT '举办场地',
  `site_link` text COMMENT '场地图片链接',
  `site_brief` text COMMENT '场地描述',
  `hoster` varchar(128) DEFAULT NULL COMMENT '主讲人',
  `hoster_link` varchar(128) DEFAULT NULL COMMENT '主讲人图片链接',
  `hoster_brief` text COMMENT '主讲人介绍',
  `guest` varchar(128) DEFAULT NULL COMMENT '嘉宾',
  `guest_link` varchar(128) DEFAULT NULL COMMENT '嘉宾图片链接',
  `guest_brief` text COMMENT '嘉宾介绍',
  `fee` float(8,2) DEFAULT NULL COMMENT '入场费用',
  `start_time` datetime DEFAULT NULL COMMENT '举办开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '举办结束时间',
  `released` bit(1) DEFAULT b'1' COMMENT '是否发布',
  `seat_num` int(11) DEFAULT NULL COMMENT '座位数',
  `remark` text COMMENT '备注',
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference`
--

LOCK TABLES `conference` WRITE;
/*!40000 ALTER TABLE `conference` DISABLE KEYS */;
INSERT INTO `conference` VALUES (5,'20200718XXXXMIBGAWG1','/site/images/topic.jpg','时尚汇','时尚汇精英，全球服饰产业链全聚于此','一场饭局，畅聊七小时，深交十多位，时尚与服饰大咖','上海松江富悦大酒店','','','Jim','/site/images/head.jpeg','Thnkout全球设计师','一龙','/site/images/head.jpeg','行业top1',99.99,'2020-10-01 10:00:01','2020-10-01 12:00:01','',34,'first open','2020-07-22 16:57:33'),(6,'20200718XXXXMIBGAWG1','/site/images/topic.jpg','时尚汇','时尚汇精英，全球服饰产业链全聚于此','一场饭局，畅聊七小时，深交十多位，时尚与服饰大咖','上海松江富悦大酒店','','','Jim','/site/images/head.jpeg','Thnkout全球设计师','一龙','/site/images/head.jpeg','行业top1',99.99,'2020-10-01 10:00:01','2020-10-01 12:00:01','',50,'first open','2020-07-23 14:31:47'),(7,'20200718XXXXMIBGAWG1','/site/images/topic.jpg','时尚汇','时尚汇精英，全球服饰产业链全聚于此','一场饭局，畅聊七小时，深交十多位，时尚与服饰大咖','上海松江富悦大酒店','','','Jim','/site/images/head.jpeg','Thnkout全球设计师','一龙','/site/images/head.jpeg','行业top1',99.99,'2020-10-01 10:00:01','2020-10-01 12:00:01','',50,'first open','2020-07-23 14:31:59'),(8,'20200718XXXXMIBGAWG1','/site/images/topic.jpg','时尚汇','时尚汇精英，全球服饰产业链全聚于此','一场饭局，畅聊七小时，深交十多位，时尚与服饰大咖','上海松江富悦大酒店','','','Jim','/site/images/head.jpeg','Thnkout全球设计师','一龙','/site/images/head.jpeg','行业top1',99.99,'2020-10-01 10:00:01','2020-10-01 12:00:01','',50,'first open','2020-07-23 14:31:59'),(10,'20200718XXXXMIBGAWG1','/site/images/topic.jpg','时尚汇','时尚汇精英，全球服饰产业链全聚于此','一场饭局，畅聊七小时，深交十多位，时尚与服饰大咖','上海松江富悦大酒店','','','Jim','/site/images/head.jpeg','Thnkout全球设计师','一龙','/site/images/head.jpeg','行业top1',99.99,'2020-10-01 10:00:01','2020-10-01 12:00:01','',50,'first open','2020-07-23 14:32:06'),(11,'20200718XXXXMIBGAWG1','/site/images/topic.jpg','时尚汇','时尚汇精英，全球服饰产业链全聚于此','一场饭局，畅聊七小时，深交十多位，时尚与服饰大咖','上海松江富悦大酒店','','','Jim','/site/images/head.jpeg','Thnkout全球设计师','一龙','/site/images/head.jpeg','行业top1',99.99,'2020-10-01 10:00:01','2020-10-01 12:00:01','',50,'first open','2020-07-23 14:32:06'),(12,'20200718XXXXMIBGAWG1','/site/images/topic.jpg','时尚汇','时尚汇精英，全球服饰产业链全聚于此','一场饭局，畅聊七小时，深交十多位，时尚与服饰大咖','上海松江富悦大酒店','','','Jim','/site/images/head.jpeg','Thnkout全球设计师','一龙','/site/images/head.jpeg','行业top1',99.99,'2020-10-01 10:00:01','2020-10-01 12:00:01','',50,'first open','2020-07-23 14:32:06'),(13,'20200718XXXXMIBGAWG1','/site/images/topic.jpg','时尚汇','时尚汇精英，全球服饰产业链全聚于此','一场饭局，畅聊七小时，深交十多位，时尚与服饰大咖','上海松江富悦大酒店','','','Jim','/site/images/head.jpeg','Thnkout全球设计师','一龙','/site/images/head.jpeg','行业top1',99.99,'2020-10-01 10:00:01','2020-10-01 12:00:01','',50,'first open','2020-07-23 14:32:06'),(14,'X08286235','/upload/regex.png','Demo','测试会议','测试','上海',NULL,NULL,'Michael','/upload/img001.jpg','土压顶地','Jack','/upload/img001.jpg','赵佳琪要厅',100.00,'2020-07-27 11:33:00','2020-07-28 11:00:00','',80,'测试','2020-07-27 11:34:24');
/*!40000 ALTER TABLE `conference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `media` (
  `id` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `mediaType` varchar(20) NOT NULL,
  `isTemporary` bit(1) DEFAULT NULL,
  `url` varchar(600) DEFAULT NULL,
  `videoTitle` varchar(42) DEFAULT NULL,
  `videoIntroduction` varchar(240) DEFAULT NULL,
  `changedTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `id` varchar(32) NOT NULL,
  `refId` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `sharedQRcodeUrl` varchar(512) DEFAULT NULL,
  `subscribe` bit(1) DEFAULT NULL,
  `nickName` varchar(256) DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `lang` varchar(10) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `headImgurl` varchar(256) DEFAULT NULL,
  `subscribeTime` datetime DEFAULT NULL,
  `unionId` varchar(32) DEFAULT NULL,
  `groupId` varchar(32) DEFAULT NULL,
  `tagidlist` varchar(20) DEFAULT NULL,
  `subscribeScene` varchar(50) DEFAULT NULL,
  `qrScene` char(20) DEFAULT NULL,
  `qrSceneStr` varchar(256) DEFAULT NULL,
  `latitude` varchar(20) DEFAULT NULL,
  `longitude` varchar(20) DEFAULT NULL,
  `precision` varchar(20) DEFAULT NULL,
  `onBlackList` bit(1) DEFAULT NULL,
  `active` bit(1) DEFAULT b'1',
  `remark` varchar(256) DEFAULT NULL,
  `changedTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES ('M0001',NULL,'Michael Lee','13524199871','Michael.wu@qq.com','/site/images/offical_qrcode.jpg','\0','Michael','','ZH','Hubei','Hubei','China','/site/images/head.jpeg','2020-07-22 03:52:06',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\0','','test michael','2020-07-28 15:38:55','2020-07-22 15:52:05'),('M0002','M0001','Jack Li',NULL,'Jack@computop.com','/site/images/offical_qrcode.jpg','\0','Jack','',NULL,NULL,NULL,'China','/site/images/head.jpeg','2020-07-22 15:52:06',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\0','','test','2020-07-28 13:39:07','2020-07-22 15:52:05'),('M0003','M0001','Alice',NULL,'Alice@computop.com','/site/images/offical_qrcode.jpg','\0','Alice','',NULL,NULL,NULL,'China','/site/images/head.jpeg','2020-07-22 15:52:06',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\0','','test','2020-07-28 13:39:07','2020-07-22 15:52:05'),('M0004','M0003','Jason',NULL,'Jason@computop.com','/site/images/offical_qrcode.jpg','\0','Jason','',NULL,NULL,NULL,'China','/site/images/head.jpeg','2020-07-22 15:52:06',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\0','','test','2020-07-28 13:39:07','2020-07-22 15:52:05');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mediaId` varchar(64) NOT NULL,
  `title` varchar(200) NOT NULL,
  `author` varchar(32) DEFAULT NULL,
  `digest` varchar(128) DEFAULT NULL,
  `thumbMediaId` varchar(64) NOT NULL,
  `showCoverPic` bit(1) DEFAULT b'0',
  `url` varchar(256) DEFAULT NULL,
  `content` text,
  `contentSourceUrl` varchar(600) DEFAULT NULL,
  `needOpenComment` bit(1) DEFAULT b'0',
  `onlyFansCanComment` bit(1) DEFAULT b'1',
  `changedTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participant`
--

DROP TABLE IF EXISTS `participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participant` (
  `id` varchar(32) NOT NULL COMMENT 'uuid主键',
  `conference_id` int(11) NOT NULL COMMENT '会议id',
  `memberId` varchar(32) NOT NULL COMMENT '会员id',
  `paid` float(8,2) DEFAULT NULL,
  `ticket_codeNum` varchar(32) DEFAULT NULL,
  `completed` bit(1) DEFAULT b'0' COMMENT '是否完成支付',
  `ipAddr` varchar(16) DEFAULT NULL COMMENT '客户端IP',
  `remark` text,
  `changedTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `data` text COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participant`
--

LOCK TABLES `participant` WRITE;
/*!40000 ALTER TABLE `participant` DISABLE KEYS */;
INSERT INTO `participant` VALUES ('3ac775a6377f4f40b095880b8e4b4524',5,'M0001',99.99,NULL,'',NULL,NULL,'2020-07-29 11:30:16','2020-07-29 11:30:16',NULL),('6b69085a562245d28c4d591c5c144daa',5,'M0003',99.99,NULL,'',NULL,NULL,'2020-07-29 11:30:16','2020-07-29 11:30:16',NULL),('eb92c17bc4024a07887f980abded6b60',5,'M0002',99.99,NULL,'',NULL,NULL,'2020-07-29 11:30:16','2020-07-29 11:30:16',NULL);
/*!40000 ALTER TABLE `participant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photo`
--

DROP TABLE IF EXISTS `photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) NOT NULL,
  `url` varchar(600) NOT NULL,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photo`
--

LOCK TABLES `photo` WRITE;
/*!40000 ALTER TABLE `photo` DISABLE KEYS */;
/*!40000 ALTER TABLE `photo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `template`
--

DROP TABLE IF EXISTS `template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `template` (
  `id` varchar(64) NOT NULL,
  `title` varchar(128) DEFAULT NULL,
  `primaryIndustry` varchar(64) DEFAULT NULL,
  `deputyIndustry` varchar(64) DEFAULT NULL,
  `content` text,
  `example` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `template`
--

LOCK TABLES `template` WRITE;
/*!40000 ALTER TABLE `template` DISABLE KEYS */;
/*!40000 ALTER TABLE `template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `codeNum` varchar(32) NOT NULL COMMENT '入场券编号',
  `conference_Id` int(11) DEFAULT NULL COMMENT '会议id',
  `conference_topic` varchar(512) DEFAULT NULL COMMENT '会议主题',
  `ticket_value` float(8,2) DEFAULT NULL COMMENT '面值',
  `active` bit(1) DEFAULT b'1' COMMENT '是否有效（已被人使用参加）',
  `createdTime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`codeNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `withdraw`
--

DROP TABLE IF EXISTS `withdraw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `withdraw` (
  `id` int(11) NOT NULL,
  `memberId` varchar(32) NOT NULL,
  `amount` decimal(18,0) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `createdTime` datetime DEFAULT NULL,
  `changedTime` datetime DEFAULT NULL,
  `remark` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `withdraw`
--

LOCK TABLES `withdraw` WRITE;
/*!40000 ALTER TABLE `withdraw` DISABLE KEYS */;
/*!40000 ALTER TABLE `withdraw` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-29 18:07:51
