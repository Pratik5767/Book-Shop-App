package com.servlet;

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
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doProcess(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doProcess(req, res);
	}

	public void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Get PrintWriter
		PrintWriter pw = res.getWriter();

		// set content Type
		res.setContentType("text/html");

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
				pw.println("<table border='1' align='center'>");
				pw.println("<tr>");
				pw.println("<th>Book Id</th>");
				pw.println("<th>Book Name</th>");
				pw.println("<th>Book Edition</th>");
				pw.println("<th>Book Price</th>");
				pw.println("<th>Edit</th>");
				pw.println("<th>Delete</th>");
				pw.println("</tr>");
				while (resultSet.next()) {
					pw.println("<tr>");
					pw.println("<td>" + resultSet.getInt(1) + "</td>");
					pw.println("<td>" + resultSet.getString(2) + "</td>");
					pw.println("<td>" + resultSet.getString(3) + "</td>");
					pw.println("<td>" + resultSet.getFloat(4) + "</td>");
					pw.println("<td><a href='editScreen?id=" + resultSet.getInt(1) + "'>Edit</a></td>");
					pw.println("<td><a href='deleteUrl?id=" + resultSet.getInt(1) + "'>Delete</a></td>");
					pw.println("</tr>");
				}
				pw.println("</table>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			pw.println("<h2>" + e.getMessage() + "</h2>");
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1>" + e.getMessage() + "</h1>");
		} finally {
			try {
				JdbcUtils.clearConnections(connection, preparedStatement, resultSet);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		pw.println("<a href='home.html'>Home</a>");
		pw.close();
	}
}
