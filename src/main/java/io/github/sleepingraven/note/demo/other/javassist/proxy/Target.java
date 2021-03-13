package io.github.sleepingraven.note.demo.other.javassist.proxy;

import lombok.ToString;

/**
 * @author Carry
 * @since 2020/6/16
 */
@ToString
public class Target {
    private final int i = 10;
    
    public void publicDemo() {
        System.out.println("public-" + this.i);
    }
    
    protected void protectedDemo() {
        System.out.println("protected-" + this.i);
    }
    
    void defaultDemo() {
        System.out.println("default-" + this.i);
    }
    
    private void privateDemo() {
        System.out.println("private-" + this.i);
    }
    
}
