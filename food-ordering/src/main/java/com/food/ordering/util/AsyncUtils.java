package com.food.ordering.util;

import java.util.concurrent.ThreadFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class AsyncUtils {

    public static ThreadFactory createNamedThreadFactory(String name) {

        return new ThreadFactoryBuilder().setNameFormat(name + "-%d").build();

    }

}