package ce0902a.gogetme.model;



public final class Location {

	private final int id;
	private final String firstLine;
	private final String secondLine;
	private final String city;
	private final String postcode;
	private final String description;
	private final String image;

	public Location(int id, String firstLine, String secondLine, String city,
			String postcode, String description, String image) {
		this.id = id;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.city = city;
		this.postcode = postcode;
		this.description = description;
		this.image = image;

	}

	public int getId() {
		return id;
	}

	public String getFirstLine() {
		return firstLine;
	}

	public String getSecondLine() {
		return secondLine;
	}

	public String getCity() {
		return city;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getDescription() {
		return description;
	}
	
	public String getImage() {
		return image;
	}
	

	@Override
	public String toString() {
		return this.firstLine +" "+ this.postcode;
	}
}
