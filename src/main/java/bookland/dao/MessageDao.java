package bookland.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import bookland.models.Message;

public interface MessageDao extends CrudRepository<Message, Long> {
	
	public List<Message> findByTransId(long id);
	
	@Query("select m from Message m where m.transId in :trans and m.senderId <> :userId and m.sendTime > :time")
	public List<Message> findNew(@Param("userId") long userId, @Param("trans") List<Long> transIds, @Param("time") Timestamp time);

}
