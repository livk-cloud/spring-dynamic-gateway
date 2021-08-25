package com.kris.common.core.listener;

import com.kris.common.core.event.KrisEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

/** @Author: chris @Date: 2021/7/30 @Description: @Since: JDK11 */
public interface KrisEventListener<T> extends ApplicationListener<KrisEvent<T>> {}
