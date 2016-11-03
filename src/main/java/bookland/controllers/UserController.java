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
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDao userDao;

	@RequestMapping("/create")
	@ResponseBody
	public String create(String email, String username, String password) {
		User user = null;
		try {
			user = new User(email, username, password);
			userDao.save(user);
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		return "User succesfully created! (id = " + user.getId() + ")";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String delete(long id) {
		try {
			User user = new User(id);
			userDao.delete(user);
		} catch (Exception ex) {
			return "Error deleting the user: " + ex.toString();
		}
		return "User succesfully deleted!";
	}

	@RequestMapping("/get-by-email")
	@ResponseBody
	public Object getByEmail(String email) {
		User user;
		try {
			user = userDao.findByEmail(email);
		} catch (Exception ex) {
			return "User not found";
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String updateAccount(long id, String email, String name) {
		try {
			User user = userDao.findOne(id);
			user.setEmail(email);
			user.setUsername(name);
			userDao.save(user);
		} catch (Exception ex) {
			return "Error updating the user: " + ex.toString();
		}
		return "User succesfully updated!";
	}

	@RequestMapping("/prefs/local")
	@ResponseBody
	public String setPrefsLocal(long id, int radius, double lon, double lat) {
		try {
			User user = userDao.findOne(id);
			user.setPrefLocalRadius(radius);
			user.setPrefLocalLat(lat);
			user.setPrefLocalLon(lon);
			userDao.save(user);
		} catch (Exception ex) {
			return "Error updating the user: " + ex.toString();
		}
		return "User succesfully updated!";
	}
}
