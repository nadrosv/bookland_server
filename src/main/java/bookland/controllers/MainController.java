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
		return "Hello World?";
	}

	@SuppressWarnings("unused")
	private static class UserLogin {
		public String name;
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
	public LoginResponse login(@RequestBody final UserLogin login) throws ServletException {
		User user = userDao.findByUsername(login.name);
		if (login.name == null || user == null) {
			throw new ServletException("Invalid login");
		}
		if (!pEncode.matches(login.password, user.getPassword())) {
			throw new ServletException("Invalid password");
		}
		return new LoginResponse(Jwts.builder().setSubject(login.name).claim("roles", user.getUsername())
				.setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").compact(), user.getId());
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public Object fakeRegistration(@RequestBody final UserLogin reg) throws ServletException {
		User user = userDao.findByUsername(reg.name);
		if (user != null) {
			throw new ServletException("User exists");
		}
		user = new User(reg.email, reg.name, pEncode.encode(reg.password));
		userDao.save(user);
		return new ResponseEntity<>(user.getId(), HttpStatus.OK);
	}

}
