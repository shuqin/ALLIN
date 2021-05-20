package cc.lovesq.constants;

/**
 * @Description 事件类型
 * @Date 2021/5/20 10:25 下午
 * @Created by qinshu
 */
public enum EventType {

    bounceshell("bounceshell", "反弹shell"),
    localrights("localrights", "本地提权"),
    brutecrack("brutecrack", "暴力破解"),
    maliciouscmd("maliciouscmd", "恶意命令"),

    ;

    String type;
    String desc;

    EventType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static String getEventDesc(String type) {
        for (EventType et: EventType.values()) {
            if (et.getType().equals(type)) {
                return et.getDesc();
            }
        }
        return "NoMatch";
    }
}
