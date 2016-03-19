package cc.lovesq.query;

import java.util.Date;

public class CreativeQuery extends PagerQuery {
    
	private static final long serialVersionUID = 1L;

	private Long creativeId;

	private String title;
	
	private String submitter;

    private String content;
    
    private String comment;

    private Date gmtCreate;

    private Date gmtModified;

    public Long getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Long creativeId) {
        this.creativeId = creativeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}
    
}
