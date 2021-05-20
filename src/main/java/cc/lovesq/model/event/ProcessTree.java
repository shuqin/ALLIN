package cc.lovesq.model.event;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @Description TODO
 * @Date 2021/5/20 6:31 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class ProcessTree {

    @JsonProperty("pid")
    private String pid;

    @JsonProperty("pname")
    private String pname;

    @JsonProperty("cmdline")
    private String cmdline;

    @JsonProperty("ppid")
    private String ppid;

    @JsonProperty("ppname")
    private String ppname;
}
