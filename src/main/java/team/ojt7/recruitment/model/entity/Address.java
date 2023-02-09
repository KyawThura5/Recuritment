package team.ojt7.recruitment.model.entity;

import java.io.Serializable;
import java.util.*;

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
	private String state;
    private String township;
    private String city;
    private String street;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTownship() {
		return township;
	}
	public void setTownship(String township) {
		this.township = township;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	@Override
	public int hashCode() {
		return Objects.hash(city, state, street, township);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(city, other.city) && Objects.equals(state, other.state)
				&& Objects.equals(street, other.street) && Objects.equals(township, other.township);
	}

    
}