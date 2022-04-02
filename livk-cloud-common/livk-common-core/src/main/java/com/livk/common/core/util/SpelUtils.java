package com.livk.common.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p>
 * SpelUtils
 * </p>
 *
 * @author livk
 * @date 2022/4/2
 */
@UtilityClass
public class SpelUtils {

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private static final StandardEvaluationContext CONTEXT = new StandardEvaluationContext();

    private <T> T parseSpel(Method method, Object[] args, String condition, Class<T> returnClass) {
        var discoverer = new LocalVariableTableParameterNameDiscoverer();
        var parameterNames = discoverer.getParameterNames(method);
        Assert.notNull(parameterNames, "参数列表不能为null");
        for (int i = 0; i < parameterNames.length; i++) {
            CONTEXT.setVariable(parameterNames[i], args[i]);
        }
        return PARSER.parseExpression(condition).getValue(CONTEXT, returnClass);
    }

    private <T> T parseSpel(Map<String, Object> variables, String condition, Class<T> returnClass) {
        CONTEXT.setVariables(variables);
        return PARSER.parseExpression(condition).getValue(CONTEXT, returnClass);
    }
}
