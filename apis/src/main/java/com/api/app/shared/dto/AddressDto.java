package com.api.app.shared.dto;

import java.io.Serializable;

public class AddressDto implements Serializable {
	private static final long serialVersionUID = 4934214225561608833L;
	private long id;
	private String addressId;
	private String city;
	private String country;
	private String streetName;
	private String postalCode;
	private String type;
	private UserDto user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	@Override
	public String toString() {
		return String.format(
				"Address [addressId=%s, city=%s, country=%s, streetName=%s, postalCode=%s, type=%s]",
				addressId, city, country, streetName, postalCode, type);
	}

	public AddressDto() {
	}

	public AddressDto(String addressId, String city, String country, String streetName, String postalCode,
			String type) {
		super();
		this.addressId = addressId;
		this.city = city;
		this.country = country;
		this.streetName = streetName;
		this.postalCode = postalCode;
		this.type = type;
	}

}
