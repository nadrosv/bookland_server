package bookland.models;

public class NearUser {
	private Long id;
	private String username;
	private int bookCount;
	private double prefLocalLat;
	private double prefLocalLon;
	private double prefLocalRadius;

	public NearUser(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	public NearUser(Long id, String username, double lat, double lon, double radius) {
		super();
		this.id = id;
		this.username = username;
		this.prefLocalLat = lat;
		this.prefLocalLon = lon;
		this.prefLocalRadius = radius;
		this.bookCount = 0;
	}
	public NearUser(Long id, String username, int bookCount){
		super();
		this.id = id;
		this.username = username;
		this.bookCount = bookCount;
		this.prefLocalLat = this.prefLocalLon = this.prefLocalRadius = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public double getPrefLocalRadius() {
		return prefLocalRadius;
	}

	public void setPrefLocalRadius(double prefLocalRadius) {
		this.prefLocalRadius = prefLocalRadius;
	}

	public int getBookCount() {
		return bookCount;
	}

	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

}
