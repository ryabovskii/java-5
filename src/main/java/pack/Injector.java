package pack;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Injector {

    public Object inject(@NotNull Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getType().getName());
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                field.setAccessible(true);
                // TODO получить значение из json
                String s1 = "pack.SomeInterface";
                String s2 = "pack.SomeImpl";
                String c2 = "pack.SODoer";
                try {
                    System.out.println("_" + Class.forName(s2).getName());
                    //System.out.println(field.getType().getName());
                    if (field.getType().getName().equals(s1)) {
                        Constructor<?> constructor = Class.forName(s2).getConstructor();
                        field.set(object, constructor.newInstance());
                    } else {
                        field.set(object, Class.forName(c2).getConstructor().newInstance());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }


}
