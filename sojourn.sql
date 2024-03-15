
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
) ;



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
) ;



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
) ;



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
) ;





CREATE TABLE `merchant` (
  `merchant_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`merchant_id`)
) ;



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
) ;





CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `merchant_id` int NOT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FK_merchant_id_idx` (`merchant_id`),
  CONSTRAINT `FK_merchant_product` FOREIGN KEY (`merchant_id`) REFERENCES `merchant` (`merchant_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ;
INSERT INTO `admin_users` VALUES (10,'Deneb','Villanueva','deneb@gmail.com',12345654,'Front Desk','asdasd',1),(12,'Dumar','Ruge','dumar@gmail.com',1239999,'Admin','asdasd',1);

INSERT INTO `customers` VALUES (9,'John','Doe','john-doe@gamil.com',123123337,2000.00,NULL,'WhoAmI',1,1),(11,'Angel','Perez','angel@gmail.com',12345654,2000.00,9,'asdasd',2,1),(13,'Jefferson','Ruge','jeffer@gmail.com',1239999,1250.00,9,'asdasd',2,1),(14,'Amanda','Londono','mandy@gmail.com',1230000,1000.00,9,'asdasd',2,1),(15,'Gilmer','Londono','totis@gmail.com',1230000,1000.00,9,'asdasd',2,1);

INSERT INTO `transactions` VALUES (2,9,'20240220',47.45,4),(3,11,'20240225',47.95,5),(4,13,'20240227',43.00,5),(5,9,'20240301',47.45,4),(6,9,'20240229',28.00,5);

INSERT INTO `detail_trans` VALUES (1,2,'20240220',5,22.95,1),(2,2,'20240220',6,24.50,1),(3,3,'20240225',4,27.95,1),(4,3,'20240225',7,20.00,1),(5,4,'20240227',8,18.00,1),(6,4,'20240227',9,25.00,1),(7,5,'20240220',5,22.95,1),(8,5,'20240220',6,24.50,1),(9,6,'20240301',10,6.00,1),(10,6,'20240301',11,22.00,1);

INSERT INTO `merchant` VALUES (4,'ITALIAN RESTAURANT'),(5,'COLOMBIAN RESTAURANT'),(6,'DIVE STORE');

INSERT INTO `merchant_users` VALUES (1,4,'Massimo','Bottura','mbottura@gmail.com',12345654,'Chef','asdasd',1),(2,5,'Leonor','Espinosa','lespinosa@gmail.com',1239999,'Chef','asdasd',1);

INSERT INTO `products` VALUES (4,'Ajiaco',27.95,5),(5,'Pasta Carbonara',22.95,4),(6,'Pasta Bolognesa',24.50,4),(7,'Carne Asada',20.00,5),(8,'Lomo de Cerdo',18.00,5), (9,'Cazuela de Mariscos',25.00,5),(10,'Chorizo con Arepa',6.00,5),(11,'Casareccia',22.00,4),(12,'Salsiccia',18.50,4),(13,'Cavolo Nero',16.00,4);


