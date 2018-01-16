package bookland.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "books")
public class Book {

	@OneToMany
	@JoinColumn(name = "book_id", referencedColumnName = "book_id")
	private List<Transaction> booksTransactions;
	
	@Id
	@Column(name = "book_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
//	@NotNull
//	@ManyToOne/*(fetch = FetchType.LAZY)*/
//	@JoinColumn(name = "owner_id")
////	@JsonBackReference
//	private User owner;

//	@NotNull
//	@ManyToOne/*(fetch = FetchType.LAZY)*/
//	@JoinColumn(name = "user_id")
////	@JsonBackReference
//	private User user;

	@Column(name = "owner_id")
	private Long ownerId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@NotNull
	private String title;

	@NotNull
	private String author;

	private Integer isbn;

	private String coverLink;

	// book condition 1-5 scale
	private Integer bookCondition;

	public Book(Long owner, String title, String author) {
		super();
//		this.owner = owner;
//		this.user = owner;
		this.ownerId = owner;
		this.userId = owner;
		this.title = title;
		this.author = author;
		this.coverLink = null;
		this.bookCondition = 0;
	}

	public Book(Long owner, String title, String author, Integer isbn, String coverLink, int condition) {
		super();
//		this.owner = owner;
//		this.user = owner;
		this.userId = owner;
		this.ownerId = owner;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.coverLink = coverLink;
		this.bookCondition = condition;
	}

//	public Book(long id) {
//		this.id = id;
//	}

	public Book() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	
//	User getOwner() {
//		return this.owner;
//	}
//	
//	void setOwner(User obj) {
//		this.owner = obj;
//	}
//	
//	User getUser() {
//		return this.user;
//	}
//	
//	void setUser(User obj) {
//		this.user = obj;
//	}
	
	
	
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