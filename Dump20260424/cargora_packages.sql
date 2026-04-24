-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: cargora
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
-- Table structure for table `packages`
--

DROP TABLE IF EXISTS `packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `internal_tracking_code` varchar(255) NOT NULL,
  `shipping_fee` decimal(10,2) DEFAULT NULL,
  `tracking_number` varchar(255) NOT NULL,
  `weight` decimal(10,2) DEFAULT NULL,
  `destination_branch_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKirx6ql4yklwvwhf25038wrbje` (`internal_tracking_code`),
  UNIQUE KEY `tracking_number_UNIQUE` (`tracking_number`),
  KEY `FKtdo1i3m5pb22tq2vyvj3botbw` (`destination_branch_id`),
  KEY `FKcj3syi9lcukrlsh6o4llk0t8f` (`user_id`),
  CONSTRAINT `FKcj3syi9lcukrlsh6o4llk0t8f` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKtdo1i3m5pb22tq2vyvj3botbw` FOREIGN KEY (`destination_branch_id`) REFERENCES `pickup_points` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packages`
--

LOCK TABLES `packages` WRITE;
/*!40000 ALTER TABLE `packages` DISABLE KEYS */;
INSERT INTO `packages` VALUES (21,'2026-04-22 15:01:34.000000','INT-021',11.00,'TRK5001',1.30,1,5),(22,'2026-04-22 15:01:34.000000','INT-022',21.00,'TRK5002',2.90,2,5),(23,'2026-04-22 15:01:34.000000','INT-023',29.00,'TRK5003',3.70,1,5),(24,'2026-04-22 15:01:34.000000','INT-024',38.00,'TRK5004',4.80,2,5),(25,'2026-04-22 15:01:34.000000','INT-025',60.00,'TRK5005',7.00,1,5),(26,'2026-04-23 17:09:29.982047','EXP-5cb1726f',0.00,'TRK123456789',2.50,2,1),(32,'2026-04-23 19:08:54.230227','EXP-baefeac0',0.00,'TRK123456889',2.50,2,4),(33,'2026-04-23 19:24:30.802745','EXP-36ffb5c4',0.00,'TRK144456889',2.50,2,4),(34,'2026-04-23 20:01:43.429793','EXP-4ef05c92',0.00,'TRK166666889',2.50,2,4);
/*!40000 ALTER TABLE `packages` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-24 15:40:14
