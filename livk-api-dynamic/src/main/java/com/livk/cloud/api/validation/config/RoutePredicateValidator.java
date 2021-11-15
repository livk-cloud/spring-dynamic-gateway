package com.livk.cloud.api.validation.config;

import com.livk.cloud.api.domain.route.Predicate;
import com.livk.cloud.api.validation.annotation.RoutePredicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

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
        return predicates.stream().map(predicate -> predicate.getArgs().get(PATTERN) != null ||
                                                    predicate.getArgs().get(GENERATED_NAME_PREFIX + "0") != null)
                       .distinct().filter(bool -> bool).count() == 1;
    }
}
