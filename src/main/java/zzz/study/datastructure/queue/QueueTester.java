package zzz.study.datastructure.queue;

public class QueueTester {

    public static void testQueue(Queue<Integer> queue) {
        for (int i = 1; i < 20; i++) {
            try {
                if (i % 4 == 0) {
                    System.out.printf("将要出队元素: %d\t", queue.dequeue());
                } else {
                    System.out.printf("将要入队元素：%d\t", i);
                    queue.enqueue(i);
                }
                System.out.println("入队/出队成功，队列内容: " + queue);
                System.out.println("队列长度: " + queue.size());
                System.out.println("队列首元素: " + queue.head());
                System.out.println("队列尾元素: " + queue.tail());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void testQueue2(Queue<String> queue) {
        String[] words = ("I have a little dream, that is to have a clean and beautiful house   " +
                "that i would be inspired to work, to learn and to relax.").split("\\s+");
        for (int i = 0; i < words.length; i++) {
            try {
                if (i % 4 == 0) {
                    System.out.println("出队元素: " + queue.dequeue());
                } else {
                    queue.enqueue(words[i]);
                }
                System.out.println("入队/出队成功，队列内容: " + queue);
                System.out.println("队列长度: " + queue.size());
                System.out.println("队列首元素: " + queue.head());
                System.out.println("队列尾元素: " + queue.tail());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("************** 测试顺序队列 *************");

        Queue<Integer> queue = new SeqQueue<Integer>();
        try {
            queue.dequeue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        testQueue(queue);

        Queue<String> queue2 = new SeqQueue<String>();
        try {
            queue2.dequeue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        testQueue2(queue2);

        System.out.println("************** 测试链式队列 *************");

        queue = new LinkedQueue<Integer>();
        try {
            queue.dequeue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        testQueue(queue);

        queue2 = new LinkedQueue<String>();
        try {
            queue2.dequeue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        testQueue2(queue2);
    }

}
