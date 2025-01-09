package com.controller;

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

@WebServlet("/deleteUrl")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String sqlQuery = "DELETE FROM book WHERE id=?";

	Connection connection = null;
	PreparedStatement preparedStatement = null;

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

		// Add CSS
		pw.println("<head>");
		pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
		pw.println("<style>");
		pw.println("body { font-family: Arial, sans-serif; margin: 2rem; }");
		pw.println("h2 { text-align: center; color: green; }");
		pw.println("a { text-decoration: none; margin-top: 1rem; display: block; text-align: center; }");
		pw.println("a.btn { padding: 10px 20px; border-radius: 5px; margin: 10px auto; display: inline-block; }");
		pw.println("a.btn-primary { background-color: #007bff; color: white; }");
		pw.println("a.btn-primary:hover { background-color: #0056b3; }");
		pw.println("a.center { display: block; margin: 1rem auto; font-weight: bold; margin-top: 40px}");
		pw.println("</style>");
		pw.println("</head>");
		pw.println("<body>");
		
		// get the id of record
		int id = Integer.parseInt(req.getParameter("id"));

		// Load JDBC Driver
		try {
			connection = JdbcUtils.getConnection();

			if (connection != null) {
				preparedStatement = connection.prepareStatement(sqlQuery);
			}

			if (preparedStatement != null) {
				preparedStatement.setInt(1, id);
			}

			int count = preparedStatement.executeUpdate();
			if (count == 1) {
				pw.println("<h2>Record Deleted Successfully</h2>");
			} else {
				pw.println("<h2>No Record Deleted</h2>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			pw.println("<h2 class='text-danger'>" + e.getMessage() + "</h2>");
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1 class='text-danger'>" + e.getMessage() + "</h1>");
		} finally {
			try {
				JdbcUtils.clearConnections(connection, preparedStatement, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		pw.println("<a href='home.html' class='btn btn-success center'>Home</a>");
        pw.println("<a href='booklist' class='btn btn-primary center'>View Book List</a>");
		pw.println("</body>");
		pw.close();
	}
}
