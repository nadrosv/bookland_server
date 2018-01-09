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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "messages")
public class Message {

	@Id
	@Column(name = "message_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne/*(fetch = FetchType.LAZY)*/
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User sender;
	
	@ManyToOne/*(fetch = FetchType.LAZY)*/
	@JoinColumn(name = "transaction_id")
	@JsonBackReference
	private Transaction transaction;

//	@Column(name = "trans_id")
//	private long transId;

//	@Column(name = "sender_id")
//	private long senderId;
	
	@Column(name = "send_time")
	private Timestamp sendTime;

	@Column(name = "message")
	private String message;

	public Message(long transId, long senderId, Timestamp sendTime, String message) {
		super();
//		this.transId = transId;
//		this.senderId = senderId;
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

//	public long getTransId() {
//		return transId;
//	}
//
//	public void setTransId(long transId) {
//		this.transId = transId;
//	}
//
//	public long getSenderId() {
//		return senderId;
//	}
//
//	public void setSenderId(long senderId) {
//		this.senderId = senderId;
//	}

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
