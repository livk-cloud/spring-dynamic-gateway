package com.livk.cloud.dynamic.validation.config;

import com.livk.cloud.dynamic.domain.route.Predicate;
import com.livk.cloud.dynamic.validation.annotation.RoutePredicate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * RoutePredicateValidator
 * </p>
 *
 * @author livk
 * @date 2021/11/12
 */
public class RoutePredicateValidator implements ConstraintValidator<RoutePredicate, List<Predicate>> {

    private static final String PATTERN = "pattern";

    private static final String GENERATED_NAME_PREFIX = "_genkey_";

    @Override
    public boolean isValid(List<Predicate> predicates, ConstraintValidatorContext constraintValidatorContext) {
        return predicates.stream().allMatch(this::verify);
    }

    private boolean verify(Predicate predicate) {
        return !CollectionUtils.isEmpty(predicate.getArgs()) && containsRoute(predicate.getArgs());
    }

    /**
     * args
     *
     * @param args Predicate args
     * @return bool
     */
    private boolean containsRoute(Map<String, String> args) {
        return args.get(PATTERN) != null ^ args.get(GENERATED_NAME_PREFIX + "0") != null;
    }

}
