package zzz.study.sql.calcite;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import shared.performance.ConsumerWrapper;
import shared.performance.PerformanceTestFramework;
import zzz.study.sql.calcite.pool.PrepareStatementPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 使用 Calcite 执行内存虚表计算
 * Created by qinshu on 2021/7/8
 */
public class ThreatSqlQuery {

    public static void main(String[]args) {

        try {

            String sql1 = "select f.hash from process_event as p inner join file as f on p.fname = f.fname where p.eventId = 'Docker-Detect-Event-1626236000' and f.eventId = 'Docker-Detect-Event-1626236000'";
            String sql2 = "select * from process_event as p inner join file as f on p.fname = f.fname inner join hash as h on f.hash = h.hash where p.eventId = 'Docker-Detect-Event-1626236000' and f.eventId = 'Docker-Detect-Event-1626236000' and h.hash in (?)";

            String sqlHash = "select * from hash as h where h.hash in (?)";
            String sqlInHash = "select * from process_event as p inner join file as f on p.fname = f.fname inner join hash as h on f.hash = h.hash where p.eventId = 'Docker-Detect-Event-1626236000' and f.eventId = 'Docker-Detect-Event-1626236000' and h.hash in ('89c85a9e1fee23a76c351a29527c9258')";

            String sqlIn = "select * from process_event as p inner join file as f on p.fname = f.fname where p.eventId = 'Docker-Detect-Event-1626236415' and f.eventId = 'Docker-Detect-Event-1626236415' and f.fname in ('aa1024.txt', 'aa1088.txt')";

            String sqlRegex = "select * from process_event as p inner join file as f on p.fname = f.fname where regex_match(p.cmd, 'sudo ls /root/.ssh/.*', 0) <> '' OR regex_match(p.cmd, 'sudo ls /etc/ssh/.*', 0) <> ''";
            //String sqlRegexMysql = "select * from process_event as p inner join file as f on p.fname = f.fname where p.cmd REGEXP 'sudo ls /root/.ssh/.*' OR p.cmd REGEXP 'sudo ls /etc/ssh/.*'";  // Unsupported

            //String sqlTupleEq = "select * from process_event as p , file f, hash h WHERE (p.fname, p.eventId, f.hash, f.eventId) = (f.fname, 'Docker-Detect-Event-1626236000', h.hash, 'Docker-Detect-Event-1626236000')"; // Unsupported
            String sqlTupleEqInstead = "select * from process_event as p , file f, hash h WHERE p.fname = f.fname AND f.hash = h.hash AND p.eventId = 'Docker-Detect-Event-1626236000' AND f.eventId = 'Docker-Detect-Event-1626236000'";

            String sqlJsonPath = "select * from process_event as p inner join file as f on p.fname = f.fname where p.eventId = 'Docker-Detect-Event-1626236415' and json_extract(f.content, '$.id') in (16,25)";

            String sqlJsonPath2 = "select * from process_event as p inner join file as f on p.fname = f.fname where p.eventId = 'Docker-Detect-Event-1626236415' and JSON_VALUE(f.content, '$.id') in (16,25)";

            String sqlNotEq = "select * from process_event as p , file f, hash h WHERE p.fname = f.fname AND p.eventId <> 'Docker-Detect-Event-1626236000' AND f.eventId = 'Docker-Detect-Event-1626236000'";

            String sqlLike = "select * from process_event as p inner join file as f on p.fname = f.fname where (p.cmd LIKE  '% ls /root/.ssh/%') OR (p.cmd LIKE '% ls /etc/ssh/%')";

            Map<Integer, Consumer> tests = new HashMap<>();
            tests.put(1, (x) -> testSingle(sql1));
            tests.put(2, (x) -> testSingle(sqlIn));
            tests.put(3, (x) -> testSingle(sqlInHash));
            tests.put(4, (x) -> {
                String sqlWithInnerJoinHash =  "select * from process_event as p inner join file as f on p.fname = f.fname inner join hash as h on f.hash = h.hash where p.eventId = 'Docker-Detect-Event-1626236000' and f.eventId = 'Docker-Detect-Event-1626236000'";
                testSingle(sqlWithInnerJoinHash);
            });
            tests.put(5, (x) -> testMulti(Lists.newArrayList(sql1, sql2)));
            tests.put(6, (x) -> testSingle(sqlRegex));
            //tests.put(7, (x) -> testSingle(sqlRegexMysql));
            //tests.put(8, (x) -> testSingle(sqlTupleEq));
            tests.put(9, (x) -> testSingle(sqlTupleEqInstead));
            tests.put(10, (x) -> testSingle(sqlJsonPath));
            tests.put(11, (x) -> testSingle(sqlJsonPath2));
            tests.put(12, (x) -> testSingle(sqlNotEq));
            tests.put(13, (x) -> testSingle(sqlLike));


            tests.get(6).accept(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testSingle(String sql) {

        PerformanceTestFramework.test(
                new ConsumerWrapper<>(sqlstr -> {

                    try {
                        RateLimiter rateLimiter = RateLimiter.create(30);
                        if (rateLimiter.tryAcquire(1, TimeUnit.SECONDS)) {
                            PreparedStatement st = PrepareStatementPool.borrow(sqlstr);
                            try {
                                ResultSet rs = PreparedStatementWrapper.execute(st, null);
                                PreparedStatementWrapper.logResult(rs);
                            } catch (Exception throwables) {
                                throwables.printStackTrace();
                            } finally {
                                PrepareStatementPool.returnObject(sqlstr, st);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }, sql), PerformanceTestFramework.SEQUENTIAL
        );

    }

    public static void testMulti(List<String> sqls) {
        PerformanceTestFramework.test(
                new ConsumerWrapper<>(sqlList -> {

                    try {
                        ResultSet rs = execute(sqls);
                        PreparedStatementWrapper.logResult(rs);
                    } catch (Exception throwables) {
                        throwables.printStackTrace();
                    }

                }, sqls), PerformanceTestFramework.SEQUENTIAL
        );
    }

    public static ResultSet execute(List<String> sqls) throws Exception {
        String sql1 = sqls.get(0);
        String sql2 = sqls.get(1);
        String sql3 = "";

        PreparedStatement p1 = PrepareStatementPool.borrow(sql1);
        PreparedStatement p2 = null;
        PreparedStatement p3 = null;
        ResultSet r2 = null;
        ResultSet r3 = null;

        try {
            ResultSet r1 = PreparedStatementWrapper.execute(p1, null);

            List<List<Object>> objs = PreparedStatementWrapper.getResult(r1);
            List<String> hashes = new ArrayList<>();
            for (List<Object> obj: objs) {
                for (Object o: obj) {
                    hashes.add(String.valueOf(o));
                }
            }

            p2 = PrepareStatementPool.borrow(sql2);
            String hashesParam = String.join("," , hashes);
            r2 = PreparedStatementWrapper.execute(p2, Arrays.asList(new PrepareStatementParamObject(1, hashesParam)));

            if (sqls.size() == 3) {
                sql3 = sqls.get(2);
                p3 = PrepareStatementPool.borrow(sql3);
                r3 = PreparedStatementWrapper.execute(p3, Arrays.asList(new PrepareStatementParamObject(1, hashesParam)));
                PreparedStatementWrapper.logResult(r3);
                return r3;
            }
            return r2;

        } finally {
            PrepareStatementPool.returnObject(sql1, p1);
            PrepareStatementPool.returnObject(sql2, p2);
            if (sql3 != null && p3 != null) {
                PrepareStatementPool.returnObject(sql3, p3);
            }
        }
    }

}
