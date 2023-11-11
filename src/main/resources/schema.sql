CREATE TABLE `account`
(
    `id`       INTEGER PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(25) NOT NULL UNIQUE,
    `name`     VARCHAR(50) NOT NULL,
    `surname`  VARCHAR(50) NOT NULL,
    `balance`  FLOAT        NOT NULL DEFAULT 0
);

CREATE TABLE `wallet_transaction`
(
    `id`               INTEGER PRIMARY KEY AUTO_INCREMENT,
    `transaction_date` DATE    NOT NULL,
    `amount`           FLOAT NOT NULL,
    `account_id`       INTEGER NOT NULL,
    FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
);

CREATE TABLE `bet`
(
    `id`         INTEGER PRIMARY KEY AUTO_INCREMENT,
    `bet_date`   DATE    NOT NULL,
    `bet_value`  INTEGER NOT NULL,
    `bet_number` INTEGER NOT NULL,
    `win`        BOOLEAN,
    `account_id` INTEGER NOT NULL,
    FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
);