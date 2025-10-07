package com.cinemagr.cinemamovietheatersystem.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reflector {

    private static final Logger LOGGER = LogManager.getLogger(Reflector.class);

    public static void display(Class<?> clazz) {
        LOGGER.info("Class information: {}", clazz.getSimpleName());

        LOGGER.info("Fields: ");
        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> {
                    LOGGER.debug("Field: {}", field.getName());
                    LOGGER.debug("  Type: {}", field.getType().getSimpleName());
                    LOGGER.debug("  Modifiers: {}", Modifier.toString(field.getModifiers()));
                    LOGGER.debug("");
                });

        LOGGER.info("Constructors: ");
        Arrays.stream(clazz.getDeclaredConstructors())
                .forEach(constructor -> {
                    LOGGER.debug("Constructor: {}", constructor.getName());
                    LOGGER.debug("Modifiers: {}", Modifier.toString(constructor.getModifiers()));
                    LOGGER.debug("Parameters: {}", Arrays.toString(constructor.getParameterTypes()));
                    LOGGER.debug("");
                });


        LOGGER.info("Methods: ");
        Arrays.stream(clazz.getDeclaredMethods())
                .forEach(method -> {
                    LOGGER.debug("Method: {}", method.getName());
                    LOGGER.debug("Return Type: {}", method.getReturnType().getSimpleName());
                    LOGGER.debug("Modifiers: {}", Modifier.toString(method.getModifiers()));
                    LOGGER.debug("Parameters: {}", Arrays.toString(method.getParameterTypes()));
                    LOGGER.debug("");
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
            LOGGER.error("Error creating object {}", e.getMessage());
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
            LOGGER.error("Error calling method: {}", e.getMessage());
            return null;
        }
    }

}
