CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internet_shop`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`item_id`));

CREATE TABLE `internet_shop`.`orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`order_id`));

  CREATE TABLE `internet_shop`.`orders_items` (
  `idorders_items_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `item_id` INT NOT NULL,
  PRIMARY KEY (`idorders_items_id`),
  INDEX `orders_items_orders_fk_idx` (`order_id` ASC) VISIBLE,
  INDEX `orders_items_items_fk_idx` (`item_id` ASC) VISIBLE,
  CONSTRAINT `orders_items_orders_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `internet_shop`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `orders_items_items_fk`
    FOREIGN KEY (`item_id`)
    REFERENCES `internet_shop`.`items` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    CREATE TABLE `internet_shop`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `token` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`));

ALTER TABLE `internet_shop`.`orders`
ADD COLUMN `user_id` INT NOT NULL AFTER `order_id`,
ADD INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE;
;
ALTER TABLE `internet_shop`.`orders`
ADD CONSTRAINT `orders_users_fk`
  FOREIGN KEY (`user_id`)
  REFERENCES `internet_shop`.`users` (`user_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;