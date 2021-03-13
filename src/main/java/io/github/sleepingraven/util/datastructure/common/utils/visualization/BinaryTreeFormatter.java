package io.github.sleepingraven.util.datastructure.common.utils.visualization;

import lombok.NonNull;
import io.github.sleepingraven.util.datastructure.binarytree.BinaryTree;
import io.github.sleepingraven.util.datastructure.common.Formatter;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 格式化 {@link BinaryTree}
 *
 * @author Carry
 * @since 2020/6/21
 */
public class BinaryTreeFormatter<T extends BinaryTree> implements Formatter<T> {
    private final Function<? super T, String> node2String;
    private final Function<? super T, ? extends T> leftGetter;
    private final Function<? super T, ? extends T> rightGetter;
    
    public BinaryTreeFormatter(@NonNull Function<? super T, ?> toDisplay,
            @NonNull Function<? super T, ? extends T> leftGetter,
            @NonNull Function<? super T, ? extends T> rightGetter) {
        this.node2String = toDisplay.andThen(String::valueOf);
        this.leftGetter = leftGetter;
        this.rightGetter = rightGetter;
    }
    
    /*
   树的结构示例：
             1
         /       \
     2               3
   /   \           /   \
 4       5       6       7
/ \     / \     / \     / \
1   1   1   1   1   1   1   1
   */
    
    @Override
    public String format(T root) {
        if (root == null) {
            return "Empty tree.";
        }
        
        final int depth = getDepth(root);
        
        // 最后一行的最大节点数为 n' = pow(2,depth-1)
        final int height = 2 * depth - 1;
        final int width = ((1 << (depth - 1)) - 1) * 4 + 1;
        // 创建表格，将所有单元格初始化为空格
        String[][] grid = new String[height][width];
        for (String[] row : grid) {
            Arrays.fill(row, " ");
        }
        
        // preorder 遍历 root，将节点的显示内容填入相应的位置
        placeTree(root, 0, width / 2, grid, depth);
        
        // 拼接填好的单元格
        return Arrays.stream(grid).map(rowFormatter).collect(rowCollector);
    }
    
    /**
     * 表格每行的显示策略
     */
    public static Function<String[], CharSequence> rowFormatter;
    /**
     * 所有行的合并策略
     */
    public static Collector<CharSequence, ?, String> rowCollector;
    
    /**
     * 串首趋近于预期位置（左对齐），串过长时相邻节点间最少保留一个空格字符
     */
    public static final Function<String[], CharSequence> ALIGNING_LEFT = row -> {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if (buffer.length() == i || !" ".equals(row[i])) {
                buffer.append(row[i]);
            }
        }
        return buffer;
    };
    /**
     * 串尾趋近于预期位置（右对齐），串过长时相邻节点间最少保留一个空格字符
     */
    public static final Function<String[], CharSequence> ALIGNING_RIGHT0 = row -> {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if (!" ".equals(row[i])) {
                int length = buffer.length();
                // 期望腾出 exceed 个字符的空间
                int exceed = Math.min(row[i].length() - 1, i);
                // 不断回退
                for (int l = length - exceed; length > l; length--) {
                    if (buffer.charAt(length - 1) != ' ') {
                        break;
                    }
                }
                buffer.setLength(length);
                if (length > 0 && buffer.charAt(length - 1) != ' ') {
                    buffer.append(' ');
                }
                buffer.append(row[i]);
            } else if (buffer.length() == i) {
                buffer.append(row[i]);
            }
        }
        return buffer;
    };
    /**
     * 更简洁的实现
     */
    public static final Function<String[], CharSequence> ALIGNING_RIGHT = row -> {
        StringBuilder buffer = new StringBuilder();
        // 可以覆盖的最低位置
        int safe = 0;
        for (int i = 0; i < row.length; i++) {
            if (!" ".equals(row[i])) {
                // 预期添加位置
                int expected = buffer.length() - (row[i].length() - 1);
                int length = Math.max(expected, safe);
                buffer.setLength(length);
                if (length > 0 && buffer.charAt(length - 1) != ' ') {
                    buffer.append(' ');
                }
                buffer.append(row[i]);
                safe = buffer.length();
            } else if (buffer.length() == i) {
                buffer.append(row[i]);
            }
        }
        return buffer;
    };
    /**
     * 串较短时，串尾与上一个节点的串尾保持预期的距离；移除了后置的空格字符
     */
    public static final Function<String[], CharSequence> MAINTAINING = row -> {
        StringBuilder buffer = new StringBuilder();
        // 上一个节点位置
        int anchor = -1;
        for (int i = 0; i < row.length; i++) {
            if (!" ".equals(row[i])) {
                int distance = i - anchor;
                if (distance <= row[i].length()) {
                    if (buffer.length() != 0) {
                        buffer.append(' ');
                    }
                    buffer.append(row[i]);
                } else {
                    buffer.append(String.format("%" + distance + "s", row[i]));
                }
                anchor = i;
            }
        }
        return buffer;
    };
    /**
     * 除最后一行的第一个节点外（如果存在），其他节点不会接触行首（即最少保留一个空格）
     */
    public static final Function<String[], CharSequence> MAINTAINING_INDENTATION = row -> {
        StringBuilder buffer = new StringBuilder();
        // 上一个节点位置
        int anchor = -1;
        for (int i = 0; i < row.length; i++) {
            if (!" ".equals(row[i])) {
                int distance = i - anchor;
                if (i != 0) {
                    buffer.append(' ');
                    distance--;
                }
                buffer.append(String.format("%" + distance + "s", row[i]));
                anchor = i;
            }
        }
        return buffer;
    };
    /**
     * {@link BinaryTreeFormatter#MAINTAINING} 的另一种实现
     */
    public static final Function<String[], CharSequence> MAINTAINING2 = row -> {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if (!" ".equals(row[i])) {
                int reserved = i - buffer.length() + 1;
                if (reserved <= row[i].length()) {
                    if (buffer.length() != 0) {
                        buffer.append(' ');
                    }
                    buffer.append(row[i]);
                } else {
                    buffer.append(String.format("%" + reserved + "s", row[i]));
                }
            }
        }
        return buffer;
    };
    /**
     * {@link BinaryTreeFormatter#MAINTAINING_INDENTATION} 的另一种实现
     */
    public static final Function<String[], CharSequence> MAINTAINING_INDENTATION2 = row -> {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if (!" ".equals(row[i])) {
                int reserved = i - buffer.length() + 1;
                if (i != 0) {
                    buffer.append(' ');
                    reserved--;
                }
                // reserved <= 0 不影响结果
                buffer.append(String.format("%" + reserved + "s", row[i]));
            }
        }
        return buffer;
    };
    
    public static final Collector<CharSequence, ?, String> JOINING_ONLY = Collectors.joining("\n");
    public static final Collector<CharSequence, ?, String> JOINING_AND_WRAP_FINALLY =
            Collectors.joining("\n", "", "\n");
    
    static {
        rowFormatter = MAINTAINING_INDENTATION;
        rowCollector = JOINING_AND_WRAP_FINALLY;
    }
    
    /**
     * 把节点内容放到表格中的对应位置
     */
    private void placeTree(T root, int r, int c, String[][] grid, final int depth) {
        // 保存当前节点
        grid[r][c] = node2String.apply(root);
        
        // 计算当前层数
        int currLevel = r / 2;
        // 若到了最后一层，无需再处理下一层
        if (currLevel == depth) {
            return;
        }
        // 计算下一行与当前的元素的列索引间隔
        int gap = 1 << (depth - currLevel - 2);
        
        // 若有 left，则记录「/」和左子树
        T left = leftGetter.apply(root);
        if (left != null) {
            grid[r + 1][c - gap] = "/";
            placeTree(left, r + 2, c - 2 * gap, grid, depth);
        }
        
        // 若有 right，则记录「\」和右子树
        T right = rightGetter.apply(root);
        if (right != null) {
            grid[r + 1][c + gap] = "\\";
            placeTree(right, r + 2, c + 2 * gap, grid, depth);
        }
    }
    
    private int getDepth(T root) {
        return root == null ? 0 : (1 + Math.max(getDepth(leftGetter.apply(root)), getDepth(rightGetter.apply(root))));
    }
    
}
