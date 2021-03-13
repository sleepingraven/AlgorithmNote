package io.github.sleepingraven.note.practice.consistent_hash;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 算法接口实现类
 *
 * @author Carry
 * @since 2021/1/31
 */
public class HashServiceImpl implements IHashService {
    
    /**
     * MurMurHash 算法,性能高,碰撞率低
     *
     * @param key
     *         String
     *
     * @return long
     */
    @Override
    public long hash(String key) {
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;
        
        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);
        
        long m = 0xc6a4a7935bd1e995L;
        int r = 47;
        
        long h = seed ^ (buf.remaining() * m);
        
        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();
            
            k *= m;
            k ^= k >>> r;
            k *= m;
            
            h ^= k;
            h *= m;
        }
        
        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }
        
        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;
        
        buf.order(byteOrder);
        return h;
    }
    
}
