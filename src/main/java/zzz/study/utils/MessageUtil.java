package zzz.study.utils;

import zzz.study.algorithm.queue.Message;

import java.util.Random;

public class MessageUtil {

  private static Random random = new Random(47);

  public static Message build() {
    Message message = new Message();
    message.setStatus(getRandInt());
    return message;
  }

  public static int getRandInt() {
    return random.nextInt(10);
  }
}
