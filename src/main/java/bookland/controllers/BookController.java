package bookland.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bookland.dao.BookDao;
import bookland.dao.UserDao;
import bookland.models.Book;
import bookland.models.User;
import io.jsonwebtoken.Claims;

@Controller
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookDao bookDao;

	@Autowired
	private UserDao userDao;

	@RequestMapping("/count")
	@ResponseBody
	public Object count(HttpServletRequest request) {
		try {
			long userId = new Long((String) ((Claims) request.getAttribute("claims")).getSubject());
			int count = bookDao.bookCount(userId);
			return new ResponseEntity<>(count, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

//	@RequestMapping(method = RequestMethod.POST)
//	@ResponseBody
//	public Object create(@RequestBody Book newBook) {
//		try {
//			User user = userDao.findById(newBook.getOwnerId());
//			if(user == null) {
//				return new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);
//			}
//			
//			//Book book = new Book(ownerId, title, author);
//			bookDao.save(newBook);
//
//			return new ResponseEntity<Book>(newBook, HttpStatus.CREATED);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	
//	@RequestMapping(method = RequestMethod.GET, params = "id")
//	@ResponseBody
//	public ResponseEntity<Book> getBook(@RequestParam("id") long id) {
//		try {
//			Book book = bookDao.findById(id);
//			return new ResponseEntity<Book>(book, HttpStatus.OK);
//		} catch (Exception ex) {
//			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
//		}
//	}
//	
//	@RequestMapping(method = RequestMethod.GET)
//	@ResponseBody
//	public Object/*ResponseEntity<List<Book>>*/ getBooks() {
//		try {
//			List<Book> books = bookDao.findAll();
//			return new ResponseEntity<>(books, HttpStatus.OK);
//		} catch (Exception ex) {
//			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
//		}
//	}
	
	@RequestMapping("/create")
	@ResponseBody
	public Object create(long ownerId, String title, String author, Integer isbn, String cover, Integer condition) {
		try {
			User user = userDao.findById(ownerId);
			if(user == null) {
				return new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);
			}
			
			Book book = new Book(ownerId, title, author);
			bookDao.save(book);

			// increasing book number in user table
			// it would be nice to have a cursor in DB for this job
			// I'll change it some day
//			int count = bookDao.bookCount(ownerId);
//			User user = userDao.findById(ownerId);
//			user.setBookCount(count);
//			userDao.save(user);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(long id) {
		try {
			bookDao.delete(bookDao.findById(id));

			// decreasing book number in user table
			// it would be nice to have a cursor in DB for this job
			// I'll change it some day
//			long ownerId = book.getOwnerId();
//			int count = bookDao.bookCount(ownerId);
//			User user = userDao.findById(ownerId);
//			user.setBookCount(count);
//			userDao.save(user);

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

	@RequestMapping("/given")
	@ResponseBody
	public Object findGiven(long userId) {
		try {
			List<Book> books = bookDao.findGiven(userId);
			if(books != null)
				return new ResponseEntity<>(books, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping("/taken")
	@ResponseBody
	public Object findTaken(long userId) {
		try {
			List<Book> books = bookDao.findTaken(userId);
			if(books.isEmpty())
				return new ResponseEntity<>(books, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping("/near")
	@ResponseBody
	public Object findNearByKeyword(long userId, String keyWord) {
		try {
			User user = userDao.findById(userId);
			List<User> nearUsers = userDao.findNear(user.getId(), user.getPrefLocalLat(), user.getPrefLocalLon(),
					user.getPrefLocalRadius());
			List<Long> usersId = nearUsers.stream().map(User::getId).collect(Collectors.toList());
			List<Book> books = bookDao.findNearByKeyWord(userId, usersId, keyWord);
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
		}
	}
}
