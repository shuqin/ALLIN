package cc.lovesq.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class CreativeDO {

  private Long creativeId;

  private String title;

  private String content;

  private String comment;

  private String submitter;

  private Date gmtCreate;

  private Date gmtModified;

}
