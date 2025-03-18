CREATE DATABASE  IF NOT EXISTS `swp391_online_shop_be` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `swp391_online_shop_be`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: swp391_online_shop_be
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `avatar` text,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `role` enum('USER','ADMIN') DEFAULT 'USER',
  `status` enum('ACTIVE','Inactive','BANNED','TEST') DEFAULT 'ACTIVE',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'johndoe','john@example.com','password123','John','Doe',NULL,'0123456789','123 Main St, City A','USER','ACTIVE','2025-02-25 03:21:23','2025-02-28 10:40:57'),(2,'jane_smith','jane@example.com','password123','Jane','Smith',NULL,'0902345678','456 Oak Ave, City B','USER','ACTIVE','2025-02-25 03:21:23','2025-02-27 09:09:50'),(3,'admin01','admin01@example.com','adminpassword','Admin','One',NULL,'0903456789','789 Pine Rd, City C','USER','ACTIVE','2025-02-25 03:21:23','2025-02-27 09:09:50'),(4,'alice_wonder','alice@example.com','alice123','Alice','Wonder',NULL,'0904567890','321 Birch Blvd, City D','USER','ACTIVE','2025-02-25 03:21:23','2025-02-27 09:09:50'),(5,'bob_builder','bob@example.com','bobbuilder','Bob','Builder',NULL,'0905678901','654 Cedar St, City E','USER','ACTIVE','2025-02-25 03:21:23','2025-02-27 09:09:50'),(6,'charlie_brown','charlie@example.com','charliebrown','Charlie','Brown',NULL,'0906789012','987 Maple Ave, City F','USER','ACTIVE','2025-02-25 03:21:23','2025-02-27 09:09:50'),(7,'david_king','david@example.com','davidking','David','King',NULL,'0907890123','159 Elm St, City G','USER','ACTIVE','2025-02-25 03:21:23','2025-02-27 09:09:50'),(8,'emily_rose','emily@example.com','emilyrose','Emily','Rose',NULL,'0908901234','753 Willow Rd, City H','USER','ACTIVE','2025-02-25 03:21:23','2025-02-27 09:09:50'),(9,'frank_castle','frank@example.com','frankcastle','Frank','Castle',NULL,'0909012345','852 Spruce Blvd, City I','USER','ACTIVE','2025-02-25 03:21:23','2025-02-27 09:09:50'),(10,'grace_hopper','grace@example.com','gracehopper','Grace','Hopper',NULL,'0900123456','951 Redwood Ave, City J','USER','ACTIVE','2025-02-25 03:21:23','2025-02-27 09:09:50'),(12,'john_doe2','john2@example.com','password123','John','Doe',NULL,'0901234567','123 Main St, City A','USER','ACTIVE','2025-02-28 01:48:09','2025-02-28 01:48:09'),(13,'john_doe5','john4@example.com','password123','John','Doe',NULL,'0123456789','123 Main St, City A','USER','ACTIVE','2025-02-28 03:32:27','2025-02-28 03:32:27'),(14,'john_doe6','john6@example.com','password123','John','Doe',NULL,'0123456789','123 Main St, City A','USER','ACTIVE','2025-02-28 03:34:22','2025-02-28 03:34:22'),(15,'johndoe7','john7@example.com','password123','John','Doe',NULL,'0123456789','123 Main St, City A','USER','ACTIVE','2025-02-28 06:53:57','2025-02-28 06:53:57'),(16,'johndoe8','john8@example.com','password123','John','Doe',NULL,'0123456789','123 Main St, City A','USER','ACTIVE','2025-02-28 07:04:33','2025-02-28 07:04:33'),(18,'johndoe10','john10@example.com','$2a$10$R0bYOjNMWQpIwEZG8qw1LeR335r/y71gw23GrMFLBnHqivooNm0tG','John','Doe',NULL,'0123456789','123 Main St, City A','USER','ACTIVE','2025-03-02 16:08:38','2025-03-02 16:08:38');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blogs`
--

DROP TABLE IF EXISTS `blogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blogs` (
  `blog_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `content` text,
  `author_id` int DEFAULT NULL,
  `status` enum('published','hidden','draft') NOT NULL DEFAULT 'published',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`blog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blogs`
--

LOCK TABLES `blogs` WRITE;
/*!40000 ALTER TABLE `blogs` DISABLE KEYS */;
INSERT INTO `blogs` VALUES (1,'Hướng dẫn chăm sóc hoa hồng','Hoa hồng là loài hoa phổ biến...',1,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(2,'Cách trồng hoa lan nở quanh năm','Hoa lan cần điều kiện đặc biệt...',2,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(3,'Làm sao để cây xương rồng không bị úng','Xương rồng dễ bị úng nước nếu...',3,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(4,'Kinh nghiệm chọn đất trồng rau sạch','Đất là yếu tố quan trọng...',4,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(5,'Công thức pha đất trồng hoa ly','Hoa ly cần loại đất giàu dinh dưỡng...',5,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(6,'Tổng hợp các giống hoa tulip đẹp','Tulip có nhiều màu sắc đa dạng...',1,'hidden','2025-03-02 05:39:15','2025-03-02 05:39:15'),(7,'Hướng dẫn trồng hoa cúc đúng cách','Hoa cúc dễ trồng nhưng...',2,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(8,'Cách tưới nước cho sen đá','Sen đá không cần quá nhiều nước...',3,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(9,'Những loại hoa dễ trồng tại nhà','Một số loài hoa như...',4,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(10,'Bí quyết giữ hoa tươi lâu sau khi cắt','Muốn hoa tươi lâu, cần...',5,'hidden','2025-03-02 05:39:15','2025-03-02 05:39:15'),(11,'Lợi ích của cây xanh trong nhà','Cây xanh giúp lọc không khí...',1,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(12,'Cách chăm sóc cây kim tiền','Cây kim tiền là loại cây phong thủy...',2,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(13,'Top 10 loài cây giúp thanh lọc không khí','Một số cây như lưỡi hổ...',3,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(14,'Hướng dẫn chọn chậu cây phù hợp','Việc chọn chậu cây phù hợp giúp...',4,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(15,'Cách làm phân bón hữu cơ tại nhà','Phân bón hữu cơ giúp cây phát triển...',5,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(16,'Hướng dẫn làm vườn trên sân thượng','Vườn trên sân thượng đang là xu hướng...',1,'hidden','2025-03-02 05:39:15','2025-03-02 05:39:15'),(17,'Những lỗi thường gặp khi trồng cây cảnh','Nhiều người mắc sai lầm khi...',2,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(18,'Cách chọn giống hoa phù hợp theo mùa','Mỗi mùa có loại hoa phù hợp riêng...',3,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(19,'Hướng dẫn bảo dưỡng cây cảnh nội thất','Cây nội thất cần chăm sóc đúng cách...',4,'published','2025-03-02 05:39:15','2025-03-02 05:39:15'),(20,'Mẹo trồng cây không cần quá nhiều đất','Một số loại cây có thể trồng trong môi trường ít đất...',5,'hidden','2025-03-02 05:39:15','2025-03-02 05:39:15');
/*!40000 ALTER TABLE `blogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Hoa Hồng','Hoa hồng biểu tượng của tình yêu và sự lãng mạn.',0,'2025-03-02 03:20:59','2025-03-02 03:32:00'),(2,'Hoa Cúc','Hoa cúc mang ý nghĩa trường thọ và sự bền vững.',1,'2025-03-02 03:20:59','2025-03-02 03:20:59'),(3,'Hoa Lan','Hoa lan thể hiện sự sang trọng, quý phái.',1,'2025-03-02 03:20:59','2025-03-02 03:20:59'),(4,'Hoa Ly','Hoa ly có hương thơm quyến rũ, biểu tượng cho sự tinh khiết.',1,'2025-03-02 03:20:59','2025-03-02 03:20:59'),(5,'Hoa Sen','Hoa sen đại diện cho sự thanh khiết và cao quý.',1,'2025-03-02 03:20:59','2025-03-02 03:20:59'),(6,'Hoa Đào','Hoa đào mang ý nghĩa may mắn, thịnh vượng.',1,'2025-03-02 03:20:59','2025-03-02 03:20:59'),(7,'Hoa Mai','Hoa mai vàng tượng trưng cho sự giàu sang, phú quý.',1,'2025-03-02 03:20:59','2025-03-02 03:20:59'),(8,'Hoa Tulip','Hoa tulip tượng trưng cho sự hoàn hảo và tình yêu.',1,'2025-03-02 03:20:59','2025-03-02 03:20:59'),(9,'Hoa Hướng Dương','Hoa hướng dương luôn hướng về mặt trời, thể hiện sự trung thành.',1,'2025-03-02 03:20:59','2025-03-02 03:20:59'),(10,'Hoa Bỉ Ngạn','Hoa bỉ ngạn mang ý nghĩa của sự chia ly và ký ức.',1,'2025-03-02 03:20:59','2025-03-02 03:20:59'),(12,'test edit 01','vcxvxc',1,'2025-03-02 05:15:28','2025-03-02 05:17:51');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `name` varchar(150) NOT NULL,
  `description` text,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `stock` int NOT NULL DEFAULT '0',
  `image` varchar(255) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sliders`
--

DROP TABLE IF EXISTS `sliders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sliders` (
  `slider_id` int NOT NULL AUTO_INCREMENT,
  `image_url` text,
  `link` text,
  `caption` text,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`slider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sliders`
--

LOCK TABLES `sliders` WRITE;
/*!40000 ALTER TABLE `sliders` DISABLE KEYS */;
INSERT INTO `sliders` VALUES (1,'https://images.unsplash.com/photo-1485827404703-89b55fcc595e?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','https://example.com/page1','Khuyến mãi mùa hè',1,'2025-03-02 06:21:03','2025-03-02 08:13:49'),(2,'https://example.com/images/slider2.jpg','https://example.com/page2','Giảm giá 50% cho thành viên mới',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(3,'https://example.com/images/slider3.jpg','https://example.com/page3','Sản phẩm mới ra mắt',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(4,'https://example.com/images/slider4.jpg','https://example.com/page4','Mua một tặng một',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(5,'https://example.com/images/slider5.jpg','https://example.com/page5','Săn deal sốc mỗi ngày',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(6,'https://example.com/images/slider6.jpg','https://example.com/page6','Ưu đãi độc quyền',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(7,'https://example.com/images/slider7.jpg','https://example.com/page7','Bộ sưu tập xuân hè 2024',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(8,'https://example.com/images/slider8.jpg','https://example.com/page8','Đăng ký nhận ưu đãi ngay',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(9,'https://example.com/images/slider9.jpg','https://example.com/page9','Chương trình khách hàng thân thiết',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(10,'https://example.com/images/slider10.jpg','https://example.com/page10','Ưu đãi chỉ có hôm nay',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(11,'https://example.com/images/slider11.jpg','https://example.com/page11','Flash sale cuối tuần',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(12,'https://example.com/images/slider12.jpg','https://example.com/page12','Cơ hội trúng thưởng iPhone',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(13,'https://example.com/images/slider13.jpg','https://example.com/page13','Mua sắm hoàn tiền lên tới 10%',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(14,'https://example.com/images/slider14.jpg','https://example.com/page14','Nhận quà ngay khi đăng ký tài khoản',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(15,'https://example.com/images/slider15.jpg','https://example.com/page15','Giảm giá cực sốc cho sản phẩm hot',1,'2025-03-02 06:21:03','2025-03-02 06:21:03'),(16,'https://images.unsplash.com/photo-1485827404703-89b55fcc595e?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D','','cxzczx',1,'2025-03-02 08:08:15','2025-03-02 08:08:15');
/*!40000 ALTER TABLE `sliders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-03  8:13:02
