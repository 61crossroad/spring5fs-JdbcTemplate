DROP TABLE member IF EXISTS;

CREATE TABLE member (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(100),
    name VARCHAR(100),
    regdate DATETIME,
    UNIQUE KEY (email)
);
