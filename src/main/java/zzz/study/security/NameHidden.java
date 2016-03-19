package zzz.study.security;

import zzz.study.utils.Base64Utils;

import java.util.Arrays;

/**
 * 名字隐藏
 * Created by qinshu on 2022/1/29
 */
public class NameHidden {

    public static void main(String[] args) {
        String name = "qin.shu";
        String nameEncoded = Base64Utils.encodeContent(name);
        System.out.println(nameEncoded);

        String originName = Base64Utils.decodeContent(nameEncoded);
        System.out.println(originName);

        char[] chars = {0x71, 0x69, 0x6e, 0x2e, 0x73, 0x68, 0x75};
        StringBuilder nameHidden = new StringBuilder();
        for (char c: chars) {
            nameHidden.append("&#" + new Integer(c) + ";");
        }
        System.out.println(nameHidden);
    }
}
