package bookland.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import bookland.models.Book;

@Transactional
public interface BookDao extends CrudRepository<Book, Long> {

	public List<Book> findByTitleContaining(String title);

	public List<Book> findByAuthorContaining(String author);


	@Query("select b from Book b where (b.title like %:keyword% or b.author like %:keyword%) and b.ownerId=b.userId and b.ownerId in :usersId and b.ownerId <> :id")
	public List<Book> findNearByKeyWord(@Param("id") long id, @Param("usersId") List<Long> usersId,
			@Param("keyword") String keyword);

	public Book findByTitle(String title);

	public Book findById(long id);

	public List<Book> findAll();

	public List<Book> findByOwnerId(long id);

}
