package cc.lovesq.threading.callable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import cc.lovesq.threading.customized.TaskInfo;

public class FileExtractCallable implements Callable<List<File>>, TaskInfo {

	private File directory;
	
	public FileExtractCallable(File directory) {
		this.directory = directory;
	}

	public String desc() {
		return "[FileExtractTask](" + "dir: " + directory + ")";
	}

	public List<File> call() throws Exception {
		List<File> result = new ArrayList<File>();
		extractFiles(directory, result);
		return result;
	}
	
    private void extractFiles(File dir, List<File> extractedFiles) {
    	if (dir.isFile()) {
    		extractedFiles.add(dir);
    	}
    	else if (dir.isDirectory()) {
    		File[] files = dir.listFiles();
    		for (File file: files) {
    			extractFiles(file, extractedFiles);
    		}
    	}
    }
}
