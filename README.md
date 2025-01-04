# BookShop App

## 📚 Project Overview

The **BookShop App** is a web-based application built using **Servlets**, **JSP (Java Server Pages)**, and **JDBC (Java Database Connectivity)**. It is designed for small to medium-sized bookshops to streamline inventory management, improve efficiency, and provide an organized system for handling book records. This project allows users to manage book records efficiently with the ability to **Add**, **View**, **Edit**, and **Delete** book details through a user-friendly interface.

## 🚀 Features

1. **Add New Book:** Insert new book records with details such as Title, Author, Price, and ISBN.  
2. **View All Books:** Display all book records in a tabular format.  
3. **Edit Book Record:** Update existing book information.  
4. **Delete Book Record:** Remove a book from the database.  

## 🛠️ Technologies Used

- **Backend:** Java Servlet  
- **Frontend:** JSP (Java Server Pages), HTML, CSS  
- **Database:** MySQL  
- **Connectivity:** JDBC  
- **Server:** Apache Tomcat  

## 🏗️ Project Structure

BookShopProject/ 
├── src/ 
│ ├── controller/ # Servlets for handling requests   
│ ├── utils/ # Database connection utility 
├── web/ 
│ ├── home.html # Home page 
├── lib/ # External libraries (MySQL Connector)  
└── README.md


## ⚙️ Setup Instructions
1. Clone the repository:  
   ```bash
   git clone https://github.com/your-repo-url.git
   
2. **Set up the MySQL database:**  
   - Import the provided SQL script.

3. **Update database credentials:**  
   Update database credentials in `utils/DBConnection.java`.

4. **Deploy the project:**  
   Deploy the project on Apache Tomcat.

5. **Access the application:**  
   http://localhost:8080/BookShopApp

## 📝 Database Schema

```sql
CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    isbn VARCHAR(13) UNIQUE NOT NULL
);
```

## 🤝 Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request.

## 📄 License

This project is licensed under the **MIT License**.
