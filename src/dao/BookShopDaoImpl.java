package dao;

import pojos.Book;
import pojos.Customer;
//import all static members of the class
import static utils.DBUtils.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookShopDaoImpl implements BookShopDao {
	private Connection cn;
	private PreparedStatement pst1, pst2, pst3, pst4,pst5;

	public BookShopDaoImpl() throws Exception {
		// cn
		cn = getConnection();
		// pst1 -- customer validation
		pst1 = cn.prepareStatement("select * from my_customers where email=? and password=?");
		// selecting categories
		pst2 = cn.prepareStatement("select distinct category from dac_books");
		// getting bks by category
		pst3 = cn.prepareStatement("select * from dac_books where category=?");
		// get book dtls by id
		pst4 = cn.prepareStatement("select * from dac_books where id=?");
		//customer registration
		pst5 = cn.prepareStatement("insert into my_customers values(NULL,?,?,?,?,?,?)");
		System.out.println("dao inst created...");
	}

	public void cleanUp() throws Exception {
		if (pst1 != null)
			pst1.close();
		if (pst2 != null)
			pst2.close();
		if (pst3 != null)
			pst3.close();
		if (pst4 != null)
			pst4.close();
		if (pst5 != null)
			pst5.close();
		if (cn != null)
			cn.close();
		System.out.println("dao cleaned up");
	}

	@Override
	public Customer validateCustomer(String email, String password) throws Exception {
		// set IN params
		pst1.setString(1, email);
		pst1.setString(2, password);
		// exec query
		try (ResultSet rst = pst1.executeQuery()) {
			if (rst.next())
				return new Customer(rst.getInt(1), rst.getDouble(2), rst.getString(3), rst.getString(4),
						rst.getString(5), rst.getDate(6), rst.getString(7));
		}
		return null;
	}

	@Override
	public List<String> getAllCategories() throws Exception {
		// create AL<String> to hold categories
		ArrayList<String> categories = new ArrayList<>();
		try (ResultSet rst = pst2.executeQuery()) {
			while (rst.next())
				categories.add(rst.getString(1));
		}
		System.out.println("dao " + categories);
		return categories;
	}

	@Override
	public List<Book> getBoookByCategory(String category) throws Exception {
		// emty AL<Book>
		ArrayList<Book> bks = new ArrayList<>();
		// set IN param
		pst3.setString(1, category);
		try (ResultSet rst = pst3.executeQuery()) {
			while (rst.next())
				bks.add(new Book(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
						rst.getDouble(5)));
		}
		return bks;
	}

	@Override
	public Book getBookById(int id) throws Exception {
		// set IN param
		pst4.setInt(1, id);
		try (ResultSet rst = pst4.executeQuery()) {
			if (rst.next())
				return new Book(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getDouble(5));
		}
		return null;
	}
	
	@Override
	public String registerCustomer(Customer c)
			throws Exception {
		pst5.setDouble(1,c.getRegAmount());  
		pst5.setString(2,c.getEmail());
		pst5.setString(3,c.getName());
		pst5.setString(4,c.getPass());  
		pst5.setDate(5,c.getRegDate());  
		pst5.setString(6,c.getRole());  
		          
		int ins=pst5.executeUpdate(); 
		if(ins==1)
		{
			return "Succesfull";
		}
		return null;
	}
	

}
