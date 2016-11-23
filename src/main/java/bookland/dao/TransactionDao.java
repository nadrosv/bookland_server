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
	
	@Query("select t from Transaction t where t.ownerId = :userid or t.userId = :userid")
	public List<Transaction> findByUser(@Param("userid") long userId);
}
