package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DB.JdbcUtils;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final String sqlQuery = "INSERT INTO book(`bookName`, `bookEdition`,`bookPrice`)VALUE(?,?,?)";

	Connection connection = null;
	PreparedStatement preparedStatement = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Get PrintWriter
		PrintWriter pw = res.getWriter();

		// set content Type
		res.setContentType("text/html");

		// get the book info
		String bookName = req.getParameter("bookName");
		String bookEdition = req.getParameter("bookEdition");
		float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));

		// Load JDBC Driver
		try {
			connection = JdbcUtils.getConnection();

			if (connection != null) {
				preparedStatement = connection.prepareStatement(sqlQuery);
			}

			if (preparedStatement != null) {
				preparedStatement.setString(1, bookName);
				preparedStatement.setString(2, bookEdition);
				preparedStatement.setFloat(3, bookPrice);
			}

			int count = preparedStatement.executeUpdate();
			if (count == 1) {
				pw.println("<h2>Record is Registered Successfully</h2>");
			} else {
				pw.println("<h2>No Record Registered</h2>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			pw.println("<h2>" + e.getMessage() + "</h2>");
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1>" + e.getMessage() + "</h1>");
		} finally {
			try {
				JdbcUtils.clearConnections(connection, preparedStatement, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		pw.println("<a center href='home.html'>Home</a>");
		pw.println("<br/>");
		pw.println("<a href='booklist'>Book List</a>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}