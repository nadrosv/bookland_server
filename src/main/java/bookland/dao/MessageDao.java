package bookland.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import bookland.models.Message;

public interface MessageDao extends CrudRepository<Message, Long> {
	
	public List<Message> findByTransId(long id);

}
