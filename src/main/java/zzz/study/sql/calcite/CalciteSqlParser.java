package zzz.study.sql.calcite;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

/**
 * TODO
 * Created by qinshu on 2021/7/10
 */
public class CalciteSqlParser {

    public static void main(String[] args) {

        SqlParser.Config config = SqlParser.Config.DEFAULT.withLex(Lex.MYSQL);

        String sql1 = "select * from process_event as p inner join file as f on p.fname = f.fname where p.eventId = 'Docker-Detect-Event-1626236000' and f.eventId = 'Docker-Detect-Event-1626236000'";
        String sql2 = "select * from process_event as p , file f, hash h WHERE (p.fname, p.eventId, f.hash, f.eventId) = (f.fname, 'Docker-Detect-Event-1626236000', h.hash, 'Docker-Detect-Event-1626236000')";

        SqlParser sqlParser = SqlParser
                .create(sql2, config);
        SqlNode sqlNode = null;
        try {
            sqlNode = sqlParser.parseStmt();
            System.out.println(sqlNode);
        } catch (SqlParseException e) {
            throw new RuntimeException("", e);
        }

    }
}
