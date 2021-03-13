package io.github.sleepingraven.util.datastructure.linkedlist;

import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta;
import io.github.sleepingraven.util.datastructure.common.annotations.Meta.Represent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Carry
 * @since 2020/6/21
 */
@FixMethodOrder(MethodSorters.JVM)
@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class LinkedListTest {
    private final int nodes;
    
    private Integer[] vs;
    private LinkedList ll;
    
    @Parameters
    public static List<Object[]> parameters() {
        return IntStream.range(0, 7)
                        .map(i -> 1 << i)
                        .map(i -> i - 1)
                        .mapToObj(i -> new Object[] { i })
                        .collect(Collectors.toList());
    }
    
    @Before
    public void assign() {
        vs = IntStream.range(0, nodes).boxed().toArray(Integer[]::new);
        ll = null;
    }
    
    @Test
    public void test() {
        ll = AnnotatingLinkedListUtil.generate(vs, Ln.class);
    }
    
    static class Ln implements LinkedList {
        @Meta(Represent.VALUE)
        int val;
        @Meta(Represent.NEXT)
        Ln next;
        
        private Ln() {
        }
        
        @Meta(Represent.FACTORY)
        public Ln(int val) {
            this.val = val;
        }
        
        @Override
        public String toString() {
            return AnnotatingLinkedListUtil.listToString(this);
        }
        
    }
    
    @After
    public void print() {
        System.out.println("节点数：" + nodes);
        System.out.println(ll);
        System.out.println();
    }
    
}
