package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DB.JdbcUtils;

@WebServlet("/booklist")
public class BookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String sqlQuery = "SELECT `id`,`bookName`,`bookEdition`,`bookPrice` FROM book";

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doProcess(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doProcess(req, res);
	}

	public void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Get PrintWriter
		PrintWriter pw = res.getWriter();

		// set content Type
		res.setContentType("text/html");

		// adding bootstrap
		pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
        pw.println("<style>");
        pw.println("table { margin-top: 2rem; }");
        pw.println(".btn-action { margin: 0 5px; }");
        pw.println(".container { margin-top: 2rem; }");
        pw.println("</style>");
        pw.println("<body>");
        pw.println("<div class='container'>");
        pw.println("<h1 class='text-center text-primary'>Book List</h1>");
        
		// Load JDBC Driver
		try {
			connection = JdbcUtils.getConnection();

			if (connection != null) {
				preparedStatement = connection.prepareStatement(sqlQuery);
			}

			if (preparedStatement != null) {
				resultSet = preparedStatement.executeQuery();
			}

			if (resultSet != null) {
				pw.println("<table class='table table-bordered table-striped table-hover text-center'>");
				pw.println("<thead class='table-dark'>");
				pw.println("<tr>");
				pw.println("<th>Book Id</th>");
				pw.println("<th>Book Name</th>");
				pw.println("<th>Book Edition</th>");
				pw.println("<th>Book Price</th>");
				pw.println("<th>Edit</th>");
				pw.println("<th>Delete</th>");
				pw.println("</tr>");
				pw.println("</thead>");
				pw.println("<tbody>");
				while (resultSet.next()) {
					pw.println("<tr>");
					pw.println("<td>" + resultSet.getInt(1) + "</td>");
					pw.println("<td>" + resultSet.getString(2) + "</td>");
					pw.println("<td>" + resultSet.getString(3) + "</td>");
					pw.println("<td>" + resultSet.getFloat(4) + "</td>");
					pw.println("<td><a href='editScreen?id=" + resultSet.getInt(1) + "' class='btn btn-warning btn-sm btn-action'>Edit</a></td>");
					pw.println("<td><a href='deleteUrl?id=" + resultSet.getInt(1) + "' class='btn btn-danger btn-sm btn-action'>Delete</a></td>");
					pw.println("</tr>");
				}
				pw.println("</tbody>");
				pw.println("</table>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			pw.println("<h2 class='text-danger'>" + e.getMessage() + "</h2>");
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1 class='text-danger'>" + e.getMessage() + "</h1>");
		} finally {
			try {
				JdbcUtils.clearConnections(connection, preparedStatement, resultSet);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		pw.println("<div class='text-center mt-4'>");
        pw.println("<a href='home.html' class='btn btn-primary'>Home</a>");
        pw.println("</div>");
        pw.println("</div>");
        pw.println("</body>");
		pw.close();
	}
}
