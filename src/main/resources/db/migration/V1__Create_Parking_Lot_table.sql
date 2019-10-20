DROP TABLE IF EXISTS `parking_lot`;

CREATE TABLE `parking_lot` (
    `name` VARCHAR(255) NOT NULL,
    `capacity` INT(5) CHECK (`capacity` >= 0),
    `location` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`name`)
);

