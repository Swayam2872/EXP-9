CREATE DATABASE RestaurantDB;
USE RestaurantDB;

-- Restaurant Table
CREATE TABLE Restaurant (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100),
    Address VARCHAR(200)
);

-- MenuItem Table
CREATE TABLE MenuItem (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100),
    Price INT,
    ResId INT,
    FOREIGN KEY (ResId) REFERENCES Restaurant(Id)
);