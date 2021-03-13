package io.github.sleepingraven.note.huffman.strategies.store.serialization;

import io.github.sleepingraven.note.huffman.common.Constants;
import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Carry
 * @since 2020/6/10
 */
public class CodeInStringSerialization extends AbstractSerializationStoreWay<Map<Byte, String>> {
    
    @Override
    protected Map<Byte, String> getSerializableByHuffmanMapping(HuffmanMapping huffmanMapping) {
        return convertMap(huffmanMapping.getCode(), v -> new String(v, Constants.CHARSET));
    }
    
    @Override
    protected HuffmanMapping parseHuffmanMapping(Map<Byte, String> s) {
        Map<Byte, byte[]> huffmanCodeMap = convertMap(s, v -> v.getBytes(Constants.CHARSET));
        return new HuffmanMapping(huffmanCodeMap);
    }
    
    private static <K, U, V> Map<K, V> convertMap(Map<K, U> from, Function<U, V> converter) {
        Function<Entry<K, U>, V> entryMapper = converter.compose(Entry::getValue);
        return from.entrySet().stream().collect(Collectors.toMap(Entry::getKey, entryMapper));
    }
    
}
