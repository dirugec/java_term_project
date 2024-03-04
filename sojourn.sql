CREATE DATABASE  IF NOT EXISTS `sojourn_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sojourn_db`;
-- MySQL dump 10.13  Distrib 8.0.25, for macos11 (x86_64)
--
-- Host: localhost    Database: sojurn_db
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `admin_users`
--

DROP TABLE IF EXISTS `admin_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_users` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` int NOT NULL,
  `role` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_users`
--

LOCK TABLES `admin_users` WRITE;
/*!40000 ALTER TABLE `admin_users` DISABLE KEYS */;
INSERT INTO `admin_users` VALUES (10,'Deneb','Villanueva','deneb@gmail.com',12345654,'Front Desk','asdasd',1),(12,'Dumar','Ruge','dumar@gmail.com',1239999,'Admin','asdasd',1);
/*!40000 ALTER TABLE `admin_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` int NOT NULL,
  `balance` decimal(12,2) NOT NULL,
  `parent_id` int DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  `user_type` int NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (9,'John','Doe','john-doe@gamil.com',123123337,2000.00,NULL,'WhoAmI',1,1),(11,'Angel','Perez','angel@gmail.com',12345654,2000.00,9,'asdasd',2,1),(13,'Jefferson','Ruge','jeffer@gmail.com',1239999,1250.00,9,'asdasd',2,1),(14,'Amanda','Londono','mandy@gmail.com',1230000,1000.00,9,'asdasd',2,1),(15,'Gilmer','Londono','totis@gmail.com',1230000,1000.00,9,'asdasd',2,1);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detail_trans`
--

DROP TABLE IF EXISTS `detail_trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detail_trans` (
  `det_trans_id` int NOT NULL,
  `trans_id` int NOT NULL,
  `date_trans` datetime NOT NULL,
  `product_id` int NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `quantity` decimal(12,2) NOT NULL,
  PRIMARY KEY (`det_trans_id`),
  KEY `FK_detail_trans_trans_id_idx` (`trans_id`),
  CONSTRAINT `FK_detail_trans_trans_id` FOREIGN KEY (`trans_id`) REFERENCES `transactions` (`trans_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detail_trans`
--

LOCK TABLES `detail_trans` WRITE;
/*!40000 ALTER TABLE `detail_trans` DISABLE KEYS */;
INSERT INTO `detail_trans` VALUES (1,2,'20240220',5,22.95,1),(2,2,'20240220',6,24.50,1),(3,3,'20240225',4,27.95,1),(4,3,'20240225',7,20.00,1),(5,4,'20240227',8,18.00,1),(6,4,'20240227',9,25.00,1),(7,5,'20240220',5,22.95,1),(8,5,'20240220',6,24.50,1),(9,6,'20240301',10,6.00,1),(10,6,'20240301',11,22.00,1);
/*!40000 ALTER TABLE `detail_trans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant`
--

DROP TABLE IF EXISTS `merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant` (
  `merchant_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`merchant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant`
--

LOCK TABLES `merchant` WRITE;
/*!40000 ALTER TABLE `merchant` DISABLE KEYS */;
INSERT INTO `merchant` VALUES (4,'ITALIAN RESTAURANT'),(5,'COLOMBIAN RESTAURANT'),(6,'DIVE STORE');
/*!40000 ALTER TABLE `merchant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant_users`
--

DROP TABLE IF EXISTS `merchant_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant_users` (
  `merchant_user_id` int NOT NULL AUTO_INCREMENT,
  `merchant_id` int NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` int NOT NULL,
  `role` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`merchant_user_id`),
  KEY `FK_merchant_users_merchant_id_idx` (`merchant_id`),
  CONSTRAINT `FK_merchant_users_merchant_id` FOREIGN KEY (`merchant_id`) REFERENCES `merchant` (`merchant_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_users`
--

LOCK TABLES `merchant_users` WRITE;
/*!40000 ALTER TABLE `merchant_users` DISABLE KEYS */;
INSERT INTO `merchant_users` VALUES (1,4,'Massimo','Bottura','mbottura@gmail.com',12345654,'Chef','asdasd',1),(2,5,'Leonor','Espinosa','lespinosa@gmail.com',1239999,'Chef','asdasd',1);
/*!40000 ALTER TABLE `merchant_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `merchant_id` int NOT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FK_merchant_id_idx` (`merchant_id`),
  CONSTRAINT `FK_merchant_product` FOREIGN KEY (`merchant_id`) REFERENCES `merchant` (`merchant_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (4,'Ajiaco',27.95,5),(5,'Pasta Carbonara',22.95,4),(6,'Pasta Bolognesa',24.50,4),(7,'Carne Asada',20.00,5),(8,'Lomo de Cerdo',18.00,5), (9,'Cazuela de Mariscos',25.00,5),(10,'Chorizo con Arepa',6.00,5),(11,'Casareccia',22.00,4),(12,'Salsiccia',18.50,4),(13,'Cavolo Nero',16.00,4);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `trans_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `date` varchar(8) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `merchant_id` int NOT NULL,
  PRIMARY KEY (`trans_id`),
  KEY `FK_customer_id_idx` (`customer_id`),
  KEY `FK_merchant_id_idx` (`merchant_id`),
  CONSTRAINT `FK_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (2,9,'20240220',47.45,4),(3,11,'20240225',47.95,5),(4,13,'20240227',43.00,5),(5,9,'20240301',47.45,4),(6,9,'20240229',28.00,5);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-29 17:42:49
