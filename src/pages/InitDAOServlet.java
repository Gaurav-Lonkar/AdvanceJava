package pages;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookShopDaoImpl;

/**
 * Servlet implementation class InitDAOServlet
 */
@WebServlet(urlPatterns="/init",loadOnStartup=1)
public class InitDAOServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		try {
			BookShopDaoImpl dao = new BookShopDaoImpl();
			// add dao unde app scope
			getServletContext().setAttribute("shop_dao", dao);
		} catch (Exception e) {
			throw new ServletException("err in init", e);
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		BookShopDaoImpl dao = (BookShopDaoImpl) getServletContext().getAttribute("shop_dao");
		if (dao != null)
			try {
				dao.cleanUp();
			} catch (Exception e) {
				throw new RuntimeException("err in destroy", e);
			}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
