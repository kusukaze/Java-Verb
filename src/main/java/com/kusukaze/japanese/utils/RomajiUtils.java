package com.kusukaze.japanese.utils;

import com.kusukaze.japanese.utils.constants.RomajiMapUtils;

/**
 * 罗马字工具类，用于假名和罗马字之间的转换
 * @author Maki Kusukaze
 * @since 2024-09-25
 */
public class RomajiUtils {
    private RomajiUtils() {}
    /**
     * 预处理，删除首尾空格，转成小写，将全角字母和空格转为半角
     * @param romaji 待预处理的罗马字
     * @return 预处理后的罗马字
     */
    private static String pretreatRomaji(String romaji) {
        romaji = romaji.trim().toLowerCase();
        char[] chars = romaji.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            // 全角空格转半角
            if(chars[i] == 12288){
                chars[i] = (char)32;
            }
            // 全角字母转半角
            else if(chars[i] > 65280 && chars[i] < 65375) {
                chars[i] = (char)(chars[i] - 65248);
            }
        }
        return new String(chars);
    }

    /**
     * 将已预处理过的罗马字转化为平假名
     * @param romaji 已经过预处理的罗马字
     * @return 转化后的假名
     */
    private static String pretreatedRomaji2Hiragana(String romaji) {
        int i,j,k;
        int n = romaji.length();
        StringBuilder ans = new StringBuilder();
        i = 0;
        while(i < n) {
            char ch = romaji.charAt(i);

            // 处理字母
            if(Character.isLetter(ch)) {
                boolean chIsVowel = ("aeiou".indexOf(ch) > -1);
                // 元音字母，转化当前的一个字母即可。下一轮跳转至下一字母。
                if(chIsVowel) {
                    ans.append(RomajiMapUtils.getHiraganaByRomaji(String.valueOf(ch)));
                    i++;
                    continue;
                }
                // 辅音字母，重复两次且不为n，将当前字母转化为促音。tc也视为有效促音。下一轮跳转至下一字母。
                if(i + 1 < n && ch != 'n' && (ch == romaji.charAt(i+1) || romaji.startsWith("tc", i))) {
                    ans.append("っ");
                    i++;
                    continue;
                }
                // 辅音字母n，从当前字母开始最多扩展至3个字母进行匹配，且顺序从长到短。下一轮跳过匹配成功的字母。
                // 若匹配失败，则保留当前字母。下一轮跳转至下一字母。
                if(ch == 'n') {
                    boolean success = false;
                    k = Math.min(i+2,n-1);

                    for(j=k;j>=i;j--) {
                        char chCurr = romaji.charAt(j);
                        if(!Character.isLetter(chCurr)) {
                            continue;
                        }
                        String hira = RomajiMapUtils.getHiraganaByRomaji(romaji.substring(i,j+1));
                        if(hira != null) {
                            success = true;
                            ans.append(hira);
                            i = j + 1;
                            break;
                        }
                    }
                    if(!success) {
                        ans.append(romaji.charAt(i));
                        i++;
                    }
                    continue;
                }

                // 辅音字母（已排除促音和拨音），从当前字母开始最多扩展至4个字母进行匹配，且顺序从短到长。下一轮跳过匹配成功的字母。
                // 若匹配失败，则保留当前字母。下一轮跳转至下一字母。
                boolean success = false;
                for(j=i+1;j<i+5 && j<n;j++) {
                    char chCurr = romaji.charAt(j);
                    if(!Character.isLetter(chCurr)) {
                        break;
                    }
                    String hira = RomajiMapUtils.getHiraganaByRomaji(romaji.substring(i,j+1));
                    if(hira != null) {
                        success = true;
                        ans.append(hira);
                        i = j + 1;
                        break;
                    }
                }
                if(!success) {
                    ans.append(romaji.charAt(i));
                    i++;
                }
            }
            // 处理非字母符号，其中单引号不保留，减号转化为长音符号，其余原样保留。
            // 一次性处理多个符号，直到遇到字母，以减少字符串拼接次数。下一轮跳转至字母。
            else {
                for(j=i+1;j<n;j++) {
                    if(Character.isLetter(romaji.charAt(j))) {
                        break;
                    }
                }
                ans.append(romaji.substring(i,j)
                        .replace("'","").replace("-","ー"));
                i = j;
            }
        }
        return String.valueOf(ans);
    }

    /**
     * 将罗马字转化为平假名<p>
     * 单引号为分隔符，减号为长音符号。
     * 无法转化的字母或符号将被保留。
     * 删除首尾空格，转成小写，将全角字母和空格转为半角。
     * @param romaji 待转化的罗马字
     * @return 转化后的假名
     */
    public static String romaji2Hiragana(String romaji) {
        if(romaji == null) {
            return null;
        }
        return pretreatedRomaji2Hiragana(pretreatRomaji(romaji));
    }

    /**
     * 将平假名转化为罗马字
     * @param hiragana 待转化的平假名
     * @return 转化后的罗马字
     */
    private static String hiragana2Romaji(String hiragana) {
        StringBuilder ans = new StringBuilder();
        int n = hiragana.length();
        int i = 0;
        while(i < n) {
            int j;
            // 贪心匹配，最多匹配2个字符（拗音）
            for(j=1;j>=0;j--) {
                if(i + j >= n) {
                    continue;
                }
                // 促音先不转化
                if("っ".equals(hiragana.substring(i,i+j+1))) {
                    j = -1; // 标记为匹配失败
                    break;
                }

                String currRomaji = RomajiMapUtils.getRomajiByHiragana(hiragana.substring(i,i+j+1));

                // 未匹配到该假名
                if(currRomaji == null) {
                    continue;
                }

                if(ans.length() > 0 && "n".indexOf(ans.charAt(ans.length()-1)) != -1
                        && "aeiouy".indexOf(currRomaji.charAt(0)) != -1) { // n+元音或y，加分隔符
                    currRomaji = "'" + currRomaji;
                }
                ans.append(currRomaji);
                i += (j + 1);
                break;
            }
            // 匹配失败
            if(j < 0) {
                ans.append(hiragana.charAt(i));
                i++;
            }
        }

        // 处理促音
        for(i=0;i<n-1;i++) {
            char currChar = ans.charAt(i);
            char nextChar = ans.charAt(i+1);
            // 促音，添加辅音
            if(currChar == 'っ') {
                // 若后面的字母为c，则用t表示促音
                if(nextChar == 'c') {
                    ans.setCharAt(i,'t');
                }
                // 辅音，双写后面的字母
                else if(Character.isLetter(nextChar) && "aeiou".indexOf(nextChar) == -1) {
                    ans.setCharAt(i,nextChar);
                }
                // 后面的字母为元音字母或符号，则保留xtu
            }
        }

        return String.valueOf(ans).replace("っ","xtu").replace("ー","-");
    }

    /**
     * 将假名转化为罗马字
     * @param kana 待转化的假名
     * @return 转化后的罗马字
     */
    public static String kana2Romaji(String kana) {
        if(kana == null) {
            return null;
        }
        return hiragana2Romaji(GojuuonzuUtils.katakana2Hiragana(kana));
    }
}
