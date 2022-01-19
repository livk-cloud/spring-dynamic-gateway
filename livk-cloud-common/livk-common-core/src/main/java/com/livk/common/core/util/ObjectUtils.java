package com.livk.common.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * <p>
 * StringUtils
 * </p>
 *
 * @author livk
 * @date 2021/12/24
 */
@UtilityClass
public class ObjectUtils {

    public boolean allHasText(String... str) {
        for (String s : str) {
            if (!StringUtils.hasText(s)) {
                return false;
            }
        }
        return true;
    }
}
