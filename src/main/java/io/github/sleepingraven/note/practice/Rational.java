package io.github.sleepingraven.note.practice;

import io.github.sleepingraven.util.ac.DataCalculator;

/**
 * 有理数（分数）
 *
 * @author Carry
 * @since 2020/1/16
 */
public class Rational {
    /**
     * 分子
     */
    private final long a;
    /**
     * 分母，大于 0
     */
    private final long b;
    
    /**
     * 使用默认的构造创建有理数<p>
     * 分子：1，分母：1
     */
    public Rational() {
        a = 1;
        b = 1;
    }
    
    /**
     * 使用分子和分母构造有理数
     *
     * @param a
     *         分子
     * @param b
     *         分母，不为0
     */
    public Rational(long a, long b) {
        if (a == Long.MIN_VALUE || b == Long.MIN_VALUE) {
            throw new ArithmeticException(String.format("a or b is too small: %d, %d", a, b));
        }
        if (b == 0) {
            throw new ArithmeticException("b is 0");
        }
        
        if (b < 0) {
            a = -a;
            b = -b;
        }
        long gcd = DataCalculator.gcd(Math.abs(a), b);
        this.a = a / gcd;
        this.b = b / gcd;
    }
    
    /**
     * 有理数相加
     *
     * @param rational
     *         加数
     *
     * @return 和
     */
    public Rational add(Rational rational) {
        return new Rational(a * rational.b + b * rational.a, b * rational.b);
    }
    
    /**
     * 有理数相减
     *
     * @param rational
     *         减数
     *
     * @return 差
     */
    public Rational subtract(Rational rational) {
        return add(rational.negative());
    }
    
    /**
     * 有理数相乘
     *
     * @param rational
     *         乘数
     *
     * @return 积
     */
    public Rational multiply(Rational rational) {
        return new Rational(a * rational.a, b * rational.b);
    }
    
    public Rational divide(Rational rational) {
        return multiply(rational.inverse());
    }
    
    /**
     * 使有理数 * -1
     *
     * @return 有理数 * -1
     */
    public Rational negative() {
        return new Rational(-a, b);
    }
    
    /**
     * 求倒数
     *
     * @return 倒数
     */
    public Rational inverse() {
        return new Rational(b, a);
    }
    
    @Override
    public String toString() {
        if (b == 1) {
            return String.valueOf(a);
        } else {
            return a + "/" + b;
        }
    }
    
}
