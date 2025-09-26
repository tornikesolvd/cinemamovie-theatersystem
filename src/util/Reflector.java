package util;

import java.lang.reflect.*;
import java.util.Arrays;

public class Reflector {

    public static void display(Class<?> clazz) {
        System.out.println(" Class Information: " + clazz.getSimpleName());

        System.out.println("Fields: ");
        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> {
                    System.out.println("Field: " + field.getName());
                    System.out.println("  Type: " + field.getType().getSimpleName());
                    System.out.println("  Modifiers: " + Modifier.toString(field.getModifiers()));

                    System.out.println();
                });

        System.out.println("Constructors: ");
        Arrays.stream(clazz.getDeclaredConstructors())
                .forEach(constructor -> {
                    System.out.println("Constructor: " + constructor.getName());
                    System.out.println("  Modifiers: " + Modifier.toString(constructor.getModifiers()));
                    System.out.println("  Parameters: " + Arrays.toString(constructor.getParameterTypes()));

                    System.out.println();
                });

        System.out.println("Methods: ");
        Arrays.stream(clazz.getDeclaredMethods())
                .forEach(method -> {
                    System.out.println("Method: " + method.getName());
                    System.out.println("  Return Type: " + method.getReturnType().getSimpleName());
                    System.out.println("  Modifiers: " + Modifier.toString(method.getModifiers()));
                    System.out.println("  Parameters: " + Arrays.toString(method.getParameterTypes()));

                    System.out.println();
                });
    }
    
    public static Object createObject(Class<?> clazz, Object... args) {
        try {
            Class<?>[] parameterTypes = Arrays.stream(args)
                    .map(Object::getClass)
                    .toArray(Class[]::new);
            
            Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (Exception e) {
            System.err.println("Error creating object: " + e.getMessage());
            return null;
        }
    }
    
    public static Object callMethod(Object obj, String methodName, Object... args) {
        try {
            Class<?>[] parameterTypes = Arrays.stream(args)
                    .map(Object::getClass)
                    .toArray(Class[]::new);
            
            Method method = obj.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(obj, args);
        } catch (Exception e) {
            System.err.println("Error calling method: " + e.getMessage());
            return null;
        }
    }
    
}
