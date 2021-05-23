package zzz.study.sql.sqlparser;

import com.alibaba.fastjson.JSON;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;

import java.io.StringReader;
import java.util.List;

import static shared.stream.ListStream.stream;

/**
 * @Description jsqlParser 解析
 * @Date 2021/5/19 2:49 下午
 * @Created by qinshu
 */
public class JsqlParser {

    public static void parse(String selectSql) {
        try {
            CCJSqlParserManager parserManager = new CCJSqlParserManager();
            Statement statement = parserManager.parse(new StringReader(selectSql));
            if (!(statement instanceof Select)) {
                return ;
            }

            SqlParserInfo spi = new SqlParserInfo();

            Select select = (Select) statement;
            PlainSelect ps = (PlainSelect) select.getSelectBody();
            TableInfo tableInfo = parseTableInfo(ps);

            List<SelectItem> selectItemList = ps.getSelectItems();
            List<String> columns = stream(selectItemList).map(JsqlParser::parseSelectColumnName);

            WhereInfo whereInfo = new WhereInfo();
            parseWhereInfo(ps.getWhere(), whereInfo);

            spi.addTableInfo(tableInfo);
            spi.setWhereInfo(whereInfo);
            spi.addColumns(columns);
            System.out.println(JSON.toJSONString(spi));
        } catch (Exception ex) {
            ex.printStackTrace();
            // log the exception
        }
    }

    private static String parseSelectColumnName(SelectItem si) {
        if (si instanceof SelectExpressionItem) {
            Column c = (Column) ((SelectExpressionItem) si).getExpression();
            return c.getColumnName();
        }
        return "";
    }

    private static TableInfo parseTableInfo(PlainSelect ps) {
        Table t = (Table) ps.getFromItem();
        String tableName = t.getName();
        String alias = tableName;
        if (t.getAlias() != null) {
            alias = t.getAlias().getName().trim();
        }
        return new TableInfo(tableName, alias);
    }

    private static void parseWhereInfo(Expression expr, WhereInfo whereInfo) {
        if (expr instanceof EqualsTo) {
            EqualsTo eq = (EqualsTo) expr;
            String left = getValue(eq.getLeftExpression());
            String right = getValue(eq.getRightExpression());
            Condition condition = new Condition(left, right);
            whereInfo.addCondition(condition);
            return ;
        }

        if (expr instanceof InExpression) {
            InExpression ie = (InExpression) expr;
            String left = getValue(ie.getLeftExpression());
            ItemsList right = ie.getRightItemsList();
            ExpressionList el = (ExpressionList) right;
            String values = String.join(",", stream(el.getExpressions()).map(JsqlParser::getValue));
            Condition condition = new Condition(left, values);
            whereInfo.addCondition(condition);
            return ;
        }

        if (expr instanceof LikeExpression) {
            LikeExpression le = (LikeExpression) expr;
            String left = getValue(le.getLeftExpression());
            String right = getValue(le.getRightExpression());
            Condition condition = new Condition(left, right);
            whereInfo.addCondition(condition);
            return ;
        }

        if (expr instanceof AndExpression) {
            AndExpression ae = (AndExpression) expr;
            Expression left = ae.getLeftExpression();
            Expression right = ae.getRightExpression();
            parseWhereInfo(left, whereInfo);
            parseWhereInfo(right, whereInfo);
            return ;
        }

        if (expr instanceof OrExpression) {
            OrExpression ae = (OrExpression) expr;
            Expression left = ae.getLeftExpression();
            Expression right = ae.getRightExpression();
            parseWhereInfo(left, whereInfo);
            parseWhereInfo(right, whereInfo);
            return ;
        }
    }

    private static String getValue(Expression expr) {
        if (expr instanceof Column) {
            return ((Column) expr).getColumnName();
        }
        if (expr instanceof StringValue) {
            return ((StringValue) expr).getValue();
        }
        if (expr instanceof LongValue) {
            return ((LongValue) expr).getStringValue();
        }
        return expr.toString();
    }
}
