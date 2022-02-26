## Note Structure

- <code>draft</code> OJ 代码编辑区域
- <code>note</code> 笔记/练习
    - <code>demo</code>，<code>helloworld</code> 一些题目和基本数据结构的练习
    - <code>huffman</code> 用 Huffman 编码实现文件压缩
    - <code>practice</code> 常见/常用算法实现
- <code>util</code> 工具/模板
    - <code>ac</code>，<code>advanced</code> 常用数据结构/工具类
    - <code>common</code>，<code>function</code>，<code>primitive</code> 基础工具类
    - <code>datastructure</code> 二叉树、链表的生成和格式化工具

<br />

## 1. <code>note</code>

### <code>huffman</code>

压缩/解压文件的程序实现

code:

```java
String srcPath = "D:/new Text Document.pdf";
StoreWay storeWay = new CodeCustom();
CodingMethod codingMethod = new DefaultCodingMethod();

Compressor compressor = new Compressor(1);
String destPath = compressor.compress(srcPath, storeWay, codingMethod);
Decompressor decompressor = new Decompressor();
String decompressPath = decompressor.decompress(destPath, codingMethod);

System.out.println(destPath);
System.out.println(decompressPath);
```

output:

```
……
用时：        834ms


D:\new Text Document.h_tar
D:\new Text Document(1).pdf
```

### <code>practice</code>

#### <code>CircularVector</code>

用来在矩阵中以螺旋的顺序生成一组下标

code:

```java
final int n = 4;
int[][] matrix = new int[n][n];
CircularVector cv = new CircularVector(0, n - 1, 0, n - 1);
for (int i = 1; i <= n * n; i++) {
    matrix[cv.x()][cv.y()] = i;
    cv.next();
}

String str = Arrays.stream(matrix).map(Arrays::toString).collect(Collectors.joining("\n"));
System.out.println(str);
```

output:

```
[1, 2, 3, 4]
[12, 13, 14, 5]
[11, 16, 15, 6]
[10, 9, 8, 7]
```

## 2. <code>util</code>

### <code>ac</code>

#### <code>DataGenerator</code>

用来

- 根据数组创建二叉树、链表
- 将字符串解析成数组

code:

```java
int[][] matrix = DataGenerator.parseIntArray("[[1,2,3],[4,5,6],[7,8,9]]", int[][].class);

String str = Arrays.stream(matrix).map(Arrays::toString).collect(Collectors.joining("\n"));
System.out.println(str);
```

output:

```
[1, 2, 3]
[4, 5, 6]
[7, 8, 9]
```

### <code>datastructure</code>

用来

- 生成二叉树、链表
- 格式化二叉树、链表

code:

```java
final int n = 31;
Integer[] a = IntStream.range(1, n + 1).boxed().toArray(i -> new Integer[n]);
TreeNode treeNode = DataGenerator.buildTree(a);

String str = treeNode.toString();
System.out.println(str);
```

output:

```
                              1
                      /               \
              2                               3
          /       \                       /       \
      4               5               6               7
    /   \           /   \           /   \           /   \
  8       9      10      11      12      13      14      15
 / \     / \     / \     / \     / \     / \     / \     / \
16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31
```

