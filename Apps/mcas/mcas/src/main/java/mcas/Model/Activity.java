package mcas.Model;

import java.util.List;

public class Activity {

	String ontClass;
	List<Person> persons;
	String location;

	public Activity(String ontClass, List<Person> persons, String location) {
		super();
		this.ontClass = ontClass;
		this.persons = persons;
		this.location = location;
	}

	public String getOntClass() {
		return ontClass;
	}

	public void setOntClass(String ontClass) {
		this.ontClass = ontClass;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
