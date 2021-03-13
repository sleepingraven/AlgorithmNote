package io.github.sleepingraven.note.practice.consistent_hash;

/**
 * 算法接口类
 *
 * @author Carry
 * @since 2021/1/31
 */
public interface IHashService {
    
    long hash(String key);
    
}
