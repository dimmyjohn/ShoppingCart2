package restproj;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import restproj.common.Common;
import restproj.mapper.CartMapper;
import restproj.model.Address;
import restproj.model.Cart;
import restproj.model.CartDto;
import restproj.model.Item;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("cart")
public class CartService {
	private static final Logger logger = LogManager.getLogger(CartService.class);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CartDto> getSearchInventory() {

		logger.trace("We've just greeted the user!");
        logger.debug("We've just greeted the user!");
        logger.info("We've just greeted the user!");
        logger.warn("We've just greeted the user!");
        logger.error("We've just greeted the user!");
        logger.fatal("We've just greeted the user!");
		String url = Common.url;
		String username = Common.user;
		String password = Common.password;
		
		String query = "Select * from SearchInventory";
		Statement stmt;
		ResultSet rs;
		Connection conn;
		List<Cart> cart = new ArrayList<Cart>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				Cart c = new Cart();
				c.setCategory(rs.getString("category"));
				List<Item> il = new ArrayList<Item>();
				Item i = new Item();
				i.setDesc(rs.getString("desc"));
				i.setId(rs.getString("id"));
				i.setName(rs.getString("name"));
				i.setSrc(rs.getString("src"));
				i.setPrice(rs.getDouble("price"));
				il.add(i);
				c.setItems(il);
				cart.add(c);
			}
			rs.close();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			
		}

		List<CartDto> dto = new CartMapper().converToDto(cart);

		return dto;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/add")
	public boolean addCart(Item item)
			throws JSONException, FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		System.out.print(item.getId());
		String url = Common.url;
		String username = Common.user;
		String password = Common.password;
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, username, password);

		System.out.print("connected");
		String query = "insert into mycart values (" + item.getId() + ",'" + item.getName() + "'," + item.getPrice()
				+ "," + item.getQty() + ",'" + item.getDesc() + "','" + item.getSrc() + "')";
		Statement stmt = conn.createStatement();
		return stmt.execute(query);

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public int deleteCart(Item item)
			throws JSONException, FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		String url = Common.url;
		String username = Common.user;
		String password = Common.password;
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, username, password);

		System.out.print("connected");
		String query = "delete from mycart where id=" + item.getId();
		Statement stmt = conn.createStatement();

		int rs = stmt.executeUpdate(query);

		return rs;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update")
	public int updateCart(Item item)
			throws JSONException, FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		String url = Common.url;
		String username = Common.user;
		String password = Common.password;
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, username, password);

		System.out.print("connected");
		String query = "update mycart set quantity=" + item.getQty() + " where id=" + item.getId();
		Statement stmt = conn.createStatement();

		int rs = stmt.executeUpdate(query);
		return rs;
	}
	// 
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/location")
	public Address getAddress(@QueryParam("lat") String lat, @QueryParam("lng") String lng) {
		Address address = new Address();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=AIzaSyAixj9ox4jhDfIJ3yqDzztuU5ZCrdK6eNA");
		CloseableHttpResponse response;
		try {
			response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
                String result = EntityUtils.toString(entity);
                JSONObject obj = new JSONObject(result);
                JSONArray result1 = obj.getJSONArray("results");
                JSONObject result2 = result1.getJSONObject(0);
                address.setAddress(result2.getString("formatted_address"));
            }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}
}
