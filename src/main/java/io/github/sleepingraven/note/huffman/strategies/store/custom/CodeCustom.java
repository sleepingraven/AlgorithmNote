package io.github.sleepingraven.note.huffman.strategies.store.custom;

import io.github.sleepingraven.note.huffman.wrapper.HuffmanMapping;
import io.github.sleepingraven.note.huffman.wrapper.OutputAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Carry
 * @since 2020/6/10
 */
public class CodeCustom extends AbstractCustomStoreWay {
    
    /**
     * 逐个写入 Huffman 编码。每个编码有如下几部分：
     * <table>
     *     <tr>
     *         <th>字节数</th>
     *         <th>说明</th>
     *     </tr>
     *     <tr>
     *         <td>1</td>
     *         <td>当前的原符号</td>
     *     </tr>
     *     <tr>
     *         <td>1</td>
     *         <td>编码需要的字节数</td>
     *     </tr>
     *     <tr>
     *         <td>1</td>
     *         <td>末尾字节补 0 数</td>
     *     </tr>
     *     <tr>
     *         <td>若干</td>
     *         <td>Huffman 编码，每位表示一个编码值</td>
     *     </tr>
     * </table>
     */
    @Override
    protected void writeHuffmanMappingMultiple(OutputAdapter out, HuffmanMapping huffmanMapping) throws IOException {
        byte[] bs = new byte[32];
        for (Entry<Byte, byte[]> entry : huffmanMapping.getCode().entrySet()) {
            byte[] code = entry.getValue();
            // 编码的字节数
            int byteNum = code.length / 8;
            // 补 0 个数
            int margin = code.length % 8;
            if (margin != 0) {
                byteNum++;
                margin = 8 - margin;
            }
            
            int pos = 0;
            for (int i = 0; i < byteNum; i++) {
                for (int j = 0; j < 8; j++) {
                    bs[i] <<= 1;
                    if (pos < code.length) {
                        bs[i] |= code[pos++];
                    }
                }
            }
            
            writeCode(out, entry.getKey(), byteNum, margin, bs);
        }
    }
    
    private static void writeCode(OutputAdapter out, byte b, int byteNum, int margin, byte[] bs) throws IOException {
        out.write(b);
        out.write((byte) byteNum);
        out.write((byte) margin);
        out.write(bs, 0, byteNum);
    }
    
    @Override
    public HuffmanMapping parseHuffmanMappingMultiple(byte[] bs) {
        Map<Byte, byte[]> huffmanCodeMap = new HashMap<>((int) (256 / 0.75));
        for (int p = 0; p < bs.length; p++) {
            byte b = bs[p++];
            int byteNum = bs[p++];
            int margin = bs[p++];
            
            int pos = 0;
            byte[] code = new byte[8 * byteNum - margin];
            for (int i = 0; i < byteNum - 1; i++) {
                for (int j = 7; j >= 0; j--) {
                    code[pos++] = (byte) ((bs[p] >>> j) & 1);
                }
                p++;
            }
            for (int m = 7; m >= margin; m--) {
                code[pos++] = (byte) ((bs[p] >>> m) & 1);
            }
            
            huffmanCodeMap.put(b, code);
        }
        return new HuffmanMapping(huffmanCodeMap);
    }
    
}
