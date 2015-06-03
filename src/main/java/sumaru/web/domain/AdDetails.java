package sumaru.web.domain;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import sumaru.persistence.domain.Ad;

public class AdDetails {

	public static AdDetails toAdDetails(Ad ad) {
		AdDetails adDetails = new AdDetails();		
		BeanUtils.copyProperties(ad, adDetails);
		
		adDetails.user = UserDetails.toUserDetails(ad.getUser());		
		adDetails.category = CategoryDetails.toCategoryDetails(ad.getCategory());
		
		return adDetails;
	}

	private CategoryDetails category; 
	private Date date;	
	private String head;
	private long id;
	private String text;
	private UserDetails user;
	

	public CategoryDetails getCategory() {
		return category;
	}

	public Date getDate() {
		return date;
	}

	public String getHead() {
		return head;
	}

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public UserDetails getUser() {
		return user;
	}

	public void setCategory(CategoryDetails category) {
		this.category = category;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

}
