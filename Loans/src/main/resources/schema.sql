CREATE TABLE IF NOT EXISTS LOAN (
    LOAN_ID INT AUTO_INCREMENT PRIMARY KEY,
    MOBILE_NUMBER VARCHAR(20) NOT NULL,
    LOAN_NUMBER VARCHAR(100) NOT NULL,
    LOAN_TYPE VARCHAR(100) NOT NULL,
    TOTAL_LOAN INT NOT NULL,
    AMOUNT_PAID INT NOT NULL,
    OUTSTANDING_AMOUNT INT NOT NULL,
    CREATED_AT DATETIME NOT NULL,
    CREATED_BY VARCHAR(20) NOT NULL,
    UPDATED_AT DATETIME DEFAULT NULL,
    UPDATED_BY VARCHAR(20) DEFAULT NULL
);