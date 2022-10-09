package restproj.model;

import java.util.List;


public class CartDto {

	private String category;
	private List<Item> items = null;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}