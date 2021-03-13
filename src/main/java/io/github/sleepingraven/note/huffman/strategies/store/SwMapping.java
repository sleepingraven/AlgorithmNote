package io.github.sleepingraven.note.huffman.strategies.store;

import lombok.Getter;
import io.github.sleepingraven.note.huffman.strategies.store.custom.CodeCustom;
import io.github.sleepingraven.note.huffman.strategies.store.custom.TreeCustom;
import io.github.sleepingraven.note.huffman.strategies.store.serialization.CodeInStringSerialization;
import io.github.sleepingraven.note.huffman.strategies.store.serialization.CodeSerialization;
import io.github.sleepingraven.note.huffman.strategies.store.serialization.TreeSerialization;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 定义了 swCode 和 StoreWay 的映射。是他们的中间映射、适配器
 *
 * @author Carry
 * @since 2021/2/15
 */
@Getter
public enum SwMapping {
    /**
     * mapping of {@link TreeSerialization}
     */
    HUFFMAN_TREE_SERIALIZATION(0x01, TreeSerialization::new, TreeSerialization.class),
    /**
     * mapping of {@link CodeSerialization}
     */
    HUFFMAN_CODE_SERIALIZATION(0x02, CodeSerialization::new, CodeSerialization.class),
    /**
     * mapping of {@link CodeInStringSerialization}
     */
    HUFFMAN_CODE_IN_STRING_SERIALIZATION(0x04, CodeInStringSerialization::new, CodeInStringSerialization.class),
    /**
     * mapping of {@link TreeCustom}
     */
    HUFFMAN_TREE_CUSTOM(0x81, TreeCustom::new, TreeCustom.class),
    /**
     * mapping of {@link CodeCustom}
     */
    HUFFMAN_CODE_CUSTOM(0x82, CodeCustom::new, CodeCustom.class),
    /**
     * invalid mapping. Don't support extension yet
     */
    INVALID(0, null, null);
    
    private final int swCode;
    private final Supplier<? extends StoreWay> supplier;
    private final Class<? extends StoreWay> represent;
    
    <S extends StoreWay> SwMapping(int swCode, Supplier<S> supplier, Class<S> represent) {
        this.swCode = swCode;
        this.supplier = supplier;
        this.represent = represent;
    }
    
    static SwMapping from(StoreWay storeWay) {
        return REPRESENT_MAP.getOrDefault(storeWay.getClass(), INVALID);
    }
    
    static SwMapping decode(int swCode) {
        return SW_CODE_MAP.getOrDefault(swCode, INVALID);
    }
    
    private static final Map<Integer, SwMapping> SW_CODE_MAP;
    private static final Map<Class<? extends StoreWay>, SwMapping> REPRESENT_MAP;
    
    static {
        EnumSet<SwMapping> set = EnumSet.complementOf(EnumSet.of(INVALID));
        SW_CODE_MAP = set.stream().collect(Collectors.toMap(SwMapping::getSwCode, Function.identity()));
        REPRESENT_MAP = set.stream().collect(Collectors.toMap(SwMapping::getRepresent, Function.identity()));
    }
}
