package ly.instant.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("fileService")
public class FileServiceImpl implements FileService {

	@Override
	public List<File> findFiles(String path, FileTraversalMethodEnum traversalMethod) {
		
		switch (traversalMethod) {
		case BREADTH_FIRST:
			return findFilesBreadthFirst(path);
		case DEPTH_FIRST:
			return findFilesDepthFirst(path);
		default:
			return new ArrayList<File>();
		}
	}
	
	@Override
	public List<File> findFilesDepthFirst(String path){
		List<File> files = new ArrayList<File>();
		List<DiscoverableFile> queue = new ArrayList<>();
		
		DiscoverableFile f = new DiscoverableFile(path);
		
		if(f.exists()){
			queue.add(f);
		} else {
			return files;
		}
		
		while(queue.size() > 0){
			int lastElement = queue.size()-1;
			f = queue.get(lastElement);
			queue.remove(lastElement);
			
			if(!f.isDiscovered()) {
				f.setDiscovered(true);
				files.add(f);

				if(f.isDirectory()) {
					for(DiscoverableFile kid : this.toList( f.listFiles() )) {
						queue.add(kid);
					}
				}
			}
			
		}
		
		return files;
	}
	
	@Override
	public List<File> findFilesBreadthFirst(String path){
		List<File> files = new ArrayList<File>();
		List<DiscoverableFile> queue = new ArrayList<>();
		
		DiscoverableFile f = new DiscoverableFile(path);
		
		if(f.exists()){
			files.add(f);
			queue.add(f);
			f.setDiscovered(true);
		} else {
			return files;
		}
		
		while(queue.size() > 0){
			f = queue.get(0);
			queue.remove(0);
			
			if(f.isDirectory()) {
				for(DiscoverableFile kid : this.toList( f.listFiles() )) {
					if(!kid.isDiscovered()){
						files.add(kid);
						queue.add(kid);
						kid.setDiscovered(true);
					}
				}
			}
		}
		
		return files;
	}
	
	@SuppressWarnings("serial")
	private class DiscoverableFile extends File{
		
		private boolean isDiscovered;

		public DiscoverableFile(String pathname) {
			super(pathname);
		}
		public DiscoverableFile(File f) {
			super(f.getPath());
		}

		public boolean isDiscovered() {
			return isDiscovered;
		}

		public void setDiscovered(boolean isDiscovered) {
			this.isDiscovered = isDiscovered;
		}
	}
	
	private List<DiscoverableFile> toList(File[] files){
		List<DiscoverableFile> discFiles = new ArrayList<>();
		
		for(File f : files){
			discFiles.add( new DiscoverableFile(f) );
		}
		
		return discFiles;
	}
}
