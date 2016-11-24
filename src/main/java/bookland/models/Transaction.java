package bookland.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "status")
	private int status;

	@Column(name = "owner_id")
	private long ownerId;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "book_id")
	private long bookId;

	@Column(name = "beg_date")
	private Date begDate;

	@Column(name = "end_date")
	private Date endDate;
	
	@NotNull
	@Column(name = "owner_rate")
	private int ownerRate;

	@NotNull
	@Column(name = "user_rate")
	private int userRate;

	@Column(name = "owner_summary")
	private String ownerSummary;

	@Column(name = "user_summary")
	private String userSummary;

	@Column(name = "messaging")
	private boolean messaging;

	public Transaction(long ownerId, long userId, long bookId) {
		super();
		this.status = 1;
		this.ownerId = ownerId;
		this.userId = userId;
		this.bookId = bookId;
		this.begDate = null;
		this.endDate = null;
		this.ownerSummary = null;
		this.userSummary = null;
		this.messaging = false;
		this.ownerRate = 1;
		this.ownerRate = 1;
	}

	public Transaction(long id) {
		super();
		this.id = id;
	}

	public Transaction() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public Date getBegDate() {
		return begDate;
	}

	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getOwnerRate() {
		return ownerRate;
	}

	public void setOwnerRate(int ownerRate) {
		this.ownerRate = ownerRate;
	}

	public int getUserRate() {
		return userRate;
	}

	public void setUserRate(int userRate) {
		this.userRate = userRate;
	}

	public String getOwnerSummary() {
		return ownerSummary;
	}

	public void setOwnerSummary(String ownerSummary) {
		this.ownerSummary = ownerSummary;
	}

	public String getUserSummary() {
		return userSummary;
	}

	public void setUserSummary(String userSummary) {
		this.userSummary = userSummary;
	}

	public boolean isMessaging() {
		return messaging;
	}

	public void setMessaging(boolean messaging) {
		this.messaging = messaging;
	}

}
