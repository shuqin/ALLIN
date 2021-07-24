package zzz.study.sql.calcite;

import zzz.study.foundations.iolearn.RWTool;

import java.io.BufferedReader;
import java.util.*;

import static shared.performance.PerformanceTestFramework.DATA_SIZE;

/**
 * 内存数据
 * Created by qinshu on 2021/7/8
 */
public class MemoryData {

    private static Map<String, List<List<String>>> data = new HashMap<>();

    private static Random random = new Random(System.currentTimeMillis());

    private static List<String> files = new ArrayList<>();

    static {
        initSensitiveDirs();
        initTableData();
    }

    private static void initSensitiveDirs() {
        files = RWTool.getLines("/Users/qinshu/workspace/ALLIN/src/main/resources/sensitive_dirs");
    }

    private static void initTableData() {
        String eventId = "Docker-Detect-Event-1626236415";

        List<List<String>> hash = new ArrayList<>();
        try {
            BufferedReader br = RWTool.getTextFileReader("/Users/qinshu/workspace/ALLIN/src/main/resources/a.txt");
            List<String> lines = new ArrayList<>();
            String line = "";
            while ((line = br.readLine()) != null) {
                line = line.trim();
                lines.add(line);
                hash.add(Arrays.asList(line));
            }

            data.put("hash", hash);

        List<List<String>> processEvents = new ArrayList<>();
        for (int i=0; i < DATA_SIZE; i++) {
            String isSudo = random.nextBoolean() ? "sudo " : "";
            String file = files.get(random.nextInt(files.size()));
            processEvents.add(Arrays.asList(""+(1000+i), "ls", isSudo + "ls " + file,  "aa"+(1000+random.nextInt(DATA_SIZE))+".txt", eventId));
        }
        processEvents.add(Arrays.asList("2000", "ls", "ls /etc/passwd", "aa2000.txt", "Docker-Detect-Event-1626236000"));
        data.put("process_event", processEvents);

        List<List<String>> files = new ArrayList<>();
        for (int j=0; j<DATA_SIZE; j++) {
            int index = random.nextInt(DATA_SIZE);
            files.add(Arrays.asList("aa"+(1000+index)+".txt", "/bin/sh/aa"+(1000+index)+".txt",  "{\"id\":"  + index + "}", eventId, lines.get(random.nextInt(1000))));
        }
        files.add(Arrays.asList("aa2000.txt", "/bin/sh/aa2000.txt", "Docker-Detect-Event-1626236000", "{\"id\":2000}", "89c85a9e1fee23a76c351a29527c9258"));
        data.put("file", files);

        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

    public static List<List<String>> getData(String tablename) {
        return data.get(tablename);
    }
}
