package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookShopDaoImpl;
import pojos.Customer;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/sess_validate")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set cont type
		response.setContentType("text/html");
		// pw -- buffered char o/p strm connected from
		// server---> clnt , to send resp
		try (PrintWriter pw = response.getWriter()) {
			// read em n pasword sent from the clnt
			String email = request.getParameter("email");
			String pass123 = request.getParameter("pass");
			//get dao inst from web app scope
			BookShopDaoImpl dao=(BookShopDaoImpl)getServletContext().getAttribute("shop_dao");
			// invoke DAO's CRUD method
			Customer cust = dao.validateCustomer(email, pass123);
			if (cust == null) {
				// invalid login
				pw.print("Invalid Login,  Pls<a href=login.html>Retry</a>");
			} else {
				pw.print("from login page....");//will not be seen
				//HS
				HttpSession hs=request.getSession();
				System.out.println("from 1st page "+hs.isNew());
				System.out.println("sess id "+hs.getId());
				//save customer details under session scope
				hs.setAttribute("customer_dtls", cust);
				//add an empty cart under session scope
				hs.setAttribute("cart", new ArrayList<Integer>());
				// page navigation --clnt pull
				response.sendRedirect("sess_category");
			}
		} catch (Exception e) {
			throw new ServletException("err in do-get", e);
		}

	}

}
