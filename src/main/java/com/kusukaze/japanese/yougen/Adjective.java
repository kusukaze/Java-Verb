package com.kusukaze.japanese.yougen;

/**
 * 形容词类（继承用言类）
 * @author Hanatsuki Kokome
 * @since 2024-09-20
 */
public class Adjective implements Yougen {
    private String name;
    public Adjective() {super();}

    public Adjective(String adjectiveName) {
        name = adjectiveName;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * 形容词的通用工具函数
     * @param suffix 变形时添加的后缀
     * @return 变化后的字符串
     */
    private String adjectiveUtil(String suffix) {
        if(name == null || name.length() < 2 || !name.endsWith("い")) {
            return null;
        }
        int n = name.length();

        if(name.endsWith("いい") && !name.endsWith("かわいい")) {
            return name.substring(0,n-2) + "よ" + suffix;
        }
        return name.substring(0,n-1) + suffix;
    }
    @Override
    public String teForm() {
        return adjectiveUtil("くて");
    }

    @Override
    public String naiForm() {
        return adjectiveUtil("くない");
    }

    /**
     * 返回此单词的です形
     * @return 变为です形后的字符串
     */
    public String desuForm() {
        if(name == null || name.length() < 2 || !name.endsWith("い")) {
            return null;
        }
        return name + "です";
    }

    @Override
    public String desuMasuForm() {
        return desuForm();
    }

    @Override
    public String taForm() {
        return adjectiveUtil("かった");
    }

    /**
     * 返回此单词的ば形
     * @return 变为ば形后的字符串
     */
    public String baForm() {
        return adjectiveUtil("ければ");
    }

    /**
     * 返回此单词的推量形
     * @return 变为推量形后的字符串
     */
    public String volitionalForm() {
        return adjectiveUtil("かろう");
    }

    /**
     * 返回此单词的ず形
     * @return 变为ず形后的字符串
     */
    public String zuForm() {
        return adjectiveUtil("からず");
    }
}
