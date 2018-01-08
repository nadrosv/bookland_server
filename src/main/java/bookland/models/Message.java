package bookland.models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {

	@Id
	@Column(name = "message_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(targetEntity = Transaction.class)
	@JoinColumn(name = "transaction_id")
	private Long transId;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "sender_id", referencedColumnName = "user_id")
	private Long senderId;
	
	@Column(name = "send_time")
	private Timestamp sendTime;

	@Column(name = "message")
	private String message;

	public Message(Long transId, Long senderId, Timestamp sendTime, String message) {
		super();
		this.transId = transId;
		this.senderId = senderId;
		this.sendTime = sendTime;
		this.message = message;
	}

	public Message(Long id) {
		super();
		this.id = id;
	}
	
	public Message() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
