package shared.util;

import org.junit.Test;
import zzz.study.sql.sqlparser.JsqlParser;

public class JsqlParserTest {

    @Test
    public void testSimpleSQLOnlyOneTableWithWhereEq() {
        String sql = "SELECT name, path, pid FROM processes WHERE on_disk = 0;";
        new JsqlParser().parse(sql);
    }

    @Test
    public void testSimpleSQLOnlyOneTableWithWhereIn() {
        String sql = "SELECT name, path, pid FROM processes WHERE on_disk = 0 and path in ('/bin/path');";
        new JsqlParser().parse(sql);
    }

    @Test
    public void testSimpleSQLOnlyOneTableWithWhereMore() {
        String sql = "SELECT name, path, pid FROM processes WHERE on_disk = 0 and path in ('/bin/path', '/etc/shadows') or name LIKE '%devel%' ;";
        new JsqlParser().parse(sql);
    }

    @Test
    public void testSimpleSQLParser() {
        String sql = "SELECT a,b,c FROM yara WHERE path='/bin/ls' AND sig_group='sig_group_1';";
        new JsqlParser().parse(sql);
    }

    @Test
    public void testSqlParse() {
        String sql = "SELECT * FROM container_events ctn, malicious_image img WHERE malicious_image.image_id=ctn.image_id";
        new JsqlParser().parse(sql);
    }
}
