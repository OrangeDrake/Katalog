package sumaru.web.domain;

import org.springframework.beans.BeanUtils;

import sumaru.persistence.domain.User;

public class UserDetails {

	public static UserDetails toUserDetails(User user) {
		UserDetails userDetails = new UserDetails();
		BeanUtils.copyProperties(user, userDetails);
		
		return userDetails;
	}

	private long id;
	private String name;
	private String password;
	private String telephone;
	private String email;

	private String role;

	public UserDetails() {

	}

	public String getEmail() {
		return email;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
