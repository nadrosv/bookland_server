package bookland.controllers;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bookland.dao.UserDao;
import bookland.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/")
public class MainController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder pEncode;

	@RequestMapping("")
	@ResponseBody
	public String index() {
		return "Cześć! (sorry for my bad Polish)";
	}

	@SuppressWarnings("unused")
	private static class UserLogin {
		public String username;
		public String password;
		public String email;
	}

	@SuppressWarnings("unused")
	private static class LoginResponse {
		public String token;
		public long userId;

		public LoginResponse(final String token, final long userId) {
			this.token = token;
			this.userId = userId;
		}
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public Object login(@RequestBody final UserLogin login) {
		try {
			User user = userDao.findByUsername(login.username);
			if (login.username == null || user == null) {
				return new ResponseEntity<>("Invalid login or user not found.", HttpStatus.BAD_REQUEST);
			}
			if (!pEncode.matches(login.password, user.getPassword())) {
				return new ResponseEntity<>("Invalid password.", HttpStatus.BAD_REQUEST);
			}
			LoginResponse res = new LoginResponse(
					Jwts.builder().setSubject(Long.toString(user.getId())).claim("roles", user.getUsername())
							.setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").compact(),
					user.getId());
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public Object register(@RequestBody final UserLogin reg) throws ServletException {
		try {
			User user = userDao.findByUsername(reg.username);
			if (user != null) {
				throw new ServletException("User exists");
			}
			user = new User(reg.email, reg.username, pEncode.encode(reg.password));
			userDao.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
		}
	}

}
