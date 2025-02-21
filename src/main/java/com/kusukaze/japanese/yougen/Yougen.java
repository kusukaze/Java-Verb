package com.kusukaze.japanese.yougen;

/**
 * 用言类
 * @author Hanatsuki Kokome
 * @since 2024-09-20
 */
public interface Yougen {
    /**
     * 返回此单词的内容
     * @return 一个字符串，内容为此单词
     */
    String getName();

    /**
     * 返回此单词的て形
     * @return 变为て形后的字符串，若无法变形则返回null
     */
    String teForm();

    /**
     * 返回此单词的ない形
     * @return 变为ない形后的字符串，若无法变形则返回null
     */
    String naiForm();

    /**
     * 返回此单词的です・ます形
     * @return 变为です・ます形后的字符串，若无法变形则返回null
     */
    String desuMasuForm();

    /**
     * 返回此单词的た形
     * @return 变为た形后的字符串，若无法变形则返回null
     */
    String taForm();
}
