-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 13, 2024 at 01:00 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sojourn_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_users`
--

CREATE TABLE `admin_users` (
  `admin_id` int(11) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` int(11) NOT NULL,
  `role` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin_users`
--

INSERT INTO `admin_users` (`admin_id`, `first_name`, `last_name`, `email`, `phone`, `role`, `password`, `active`) VALUES
(10, 'Deneb', 'Villanueva', 'deneb@gmail.com', 12345654, 'Front Desk', 'asdasd', 1),
(12, 'Dumar', 'Ruge', 'dumar@gmail.com', 1239999, 'Admin', '1234', 1);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` int(11) NOT NULL,
  `balance` decimal(12,2) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  `user_type` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `first_name`, `last_name`, `email`, `phone`, `balance`, `parent_id`, `password`, `user_type`, `active`) VALUES
(9, 'Bruce', 'Wayne', 'john-doe@gamil.com', 123123337, 242172.13, NULL, 'abcd', 1, 1),
(11, 'Angel', 'Perez', 'angel@gmail.com', 12345654, 2000.00, 9, 'asdasd', 2, 1),
(13, 'Jefferson', 'Ruge', 'jeffer@gmail.com', 1239999, 1250.00, 9, 'asdasd', 2, 1),
(14, 'Amanda', 'Londono', 'mandy@gmail.com', 1230000, 1000.00, 9, 'asdasd', 2, 0),
(15, 'Gilmer', 'Londono', 'totis@gmail.com', 1230000, 1000.00, NULL, 'asdasd', 2, 1),
(16, 'felipe', 'williams', 'felipe@gmail.com', 1231231231, 0.00, NULL, '1234', 1, 1),
(17, 'angelica', 'manila', 'amanila@gmail.com', 123456789, 0.00, 0, '1234567', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `detail_trans`
--

CREATE TABLE `detail_trans` (
  `det_trans_id` int(11) NOT NULL,
  `trans_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `quantity` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `detail_trans`
--

INSERT INTO `detail_trans` (`det_trans_id`, `trans_id`, `product_id`, `price`, `quantity`) VALUES
(1, 2, 5, 22.95, 1.00),
(2, 2, 6, 24.50, 1.00),
(3, 3, 4, 27.95, 1.00),
(4, 3, 7, 20.00, 1.00),
(5, 4, 8, 18.00, 1.00),
(6, 4, 9, 25.00, 1.00),
(7, 5, 5, 22.95, 1.00),
(8, 5, 6, 24.50, 1.00),
(9, 6, 10, 6.00, 1.00),
(10, 6, 11, 22.00, 1.00),
(11, 7, 6, 24.50, 1.00),
(12, 7, 12, 18.50, 1.00),
(13, 8, 5, 22.95, 3.00);

-- --------------------------------------------------------

--
-- Table structure for table `merchant`
--

CREATE TABLE `merchant` (
  `merchant_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `merchant`
--

INSERT INTO `merchant` (`merchant_id`, `name`) VALUES
(4, 'ITALIAN RESTAURANT'),
(5, 'COLOMBIAN RESTAURANT'),
(6, 'DIVE STORE');

-- --------------------------------------------------------

--
-- Table structure for table `merchant_users`
--

CREATE TABLE `merchant_users` (
  `merchant_user_id` int(11) NOT NULL,
  `merchant_id` int(11) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` int(11) NOT NULL,
  `role` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `merchant_users`
--

INSERT INTO `merchant_users` (`merchant_user_id`, `merchant_id`, `first_name`, `last_name`, `email`, `phone`, `role`, `password`, `active`) VALUES
(1, 4, 'Massimo', 'Bottura', 'mbottura@gmail.com', 12345654, 'Chef', 'qweqwe', 1),
(2, 5, 'Leonor', 'Espinosa', 'lespinosa@gmail.com', 1239999, 'Chef', 'asdasd', 1);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `merchant_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `name`, `price`, `merchant_id`) VALUES
(4, 'Ajiaco', 27.95, 5),
(5, 'Pasta Carbonara', 22.95, 4),
(6, 'Pasta Bolognesa', 24.50, 4),
(7, 'Carne Asada', 20.00, 5),
(8, 'Lomo de Cerdo', 18.00, 5),
(9, 'Cazuela de Mariscos', 25.00, 5),
(10, 'Chorizo con Arepa', 6.00, 5),
(11, 'Casareccia', 22.00, 4),
(12, 'Salsiccia', 18.50, 4),
(13, 'Cavolo Nero', 16.00, 4);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `trans_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `date` varchar(8) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `merchant_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`trans_id`, `customer_id`, `date`, `amount`, `merchant_id`) VALUES
(2, 9, '20240220', 47.45, 4),
(3, 11, '20240225', 47.95, 5),
(4, 13, '20240227', 43.00, 5),
(5, 9, '20240301', 47.45, 4),
(6, 9, '20240229', 28.00, 5),
(7, 9, '20240412', 43.00, 4),
(8, 9, '20240412', 68.85, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_users`
--
ALTER TABLE `admin_users`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `detail_trans`
--
ALTER TABLE `detail_trans`
  ADD PRIMARY KEY (`det_trans_id`),
  ADD KEY `FK_detail_trans_trans_id_idx` (`trans_id`);

--
-- Indexes for table `merchant`
--
ALTER TABLE `merchant`
  ADD PRIMARY KEY (`merchant_id`);

--
-- Indexes for table `merchant_users`
--
ALTER TABLE `merchant_users`
  ADD PRIMARY KEY (`merchant_user_id`),
  ADD KEY `FK_merchant_users_merchant_id_idx` (`merchant_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `FK_merchant_id_idx` (`merchant_id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`trans_id`),
  ADD KEY `FK_customer_id_idx` (`customer_id`),
  ADD KEY `FK_merchant_id_idx` (`merchant_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_users`
--
ALTER TABLE `admin_users`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `detail_trans`
--
ALTER TABLE `detail_trans`
  MODIFY `det_trans_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `merchant`
--
ALTER TABLE `merchant`
  MODIFY `merchant_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `merchant_users`
--
ALTER TABLE `merchant_users`
  MODIFY `merchant_user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `trans_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_trans`
--
ALTER TABLE `detail_trans`
  ADD CONSTRAINT `FK_detail_trans_trans_id` FOREIGN KEY (`trans_id`) REFERENCES `transactions` (`trans_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `merchant_users`
--
ALTER TABLE `merchant_users`
  ADD CONSTRAINT `FK_merchant_users_merchant_id` FOREIGN KEY (`merchant_id`) REFERENCES `merchant` (`merchant_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FK_merchant_product` FOREIGN KEY (`merchant_id`) REFERENCES `merchant` (`merchant_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `FK_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
