package io.github.sleepingraven.util.advanced;

import junit.framework.TestCase;
import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.Before;
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
public class InverseElementTest {
    private final int a;
    private final long p;
    private final long expected;
    
    private long inv;
    
    @Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
                { 212353, 1001733991047948000L, 823816093931522017L },
                });
    }
    
    @Before
    public void assign() {
        inv = InverseElement.inv(a, p);
    }
    
    @Test
    public void testInverseElement() {
        TestCase.assertEquals(expected, inv);
    }
    
    @After
    public void print() {
        System.out.println(inv);
    }
    
}
