package cc.lovesq.model.event;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("pid")
    @JsonProperty("pid")
    private String pid;

    @SerializedName("pname")
    @JsonProperty("pname")
    private String pname;

    @SerializedName("cmdline")
    @JsonProperty("cmdline")
    private String cmdline;

    @SerializedName("ppid")
    @JsonProperty("ppid")
    private String ppid;

    @SerializedName("ppname")
    @JsonProperty("ppname")
    private String ppname;
}
