package bookland.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bookland.dao.BookDao;
import bookland.dao.MessageDao;
import bookland.dao.TransactionDao;
import bookland.dao.UserDao;
import bookland.models.Book;
import bookland.models.Message;
import bookland.models.Transaction;
import bookland.models.User;
import io.jsonwebtoken.Claims;

@Controller
@RequestMapping("/api/trans")
public class TransactionController {

	@Autowired
	private TransactionDao transDao;

	@Autowired
	private MessageDao messDao;

	@Autowired
	private BookDao bookDao;

	@Autowired
	private UserDao userDao;

	@RequestMapping("/all")
	@ResponseBody
	public Object getAll(long userId) {
		try {
			List<Transaction> trans = transDao.findActive(userId);
			return new ResponseEntity<>(trans, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/history")
	@ResponseBody
	public Object getHistory(long userId) {
		try {
			List<Transaction> trans = transDao.findArchived(userId);
			return new ResponseEntity<>(trans, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/get")
	@ResponseBody
	public Object get(long transId) {
		try {
			Transaction trans = transDao.findById(transId);
			return new ResponseEntity<>(trans, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/init")
	@ResponseBody
	public Object init(long bookId, long userId) {
		try {
			Transaction check = transDao.findByUserIdAndBookId(userId, bookId);

			if (check == null) {
				Book book = bookDao.findById(bookId);
				User owner = userDao.findById(book.getOwnerId());
				User user = userDao.findById(userId);
				Transaction trans = new Transaction(book.getOwnerId(), userId, bookId, owner.getUsername(),
						user.getUsername(), book.getTitle(), book.getAuthor());
				transDao.save(trans);
				return new ResponseEntity<>(trans, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/accept")
	@ResponseBody
	public Object accept(long transId) {
		try {
			Transaction trans = transDao.findById(transId);
			trans.setStatus(2);
			trans.setMessaging(true);
			transDao.save(trans);

			Book book = bookDao.findById(trans.getBookId());
			book.setUserId(trans.getUserId());
			bookDao.save(book);
			return new ResponseEntity<>(trans, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/confirm")
	@ResponseBody
	public Object confirm(long transId) {
		try {
			Transaction trans = transDao.findById(transId);
			Date today = new Date();
			trans.setStatus(3);
			trans.setBegDate(today);
			// try to use this later:
			// LocalDateTime.from(dt.toInstant()).plusDays(1);

			// trans.setEndDate(today);
			transDao.save(trans);

			// Book book = bookDao.findById(trans.getBookId());
			// book.setUserId(trans.getUserId());
			// bookDao.save(book);
			return new ResponseEntity<>(trans, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/finalize")
	@ResponseBody
	public Object finalize(long transId) {
		try {
			Transaction trans = transDao.findById(transId);
			trans.setStatus(4);
			transDao.save(trans);

			Book book = bookDao.findById(trans.getBookId());
			book.setUserId(trans.getOwnerId());
			bookDao.save(book);
			return new ResponseEntity<>(trans, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/close")
	@ResponseBody
	public Object close(long transId, String feedback, int rate, boolean owner) {
		try {
			Transaction trans = transDao.findById(transId);
			if (trans.getStatus() != 5) {
				if (owner) {
					trans.setOwnerSummary(feedback);
					trans.setOwnerRate(rate);
					User bookUser = userDao.findById(trans.getUserId());
					bookUser.vote(rate);
					userDao.save(bookUser);
				} else {
					trans.setUserSummary(feedback);
					trans.setUserRate(rate);
					User bookOwner = userDao.findById(trans.getOwnerId());
					bookOwner.vote(rate);
					userDao.save(bookOwner);
				}
				if (trans.getOwnerRate() != 0 && trans.getUserRate() != 0) {
					trans.setStatus(5);
				}
			}

			transDao.save(trans);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/reject")
	@ResponseBody
	public Object reject(long transId) {
		try {
			Transaction trans = transDao.findById(transId);
			transDao.delete(trans);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/contact")
	@ResponseBody
	public Object contact(long transId, long userId, String message) {
		try {
			Timestamp now = new Timestamp(System.currentTimeMillis());
			Message mess = new Message(transId, userId, now, message);
			messDao.save(mess);
			return new ResponseEntity<>(mess, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/messages")
	@ResponseBody
	public Object contact(long transId) {
		try {
			List<Message> messages = messDao.findByTransId(transId);
			return new ResponseEntity<>(messages, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/news")
	@ResponseBody
	public Object news(HttpServletRequest request) {
		try {
			Long userId = new Long((String) ((Claims) request.getAttribute("claims")).getSubject());
			User user = userDao.findById(userId);
			List<Transaction> transs = transDao.findActive(userId);
			List<Message> mess = null;

			if (!transs.isEmpty()) {
				List<Long> transIds = transs.stream().map(Transaction::getId).collect(Collectors.toList());
				mess = messDao.findNew(userId, transIds, user.getLastUpdate());
				user.setLastUpdate(new Timestamp(System.currentTimeMillis()));
				userDao.save(user);
			}

			return new ResponseEntity<>(mess, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}
}
