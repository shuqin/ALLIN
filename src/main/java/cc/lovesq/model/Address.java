package cc.lovesq.model;

/**
 * Created by shuqin on 16/10/24.
 */
public class Address {

    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Address{" +
                "detail='" + detail + '\'' +
                '}';
    }
}
