-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 30, 2024 at 10:57 AM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `marcodata`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE IF NOT EXISTS `book` (
  `bookTitle` varchar(100) NOT NULL,
  `author` varchar(100) NOT NULL,
  `bookType` varchar(100) NOT NULL,
  `image` varchar(500) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`bookTitle`, `author`, `bookType`, `image`, `date`) VALUES
('Programming Language', 'Thumbnail_01', 'Non-fiction', 'C:\\Users\\abans\\eclipse-workspace\\LibraryManagementSystem\\src\\image\\programming language book.jpg', '2018-10-16'),
('JavaFX Tutorial', 'Thumbnail_02', 'Non-fiction', 'C:\\Users\\abans\\eclipse-workspace\\LibraryManagementSystem\\src\\image\\javafx tutorial book.jpg', '2020-04-24'),
('Java Tutorial', 'Thumbnail_04', 'Non-fiction', 'C:\\Users\\abans\\eclipse-workspace\\LibraryManagementSystem\\src\\image\\java tutorial.jpg', '2019-12-17'),
('Python Tutorial', 'Thumbnail_05', 'Non-fiction', 'C:\\Users\\abans\\eclipse-workspace\\LibraryManagementSystem\\src\\image\\python tutorial.jpg', '2017-08-06'),
('C# Tutorial', 'Thumbnail_03', 'Non-fiction', 'C:\\Users\\abans\\eclipse-workspace\\LibraryManagementSystem\\src\\image\\c#  tutorial book', '2022-05-18');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
