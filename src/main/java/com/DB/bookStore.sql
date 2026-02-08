CREATE DATABASE bookStore;
use bookstore;

CREATE TABLE book(
	id INT AUTO_iNCREMENT PRIMARY KEY,
    bookName VARCHAR(45),
    bookEdition VARCHAR(45),
    bookPrice FLOAT
);

SELECT id, bookName, bookEdition, bookPrice FROM book;