package cc.lovesq.model.event;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @Description TODO
 * @Date 2021/5/20 6:32 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class Container {

    @JsonProperty("container_id")
    private String containerId;

    @JsonProperty("container_name")
    private String containerName;

    @JsonProperty("image_id")
    private String imageId;

    @JsonProperty("status")
    private String status;
}
