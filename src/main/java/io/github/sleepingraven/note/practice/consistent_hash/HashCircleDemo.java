package io.github.sleepingraven.note.practice.consistent_hash;

import java.util.*;
import java.util.stream.IntStream;

/**
 * 测试类
 *
 * @author Carry
 * @since 2021/1/31
 */
public class HashCircleDemo {
    private static final String IP_PREFIX = "192.168.0.";
    private static final int NODE_NUM = 10;
    
    public static void main(String[] args) {
        // 每台真实机器节点上保存的记录条数
        Map<String, Integer> ip2Count = new LinkedHashMap<>();
        // 真实机器节点, 模拟 NODE_NUM 台
        List<Node> nodes = new ArrayList<>();
        
        IntStream.rangeClosed(1, NODE_NUM).forEach(i -> {
            // 初始化记录
            String ip = IP_PREFIX + i;
            ip2Count.put(ip, 0);
            Node node = new Node(ip, "node-" + i);
            nodes.add(node);
        });
        
        IHashService hashService = new HashServiceImpl();
        // 每台真实机器引入 500 个虚拟节点
        ConsistentHash<Node> consistentHash = new ConsistentHash<>(hashService, 500, nodes);
        
        // 将 5000 条记录尽可能均匀的存储到 NODE_NUM 台机器节点上
        for (int i = 0; i < 5000; i++) {
            // 产生随机一个字符串当做一条记录，可以是其它更复杂的业务对象，比如随机字符串相当于对象的业务唯一标识
            String data = UUID.randomUUID().toString() + i;
            // 通过记录找到真实机器节点
            Node node = consistentHash.get(data);
            // 再这里可以能过其它工具将记录存储真实机器节点上，比如 MemoryCache 等
            // ...
            // 每台真实机器节点上保存的记录条数加 1
            ip2Count.computeIfPresent(node.getIp(), (k, v) -> v + 1);
        }
        
        // 打印每台真实机器节点保存的记录条数
        ip2Count.forEach((ip, cnt) -> System.out.printf("%-13s%s%s\n", ip, "节点记录条数：", cnt));
    }
    
}
