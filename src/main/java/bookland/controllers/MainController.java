package bookland.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bookland.models.User;
import bookland.models.UserDao;

@RestController
public class MainController {

	@Autowired
	private UserDao userDao;

	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "Hello World?";
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object fakeLogin(String login, String password) {
		User user;
		try {
			user = userDao.findByUsername(login);
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (user.getPassword().equals(password)) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else{
				return new ResponseEntity<>(user.getPassword(),HttpStatus.BAD_REQUEST);
			}
			// return new ResponseEntity<>(user,HttpStatus.OK);
		} catch (Exception ex) {
			return "Error occured: " + ex.toString();
		}
	}

}
