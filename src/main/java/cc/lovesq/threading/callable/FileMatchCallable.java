package cc.lovesq.threading.callable;

import shared.multitasks.customized.TaskInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class FileMatchCallable implements Callable<List<File>>, TaskInfo {

    private File directory;   // 要匹配的非目录文件
    private Pattern pattern;   // 要匹配的文件名模式

    public FileMatchCallable(File directory, Pattern pattern) {
        this.directory = directory;
        this.pattern = pattern;
    }

    public String desc() {
        return "[FileSearchTask](" + "dir: " + directory.getAbsolutePath() + ", "
                + "pattern: " + pattern + ")";
    }

    public List<File> call() throws Exception {
        List<File> result = new ArrayList<File>();
        if (directory.isFile()) {
            boolean matched = pattern.matcher(directory.getName()).matches();
            if (matched) {
                result.add(directory);
            }
        }
        return result;
    }

}
