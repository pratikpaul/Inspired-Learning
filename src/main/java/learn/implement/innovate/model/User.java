package learn.implement.innovate.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;

	@JsonProperty("id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonProperty(value = "first-name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty(value = "last-name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty(value = "user-name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty(value = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String passwordÍ) {
		this.password = passwordÍ;
	}
	
	@Override
	public String toString() {
		return new StringBuffer(" userID : ").append(this.id)
				.append(" firstName : ").append(this.firstName)
				.append(" lastName : ").append(this.lastName).append(" userName : ")
				.append(this.userName).append(" password : ")
				.append(this.password).toString();
	}
	
	/*public String toString(){
		return "User [userID=" + id + ", firstName="
			      + firstName + ", lastName=" + lastName + ", userName="
			      + userName + ", password=" + password + "]";
	}*/
}
