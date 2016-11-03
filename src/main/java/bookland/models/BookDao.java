package bookland.models;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;


@Transactional
public interface BookDao extends CrudRepository<Book, Long> {

	public Book findByTitle(String title);
	public Book findById(long id);
	public List<Book> findAll();
	public List<Book> findByOwnerID(long id);
	
}
