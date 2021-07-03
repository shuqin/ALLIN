package zzz.study.foundations.generics;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static Class getActualType(Field field) {
        Class<?> fieldType = field.getType();
        // 集合List元素
        if (fieldType.equals(List.class)) {
            // 当前集合的泛型类型
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericType;
                // 得到泛型里的class类型对象
                Class<?> actualTypeArgument = (Class<?>) (pt.getActualTypeArguments()[0]);
                return actualTypeArgument;
            }
        }
        return fieldType;
    }

    public static void main(String[] args) throws Exception {
        MyList<User> userMyList = new MyList<User>();//传入泛型
        userMyList.add(new User());
        System.out.println(userMyList.getGenericType());
        System.out.println(userMyList.getGenericTypeWithReflection());
        userMyList.list.forEach(user -> {
            System.out.println(user.name);
        });
    }

    public static class MyList<T> {
        private List<T> list = new ArrayList<>();

        public MyList() {
        }

        public void add(T t) {
            list.add(t);
        }

        // list 有元素时
        public String getGenericType() {
            String type = "";
            if (list.size() > 0) {
                type = list.get(0).getClass().getName();
            }
            return type;
        }

        public String getGenericTypeWithReflection() {
            try {
                Field f = this.getClass().getDeclaredField("list");
                String cname = getActualType(f).getName();
                return cname;
            } catch (NoSuchFieldException e) {
                return "";
            }
        }
    }

    public static class User {
        String name;
    }

}
