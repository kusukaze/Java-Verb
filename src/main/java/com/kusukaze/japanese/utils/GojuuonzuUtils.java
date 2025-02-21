package com.kusukaze.japanese.utils;

import java.util.HashMap;

/**
 * 五十音图工具类，用于假名之间的转换
 * @author Hanatsuki Kokome
 * @since 2024-09-21
 */
public class GojuuonzuUtils {
    private static final String hiraganaTable =
            "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほ" +
            "まみむめもやいゆえよらりるれろわゐうゑをがぎぐげござじずぜぞ" +
            "だぢづでどばびぶべぼぱぴぷぺぽぁぃぅぇぉゃゅょっん";
    private static final String katakanaTable =
            "アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホ" +
            "マミムメモヤイユエヨラリルレロワヰウヱヲガギグゲゴザジズゼゾ" +
            "ダヂヅデドバビブベボパピプペポァィゥェォャュョッン";

    private static final String hasLengthKana =
            "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほ" +
            "まみむめもやいゆえよらりるれろわゐうゑをがぎぐげござじずぜぞ" +
            "だぢづでどばびぶべぼぱぴぷぺぽっん" +
            "アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホ" +
            "マミムメモヤイユエヨラリルレロワヰウヱヲガギグゲゴザジズゼゾ" +
            "ダヂヅデドバビブベボパピプペポッンー";

    private static final String noLengthKana = "ぁぃぅぇぉゃゅょァィゥェォャュョ";
    private static final HashMap<Character,Character> hiraganaKatakanaMap = new HashMap<>();
    private static final HashMap<Character,Character> katakanaHiraganaMap = new HashMap<>();

    static {
        int n = hiraganaTable.length();
        for(int i=0;i<n;i++) {
            hiraganaKatakanaMap.put(hiraganaTable.charAt(i),katakanaTable.charAt(i));
            katakanaHiraganaMap.put(katakanaTable.charAt(i),hiraganaTable.charAt(i));
        }
    }

    private GojuuonzuUtils() {}

    /**
     * 将片假名转化为平假名<p>
     * 对于非假名字符，维持原状
     *
     * @param katakana 待转化的片假名
     * @return 转化后的平假名
     */
    public static String katakana2Hiragana(String katakana)
    {
        if(katakana == null) {
            return null;
        }
        StringBuilder ans = new StringBuilder();
        int n = katakana.length();
        for(int i=0;i<n;i++) {
            Character kata = katakanaHiraganaMap.get(katakana.charAt(i));
            if(kata != null) {
                ans.append(kata);
            }
            else {
                ans.append(katakana.charAt(i));
            }
        }
        return String.valueOf(ans);
    }

    /**
     * 将平假名转化为片假名<p>
     * 对于非假名字符，维持原状
     *
     * @param hiragana 待转化的片假名
     * @return 转化后的平假名
     */
    public static String hiragana2Katakana(String hiragana)
    {
        if(hiragana == null) {
            return null;
        }
        StringBuilder ans = new StringBuilder();
        for(int i=0;i<hiragana.length();i++)
        {
            Character hira = hiraganaKatakanaMap.get(hiragana.charAt(i));
            if(hira != null) {
                ans.append(hira);
            }
            else {
                ans.append(hiragana.charAt(i));
            }
        }
        return String.valueOf(ans);
    }

    /**
     * 将平假名转换为指定段上的假名<p>
     * 仅用于动词变形，不完全按照五十音图变化
     * は行假名将转化为あ行
     * 若结果为あ，则转化为わ
     * 若匹配失败，则原样返回
     *
     * @param hiragana 待转化的平假名
     * @param dan 需要转化为哪一段，取值范围为「あいうえお」
     * @return 转化后的平假名
     */
    public static String toSpecificDan(char hiragana,char dan)
    {
        String dans = "あいうえお";
        int pos = hiraganaTable.indexOf(hiragana);
        if(pos < 0) {
            return String.valueOf(hiragana);
        }
        int row = pos / 5;
        int line = dans.indexOf(dan);
        if(line < 0) {
            return String.valueOf(hiragana);
        }
        if(row == 5) {
            row = 0;
        }
        if(row == 0 && line == 0) {
            return "わ";
        }
        return String.valueOf(hiraganaTable.charAt(row*5+line));
    }

    /**
     * 判断字符是否为平假名
     *
     * @param ch 待判断字符
     * @return 判断结果
     */
    public static boolean isHiragana(char ch) {
        return hiraganaTable.indexOf(ch) != -1;
    }

    /**
     * 判断字符是否为片假名
     *
     * @param ch 待判断字符
     * @return 判断结果
     */
    public static boolean isKatagana(char ch) {
        return katakanaTable.indexOf(ch) != -1 || ch == 'ー';
    }

    /**
     * 判断字符是否为假名
     *
     * @param ch 待判断字符
     * @return 判断结果
     */
    public static boolean isKana(char ch) {
        return isHiragana(ch) || isKatagana(ch);
    }

    /**
     * 获取假名的长度
     *
     * @param kana 待判断假名
     * @return 长度
     */
    public static int length(String kana) {
        int n = kana.length();
        int ans = 0;
        for(int i=0;i<n;i++) {
            char ch = kana.charAt(i);
            if(hasLengthKana.indexOf(ch) != -1) {
                ans++;
            }
        }
        return ans;
    }
}
