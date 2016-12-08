package bookland.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import bookland.models.Transaction;

@Transactional
public interface TransactionDao extends CrudRepository<Transaction, Long> {
	
	public Transaction findById(long id);
	public List<Transaction> findByOwnerId(long id);
	
	@Query("select t from Transaction t where (t.ownerId = :userid or t.userId = :userid) and t.status <> 5 order by t.id desc")
	public List<Transaction> findActive(@Param("userid") long userId);
	
	@Query("select t from Transaction t where (t.ownerId = :userid or t.userId = :userid) and t.status = 5")
	public List<Transaction> findArchived(@Param("userid") long userId);
	
	public Transaction findByUserIdAndBookId(long userId, long bookId);
}
