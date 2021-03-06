package bookland.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bookland.dao.UserDao;
import bookland.models.NearUser;
import bookland.models.User;
import io.jsonwebtoken.Claims;

@Controller
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private PasswordEncoder pEncode;

	@Autowired
	private UserDao userDao;

	@SuppressWarnings("unused")
	private static class NewPass {
		public String username;
		public String password;
		public String newpassword;
	}

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
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("Error creating the user: " + ex, HttpStatus.NOT_FOUND);
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
			return new ResponseEntity<>("Error deleting the user: " + ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/get-by-email")
	@ResponseBody
	public Object getByEmail(String email) {
		try {
			User user = userDao.findByEmail(email);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);

		}
	}

	@RequestMapping("/near")
	@ResponseBody
	public Object findNear(long userId) {
		try {
			User user = userDao.findById(userId);
			List<User> nearUsers = userDao.findNear(user.getId(), user.getPrefLocalLat(), user.getPrefLocalLon(),
					user.getPrefLocalRadius());
			List<NearUser> users = nearUsers.stream().map(p -> new NearUser(p.getId(), p.getUsername(),
					p.getPrefLocalLat(), p.getPrefLocalLon(), p.getPrefLocalRadius())).collect(Collectors.toList());
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public Object updateAccount(long id, String email, String name) {
		try {
			User user = userDao.findOne(id);
			user.setEmail(email);
			user.setUsername(name);
			userDao.save(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("Error updating the user: " + ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/prefs/local", method = RequestMethod.PUT)
	@ResponseBody
	public Object setPrefsLocal(long id, double radius, double lon, double lat) {
		try {
			User user = userDao.findOne(id);
			user.setPrefLocalRadius(radius);
			user.setPrefLocalLat(lat);
			user.setPrefLocalLon(lon);
			userDao.save(user);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>("Error deletupdating the user: " + ex, HttpStatus.NOT_FOUND);

		}
	}

	@RequestMapping(value = "/newPass", method = RequestMethod.POST)
	@ResponseBody
	public Object getId(final HttpServletRequest request, @RequestBody final NewPass passReq) {
		try {
			Long userId = new Long((String) ((Claims) request.getAttribute("claims")).getSubject());
			User user = userDao.findById(userId);

			if (pEncode.matches(passReq.password, user.getPassword())) {
				user.setPassword(pEncode.encode(passReq.newpassword));
				userDao.save(user);
				return new ResponseEntity<>(HttpStatus.OK);
			}else{
				return new ResponseEntity<>(passReq.password + " " + passReq.newpassword, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception ex) {
			return new ResponseEntity<>("Some error occured: " + ex, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/topBookCount", method = RequestMethod.GET)
	@ResponseBody
	public Object topBookCount() {
		try {
			List<User> topusers = userDao.findTop5ByOrderByBookCountDesc();
			List<NearUser> users = topusers.stream()
					.map(p -> new NearUser(p.getId(), p.getUsername(), p.getBookCount())).collect(Collectors.toList());

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}
}
