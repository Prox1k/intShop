CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internet_shop`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`item_id`));

INSERT INTO `internet_shop`.`items` (`name`, `price`) VALUES ('iPhone11 ', '1000');

INSERT INTO `internet_shop`.`items` (`name`, `price`) VALUES ('iPhoneX', '800');
