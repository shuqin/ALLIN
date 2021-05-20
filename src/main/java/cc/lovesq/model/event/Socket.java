package cc.lovesq.model.event;

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

    @JsonProperty("src_ip")
    private String srcIp;

    @JsonProperty("src_port")
    private String srcPort;

    @JsonProperty("type")
    private String type;

    @JsonProperty("in_out")
    private String inOut;

    @JsonProperty("dest_ip")
    private String destIp;

    @JsonProperty("dest_port")
    private String destPort;
}
