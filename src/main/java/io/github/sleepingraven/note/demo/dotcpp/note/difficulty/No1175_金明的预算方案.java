package io.github.sleepingraven.note.demo.dotcpp.note.difficulty;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Carry
 * @since 2020/1/4
 */
public class No1175_金明的预算方案 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 预算
        int n = scanner.nextInt() / 10;
        // 物品个数
        int m = scanner.nextInt();
        Goods[] ms = new Goods[m];
        for (int i = 0; i < m; i++) {
            ms[i] = new Goods(scanner.nextInt() / 10, scanner.nextInt(), scanner.nextInt() - 1);
        }
        
        System.out.println(fun(n, ms) * 10);
    }
    
    private static int fun(int n, Goods[] gs) {
        ResultNode[] results = new ResultNode[n + 1];
        for (int i = 0; i < results.length; i++) {
            results[i] = new ResultNode();
        }
        
        for (int i = 0; i < gs.length; i++) {
            for (int j = n; j >= 1; j--) {
                ResultNode rn = results[j];
                // 是否需要额外计算主件
                boolean needMajor = gs[i].majorIndex >= 0;
                // 需要主件且已存在
                boolean needAndContains = false;
                if (needMajor) {
                    needAndContains = rn.goodsIndexSet.contains(gs[i].majorIndex);
                    needMajor = !needAndContains;
                }
                int price = gs[i].price;
                int value = gs[i].value;
                if (needMajor) {
                    price += gs[gs[i].majorIndex].price;
                    value += gs[gs[i].majorIndex].value;
                }
                if (j >= price) {
                    int priceSum = results[j - price].priceSum + price;
                    if (needAndContains) {
                        priceSum += rn.priceSum;
                    }
                    if (priceSum > j) {
                        continue;
                    }
                    
                    int pv = results[j - price].priceAndValue + price * value;
                    if (pv > rn.priceAndValue) {
                        rn.priceAndValue = pv;
                        rn.goodsIndexSet.add(i);
                        if (needMajor) {
                            rn.goodsIndexSet.add(gs[i].majorIndex);
                        }
                        rn.priceSum = priceSum;
                    }
                }
            }
        }
        
        return results[results.length - 1].priceAndValue;
    }
    
    private static class Goods {
        // 价格
        int price;
        // 重要度
        int value;
        // 主件编号（-1为无）
        int majorIndex;
        
        public Goods(int price, int value, int majorIndex) {
            this.price = price;
            this.value = value;
            this.majorIndex = majorIndex;
        }
        
    }
    
    private static class ResultNode {
        int priceSum;
        int priceAndValue;
        Set<Integer> goodsIndexSet = new TreeSet<>();
        
        @Override
        public String toString() {
            return "ResultNode{" + "priceSum=" + priceSum + ", priceAndValue=" + priceAndValue + ", goodsIndexSet=" +
                   goodsIndexSet + '}';
        }
        
    }
    
}
