-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: chaviro
-- ------------------------------------------------------
-- Server version	5.7.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `csvjsonattributemap`
--

DROP TABLE IF EXISTS `csvjsonattributemap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `csvjsonattributemap` (
  `projectid` varchar(50) NOT NULL,
  `tablename` varchar(50) DEFAULT NULL,
  `csvattribute` varchar(100) NOT NULL,
  `chaviattribute` varchar(100) DEFAULT NULL,
  `instancetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`projectid`,`csvattribute`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `diseasesite`
--

DROP TABLE IF EXISTS `diseasesite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `diseasesite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `shortname` varchar(50) DEFAULT NULL,
  `snomed_id` varchar(45) DEFAULT NULL,
  `instnacetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `diseasesubsite`
--

DROP TABLE IF EXISTS `diseasesubsite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `diseasesubsite` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `siteid` int(10) DEFAULT NULL,
  `instancetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `siteid` (`siteid`),
  CONSTRAINT `diseasesubsite_ibfk_1` FOREIGN KEY (`siteid`) REFERENCES `diseasesite` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frameofreferenceuid_map`
--

DROP TABLE IF EXISTS `frameofreferenceuid_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `frameofreferenceuid_map` (
  `DCMFrameOfReferenceUID` varchar(200) NOT NULL,
  `SysFrameOfReferenceUID` varchar(300) DEFAULT NULL,
  `SysStudyUID` varchar(300) DEFAULT NULL,
  `instanceCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`DCMFrameOfReferenceUID`),
  UNIQUE KEY `SysFrameOfReferenceUID` (`SysFrameOfReferenceUID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `login` (
  `uid` varchar(200) NOT NULL,
  `pass` varchar(300) DEFAULT NULL,
  `name` varchar(130) DEFAULT NULL,
  `emailid` varchar(200) NOT NULL,
  `contact` varchar(15) NOT NULL,
  `utype` char(1) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `entryDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `emailid` (`emailid`),
  UNIQUE KEY `contact` (`contact`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `moleculescatalog`
--

DROP TABLE IF EXISTS `moleculescatalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `moleculescatalog` (
  `symbol` varchar(30) NOT NULL,
  `moleculetype` varchar(50) NOT NULL,
  `moleculename` varchar(200) DEFAULT NULL,
  `tyumortype` varchar(200) DEFAULT NULL,
  `instancetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`symbol`,`moleculetype`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `otherattributevalues`
--

DROP TABLE IF EXISTS `otherattributevalues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `otherattributevalues` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `projectid` varchar(50) DEFAULT NULL,
  `patientid` varchar(50) DEFAULT NULL,
  `attributename` varchar(50) DEFAULT NULL,
  `attributevalue` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `projectid` (`projectid`),
  CONSTRAINT `otherattributevalues_ibfk_1` FOREIGN KEY (`projectid`) REFERENCES `project` (`projectid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `otheruid_map`
--

DROP TABLE IF EXISTS `otheruid_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `otheruid_map` (
  `dcmUID` varchar(200) NOT NULL,
  `SysUID` varchar(200) DEFAULT NULL,
  `instanceCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`dcmUID`),
  UNIQUE KEY `SysUID` (`SysUID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patientid_map`
--

DROP TABLE IF EXISTS `patientid_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `patientid_map` (
  `DCMPatientID` varchar(200) NOT NULL,
  `SysPatientID` varchar(300) NOT NULL,
  `instanceCreated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`DCMPatientID`),
  UNIQUE KEY `SysPatientID` (`SysPatientID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `project` (
  `projectname` varchar(200) DEFAULT NULL,
  `projectid` varchar(100) NOT NULL,
  `approvalno` varchar(200) NOT NULL,
  `approvaldate` date DEFAULT NULL,
  `centerlist` varchar(300) DEFAULT NULL,
  `piname` varchar(100) DEFAULT NULL,
  `countrieslist` varchar(200) DEFAULT NULL,
  `scientifictitle` varchar(200) DEFAULT NULL,
  `projectabstract` varchar(500) DEFAULT NULL,
  `iscontainanimalimage` tinyint(1) DEFAULT NULL,
  `instancetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`projectid`),
  UNIQUE KEY `approvalno` (`approvalno`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `projectmap`
--

DROP TABLE IF EXISTS `projectmap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `projectmap` (
  `projectid` varchar(100) NOT NULL,
  `patientid` varchar(100) NOT NULL,
  PRIMARY KEY (`projectid`,`patientid`),
  CONSTRAINT `projectmap_ibfk_1` FOREIGN KEY (`projectid`) REFERENCES `project` (`projectid`),
  CONSTRAINT `projectmap_ibfk_2` FOREIGN KEY (`projectid`) REFERENCES `project` (`projectid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `projecttemplate`
--

DROP TABLE IF EXISTS `projecttemplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `projecttemplate` (
  `input_attribute` varchar(100) DEFAULT NULL,
  `project_id` varchar(10) NOT NULL,
  `chavi_attribute` varchar(100) DEFAULT NULL,
  `chavi_table` varchar(50) NOT NULL,
  `instancetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `projecttemplateotherattribute`
--

DROP TABLE IF EXISTS `projecttemplateotherattribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `projecttemplateotherattribute` (
  `attributename` varchar(50) NOT NULL,
  `chaviattribute` varchar(100) DEFAULT NULL,
  `associatedtable` varchar(50) NOT NULL,
  `projectid` varchar(50) NOT NULL,
  `instancetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`attributename`,`associatedtable`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `seriesuid_map`
--

DROP TABLE IF EXISTS `seriesuid_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `seriesuid_map` (
  `DCMSeriesUID` varchar(200) NOT NULL,
  `SysStudyUID` varchar(300) NOT NULL,
  `serialNo` int(3) NOT NULL,
  `instanceCreated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`DCMSeriesUID`),
  UNIQUE KEY `SysStudyUID` (`SysStudyUID`,`serialNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sopuid_map`
--

DROP TABLE IF EXISTS `sopuid_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sopuid_map` (
  `DCMSOPInsID` varchar(200) NOT NULL,
  `SysSeriesUID` varchar(300) NOT NULL,
  `serialNo` int(3) NOT NULL,
  `instanceCreated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`DCMSOPInsID`),
  UNIQUE KEY `SysSeriesUID` (`SysSeriesUID`,`serialNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `studydate_ref`
--

DROP TABLE IF EXISTS `studydate_ref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `studydate_ref` (
  `SysPatientID` varchar(300) NOT NULL,
  `studyDate` varchar(300) DEFAULT NULL,
  `difference` int(5) DEFAULT NULL,
  `instanceCreated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`SysPatientID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `studyuid_map`
--

DROP TABLE IF EXISTS `studyuid_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `studyuid_map` (
  `DCMStudyUID` varchar(200) NOT NULL,
  `SysPatientID` varchar(300) NOT NULL,
  `serialNo` int(3) NOT NULL,
  `instanceCreated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`DCMStudyUID`),
  UNIQUE KEY `SysPatientID` (`SysPatientID`,`serialNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'chaviro'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-18 17:06:22
