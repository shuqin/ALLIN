package zzz.study.datastructure.list;


public class MyListTester {

    public static void testInsert(MyLinkedList<Integer> list, int data, int i) {
        System.out.printf("将元素 %d 插入到链表的第 %d 个位置上: \t", data, i);
        try {
            list.insert(data, i);
            System.out.println("链表内容： " + list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void testOneTimeInsert(MyLinkedList<Integer> list) {
        Integer[] datas = new Integer[]{3, 6, 7};
        try {
            list.oneTimeInsert(datas, 1);  // 插入链表头
            System.out.println(list);
            list.oneTimeInsert(datas, 2);  // 插入链表中间
            System.out.println(list);
            list.oneTimeInsert(datas, list.size() + 1);  // 插入链表尾
            System.out.println(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public static void testLocateElem(MyLinkedList<Integer> list) {
        for (int i = 0; i <= list.size() + 1; i++) {
            try {
                System.out.printf("第 %d 个位置上的元素为: ", i);
                System.out.println(list.locateElem(i));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void testSetElem(MyLinkedList<Integer> list) {
        for (int i = 0; i <= list.size() + 1; i++) {
            try {
                System.out.printf("第 %d 个位置上的元素将要修改为: %d\t", i, i);
                list.setElem(i, i);
                System.out.printf("修改成功！\n");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("修改后的链表内容：" + list);
    }

    public static void testDelete(MyLinkedList<Integer> list, int i) {

        try {
            System.out.printf(" 将要删除第 %d 个元素: ", i);
            System.out.println(" 删除成功！ 删除的元素为: " + list.delete(i));
            System.out.println("删除元素后的链表: " + list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void testSearch(MyLinkedList<Integer> list, int data) {
        System.out.println("查找元素 " + data + " ： " + (list.search(data) == null ? "查找失败，链表中无此结点！" : list.search(data)));
        System.out.println("前趋结点: " + (list.precessor(data) == null ? "无前趋结点!" : list.precessor(data)));
        System.out.println("后继结点: " + (list.successor(data) == null ? "无后继结点!" : list.successor(data)));
    }

    public static void testDelElem(MyLinkedList<Integer> list) {
        System.out.println("删除元素 1：" + (list.deleteElem(1) == null ? "链表中不存在该元素的结点！" : "删除成功！"));
        System.out.println(list);
        System.out.println("删除元素 5：" + (list.deleteElem(5) == null ? "链表中不存在该元素的结点！" : "删除成功！"));
        System.out.println(list);
        System.out.println("删除元素 9：" + (list.deleteElem(9) == null ? "链表中不存在该元素的结点！" : "删除成功！"));
        System.out.println(list);
        System.out.println("删除元素 30：" + (list.deleteElem(30) == null ? "链表中不存在该元素的结点！" : "删除成功！"));
        System.out.println(list);
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<Integer>();
        System.out.println("链表为空 ？ " + (list.isEmpty() ? "是" : "否"));

        try {
            testInsert(list, 7, 0);
            testInsert(list, 7, 2);
            testInsert(list, 7, 1);
            testInsert(list, 9, 2);
            testInsert(list, 3, 3);
            testInsert(list, 5, 1);

            testLocateElem(list);
            testSetElem(list);

            System.out.println("************ 一次性插入多个元素 *************");
            testOneTimeInsert(list);

            System.out.println("************ 删除指定位置的元素 *************");
            testDelete(list, 0);
            testDelete(list, 1);
            testDelete(list, 4);
            testDelete(list, list.size());
            testDelete(list, list.size() + 1);

            System.out.println("************** 清空链表 **************");
            list.clear();
            System.out.println(list);

            list.oneTimeInsert(new Integer[]{5, 4, 3, 2, 1}, 1);
            System.out.println(list);

            System.out.println("************** 链表逆置 **************");
            list.reverse();
            System.out.println(list);

            System.out.println("************** 衔接链表 **************");
            MyLinkedList<Integer> another = new MyLinkedList<Integer>();
            list.append(another);
            System.out.println(list);

            another.oneTimeInsert(new Integer[]{10, 20, 30}, 1);
            list.append(another);
            System.out.println(list);
            System.out.println("链表当前长度: " + list.size());

            System.out.println("************* 查找元素及其前趋，后继 ***************");
            testSearch(list, 1);
            testSearch(list, 5);
            testSearch(list, 9);
            testSearch(list, 20);
            testSearch(list, 30);

            System.out.println("**************** 删除指定的元素 *****************");
            testDelElem(list);
            System.out.println("链表当前长度: " + list.size());

            System.out.println("**************** 合并有序列表 *****************");
            MyLinkedList<Integer> newList = new MyLinkedList<Integer>();
            newList.oneTimeInsert(new Integer[]{1, 8, 16, 24}, 1);

            System.out.println("**************** 复制链表 *****************");
            MyLinkedList<Integer> la = list.copyList();
            MyLinkedList<Integer> lb = newList.copyList();
            System.out.println("la : " + la);
            System.out.println("lb : " + lb);

            MyLinkedList<Integer> mergedList = MyLinkedList.mergeList(list, newList);
            System.out.println("merged list: " + mergedList);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }

}
