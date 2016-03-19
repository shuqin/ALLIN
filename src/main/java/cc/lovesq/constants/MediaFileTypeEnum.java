package cc.lovesq.constants;

public enum MediaFileTypeEnum {
	/**
	 * flv视频格式
	 */
	FLV("flv"),
	/**
	 * avi视频格式
	 */
	AVI("avi"),
	/**
	 * mp4视频格式
	 */
	MP4("mp4"),
	/**
	 * swf视频格式
	 */
	SWF("swf"),
	/**
	 * jpg图片格式
	 */
	JPG("jpg"),
	/**
	 * png图片格式
	 */
	PNG("png"),
	/**
	 * gif图片格式
	 */
	GIF("gif");
	
	private String	value;
	
	MediaFileTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static MediaFileTypeEnum getByValue(String value) {
		for (MediaFileTypeEnum mediaFileType : MediaFileTypeEnum.values()) {
			if (mediaFileType.getValue().equals(value)) {
				return mediaFileType;
			}
		}
		return null;
	}

}
