package bookland.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bookland.dao.UserDao;
import bookland.models.User;

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
//				String token = TokenService.createJWT("xxx", "Bookland", "req", 3600000);
//				return new ResponseEntity<>(token, HttpStatus.OK);
				 return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@RequestMapping(path = "/tokentest", method = RequestMethod.POST)
//	@ResponseBody
//	public Object fakeAuth(String token) {
//		String decode = TokenService.parseJWT(token);
//		return decode;
//	}

}
