-- MySQL Workbench Synchronization
-- Generated: 2023-12-06 16:53
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Andreas Bayu P

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `mydb`.`cerbungs` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `num_likes` INT(11) NOT NULL,
  `status` TINYINT(4) NOT NULL,
  `num_paragraph` INT(11) NOT NULL,
  `genre` VARCHAR(45) NOT NULL,
  `waktu_dibuat` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `mydb`.`paragraphs` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `isi` VARCHAR(100) NOT NULL,
  `num_like` VARCHAR(45) NOT NULL,
  `cerbung_id` INT(11) NOT NULL,
  `author_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_paragraf_cerbung_idx` (`cerbung_id` ASC),
  INDEX `fk_paragraf_author1_idx` (`author_id` ASC),
  CONSTRAINT `fk_paragraf_cerbung`
    FOREIGN KEY (`cerbung_id`)
    REFERENCES `mydb`.`cerbungs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_paragraf_author1`
    FOREIGN KEY (`author_id`)
    REFERENCES `mydb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `mydb`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(128) NOT NULL,
  `num_follower` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `mydb`.`notifications` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `waktu_notif` DATE NOT NULL,
  `isi_notif` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `mydb`.`approval_cerbung` (
  `cerbung_id` INT(11) NOT NULL,
  `author_id` INT(11) NOT NULL,
  `status_approval` TINYINT(4) NOT NULL,
  `notifications_id` INT(11) NOT NULL,
  PRIMARY KEY (`cerbung_id`, `author_id`),
  INDEX `fk_cerbung_author_author1_idx` (`author_id` ASC),
  INDEX `fk_cerbung_author_cerbung1_idx` (`cerbung_id` ASC),
  INDEX `fk_cerbung_status_notifications1_idx` (`notifications_id` ASC),
  CONSTRAINT `fk_cerbung_author_cerbung1`
    FOREIGN KEY (`cerbung_id`)
    REFERENCES `mydb`.`cerbungs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cerbung_author_author1`
    FOREIGN KEY (`author_id`)
    REFERENCES `mydb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cerbung_status_notifications1`
    FOREIGN KEY (`notifications_id`)
    REFERENCES `mydb`.`notifications` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `mydb`.`following` (
  `users_id_1` INT(11) NOT NULL,
  `users_id_2` INT(11) NOT NULL,
  `is_following` TINYINT(4) NOT NULL,
  PRIMARY KEY (`users_id_1`, `users_id_2`),
  INDEX `fk_users_users_users2_idx` (`users_id_2` ASC),
  INDEX `fk_users_users_users1_idx` (`users_id_1` ASC),
  CONSTRAINT `fk_users_users_users1`
    FOREIGN KEY (`users_id_1`)
    REFERENCES `mydb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_users_users2`
    FOREIGN KEY (`users_id_2`)
    REFERENCES `mydb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
