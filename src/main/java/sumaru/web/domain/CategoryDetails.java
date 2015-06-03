package sumaru.web.domain;

import org.springframework.beans.BeanUtils;

import sumaru.persistence.domain.Category;

public class CategoryDetails {

	public static CategoryDetails toCategoryDetails(Category category) {
		CategoryDetails categoryDetails = new CategoryDetails();
		BeanUtils.copyProperties(category, categoryDetails);
		
		return categoryDetails;
	}
	
	private int id;
	private String element;

	private int count;

	public int getCount() {
		return count;
	}

	public String getElement() {
		return element;
	}

	public int getId() {
		return id;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public void setId(int id) {
		this.id = id;
	}

}
