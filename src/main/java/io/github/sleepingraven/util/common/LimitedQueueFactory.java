package io.github.sleepingraven.util.common;

import java.lang.reflect.Proxy;
import java.util.Queue;
import java.util.function.Supplier;

/**
 * @author Carry
 * @since 2021/1/20
 */
public class LimitedQueueFactory {
    private static final Class<?>[] INTERFACES = { Queue.class };
    
    public static <E> Queue<E> newLimitedQueue(Supplier<? extends Queue<E>> srcQueueSupplier, int limit) {
        return newLimitedQueue(srcQueueSupplier.get(), limit);
    }
    
    /**
     * 使用 JDK 动态代理，需要返回接口类型，实现类型不能转型
     */
    public static <E> Queue<E> newLimitedQueue(Queue<E> srcQueue, int limit) {
        Object proxyInstance =
                Proxy.newProxyInstance(srcQueue.getClass().getClassLoader(), INTERFACES, (proxy, method, args) -> {
                    Object ret;
                    switch (method.getName()) {
                        case "offer":
                            ret = offerWithLimit(srcQueue, (E) args[0], limit);
                            break;
                        default:
                            ret = method.invoke(srcQueue, args);
                    }
                    return ret;
                });
        return (Queue<E>) proxyInstance;
    }
    
    public static <E> boolean offerWithLimit(Queue<E> queue, E e, int limit) {
        if (queue.size() == limit) {
            queue.poll();
        }
        return queue.offer(e);
    }
    
}
