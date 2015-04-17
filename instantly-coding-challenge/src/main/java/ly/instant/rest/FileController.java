package ly.instant.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ly.instant.rest.domain.FileForRest;
import ly.instant.services.FileService;
import ly.instant.services.FileTraversalMethodEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/files")
public class FileController {
	private static Logger log = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileService fileService;

	/**
	 * Inspects the files inside a file path and returns information about the files. 
	 * 
	 * @param path
	 * @param display
	 * @param traversalMethod
	 * @return file information where the file order depends on the traversalMethod
	 */
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody Object getFileInformation(String path, String[] display, String traversalMethod) {
    	
    	List<String> displayString = display == null ? new ArrayList<>() : Arrays.asList(display);
    	
    	log.debug("path={}", path);
    	log.debug("display={}", displayString);
    	log.debug("traversalMethod={}", traversalMethod);
    	
    	return fileService.findFiles(path, FileTraversalMethodEnum.valueOf(traversalMethod))
    							.stream()
						    	.map(f -> {
						    		FileForRest ffr = new FileForRest();
						    		for(String s : displayString){
						    			ffr.addProperty(f, s);
						    		}
						    		return ffr;
						    		})
						    	.collect(Collectors.toList());
    	
    }

}
