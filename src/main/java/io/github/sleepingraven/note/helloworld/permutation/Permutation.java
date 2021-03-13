package io.github.sleepingraven.note.helloworld.permutation;

/**
 * <h1>全排列的生成算法</h1>
 * <p>
 * 全排列的生成算法就是对于给定的字符集，用有效的方法将所有可能的全排列无重复无遗漏地枚举出来。
 * 任何n个字符的排列都可以与1-n的数字的排列一一对应，因此在此就以n个数字的排列为例说明排列的生成法。
 * <p>
 * n个字符的全体排列之间存在一个确定的线性顺序关系。所有的排列中除最后一个排列外，都有一个后继；除第一个排列外，都有一个前驱。
 * 每个排列的后继都可以从它的前驱经过最少的变化得到，全排列的生成算法就是从第一个排列开始逐个生成所有的排列的方法。
 * <p>
 * 全排列的生成法通常有以下几种：
 * <ul>
 *     <li>字典序法</li>
 *     <li>递增进位数制法</li>
 *     <li>递减进位数制法</li>
 *     <li>邻位交换法</li>
 *     <li>递归类算法</li>
 * </ul>
 * <h2>1.字典序法-效率高且顺序自然</h2>
 * 字典序法中，对于数字1、2、3......n的排列，不同排列的先后关系是从左到右逐个比较对应的数字的先后来决定的。例如对于5个数字的排列12354和12345，排列12345在前，
 * 排列12354在后。按照这样的规定，5个数字的所有的排列中最前面的是12345，最后面的是54321。<p>
 * 字典序算法如下：<p>
 * 设P是1～n的一个全排列:p=p1p2......pn=p1p2......pj-1pjpj+1......pk-1pkpk+1......pn<p>
 * 1）从排列的右端开始，找出第一个比右边数字小的数字的序号j（j从左端开始计算），即 j=max{i|pi < pi+1}<p>
 * 2）在pj的右边的数字中，找出所有比pj大的数中最小的数字pk，即 k=max{i|pi>pj}（右边的数从右至左是递增的，因此k是所有大于pj的数字中序号最大者）<p>
 * 3）对换pi，pk<p>
 * 4）再将pj+1......pk-1pkpk+1pn倒转得到排列p’’=p1p2.....pj-1pjpn.....pk+1pkpk-1.....pj+1，这就是排列p的下一个下一个排列。<p>
 * 例如839647521是数字1～9的一个排列。从它生成下一个排列的步骤如下：<p>
 * 自右至左找出排列中第一个比右边数字小的数字4 839647521<p>
 * 在该数字后的数字中找出比4大的数中最小的一个5 839647521<p>
 * 将5与4交换 839657421<p>
 * 将7421倒转 839651247<p>
 * 所以839647521的下一个排列是839651247。<p>
 * <p>
 * <h2>2.递增进位数制法</h2>
 * 在递增进位制数法中，从一个排列求另一个排列需要用到中介数。如果用 ki表示排列p1p2...pi...pn中元素pi的右边比pi小的数的个数，则排列的中介数就是对应的
 * 排列k1 ...... ki...... kn-1。<p>
 * 例如排列839647521的中介数是72642321，7、2、6、......分别是排列中数字8、3、9、......的右边比它小的数字个数。<p>
 * 中介数是计算排列的中间环节。已知一个排列，要求下一个排列，首先确定其中介数，一个排列的后继，其中介数是原排列中介数加1，需要注意的是，如果中介数的
 * 末位kn-1+1=2，则要向前进位，一般情形，如果ki+1=n-i+1，则要进位，这就是所谓的递增进位制。例如排列839647521的中介数是72642321，则下一个排列的中介数
 * 是67342221+1=67342300（因为1+1=2，所以向前进位，2+1=3，又发生进位，所以下一个中介数是67342300）。<p>
 * 得到中介数后，可根据它还原对应得排列。<p>
 * 算法如下：<p>
 * 中介数k1、k2、......、kn-1的各位数字顺序表示排列中的数字n、n-1、......、2在排列中距右端的的空位数，因此，要按k1、k2、......、kn-1的值从右向左
 * 确定n、n-1、......、2的位置，并逐个放置在排列中：i放在右起的ki+1位，如果某位已放有数字，则该位置不算在内，最后一个空位放1。<p>
 * 因此从67342300可得到排列849617523，它就是839647521的后一个排列。因为9最先放置，k1=6，9放在右起第7位，空出6个空位，然后是放8，k2=7，8放在右起第8位，
 * 但9占用一位，故8应放在右起第9位，余类推。<p>
 * <p>
 * <h2>3.递减进位制数法</h2>
 * 在递增进位制数法中，中介数的最低位是逢2进1，进位频繁，这是一个缺点。把递增进位制数翻转,就得到递减进位制数。<p>
 * 839647521的中介数是67342221(k1k2...kn-1)，倒转成为12224376(kn-1...k2k1)，这是递减进位制数的中介数：ki(i=n-1,n-2,...,2)位逢i向ki-1位进1。给定排列p，
 * p的下一个排列的中介数定义为p的中介数加1。例如p=839647521，p的中介数为12224376，p的下一个排列的中介数为12224376+1=12224377，由此得到p的下一个
 * 排列为893647521。<p>
 * 给定中介数，可用与递增进位制数法类似的方法还原出排列。但在递减进位制数中，可以不先计算中介数就直接从一个排列求出下一个排列。具体算法如下：<p>
 * 1）如果p(i)=n且i<>n，则p(i)与p(i-1)交换<p>
 * 2）如果p(n)=n，则找出一个连续递减序列9、8、......、i，将其从排列左端删除，再以相反顺序加在排列右端，然后将i-1与左边的数字交换
 * 例如p=893647521的下一个排列是983647521。求983647521的下一个排列时，因为9在最左边且第2位为8，第3位不是7，所以将8和9从小到大排于最右端364752189，
 * 再将7与其左方数字对调得到983647521的下一个排列是367452189。又例如求987635421的下一个排列，只需要将9876从小到大排到最右端并将5与其左方数字3对调，
 * 得到534216789。<p>
 * <p>
 * <h2>4.邻位对换法-效率最高但顺序不自然</h2>
 * 邻位对换法中下一个排列总是上一个排列某相邻两位对换得到的。以4个元素的排列为例，将最后的元素4逐次与前面的元素交换，可以生成4个新排列：<p>
 * 1 2 3 4, 1 2 4 3, 1 4 2 3, 4 1 2 3<p>
 * 然后将最后一个排列的末尾的两个元素交换，再逐次将排头的4与其后的元素交换，又生成四个新排列：<p>
 * 4 1 3 2, 1 4 3 2, 1 3 4 2, 1 3 2 4<p>
 * 再将最后一个排列的开头的两个元素交换，将4从后往前移：<p>
 * 3 1 2 4, 3 1 4 2, 3 4 1 2, 4 3 1 2<p>
 * 如此循环4!次既可求出全部排列。<p>
 * <p>
 * <h2>5.元素增值法（n进制法）-效率低</h2>
 * 1）从原始排列p=p1p2......pn开始，第n位加n-1，如果该位的值超过n，则将它除以n，用余数取代该位，并进位（将第n-1位加1）<p>
 * 2）再按同样方法处理n-1位，n-2位，......，直至不再发生进位为止，处理完一个排列就产生了一个新的排列<p>
 * 3）将其中有相同元素的排列去掉<p>
 * 4）当第一个元素的值>n则结束<p>
 * 以3个数1、2、3的排列为例：原始排列是1 2 3，从它开始，第3个元素是3，3+2=5，5 Mod 3=2，第2个元素是2，2+1=3，所以新排列是1 3 2。通过元素增值，顺序产生的
 * 排列是：1 2 3，1 3 2，2 1 1，2 1 3，2 2 2，2 3 1，2 3 3，3 1 2，3 2 1<p>
 * 有下划线的排列中存在重复元素，丢弃，余下的就是全部排列。<p>
 * <p>
 * <h2>6.递归类算法</h2>
 * 全排列的生成方法用递归方式描述比较简洁，实现的方法也有多种。<p>
 * 1）回溯法<p>
 * 回溯法通常是构造一棵生成树。以3个元素为例；树的结点有个数据，可取值是1、2、3。如果某个为0，则表示尚未取值。<p>
 * 初始状态是(0，0，0)，第1个元素值可以分别挑选1，2，3，因此扩展出3个子结点。用相同方法找出这些结点的第2个元素的可能值，如此反复进行，一旦出现新结点的3个
 * 数据全非零，那就找到了一种全排列方案。当尝试了所有可能方案，即获得了问题的解答。<p>
 * 2）递归算法<p>
 * 如果用P表示n个元素的排列，而Pi表示不包含元素i的排列，(i)Pi表示在排列Pi前加上前缀i的排列，那么，n个元素的排列可递归定义为：<p>
 * 如果n=1，则排列P只有一个元素i<p>
 * 如果n>1，则排列P由排列(i)Pi构成（i=1、2、....、n-1）。<p>
 * 根据定义，容易看出如果已经生成了k-1个元素的排列，那么，k个元素的排列可以在每个k-1个元素的排列Pi前添加元素i而生成。例如2个元素的排列是1 2和2 1，对与个
 * 元素而言，p1是2 3和3 2，在每个排列前加上1即生成1 2 3和1 3 2两个新排列，p2和p3则是1 3、3 1和1 2、2 1，按同样方法可生成新排列2 1 3、2 3 1和3 1 2、3 2 1。<p>
 * 3）循环移位法<p>
 * 如果已经生成了k-1个元素的排列，则在每个排列后添加元素k使之成为k个元素的排列，然后将每个排列循环左移（右移），每移动一次就产生一个新的排列。<p>
 * 例如2个元素的排列是1 2和2 1。在1 2 后加上3成为新排列1 2 3，将它循环左移可再生成新排列2 3 1、3 1 2，同样2 1 可生成新排列2 1 3、1 3 2和3 2 1。<p>
 *
 * @author Carry
 * @since 2020/1/6
 */
public interface Permutation {
}
