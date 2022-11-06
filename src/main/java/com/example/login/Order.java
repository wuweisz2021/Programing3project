package com.example.login;

import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orderdata")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long OrderId;

	@Column(nullable = false, length = 20)
	private String payment;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "parks_ordered", joinColumns = @JoinColumn(name = "OrderId", referencedColumnName = "OrderId"), inverseJoinColumns = @JoinColumn(name = "parkId", referencedColumnName = "parkId"))
	private List<Park> parks;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getOrderId() {
		return OrderId;
	}

	public void setOrderId(Long orderId) {
		OrderId = orderId;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public List<Park> getParks() {
		return parks;
	}

	public void setParks(List<Park> parks) {
		this.parks = parks;
	}

	@Override
	public String toString() {
		return "Order [OrderId=" + OrderId + ", payment=" + payment + ", user=" + user.getId() + "]";
	}

}
