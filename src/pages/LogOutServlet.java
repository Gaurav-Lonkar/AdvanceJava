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
import pojos.Book;
import pojos.Customer;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/sess_logout")
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set cont type
		response.setContentType("text/html");
		// pw -- buffered char o/p strm connected from
		// server---> clnt , to send resp
		try (PrintWriter pw = response.getWriter()) {

			// HS
			HttpSession hs = request.getSession();
			Customer c = (Customer) hs.getAttribute("customer_dtls");
			if (c != null) {
				pw.print("<h4>Hello ," + c.getName() + "</h4>");
				// get dao instance from web appln scope
				BookShopDaoImpl dao = (BookShopDaoImpl) getServletContext().getAttribute("shop_dao");
				// get user's cart from session scope
				ArrayList<Integer> shoppingCart = (ArrayList<Integer>) hs.getAttribute("cart");
				// invoke dao layer method to fetch details of the bk ids in the
				// cart
				pw.print("<h4 align=center>Cart Contents</h4><h3>");
				double total = 0;
				for (int i : shoppingCart) // auto un boxing : Integer --->int
				{
					Book b = dao.getBookById(i);
					pw.print(b.getTitle() + " " + b.getAuthor() + " " + b.getPrice() + "<br>");
					total += b.getPrice();
				}
				pw.print("Cart Price : " + total + "<br>");
				pw.print("</h3>");
				pw.print("<br>You have logged out successfully...");
			} else
				pw.print("Session tracking failed.....");

			// discard HS
			hs.invalidate();

			// add a link to index page
			pw.print("<h4><a href=index.html>Visit Again</a></h4>");

		} catch (Exception e) {
			throw new ServletException("err in do-get", e);
		}

	}

}
