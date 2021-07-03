package zzz.study.javatech.JDBClearn;

public class CourseChosenTemplate implements Template {

    private String text =
            "$1 ,  你好！ 你所选修的课程是 $2, 共 $3 学时， 在 $4 上, 请勿错过。 ";

    public String getText() {
        return text;
    }

}
