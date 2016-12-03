package bookland.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "trans_id")
	private long transId;

	@Column(name = "sender_id")
	private long senderId;

	@Column(name = "send_time", columnDefinition="DATETIME")
	private Date sendTime;

	@Column(name = "message")
	private String message;

	public Message(long transId, long senderId, Date sendTime, String message) {
		super();
		this.transId = transId;
		this.senderId = senderId;
		this.sendTime = sendTime;
		this.message = message;
	}

	public Message(long id) {
		super();
		this.id = id;
	}
	
	public Message() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTransId() {
		return transId;
	}

	public void setTransId(long transId) {
		this.transId = transId;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
