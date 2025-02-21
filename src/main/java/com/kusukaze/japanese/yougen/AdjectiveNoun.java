package com.kusukaze.japanese.yougen;

/**
 * 形容动词类（继承用言类）
 * @author Hanatsuki Kokome
 * @since 2024-09-27
 */
public class AdjectiveNoun implements Yougen {
    private String name;
    public AdjectiveNoun() {super();}

    public AdjectiveNoun(String adjectiveNounName) {
        name = adjectiveNounName;
    }

    /**
     * 形容动词的通用工具函数
     * @param suffix 变形时添加的后缀
     * @return 变化后的字符串
     */
    private String adjectiveNounUtil(String suffix) {
        if(name == null || name.length() < 2) {
            return null;
        }
        return name + suffix;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String teForm() {
        return adjectiveNounUtil("で");
    }

    @Override
    public String naiForm() {
        return adjectiveNounUtil("ではない");
    }

    /**
     * 返回此单词的です形
     * @return 变为です形后的字符串
     */
    public String desuForm() {
        return adjectiveNounUtil("です");
    }

    @Override
    public String desuMasuForm() {
        return desuForm();
    }

    @Override
    public String taForm() {
        return adjectiveNounUtil("だった");
    }

    /**
     * 返回此单词的ず形
     * @return 变为ず形后的字符串
     */
    public String zuForm() {
        return adjectiveNounUtil("ならず");
    }
}
