package io.github.sleepingraven.note.huffman.strategies.coding.bitstream.processer;

import lombok.NonNull;
import io.github.sleepingraven.note.huffman.entity.ByteDataNode;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 将每个字节分离为 8 个，再根据映射解析为原符号
 *
 * @author 10132
 */
public class BitStreamParser extends BaseBitStreamProcessor<Byte> {
    private final Map<Byte, byte[]> huffmanCodeMap;
    private final Map<Integer, List<Byte>> lengthMap;
    private int moved;
    
    private final ByteDataNode root;
    private ByteDataNode p;
    
    public BitStreamParser(HuffmanMapping huffmanMapping, int capacity) {
        super(Math.max(capacity, 256 + 8));
        this.huffmanCodeMap = huffmanMapping.getCode();
        // 预处理 huffmanCodeMap，加快速度
        this.lengthMap = huffmanCodeMap.entrySet()
                                       .stream()
                                       .collect(Collectors.groupingBy(e -> e.getValue().length,
                                                                      Collectors.mapping(Entry::getKey,
                                                                                         Collectors.toList())));
        this.root = huffmanMapping.getTree();
        this.p = root;
    }
    
    /**
     * 移除末尾若干位（实际移除了这些数目的字节）
     *
     * @param margin
     *         结尾空余位数（补 0 数）
     */
    public void prune(int margin) {
        rear = (rear - margin + capacity) % capacity;
        size -= margin;
    }
    
    /**
     * 将一个 bit 流入队
     *
     * @throws NullPointerException
     *         b is null
     */
    @Override
    public boolean enqueue(@NonNull Byte b) {
        if (8 + size > capacity) {
            return false;
        }
        for (int i = 7; i >= 0; i--) {
            circularQueue[rear++] = (byte) ((b >> i) & 1);
            rear %= capacity;
        }
        size += 8;
        return true;
    }
    
    /**
     * 根据已有字节编码，返回一个源符号
     * O(∑nm)，即 O(2^m)。查找了 m 种长度（即树高），每种长度有 n 个符号
     *
     * @return 原符号；如果当前没有匹配的编码，则返回 null
     */
    public Byte dequeue0() {
        // 判断从 moved 到 rear 能否匹配
        for (int m = (moved - front + capacity) % capacity + 1; m <= size; m++) {
            moved++;
            if (!lengthMap.containsKey(m)) {
                continue;
            }
            for (byte b : lengthMap.get(m)) {
                byte[] code = huffmanCodeMap.get(b);
                // 逐一匹配
                int i;
                for (i = 0; i < m; i++) {
                    if (code[i] != circularQueue[(front + i) % capacity]) {
                        break;
                    }
                }
                // 匹配到了则返回 b
                if (i == m) {
                    size -= m;
                    moved %= capacity;
                    front = moved;
                    return b;
                }
            }
        }
        return null;
    }
    
    /**
     * 注意 root 不能为空，且不是叶子
     */
    @Override
    public Byte dequeue() {
        while (size > 0) {
            p = circularQueue[front++] == 0 ? p.getLeft() : p.getRight();
            front %= circularQueue.length;
            size--;
            if (p.getLeft() == null) {
                byte b = p.getData();
                p = root;
                return b;
            }
        }
        return null;
    }
    
}
