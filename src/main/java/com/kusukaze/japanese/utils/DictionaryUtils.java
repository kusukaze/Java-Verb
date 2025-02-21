package com.kusukaze.japanese.utils;

import com.kusukaze.japanese.utils.constants.NekoEmojiConstants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 字典工具类，用于查询单词
 * @author Hanatsuki Kokome
 * @since 2024-09-21
 */
public class DictionaryUtils {
    public static List<HashMap<String,String>> verbDictionary = new ArrayList<>();
    // 动词词典加载路径
    private static final String verbDictionaryPath = "src/main/resources/dictionary/verbDictionary.txt";

    static {
        loadVerbDictionary(true);
    }

    private DictionaryUtils() {}


    /**
     * 读取并加载动词字典至内存<p>
     * 当字典为空，或启用强制加载时，将进行加载，否则不加载
     * 一旦加载，将覆盖原有内容
     *
     * @param forceLoad 是否强制加载
     * @return 字典条目数，-1表示加载失败，-2表示未加载
     */
    public static int loadVerbDictionary(boolean forceLoad) {
        long startTime = System.currentTimeMillis();
        if(!forceLoad && verbDictionary.size() > 0) {
            return -2;
        }
        try {
            System.out.println("正在加载字典喵 " + NekoEmojiConstants.WORKING);
            BufferedReader reader = new BufferedReader(new FileReader(verbDictionaryPath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if(values.length < 3) {
                    continue;
                }
                HashMap<String,String> verb = new HashMap<>();
                // 没有汉字，则将读音赋值给汉字
                if(values[0].isEmpty()) {
                    values[0] = values[1];
                }
                // 词尾不为する的サ便动词补上する
                if(values[2].startsWith("vs") && !values[1].endsWith("する")) {
                    values[0] += "する";
                    values[1] += "する";
                }
                verb.put("kaki",values[0]);
                verb.put("yomi",GojuuonzuUtils.katakana2Hiragana(values[1])); // 读音转为平假名
                verb.put("type",values[2]);
                verbDictionary.add(verb);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("字典加载异常 " + NekoEmojiConstants.FAILURE + "\n");
            return -1;
        }
        long runTime = System.currentTimeMillis() - startTime;
        System.out.println("字典加载成功" + NekoEmojiConstants.SUCCESS + "   耗时：" + runTime + " ms\n");

        return verbDictionary.size();
    }

    /**
     * 查询动词字典并返回动词类型<p>
     * 若动词字典为空，则加载后再查询
     *
     * @param verb 待查询的动词
     * @return 该动词的类型，若未查找到则返回null
     */
    public static String getVerbType(String verb) {
        if(verb == null) {
            return null;
        }
        for (HashMap<String, String> stringStringHashMap : verbDictionary) {
            if(stringStringHashMap.get("kaki").equals(verb)) {
                return stringStringHashMap.get("type");
            }
        }
        return null;
    }

    /**
     * 根据汉字或假名查询动词
     *
     * @param keyword 待查询的假名(可含汉字，不区分平片假名)
     * @return 查询到的动词列表
     */
    public static List<String> getVerbNameByKanjiOrKana(String keyword) {
        keyword = GojuuonzuUtils.katakana2Hiragana(keyword);
        List<String> verbList = new ArrayList<>();
        if(keyword == null) {
            return verbList;
        }
        for (HashMap<String, String> stringStringHashMap : verbDictionary) {
            // 优先匹配写法，匹配不到时再匹配读音
            String currentKaki = GojuuonzuUtils.katakana2Hiragana(stringStringHashMap.get("kaki"));
            if(currentKaki.equals(keyword)) {
                verbList.add(stringStringHashMap.get("kaki"));
                continue;
            }
            String currentYomi = GojuuonzuUtils.katakana2Hiragana(stringStringHashMap.get("yomi"));
            if(currentYomi.equals(keyword)) {
                verbList.add(stringStringHashMap.get("kaki"));
            }
        }
        return verbList;
    }
}
