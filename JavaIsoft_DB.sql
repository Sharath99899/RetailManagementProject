CREATE DATABASE JavaIsoft_DB;

USE JavaIsoft_DB;
CREATE TABLE Cars (
    carId INT AUTO_INCREMENT PRIMARY KEY,
    carName VARCHAR(100),
    brand VARCHAR(100),
    yearOfManufacture INT,
    fuelType VARCHAR(50),
    transmissionType VARCHAR(50),
    engineCapacity INT,
    price DOUBLE,
    color VARCHAR(50)
);
SELECT * FROM Cars;
drop table Cars;

-------------------------------------------------------------------------------------------------------------------------------------------------

USE JavaIsoft_DB;
CREATE TABLE PersonData 
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    bloodGroup VARCHAR(5),
    number VARCHAR(10),
    email VARCHAR(100)
);
SELECT * FROM PersonData;
drop table PersonData;

-------------------------------------------------------------------------------------------------------------------------------------------------

USE JavaIsoft_DB;
CREATE TABLE StudentTable (
    studentId INT AUTO_INCREMENT PRIMARY KEY,
    studentName VARCHAR(255),
    age INT,
    email VARCHAR(255),
    phone VARCHAR(15),
    fee DECIMAL(10, 2)
);
SELECT * FROM StudentTable;
drop table StudentTable;

-------------------------------------------------------------------------------------------------------------------------------------------------


USE JavaIsoft_DB;
CREATE TABLE Country (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);
CREATE TABLE State (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    country_id INT,
    FOREIGN KEY (country_id) REFERENCES Country(id) ON DELETE CASCADE
);
INSERT INTO Country (name) VALUES 
('India'), 
('USA'), 
('Canada'),
('UK'),
('Australia');
INSERT INTO State (name, country_id) VALUES
-- States for India
('Telangana', 1),
('Karnataka', 1),
('Andhra Pradesh', 1),
('Tamil Nadu', 1),
('Uttar Pradesh', 1),
-- States for USA
('California', 2),
('Texas', 2),
('New York', 2),
('Florida', 2),
('Illinois', 2),
-- States for Canada
('Ontario', 3),
('Quebec', 3),
('British Columbia', 3),
('Alberta', 3),
('Nova Scotia', 3),
-- States for UK
('England', 4),
('Scotland', 4),
('Wales', 4),
('Northern Ireland', 4),
('Cornwall', 4),
-- States for Australia
('New South Wales', 5),
('Queensland', 5),
('Victoria', 5),
('South Australia', 5),
('Western Australia', 5);
SELECT * FROM Country;
SELECT * FROM State;
Drop table Country;
Drop table State;

-------------------------------------------------------------------------------------------------------------------------------------------------


CREATE DATABASE StudentManagement;
USE StudentManagement;
CREATE TABLE Country (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);
CREATE TABLE State (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    country_id INT,
    FOREIGN KEY (country_id) REFERENCES Country(id) ON DELETE CASCADE
);
INSERT INTO Country (name) VALUES 
('India'), 
('USA'), 
('Canada'),
('UK'),
('Australia');
INSERT INTO State (name, country_id) VALUES
-- States for India
('Telangana', 1),
('Karnataka', 1),
('Andhra Pradesh', 1),
('Tamil Nadu', 1),
('Uttar Pradesh', 1),
-- States for USA
('California', 2),
('Texas', 2),
('New York', 2),
('Florida', 2),
('Illinois', 2),
-- States for Canada
('Ontario', 3),
('Quebec', 3),
('British Columbia', 3),
('Alberta', 3),
('Nova Scotia', 3),
-- States for UK
('England', 4),
('Scotland', 4),
('Wales', 4),
('Northern Ireland', 4),
('Cornwall', 4),
-- States for Australia
('New South Wales', 5),
('Queensland', 5),
('Victoria', 5),
('South Australia', 5),
('Western Australia', 5);

-- Create Registration Table for storing user registration data
CREATE TABLE registration (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(255) NOT NULL,
    student_age INT NOT NULL,
    student_gender VARCHAR(50) NOT NULL,
    student_phone VARCHAR(15),
    student_blood_group ENUM('O+', 'O-', 'A+', 'A-', 'B+', 'B-', 'AB+', 'AB-') NOT NULL, 
    student_email VARCHAR(255) UNIQUE NOT NULL,
    student_password VARCHAR(255) NOT NULL
);
-- Create Dashboard Table for storing additional student data
CREATE TABLE dashboard (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(255),
    student_age INT,
    student_gender VARCHAR(50),
    student_dob DATE,
    student_phone VARCHAR(15),
    student_date_of_joining DATE,
    student_fee DECIMAL(10, 2),
    student_branch VARCHAR(50),
    student_country INT,
    student_state INT,
    student_address TEXT,
    FOREIGN KEY (student_country) REFERENCES Country(id),
    FOREIGN KEY (student_state) REFERENCES State(id)
);

SELECT * FROM Country;
SELECT * FROM State;
SELECT * FROM registration;
SELECT * FROM dashboard;

Drop table Country;
Drop table State;
drop table registration;
drop table dashboard;


-------------------------------------------------------------------------------------------------------------------------------------------------
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