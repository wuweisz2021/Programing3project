package com.example.login;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "user")
public class User {
	@Id
	@Column(nullable = false, length = 4)
	@NotBlank(message = "Please enter your staff ID")
	@Pattern(regexp = "^\\d{4}$", message = "please enter exactly 4 number")
	private String id;

	@Column(nullable = false, length = 20)
	private String userName;

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = false)
	private String role;

	@Column(nullable = false)
	private String password;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@CreationTimestamp
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@UpdateTimestamp
	private Date updatedAt;
//@JoinColumn(name = "user_id", referencedColumnName = "id")

	@OneToMany(mappedBy="user")
	private List<Order> user_orders;


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		checkPassword();// check
	}

	@Transient
	private String confirmPassword;

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
		checkPassword();// check
	}

	private void checkPassword() {
		if (this.password == null || this.confirmPassword == null) {
			return;
		} else if (!this.password.equals(confirmPassword)) {
			this.confirmPassword = null;

		}
	}

	@Transient
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public List<Order> getUser_orders() {
		return user_orders;
	}

	public void setUser_orders(List<Order> user_orders) {
		this.user_orders = user_orders;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/*
	 * @PrePersist protected void onCreate() { createdAt = new Date(); }
	 * 
	 * @PreUpdate protected void onUpdate() { updatedAt = new Date(); }
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", email=" + email + ", role=" + role + ", password="
				+ password + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", user_orders=" + user_orders
				+ ", confirmPassword=" + confirmPassword + "]";
	}



}
