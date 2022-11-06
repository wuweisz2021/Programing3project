package com.example.login;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "park")
public class Park {
	@Id
	@Column(nullable = false, length = 5)
	@NotBlank(message = "Please enter your staff ID")
	@Pattern(regexp = "^\\d{5}$", message = "please enter exactly 5 number")
	private String parkId;

	@Column(nullable = false, length = 40)
	private String parkName;

	@Column(nullable = false)

	@Min(10)
	@Max(200)
	private Float price;

	@Column(nullable = false)
	private String address;

	@Column(nullable = true)
	private String introduction;

	@Column(nullable = true)
	private String image;

	@Column(nullable = true)
	private String pulished;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column(nullable = false, updatable = false)
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date updatedAt;

	@ManyToOne
	@JoinColumn(name = "OrderId")
	private Order order;

	@Transient
	public String getImagePath() {
		if (image == null || parkId == null)
			return null;

		return "/park-photos/" + parkId + "/" + image;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPulished() {
		return pulished;
	}

	public void setPulished(String pulished) {
		this.pulished = pulished;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Park [parkId=" + parkId + ", parkName=" + parkName + ", price=" + price + ", address=" + address
				+ ", introduction=" + introduction + ", image=" + image + ", pulished=" + pulished + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", order=" + order + "]";
	}

	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = new Date();
	}

}
