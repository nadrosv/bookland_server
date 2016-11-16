package bookland.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import bookland.models.User;


@Transactional
public interface UserDao extends CrudRepository<User, Long> {

  public User findByEmail(String email);
  public User findById(long id);
  public User findByUsername(String name);

}
