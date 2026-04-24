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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_USER') DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKk8h1bgqoplx0rkngj01pm1rgp` (`username`),
  UNIQUE KEY `UKe4w4av1wrhanry7t6mxt42nou` (`user_id`),
  CONSTRAINT `FKnjuop33mo69pd79ctplkck40n` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,_binary '','$2a$08$wzNPIkxKKutrWCQDrXBjKu6ZA1JqStyUoFv7MTaVzissaHpxCkwhi','ROLE_USER','sevinc_user',1),(2,_binary '','$2a$12$tp8lSlbuUqnE3Bgup6UC5u4NetDazWzcxZ.ZpHrZ68VZMb9epipMq','ROLE_ADMIN','ali_admin',2),(3,_binary '','$2a$12$m1yJ9Bh34kdXzDUDyWPs3Ov.7Ft6Vxhhe0xH5gEWCcSaockIIUXhS','ROLE_USER','leyla_user',3),(4,_binary '','$2a$12$itho5PI/Cm5fHLjrwZPVS.BcHj4b4wgdqhQZ4CwRoS8I/.bDV4kyi','ROLE_USER','ramin_user',4),(5,_binary '','$2a$12$5uAjZNEIpbiwrgL20X1d/ODGpklvHzKeXvc05HR5xG5kxUEL7mULS','ROLE_USER','narmin_user',5),(6,_binary '','$2a$10$sI9B8fql8AiAfB6t78NR8.lpRaQPl3aFnzpaYcmr1XNrVUZt9EsK.','ROLE_EMPLOYEE','revan21@gmail.com',6),(7,_binary '','$2a$10$clAnxIa0JHHB4RjIs/rtDenIjXKl57UNINGu9SQHZYyVi0e.D8I3y','ROLE_USER','sevinc@gmail.com',8),(8,_binary '','$2a$10$IjTz7rrakW/32Lu9RdtIluDXgrrjA.J8IqL.X67wYZDj6EgUu1ARW','ROLE_USER','sevin1@gmail.com',9),(9,_binary '','$2a$10$XfQchHhim0G2lYH.WZoXCOUJcFNYdy268Y6iijAYH.Y8D8vblrPnG','ROLE_USER','example@gmail.com',10);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
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
