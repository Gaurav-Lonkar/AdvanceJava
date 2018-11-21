package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookShopDaoImpl;
import pojos.Book;

/**
 * Servlet implementation class CategoryDetailsServlet
 */
@WebServlet("/category_dtls")
public class CategoryDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		try (PrintWriter pw = response.getWriter()) {
			// get dao instance from web app scope
			BookShopDaoImpl dao = (BookShopDaoImpl) getServletContext().getAttribute("shop_dao");
			// get selected castegory
			String category = request.getParameter("cat");
			// invoke dao layer's method to fetch bks by specified category
			List<Book> l1 = dao.getBoookByCategory(category);
			pw.print("<h4 align='center'>Books Under " + category + "</h4>");
			pw.print("<form action='add_to_cart'>");

			for (Book b : l1)
				pw.print("<input type='checkbox' name='bk_id' value=" + b.getBookId() + ">" + b.getTitle() + "&nbsp "
						+ b.getAuthor() + "&nbsp " + b.getPrice() + "<br>");

			pw.print("<input type=submit value='Add To Cart'>");
			pw.print("</form>");

		} catch (Exception e) {
			throw new ServletException("err in do-get of " + getClass().getName(), e);
		}
	}

}
