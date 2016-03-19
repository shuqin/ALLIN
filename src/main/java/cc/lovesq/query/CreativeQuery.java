package cc.lovesq.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreativeQuery extends PagerQuery {

    private Long creativeId;
    private String title;
    private String submitter;
    private String content;
    private String comment;

}
