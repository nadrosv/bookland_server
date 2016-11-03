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
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookDao bookDao;

	@RequestMapping("/create")
	@ResponseBody
	public Object create(long ownerId, String title, String author) {
		Book book = null;
		try {
			book = new Book(ownerId, title, author);
			bookDao.save(book);
			return book;
		} catch (Exception ex) {
			return "Error creating the Book: " + ex.toString();
		}
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

	@RequestMapping("/update")
	@ResponseBody
	public Object updateBook(long id, String title, String author, long userId) {
		try {
			Book book = bookDao.findOne(id);
			book.setTitle(title);
			book.setAuthor(author);
			book.setUserId(userId);
			bookDao.save(book);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/user")
	@ResponseBody
	public Object getByOwnerId(long id) {
//		List<Book> book;
		try {
			List<Book> books = bookDao.findByOwnerId(id);
//			return new ResponseEntity<>(books, HttpStatus.OK);
			return books;
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
		}
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
	}
}
