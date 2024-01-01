-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.22-MariaDB

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
-- Table structure for table `approval_cerbung`
--

DROP TABLE IF EXISTS `approval_cerbung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `approval_cerbung` (
  `cerbung_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `notifications_id` int(11) NOT NULL,
  `status_approval` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`cerbung_id`,`author_id`),
  KEY `fk_cerbung_author_author1_idx` (`author_id`),
  KEY `fk_cerbung_author_cerbung1_idx` (`cerbung_id`),
  KEY `fk_cerbung_status_notifications1_idx` (`notifications_id`),
  CONSTRAINT `fk_cerbung_author_author1` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cerbung_author_cerbung1` FOREIGN KEY (`cerbung_id`) REFERENCES `cerbungs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cerbung_status_notifications1` FOREIGN KEY (`notifications_id`) REFERENCES `notifications` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `approval_cerbung`
--

LOCK TABLES `approval_cerbung` WRITE;
/*!40000 ALTER TABLE `approval_cerbung` DISABLE KEYS */;
/*!40000 ALTER TABLE `approval_cerbung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cerbungs`
--

DROP TABLE IF EXISTS `cerbungs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cerbungs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `num_likes` int(11) DEFAULT NULL,
  `access` tinyint(4) DEFAULT NULL,
  `genre_id` int(11) NOT NULL,
  `num_paragraph` int(11) DEFAULT NULL,
  `url_gambar` varchar(45) DEFAULT NULL,
  `waktu_dibuat` date DEFAULT NULL,
  `users_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cerbungs_genre1_idx` (`genre_id`),
  KEY `fk_cerbungs_users1_idx` (`users_id`),
  CONSTRAINT `fk_cerbungs_genre1` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cerbungs_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cerbungs`
--

LOCK TABLES `cerbungs` WRITE;
/*!40000 ALTER TABLE `cerbungs` DISABLE KEYS */;
INSERT INTO `cerbungs` VALUES (1,'Halo','Halo',0,1,1,0,'https://picsum.photos/200','2023-01-01',1),(2,'Test','Test',0,2,2,0,'https://picsum.photos/200','2023-02-01',2);
/*!40000 ALTER TABLE `cerbungs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow_cerbung`
--

DROP TABLE IF EXISTS `follow_cerbung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follow_cerbung` (
  `cerbungs_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  `is_follow` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`cerbungs_id`,`users_id`),
  KEY `fk_cerbungs_users_users1_idx` (`users_id`),
  KEY `fk_cerbungs_users_cerbungs1_idx` (`cerbungs_id`),
  CONSTRAINT `fk_cerbungs_users_cerbungs1` FOREIGN KEY (`cerbungs_id`) REFERENCES `cerbungs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cerbungs_users_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow_cerbung`
--

LOCK TABLES `follow_cerbung` WRITE;
/*!40000 ALTER TABLE `follow_cerbung` DISABLE KEYS */;
INSERT INTO `follow_cerbung` VALUES (1,1,1),(2,2,1);
/*!40000 ALTER TABLE `follow_cerbung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `following`
--

DROP TABLE IF EXISTS `following`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `following` (
  `users_id_1` int(11) NOT NULL,
  `users_id_2` int(11) NOT NULL,
  `is_following` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`users_id_1`,`users_id_2`),
  KEY `fk_users_users_users2_idx` (`users_id_2`),
  KEY `fk_users_users_users1_idx` (`users_id_1`),
  CONSTRAINT `fk_users_users_users1` FOREIGN KEY (`users_id_1`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_users_users2` FOREIGN KEY (`users_id_2`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `following`
--

LOCK TABLES `following` WRITE;
/*!40000 ALTER TABLE `following` DISABLE KEYS */;
/*!40000 ALTER TABLE `following` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genre` (
  `id` int(11) NOT NULL,
  `nama` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1,'Aksi'),(2,'Misteri');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `waktu_notif` date DEFAULT NULL,
  `isi_notif` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paragraph_like`
--

DROP TABLE IF EXISTS `paragraph_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paragraph_like` (
  `users_id` int(11) NOT NULL,
  `paragraphs_id` int(11) NOT NULL,
  `is_like` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`users_id`,`paragraphs_id`),
  KEY `fk_users_has_paragraphs_paragraphs1_idx` (`paragraphs_id`),
  KEY `fk_users_has_paragraphs_users1_idx` (`users_id`),
  CONSTRAINT `fk_users_has_paragraphs_paragraphs1` FOREIGN KEY (`paragraphs_id`) REFERENCES `paragraphs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_paragraphs_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paragraph_like`
--

LOCK TABLES `paragraph_like` WRITE;
/*!40000 ALTER TABLE `paragraph_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `paragraph_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paragraphs`
--

DROP TABLE IF EXISTS `paragraphs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paragraphs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isi` varchar(100) DEFAULT NULL,
  `waktu_buat` date DEFAULT NULL,
  `cerbung_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_paragraf_cerbung_idx` (`cerbung_id`),
  KEY `fk_paragraf_author1_idx` (`users_id`),
  CONSTRAINT `fk_paragraf_author1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_paragraf_cerbung` FOREIGN KEY (`cerbung_id`) REFERENCES `cerbungs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paragraphs`
--

LOCK TABLES `paragraphs` WRITE;
/*!40000 ALTER TABLE `paragraphs` DISABLE KEYS */;
/*!40000 ALTER TABLE `paragraphs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `url_profile` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'bayu','bayu','https://picsum.photos/200'),(2,'vincen','vincent','https://picsum.photos/200'),(3,'feli','feli','https://picsum.photos/200'),(4,'andre','andre','https://picsum.photos/200');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-01 16:26:56
