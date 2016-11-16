package bookland.dao;

import org.springframework.data.repository.CrudRepository;

import bookland.models.Message;

public interface MessageDao extends CrudRepository<Message, Long> {

}
