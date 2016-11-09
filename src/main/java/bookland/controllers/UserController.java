package bookland.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bookland.models.User;
import bookland.models.UserDao;

@Controller
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserDao userDao;

	@RequestMapping("")
	@ResponseBody
	public Object getUser(long id) {
		User user;
		try {
			user = userDao.findById(id);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/create")
	@ResponseBody
	public Object create(String email, String username, String password) {
		try {
			User user = new User(email, username, password);
			userDao.save(user);
			return new ResponseEntity<>(user,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("Error creating the user: " + ex,HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(long id) {
		try {
			User user = new User(id);
			userDao.delete(user);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("Error deleting the user: " + ex,HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/get-by-email")
	@ResponseBody
	public Object getByEmail(String email) {
		try {
			User user = userDao.findByEmail(email);
			return new ResponseEntity<>(user,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex,HttpStatus.NOT_FOUND);

		}
	}

	@RequestMapping("/update")
	@ResponseBody
	public Object updateAccount(long id, String email, String name) {
		try {
			User user = userDao.findOne(id);
			user.setEmail(email);
			user.setUsername(name);
			userDao.save(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("Error updating the user: " + ex,HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/prefs/local")
	@ResponseBody
	public Object setPrefsLocal(long id, int radius, double lon, double lat) {
		try {
			User user = userDao.findOne(id);
			user.setPrefLocalRadius(radius);
			user.setPrefLocalLat(lat);
			user.setPrefLocalLon(lon);
			userDao.save(user);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>("Error deletupdating the user: " + ex,HttpStatus.NOT_FOUND);

		}
	}
}
