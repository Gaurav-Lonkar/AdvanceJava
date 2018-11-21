package dao;

import java.util.List;

import pojos.Book;
import pojos.Customer;

public interface BookShopDao {
   Customer validateCustomer(String email,String password)
   
	throws Exception;
   String registerCustomer(Customer c)
		   throws Exception;
   List<String> getAllCategories() throws Exception;
   List<Book> getBoookByCategory(String category)throws Exception;
   Book getBookById(int id)throws Exception;
}
