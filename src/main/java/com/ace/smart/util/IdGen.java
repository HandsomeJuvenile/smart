package com.ace.smart.util;

import org.springframework.util.IdGenerator;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGen{
    private static AtomicInteger counter = new AtomicInteger(0);

    /**
     * 长生消息id
     */
    public static long getAtomicCounter() {
        if (counter.get() > 999999) {
            counter.set(1);
        }
        long time = System.currentTimeMillis();
        long returnValue = time * 100 + counter.incrementAndGet();
        return returnValue;
    }

    private static long incrementAndGet() {
        return counter.incrementAndGet();
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0,4);
    }

    public static void main(String[] args) {
        System.out.println(uuid());
    }
}