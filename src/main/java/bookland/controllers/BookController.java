package bookland.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bookland.dao.BookDao;
import bookland.dao.UserDao;
import bookland.models.Book;
import bookland.models.User;

@Controller
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookDao bookDao;

	@Autowired
	private UserDao userDao;

	@RequestMapping("/create")
	@ResponseBody
	public Object create(long ownerId, String title, String author) {
		try {
			Book book = new Book(ownerId, title, author);
			bookDao.save(book);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(long id) {
		try {
			Book book = new Book(id);
			bookDao.delete(book);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
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
		try {
			List<Book> books = bookDao.findByOwnerId(id);
			return new ResponseEntity<>(books, HttpStatus.OK);
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

	@RequestMapping("/title/{title}")
	@ResponseBody
	public Object findByTitle(@PathVariable("title") String title) {
		try {
			List<Book> books = bookDao.findByTitleContaining(title);
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/author/{author}")
	@ResponseBody
	public Object findByAuthor(@PathVariable("author") String author) {
		try {
			List<Book> books = bookDao.findByAuthorContaining(author);
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/near")
	@ResponseBody
	public Object findNearByKeyword(long userId, String keyWord) {
		try {
			User user = userDao.findById(userId);
			List<User> nearUsers = userDao.findNear(user.getPrefLocalLat(), user.getPrefLocalLon(),
					user.getPrefLocalRadius());
			List<Long> usersId = nearUsers.stream().map(User::getId).collect(Collectors.toList());
			List<Book> books = bookDao.findNearByKeyWord(userId, usersId, keyWord);
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
		}
	}
}
