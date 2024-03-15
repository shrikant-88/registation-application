package com.example.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.DbUtil.DBUtil;

@WebServlet("/reg")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String SQL_INSERT = "insert into studentdetails values(?,?,?,?)";
    public static final String SQL_FETCH = "select * from studentdetails where email=? and password=?";
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
        	
        	connection = DBUtil.getDBConection();
        	preparedStatement = connection.prepareStatement(SQL_FETCH);
        	preparedStatement.setString(1, email);
        	preparedStatement.setString(2, password);
        	resultSet = preparedStatement.executeQuery();
        	if(resultSet.next()) {
        		String name = resultSet.getString(1);
                out.println("<!DOCTYPE html>\r\n"
                		+ "<html>\r\n"
                		+ "<head>\r\n"
                		+ "  <title>Document</title>\r\n"
                		+ "</head>\r\n"
                		+ "<body>\r\n"
                		+ "  <h1>hello "+name+"</h1>\r\n"
                		+ " <a href=\"/userRegistation\">goback</a>"
                		+ "</body>\r\n"
                		+ "</html>");
        	}else {
        		out.println("<!DOCTYPE html>\r\n"
        				+ "<html>\r\n"
        				+ "<head>\r\n"
        				+ "  <title>Document</title>\r\n"
        				+ "</head>\r\n"
        				+ "<body>\r\n"
        				+ "  <h1>please enter correct email and password</h1>\r\n"
        				+ "</body>\r\n"
        				+ "</html>");
        	}
        }catch(Exception e) {
        	
        }
        finally {
        	
        }
    }

       

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sname = request.getParameter("name");
        String Npass = request.getParameter("Npassword");
        String Cpass = request.getParameter("Cpassword");
        String Email = request.getParameter("email");
        String dob = request.getParameter("dob");

        PrintWriter out = response.getWriter();

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUtil.getDBConection();

            if (Npass.equals(Cpass)) {
                ps = con.prepareStatement(SQL_INSERT);
                ps.setString(1, sname);
                ps.setString(2, Npass);
                ps.setString(3, Email);
                ps.setString(4, dob);

                int rowCount = ps.executeUpdate();

                if (rowCount != 0) {
                    out.println("<html lang=\"en\"><head><title>Response</title></head><body><h2>Data inserted successfully</h2></body></html>");
                    System.out.println("Data inserted successfully");
                } else {
                    out.println("<html lang=\"en\"><head><title>Response</title></head><body><h2>Failed to insert data</h2></body></html>");
                    System.out.println("Failed to insert data");
                }
            } else {
                out.println("<html lang=\"en\"><head><title>Response</title></head><body><h2>Passwords do not match</h2></body></html>");
                System.out.println("Passwords do not match");
            }
        } catch (Exception e) {
            out.println("<html lang=\"en\"><head><title>Response</title></head><body><h2>Error occurred: " + e.getMessage() + "</h2></body></html>");
            System.out.println("Error occurred: " + e);
        } finally {
            DBUtil.cleanUpResources(null, ps, con);
        }
    }
}
