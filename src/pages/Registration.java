package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookShopDaoImpl;
import pojos.Customer;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns="/registeration",loadOnStartup=1)
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookShopDaoImpl dao;

	public void init() throws ServletException {
		// create DAO instance
		try {
			dao = new BookShopDaoImpl();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException("err in init", e);
		}

	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// clean up dao
		if (dao != null)
			try {
				dao.cleanUp();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("err in destroy", e);
			}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set cont type
		response.setContentType("text/html"); 
		try {
		PrintWriter pw = response.getWriter();  
		          
		String n=request.getParameter("name");  
		String e=request.getParameter("email");  
		String p=request.getParameter("pass");  
		String d=request.getParameter("dor"); 
		String a=request.getParameter("ramt"); 
		String r=request.getParameter("role"); 
		
		
		
		Customer c=new Customer(Double.parseDouble(a),e,n,p,Date.valueOf(d),r);
		System.out.println(c);
			String insert= dao.registerCustomer(c);
			if (insert.equals("Succesfull"))
			{
				pw.print("Registration Succesful<br/>");
			pw.print("<a href=login.html>To login click here</a>");
			}
		} catch (Exception e) {
			throw new ServletException("you have an error",e);
		}

	}
	

}
