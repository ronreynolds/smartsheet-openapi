package com.ronreynolds.util.reflection;

import java.lang.reflect.Method;

public class Reflection {
    private Reflection() {
    }

    /**
     * i can never remember the difference between getMethod and getDeclaredMethod so i use this instead
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... argTypes) throws NoSuchMethodException {
        Method method;
        try {
            // getDeclaredMethod returns ALL methods (public, private, protected) but only of this specific type (not inherited)
            method = clazz.getDeclaredMethod(name, argTypes);
            method.setAccessible(true);
        } catch (NoSuchMethodException notHere) {
            try {
                method = clazz.getMethod(name, argTypes);
                // getMethod ONLY returns public methods (but includes inherited public methods) so they're already accessible
            } catch (NoSuchMethodException notPublic) {
                // go recursive
                Class<?> parentType = clazz.getSuperclass();
                if (parentType == null) {
                    // end of the road
                    throw notHere;
                }
                return getMethod(parentType, name, argTypes);
            }
        }
        return method;
    }

    private static final Class<?>[] NO_ARG_TYPES = {};
    private static final Object[] NO_ARGS = {};

    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object instance, String methodName, Class<T> responseType) {
        try {
            return (T) getMethod(instance.getClass(), methodName, NO_ARG_TYPES).invoke(instance, NO_ARGS);
        } catch (ReflectiveOperationException fail) {
            throw new RuntimeException("failed to invoke " + methodName, fail);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T invokeStatic(Class<?> instanceType, String methodName, Class<T> responseType) {
        try {
            return (T) getMethod(instanceType, methodName, NO_ARG_TYPES).invoke(null, NO_ARGS);
        } catch (ReflectiveOperationException fail) {
            throw new RuntimeException("failed to invoke " + methodName, fail);
        }
    }
}
