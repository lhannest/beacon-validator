package bio.knowledge.validator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	public static <T> List<T> asList(T... objects) {
		List<T> list = new ArrayList<T>();
		for (T object : objects) {
			list.add(object);
		}
		return list;
	}
	
	public static String getHost(String uri) {
		try {
			URI u = new URI(uri);
			String host = u.getHost();
			
			// If we're passing in a string that has already been converted into a host,
			// then host will be null.
			if (host != null) {
				return host;
			} else {
				return uri;
			}
			
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
