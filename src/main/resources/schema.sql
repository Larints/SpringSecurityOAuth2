CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255),
                       name VARCHAR(255),
                       password VARCHAR(255),
                       role VARCHAR(255),
                       provider VARCHAR(255),
                       providerId VARCHAR(255)
);