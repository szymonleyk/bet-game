CREATE TABLE `account`
(
    `id`       INTEGER PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(150) NOT NULL UNIQUE,
    `name`     VARCHAR(150) NOT NULL,
    `surname`  VARCHAR(150) NOT NULL
);

CREATE TABLE `wallet_transaction`
(
    `id`              INTEGER PRIMARY KEY AUTO_INCREMENT,
    `transactionDate` DATE   NOT NULL,
    `amount`          INT    NOT NULL,
    `accountId`       INTEGER NOT NULL,
    FOREIGN KEY (`accountId`) REFERENCES `account` (`id`)
);