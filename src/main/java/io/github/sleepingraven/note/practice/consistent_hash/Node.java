package io.github.sleepingraven.note.practice.consistent_hash;

import lombok.*;

/**
 * 模拟机器节点
 *
 * @author Carry
 * @since 2021/1/31
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Node {
    private String ip;
    private String name;
    
}
