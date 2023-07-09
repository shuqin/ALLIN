package shared.utils;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EncodingDetector {

    /**
     * 通过二进制流获取流的编码格式
     * https://code.google.com/archive/p/juniversalchardet/
     *
     */
    public static Charset getEncoding(byte[] data) {
        UniversalDetector detector = new UniversalDetector(null);
        Charset charset = null;
        int nread;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
            while ((nread = bis.read(data)) > 0 && !detector.isDone()) {
                detector.handleData(data, 0, nread);
            }

            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            detector.reset();
            if (encoding != null) {
                charset = Charset.forName(encoding);
            }
        } catch (IOException e) {
            // ignore
        }
        return charset != null ? charset : StandardCharsets.UTF_8;
    }

}
