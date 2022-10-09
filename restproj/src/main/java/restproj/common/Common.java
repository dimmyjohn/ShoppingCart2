package restproj.common;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Common {
	public static String url;
	public static String user;
	public static String password;
	static {
		System.out.print("static initialized");
		try (InputStream inputStream = Common.class
				.getClassLoader().getResourceAsStream("config.properties")) {
			Properties prop = new Properties();
            prop.load(inputStream);
            url = prop.getProperty("db.url");
			user = prop.getProperty("db.user");
			password = prop.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
