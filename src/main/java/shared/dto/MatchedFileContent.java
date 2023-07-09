package shared.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 命中文件内容
 * Created by qinshu on 2021/12/31
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class MatchedFileContent {

    private long startLine;

    private String partContent;

    public MatchedFileContent(long startLine, String partContent) {
        this.startLine = startLine;
        this.partContent = partContent;
    }

}
