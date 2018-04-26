package mcas.Model;

public class Service {

	String id;
	String name;
	int preference;

	public Service(String id, String name, int preference) {
		super();
		this.id = id;
		this.name = name;
		this.preference = preference;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPreference() {
		return preference;
	}

	public void setPreference(int preference) {
		this.preference = preference;
	}

}
