package bookland.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import bookland.models.User;


@Transactional
public interface UserDao extends CrudRepository<User, Long> {

	public List<User> findAll();
	public User findById(Long id);
	public User findByEmail(String email);
	public User findByUsername(String name);
	public User findByEmailOrUsername(String email, String username);

	@Query("SELECT u FROM User u WHERE u.id <> :userId and 111.195*sqrt(power(u.prefLocalLat-:lat,2)+power(cos(pi()/180*:lat)*(u.prefLocalLon-:lon),2)) < :radius")
	public List<User> findNear(@Param("userId") long userId, @Param("lat") double lat, @Param("lon") double lon, @Param("radius") double radius);

	public List<User> findTop5ByOrderByBookCountDesc();

}