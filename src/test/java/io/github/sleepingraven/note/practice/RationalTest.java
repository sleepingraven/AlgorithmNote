package io.github.sleepingraven.note.practice;

import junit.framework.TestCase;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.List;

/**
 * @author Carry
 * @since 2021/2/12
 */
@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class RationalTest {
    private final Rational r;
    private final String expected;
    
    @Parameters
    public static List<Object[]> parameters() {
        Rational r_5_1 = new Rational(5, 1);
        Rational r_1_15 = new Rational(1, 15);
        Rational r__1__1 = new Rational(-1, -1);
        return Arrays.asList(new Object[][] {
                { r_5_1, "5" },
                { r_1_15, "1/15" },
                { r_5_1.add(r_1_15), "76/15" },
                { r_5_1.divide(r_1_15), "75" },
                { r__1__1, "1" },
                });
    }
    
    @Test
    public void test() {
        TestCase.assertEquals(expected, r.toString());
    }
    
}
