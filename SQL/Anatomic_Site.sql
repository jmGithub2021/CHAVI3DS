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
-- Dumping data for table `diseasesite`
--

LOCK TABLES `diseasesite` WRITE;
/*!40000 ALTER TABLE `diseasesite` DISABLE KEYS */;
INSERT INTO `diseasesite` VALUES (1,'Central Nervous System','CNS',NULL,'2019-11-23 10:02:07'),(2,'Eye and Orbit','Eye',NULL,'2019-11-23 10:02:07'),(3,'Ear and Auditory System','Ear',NULL,'2019-11-23 10:02:07'),(4,'Head and Neck including Salivary Glands','Head-Neck',NULL,'2019-11-23 10:02:07'),(5,'Thorax','Thorax',NULL,'2019-11-23 10:02:07'),(6,'Lung','Lung',NULL,'2019-11-23 10:02:07'),(7,'Breast','Breast',NULL,'2019-11-23 10:02:07'),(8,'Upper Gastrointestinal Tract','Upper-GI',NULL,'2019-11-23 10:02:07'),(9,'Lower Gastrointestinal Tract','Lower-GI',NULL,'2019-11-23 10:02:07'),(10,'Liver and Bilary system','Hepatobiliary',NULL,'2019-11-23 10:02:07'),(11,'Urinary System','Urinary',NULL,'2019-11-23 10:02:07'),(12,'Male Genital System','Male-genital',NULL,'2019-11-23 10:02:07'),(13,'Female Genital System','Gynecological',NULL,'2019-11-23 10:02:07'),(14,'Skin and Appendeges','Cutaneous',NULL,'2019-11-23 10:02:07'),(15,'Pancreas','Pancreas',NULL,'2019-11-23 10:02:07'),(16,'Other Exocrine Glands','Exocrine',NULL,'2019-11-23 10:02:07'),(17,'Musculoskeletal System including Soft Tissues','Bone-soft-tissue',NULL,'2019-11-23 10:02:07'),(18,'Blood and Lymphatic System','Hematological',NULL,'2019-11-23 10:02:07'),(19,'Endocrine System','Endocrine',NULL,'2019-11-23 10:02:07'),(20,'Peripheral Nervous System','PNS',NULL,'2019-11-23 10:02:07'),(21,'Unknown or Unspecified','Unknown',NULL,'2019-11-23 10:02:07');
/*!40000 ALTER TABLE `diseasesite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `patientid_map`
--

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

-- Dump completed on 2021-02-19 19:57:10
