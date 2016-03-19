package cc.lovesq.model.event;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @Description 容器对象
 * @Date 2021/5/20 6:32 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class Container {

    @SerializedName("container_id")
    @JsonProperty("container_id")
    private String containerId;

    @SerializedName("container_name")
    @JsonProperty("container_name")
    private String containerName;

    @SerializedName("image_id")
    @JsonProperty("image_id")
    private String imageId;

    @SerializedName("status")
    @JsonProperty("status")
    private String status;
}
