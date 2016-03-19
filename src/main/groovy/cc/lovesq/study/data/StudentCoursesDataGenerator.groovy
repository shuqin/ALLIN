package cc.lovesq.study.data

class StudentCoursesDataGenerator {

    private static final STUDENT_PREFIX = "STU";
    private static final TEACHER_PREFIX = "TCH";
    private static final ROOM_PREFIX = "ROOM";
    private static final COURSE_PREFIX = "CRE";

    static Random random = new Random(47);

    static int THREE_MONTH = 3 * 60 * 60 * 24 * 30;

    static void main(args) {


        def filePath = "./sql/stu_courses.sql"
        File file = new File(filePath)
        def batchSize = 50

        file.withWriter { writer ->
            for (int i = 0; i < 8000000 / batchSize; i++) {

                def insertSql = "insert into student_courses(s_id, t_id, room, c_id, c_time) values "

                for (int j = 0; j < batchSize; j++) {
                    def sId = STUDENT_PREFIX + "_" + random.nextInt(40000)
                    def tId = TEACHER_PREFIX + random.nextInt(100)
                    def room = ROOM_PREFIX + random.nextInt(50)
                    def cId = COURSE_PREFIX + random.nextInt(60)
                    def cTime = Math.floor((System.currentTimeMillis() - random.nextInt(THREE_MONTH)) / 1000)
                    insertSql += "('$sId', '$tId', '$room', '$cId', $cTime),"
                }
                insertSql = insertSql.substring(0, insertSql.length() - 1) + ";\n"


                //print(insertSql)
                writer.write(insertSql)
            }
        }

    }
}
