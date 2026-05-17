-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: btl_java
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `accountId` int NOT NULL AUTO_INCREMENT,
  `handle` varchar(100) NOT NULL,
  `platformId` int DEFAULT NULL,
  `addedDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastCrawlDate` datetime DEFAULT NULL,
  `isActive` tinyint DEFAULT NULL,
  PRIMARY KEY (`accountId`),
  KEY `platformId` (`platformId`),
  CONSTRAINT `accounts_ibfk_1` FOREIGN KEY (`platformId`) REFERENCES `platforms` (`platformId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `codeanalysis`
--

DROP TABLE IF EXISTS `codeanalysis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codeanalysis` (
  `analysisId` int NOT NULL AUTO_INCREMENT,
  `submissionId` int DEFAULT NULL,
  `dataStructures` text,
  `algorithms` text,
  `aiProbability` decimal(5,2) DEFAULT NULL,
  `aiFeedback` text,
  PRIMARY KEY (`analysisId`),
  KEY `submissionId` (`submissionId`),
  CONSTRAINT `codeanalysis_ibfk_1` FOREIGN KEY (`submissionId`) REFERENCES `submissions` (`submissionId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `platforms`
--

DROP TABLE IF EXISTS `platforms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `platforms` (
  `platformId` int NOT NULL AUTO_INCREMENT,
  `platformName` varchar(50) NOT NULL,
  `websiteUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`platformId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `submissions`
--

DROP TABLE IF EXISTS `submissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `submissions` (
  `submissionId` int NOT NULL AUTO_INCREMENT,
  `accountId` int DEFAULT NULL,
  `remoteId` varchar(50) DEFAULT NULL,
  `problemTitle` varchar(255) DEFAULT NULL,
  `programmingLanguage` varchar(50) DEFAULT NULL,
  `sourceCode` longtext,
  `submissionTime` datetime DEFAULT NULL,
  PRIMARY KEY (`submissionId`),
  KEY `accountId` (`accountId`),
  CONSTRAINT `submissions_ibfk_1` FOREIGN KEY (`accountId`) REFERENCES `accounts` (`accountId`)
) ENGINE=InnoDB AUTO_INCREMENT=313 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-17 17:13:00
