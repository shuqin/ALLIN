package cc.lovesq.model;

/**
 * @Description 单词计数
 * @Date 2021/5/23 4:46 下午
 * @Created by qinshu
 */
public class Word {

    private String word;
    private int len;

    public Word(String word, int len) {
        this.word = word;
        this.len = len;
        System.out.println(this);
    }

    public String getWord() {
        return word;
    }

    public int getLen() {
        return len;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", len=" + len +
                '}';
    }
}
