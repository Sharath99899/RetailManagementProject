-- RetailManagementProject Database--
Create database Retail_DB;
use Retail_DB;

CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50),
    Gender ENUM('Male', 'Female', 'Other') NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL UNIQUE,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    City VARCHAR(50),
    State VARCHAR(50),
    Country VARCHAR(50),
    PostalCode VARCHAR(15),
    RegisteredAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SELECT * FROM Users;
TRUNCATE TABLE Users;
DROP TABLE Users;

CREATE TABLE ProductList (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(255) NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    Quantity INT NOT NULL,
    ExpireDate DATE NOT NULL
);

INSERT INTO ProductList (ProductName, Price, Quantity, ExpireDate) VALUES
('Sprite', 40.00, 100, '2025-12-31'),
('Toor Dal', 65.00, 200, '2024-11-30'),
('Tata Salt', 40.00, 100, '2025-12-31'),
('Lux International Soap', 75.00, 200, '2024-11-30'),
('CloseUp Toothpaste', 30.00, 100, '2025-12-31'),
(' Oil', 156.00, 200, '2024-11-30'),
('Ice Cream', 50.00, 100, '2025-12-31'),
('Milk 1Liter', 85.00, 200, '2024-11-30');


SELECT * FROM ProductList;

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(255) NOT NULL
);

INSERT INTO products (name, price, image_url) VALUES
('Sprite', 40.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7_drU9l6a4VnbCrch0lfuvDq3tfWnAMPVaw&s'),
('Toor Dal', 65.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTte0xIh8Am6X_no0zVykL2SzOYJHxBfYresA&s'),
('Lux International Soap', 70.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPHu7RzvJzjeRrPSx-CpLAIfqxR7PEaedONA&s'),
('CloseUp Toothpaste', 37.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgDbkHmoTV6royRX2EK2lm6yycEz5mPqzF_Q&s'),
('Milk', 45.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4Z-zpAx7AhKL-IlF8GeDivEv4xrGO0n__Lw&s'),
('Oil', 150.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGDzqK3qIxbmFRjyORMPDeq63CgWTVrFTfcQ&s'),
('Ice Cream', 50.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNP5MYEL8DtUUcul_S08CtPZ15GyCzerTQ4g&s'),
('Tata Salt', 38.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5bxlCMF4aB0VT9YDqAvtCuyAzDgvY0meClA&s');

SELECT * FROM products;

SELECT * FROM cart;
TRUNCATE TABLE cart;
DROP TABLE cart;

CREATE TABLE cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL
);
