package pages;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/add_to_cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//HS
		HttpSession hs=request.getSession();
		//get user's shopping cart from session scope
		@SuppressWarnings("unchecked")
		ArrayList<Integer> l1=(ArrayList<Integer>)hs.getAttribute("cart");
		//get selected book ids from req param
		String[] ids=request.getParameterValues("bk_id");
		//populate the cart with selected book ids
		for(String s : ids)
			l1.add(Integer.parseInt(s));
		System.out.println("cart : "+l1);
		//redirect to main page --category
		response.sendRedirect("sess_category");
		
	}

}








