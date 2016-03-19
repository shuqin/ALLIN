package zzz.study.javatech.JDBClearn;

/**
 * DBRecord: 与数据库中一条行记录相对应的数据对象
 */
public class DBRecord {

    // 将数据库中检索到的一条记录转化为字符串并存储
    private String[] datas;

    public DBRecord(String[] datas) {
        this.datas = datas;
    }

    // 返回数据库中一条记录的第 index 个数据项
    public String getDataTerm(int index) {
        if (index < 0 || index > datas.length - 1) {
            throw new ArrayIndexOutOfBoundsException("数组下标越界!");
        }
        return datas[index];
    }

    // 返回数据库一条记录所含的数据项的数目
    public int dataTermNum() {
        return datas.length;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[DBRecord]: ");
        for (String s : datas) {
            sb.append(s);
            sb.append(' ');
        }
        sb.append('\n');
        return sb.toString();
    }

}
