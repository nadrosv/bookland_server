package bookland.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bookland.models.Book;
import bookland.models.BookDao;

@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookDao bookDao;

	@RequestMapping("/create")
	@ResponseBody
	public Object create(long ownerID, String title, String author) {
		Book book = null;
		try {
			book = new Book(ownerID, title, author);
			bookDao.save(book);
			return book;
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
//		return "User succesfully created! (id = " + book.getId() + ")";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String delete(long id) {
		try {
			Book book = new Book(id);
			bookDao.delete(book);
		} catch (Exception ex) {
			return "Error deleting the user: " + ex.toString();
		}
		return "Book succesfully deleted!";
	}

	@RequestMapping("/user")
	@ResponseBody
	public Object getByUserId(long id) {
		List<Book> book;
		try {
			book = bookDao.findByOwnerID(id);
			return new ResponseEntity<>(book, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
		}
		// return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@RequestMapping("")
	@ResponseBody
	public Object getById(long id) {
		try {
			Book book = bookDao.findById(id);
			return new ResponseEntity<>(book, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
		}
		// return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@RequestMapping("/all")
	@ResponseBody
	public Object getAll() {
		try {
			List<Book> books = bookDao.findAll();
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
		}
		// return new ResponseEntity<>(book, HttpStatus.OK);
	}
}
