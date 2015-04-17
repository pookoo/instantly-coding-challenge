package ly.instant.rest.domain;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

/**
 * 
 * Holds the file information that is sent to the client.
 *
 */
public class FileForRest {
	TreeMap<String, String> fileProperties = new TreeMap<>();

	public TreeMap<String, String> getFileProperties() {
		return fileProperties;
	}
	
	public void addProperty(File f, String property){
		switch (property) {
		case "name":
			fileProperties.put("name", f.getName());
			break;
		case "size":
			fileProperties.put("size", f.length() + " bytes");
			break;
		case "date_modified":
			fileProperties.put("date_modified", new SimpleDateFormat().format(new Date(f.lastModified())));
			break;
		}
	}
}
