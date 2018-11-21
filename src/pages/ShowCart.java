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

/**
 * Servlet implementation class ShowCart
 */
@WebServlet("/showcart")
public class ShowCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
				HttpSession hs = request.getSession();
		BookShopDaoImpl dao = (BookShopDaoImpl) getServletContext().getAttribute("shop_dao");
		// get user's cart from session scope
		ArrayList<Integer> shoppingCart = (ArrayList<Integer>) hs.getAttribute("cart");
		response.setContentType("text/html");
		try {
			PrintWriter pw=response.getWriter();
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
			pw.print("<a href='sess_category'>Back to Categories</a>");
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		

}
