package com.livk.common.core.util;

import com.livk.common.core.function.FieldFunction;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>
 * ReflectionUtils
 * </p>
 *
 * @author livk
 * @date 2022/2/25
 */
@UtilityClass
public class ReflectionUtils {

    public <T> String getFieldName(FieldFunction<T> func) {
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(func);
            String getter = serializedLambda.getImplMethodName();
            if (getter.startsWith("get")) {
                getter = getter.substring(3);
            } else if (getter.startsWith("is")) {
                getter = getter.substring(2);
            } else {
                return null;
            }
            if (!StringUtils.hasText(getter)) {
                return null;
            }
            char[] cs = getter.toCharArray();
            cs[0] += 32;
            return String.valueOf(cs);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
