package bookland.models;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private Set<Book> ownedBooks = new HashSet<Book>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Book> usedBooks = new HashSet<Book>();
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private Set<Transaction> ownersTransactions = new HashSet<Transaction>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Transaction> usersTransactions = new HashSet<Transaction>();
	
	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private Set<Message> messages = new HashSet<Message>();
	
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;

	@NotNull
	private String email;

	@NotNull
	private String username;

	@NotNull
	private String password;

	@Column(name = "rate_pos")
	private int ratePos;

	@Column(name = "rate_neg")
	private int rateNeg;

	private int reputation;

	@Column(name = "last_update")
	private Timestamp lastUpdate;

	@Column(name = "pref_local_radius")
	private double prefLocalRadius;

	@Column(name = "pref_local_lat")
	private double prefLocalLat;

	@Column(name = "pref_local_lon")
	private double prefLocalLon;

	@Column(name = "pref_lend_time")
	@NotNull
	private int prefLendTime;

	@Column(name = "book_count")
	@NotNull
	private int bookCount;

	private String aboutUser;

	// private boolean banned;
	// private int prefLendTime;
	// private double prefSearchRadius;
	// private double prefSearchLat;
	// private double prefSearchLon;

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	public User(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.lastUpdate = new Timestamp(System.currentTimeMillis());
		this.bookCount = 0;

		this.rateNeg = 0;
		this.ratePos = 0;
		this.reputation = -1;

		this.prefLocalLat = 0;
		this.prefLocalLon = 0;
		this.prefLocalRadius = 0;
		this.prefLendTime = 30;
		this.aboutUser = null;
	}

	public Long getId() {
		return id;
	}

	public void vote(int rate) {
		if (rate != 0) {
			setRatePos(getRatePos() + 1);
		} else {
			setRateNeg(getRateNeg() + 1);
		}
		
		if (getRatePos() != 0 && getRateNeg() != 0) {
			setReputation((100 * ratePos) / (ratePos + rateNeg));
		}else if(getRatePos() == 0){
			setReputation(0);
		}
	}

	public void setId(Long value) {
		this.id = value;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String value) {
		this.username = value;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRatePos() {
		return ratePos;
	}

	public void setRatePos(int ratePos) {
		this.ratePos = ratePos;
	}

	public int getRateNeg() {
		return rateNeg;
	}

	public void setRateNeg(int rateNeg) {
		this.rateNeg = rateNeg;
	}

	public double getPrefLocalRadius() {
		return prefLocalRadius;
	}

	public void setPrefLocalRadius(double prefLocalRadius) {
		this.prefLocalRadius = prefLocalRadius;
	}

	public double getPrefLocalLat() {
		return prefLocalLat;
	}

	public void setPrefLocalLat(double prefLocalLat) {
		this.prefLocalLat = prefLocalLat;
	}

	public double getPrefLocalLon() {
		return prefLocalLon;
	}

	public void setPrefLocalLon(double prefLocalLon) {
		this.prefLocalLon = prefLocalLon;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getPrefLendTime() {
		return prefLendTime;
	}

	public void setPrefLendTime(int prefLendTime) {
		this.prefLendTime = prefLendTime;
	}

	public int getBookCount() {
		return bookCount;
	}

	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

	public String getAboutUser() {
		return aboutUser;
	}

	public void setAboutUser(String aboutUser) {
		this.aboutUser = aboutUser;
	}

	public int getReputation() {
		return reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

}
