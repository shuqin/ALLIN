package cc.lovesq.model.event;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @Description TODO
 * @Date 2021/5/20 6:34 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class Socket {

    @SerializedName("src_ip")
    @JsonProperty("src_ip")
    private String srcIp;

    @SerializedName("src_port")
    @JsonProperty("src_port")
    private String srcPort;

    @SerializedName("type")
    @JsonProperty("type")
    private String type;

    @SerializedName("in_out")
    @JsonProperty("in_out")
    private String inOut;

    @SerializedName("dest_ip")
    @JsonProperty("dest_ip")
    private String destIp;

    @SerializedName("dest_port")
    @JsonProperty("dest_port")
    private String destPort;
}
