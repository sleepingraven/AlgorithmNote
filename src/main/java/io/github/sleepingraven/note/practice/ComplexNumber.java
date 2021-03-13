package io.github.sleepingraven.note.practice;

/**
 * 复数
 *
 * @author Carry
 * @since 2020/7/20
 */
public class ComplexNumber {
    public final int real;
    public final int image;
    
    public ComplexNumber(int real, int image) {
        this.real = real;
        this.image = image;
    }
    
    /**
     * 复数加法
     */
    public ComplexNumber add(ComplexNumber c) {
        return new ComplexNumber(real + c.real, image + c.image);
    }
    
    /**
     * 复数减法
     */
    public ComplexNumber mul(ComplexNumber c) {
        return new ComplexNumber(real * c.real - image * c.image, real * c.image + image * c.real);
    }
    
}
