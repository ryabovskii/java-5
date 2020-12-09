package pack;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

public class Injector {

    private static final String propertiesPatchString = "src/main/resources/propInj.json";

    private String getFromProperties(String key) {
        try {
            String jsonString = new String(Files.readAllBytes(Path.of(propertiesPatchString)));
            JSONObject json = new JSONObject(jsonString);
            return json.get(key).toString();
        } catch (Exception e) {
            System.out.println("Cant get from properties\n" + e);
            return null;
        }
    }

    public Object inject(@NotNull Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                field.setAccessible(true);
                String key = field.getType().getName();
                String value = getFromProperties(key);
                if (value == null) {
                    System.out.println("Cant inject");
                    return null;
                }
                try {
                    Constructor<?> constructor = Class.forName(value).getConstructor();
                    field.set(object, constructor.newInstance());
                } catch (Exception e) {
                    System.out.println("Cant inject" + e);
                }
            }
        }
        return object;
    }


}
