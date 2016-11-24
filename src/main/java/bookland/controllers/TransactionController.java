package bookland.controllers;

import java.util.Date;
import java.util.List;

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
	public Object getAll(long userId){
		try {
			List<Transaction> trans = transDao.findByUser(userId);
			return new ResponseEntity<>(trans, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/get")
	@ResponseBody
	public Object get(long transId){
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
			Book book = bookDao.findById(bookId);
			Transaction trans = new Transaction(book.getOwnerId(), userId, bookId);
			transDao.save(trans);
			return new ResponseEntity<>(trans, HttpStatus.OK);
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
			transDao.save(trans);
			
			Book book = bookDao.findById(trans.getBookId());
			book.setUserId(trans.getUserId());
			bookDao.save(book);
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
			if (owner) {
				trans.setOwnerSummary(feedback);
				trans.setOwnerRate(rate);
				User bookUser = userDao.findById(trans.getUserId());
				bookUser.vote(rate);
			} else {
				trans.setUserSummary(feedback);
				trans.setUserRate(rate);
				User bookOwner = userDao.findById(trans.getOwnerId());
				bookOwner.vote(rate);
			}
			if (trans.getOwnerSummary() != null && trans.getUserSummary() != null) {
				
				trans.setStatus(5);
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
			Date today = new Date();
			Message mess = new Message(transId, userId, today, message);
			messDao.save(mess);
			return new ResponseEntity<>(mess, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

}
