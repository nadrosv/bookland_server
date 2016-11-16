package bookland.dao;

import org.springframework.data.repository.CrudRepository;

import bookland.models.Transaction;

public interface TransactionDao extends CrudRepository<Transaction, Long> {
	public Transaction findById(long id);
}
