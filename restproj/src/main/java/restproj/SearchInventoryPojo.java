package restproj;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SearchInventoryPojo {
	@Column(name="category")
	private String category;
	
	@Column(name="name")
	private String name;
	
	@Column(name="price")
	private double price;
	
	@Column(name="src")
	private String src;
	
	@Column(name="desc")
	private String desc;
	
	@Column(name="id")
	private String id;
}
