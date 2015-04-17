package ly.instant.services;

import java.io.File;
import java.util.List;

/**
 * 
 * Retrieves files from file system.
 *
 */
public interface FileService {

	List<File> findFilesDepthFirst(String path);

	List<File> findFilesBreadthFirst(String path);
	
	List<File> findFiles(String path, FileTraversalMethodEnum traversalMethod);

}
