-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: localhost    Database: quizgrub
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.24.04.1

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
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question_text` text NOT NULL,
  `option_a` text NOT NULL,
  `option_b` text NOT NULL,
  `option_c` text NOT NULL,
  `option_d` text NOT NULL,
  `correct_option` varchar(1) NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  `difficulty` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'Which planet is known as the Red Planet?','Earth','Venus','Mars','Jupiter','C','Science','Easy'),(2,'Who wrote \'Romeo and Juliet\'?','Charles Dickens','William Shakespeare','Mark Twain','Jane Austen','B','Literature','Medium'),(3,'What is the chemical symbol for water?','O2','H2O','CO2','H2SO4','B','Science','Easy'),(4,'Which country hosted the 2016 Summer Olympics?','Brazil','China','UK','Greece','A','General Knowledge','Medium'),(5,'What is 15 + 26?','41','40','42','43','A','Math','Easy'),(6,'Which gas do plants absorb for photosynthesis?','Oxygen','Nitrogen','Carbon Dioxide','Hydrogen','C','Science','Easy'),(7,'What is the square root of 144?','10','11','12','13','C','Math','Medium'),(8,'\"What is the output of \'System.out.println(3 + 4 + \"\"Quiz\"\");\'\"','7Quiz','34Quiz','Quiz34','Quiz7','A','Java','Easy'),(9,'Which company created Java?','Sun Microsystems','Google','Microsoft','Apple','A','Java','Easy'),(10,'Which keyword is used to inherit a class in Java?','implement','extends','inherits','super','B','Java','Easy'),(11,'What is the size of int in Java?','4 bytes','2 bytes','8 bytes','Depends on system','A','Java','Medium'),(12,'Which collection class allows duplicate elements in Java?','Set','Map','List','HashMap','C','Java','Easy'),(13,'What does JVM stand for?','Java Variable Machine','Java Virtual Machine','Java Verified Machine','Java Vendor Machine','B','Java','Easy'),(14,'Which operator is used for object comparison in Java?','==','.equals()','!=','===','B','Java','Medium'),(15,'What is the default value of a boolean variable in Java?','true','false','null','0','B','Java','Easy'),(16,'Which method is the entry point of any Java program?','start()','run()','main()','init()','C','Java','Easy'),(17,'Which keyword is used to stop a loop in Java?','exit','stop','break','return','C','Java','Easy');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-19 19:07:03
