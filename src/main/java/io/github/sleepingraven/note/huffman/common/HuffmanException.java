package io.github.sleepingraven.note.huffman.common;

/**
 * @author Carry
 * @since 2020/1/20
 */
public class HuffmanException extends Exception {
    
    public HuffmanException() {
    }
    
    public HuffmanException(String message) {
        super(message);
    }
    
    public HuffmanException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public HuffmanException(Throwable cause) {
        super(cause);
    }
    
    public HuffmanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
