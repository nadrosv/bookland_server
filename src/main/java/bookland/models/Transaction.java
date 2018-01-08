package bookland.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@Column(name = "transaction_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "status")
	private int status;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "owner_id", referencedColumnName = "user_id")
	private Long ownerId;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private Long userId;

//	@Column(name = "ownername")
//	private String ownerName;
//
//	@Column(name = "username")
//	private String userName;

//	@Column(name = "title")
//	private String title;
//
//	@Column(name = "author")
//	private String author;

	@ManyToOne(targetEntity = Book.class)
	@JoinColumn(name = "book_id")
	private Long bookId;

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

	public Transaction(Long ownerId, Long userId, Long bookId) {
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
		this.ownerRate = 0;
		this.ownerRate = 0;
	}

	public Transaction(Long ownerId, Long userId, Long bookId, String ownerName, String userName, String title,
			String author) {
		super();
		this.status = 1;
		this.ownerId = ownerId;
		this.userId = userId;
		this.bookId = bookId;
//		this.title = title;
//		this.author = author;
//		this.ownerName = ownerName;
//		this.userName = userName;
		this.begDate = null;
		this.endDate = null;
		this.ownerSummary = null;
		this.userSummary = null;
		this.messaging = false;
		this.ownerRate = 0;
		this.ownerRate = 0;
	}

	public Transaction(Long id) {
		super();
		this.id = id;
	}

	public Transaction() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
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

//	public String getOwnerName() {
//		return ownerName;
//	}
//
//	public void setOwnerName(String ownerName) {
//		this.ownerName = ownerName;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}

//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public String getAuthor() {
//		return author;
//	}
//
//	public void setAuthor(String author) {
//		this.author = author;
//	}

}
