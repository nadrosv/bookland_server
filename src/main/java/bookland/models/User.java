package bookland.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

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

	@Column(name = "pref_local_radius")
	private double prefLocalRadius;

	@Column(name = "pref_local_lat")
	private double prefLocalLat;

	@Column(name = "pref_local_lon")
	private double prefLocalLon;

	// private boolean banned;
	// private int bookCount;
	// private int prefLendTime;
	// private double prefSearchRadius;
	// private double prefSearchLat;
	// private double prefSearchLon;
	// private String aboutUser;

	public User() {
	}

	public User(long id) {
		this.id = id;
	}

	public User(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long value) {
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

}
