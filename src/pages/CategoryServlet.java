package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
@WebServlet("/sess_category")
public class CategoryServlet extends HttpServlet {
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
			//pw.print("from category page....<br>");
			// get HS from WC
			HttpSession hs123 = request.getSession();
			// get user details from session scope
			Customer c = (Customer) hs123.getAttribute("customer_dtls");
			if (c != null) {
				pw.print("Hello ," + c.getName() + "<br>");
				// get dao instance from web app scope
				BookShopDaoImpl dao = (BookShopDaoImpl) getServletContext().getAttribute("shop_dao");
				//fetch categories from DAO
				List<String>l1=dao.getAllCategories();
				// generate form dynamically from servlet
				pw.print("<form action=category_dtls>");
				pw.print("Choose Category");
				pw.print("<select name=cat>");
				for(String s : l1)
					pw.print("<option value="+s+">"+s+"</option>");
				pw.print("</select><br>");
				pw.print("<input type=submit value=Choose>");
				pw.print("<input type=submit value='Show Cart' formaction='showcart'>");
				pw.print("</form>");
			} else
				pw.print("Session tracking failed.....");
			// add logout link
			pw.print("<h4><a href=sess_logout>Log Me Out</a></h4>");

		} catch (Exception e) {
			throw new ServletException("err in do-get", e);
		}

	}

}
