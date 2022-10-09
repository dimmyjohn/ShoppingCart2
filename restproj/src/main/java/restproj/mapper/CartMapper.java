package restproj.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import restproj.model.Cart;
import restproj.model.CartDto;

public class CartMapper {
	public List<CartDto> converToDto(List<Cart> c) {
		HashMap<String, CartDto> cartMap = new HashMap<String, CartDto>();
		
		
		
		for (int i = 0; i < c.size(); i++) {
			if (cartMap.containsKey(c.get(i).getCategory())) {
				CartDto selected = cartMap.get(c.get(i).getCategory());
				selected.getItems().add(c.get(i).getItems().get(0));
			} else {
				CartDto newDto = new CartDto();
	        	newDto.setCategory(c.get(i).getCategory());
	        	newDto.setItems(c.get(i).getItems());
	        	cartMap.put(c.get(i).getCategory(), newDto);
			}
		}
		
		Collection<CartDto> values = cartMap.values();
		ArrayList<CartDto> cartDto = new ArrayList<>(values);
		return cartDto;
	}
}
