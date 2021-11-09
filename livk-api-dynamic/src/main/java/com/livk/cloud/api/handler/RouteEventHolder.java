package com.livk.cloud.api.handler;

/**
 * <p>
 * RouteEventHolder
 * </p>
 *
 * @author livk
 * @date 2021/11/9
 */
public class RouteEventHolder {

    private RouteEventHolder() {
    }

    private static final ThreadLocal<String> ROUTE_EVENT = new ThreadLocal<>();

    public static void set(String destination) {
        ROUTE_EVENT.set(destination);
    }

    public static String get() {
        return ROUTE_EVENT.get();
    }

    public static void clear() {
        ROUTE_EVENT.remove();
    }
}
