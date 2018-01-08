package bookland.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@Column(name = "book_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
//	@NotNull
	@ManyToOne/*(fetch = FetchType.LAZY)*/
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User owner;
//	@ManyToOne(targetEntity = User.class)
//	@JoinColumn(name = "owner_id", referencedColumnName = "user_id")
//	private Long ownerId;

//	@NotNull
	@ManyToOne/*(fetch = FetchType.LAZY)*/
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;
//	@ManyToOne(targetEntity = User.class)
//	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
//	private Long userId;

	@NotNull
	private String title;

	@NotNull
	private String author;

	private Integer isbn;

	private String coverLink;

	// book condition 1-5 scale
	private Integer bookCondition;

	public Book(Long ownerId, String title, String author) {
//		this.ownerId = ownerId;
//		this.userId = ownerId;
		this.title = title;
		this.author = author;
		this.coverLink = null;
		this.bookCondition = 0;
	}

	public Book(Long ownerId, String title, String author, Integer isbn, String coverLink, int condition) {
		super();
//		this.ownerId = ownerId;
//		this.userId = ownerId;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.coverLink = coverLink;
		this.bookCondition = condition;
	}

	public Book(Long id) {
		this.id = id;
	}

	public Book() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	User getOwner() {
		return this.owner;
	}
	
	void setOwner(User obj) {
		this.owner = obj;
	}
	
	User getUser() {
		return this.user;
	}
	
	void setUser(User obj) {
		this.user = obj;
	}
	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

//	public Long getOwnerId() {
//		return ownerId;
//	}
//
//	public void setOwnerId(Long ownerId) {
//		this.ownerId = ownerId;
//	}
//
//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}

	public Integer getIsbn() {
		return isbn;
	}

	public void setIsbn(Integer isbn) {
		this.isbn = isbn;
	}

	public String getCoverLink() {
		return coverLink;
	}

	public void setCoverLink(String coverLink) {
		this.coverLink = coverLink;
	}

	public Integer getCondition() {
		return bookCondition;
	}

	public void setCondition(Integer condition) {
		this.bookCondition = condition;
	}

}