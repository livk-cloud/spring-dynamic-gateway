package com.kris.common.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * LivkLog
 * </p>
 *
 * @author livk
 */
public class LivkLog {

    private LivkLog() {
    }

    public static LivkLogBuilder of(Logger log) {
        return new LivkLogBuilder(log);
    }

    public static <T> LivkLogBuilder of(Class<T> targetClass) {
        return of(LoggerFactory.getLogger(targetClass));
    }

    public static LivkLogBuilder of(Object object) {
        return of(object.getClass());
    }

    public record LivkLogBuilder(Logger log) {

        public void info(String content, Object... value) {
            if (log.isInfoEnabled()) {
                log.info(content, value);
            }
        }

        public void debug(String content, Object... value) {
            if (log.isDebugEnabled()) {
                log.debug(content, value);
            }
        }

        public void warn(String content, Object... value) {
            if (log.isWarnEnabled()) {
                log.warn(content, value);
            }
        }

        public void trace(String content, Object... value) {
            if (log.isTraceEnabled()) {
                log.trace(content, value);
            }
        }

        public void error(String content, Object... value) {
            if (log.isErrorEnabled()) {
                log.error(content, value);
            }
        }
    }
}
