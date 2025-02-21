package com.kusukaze.japanese.yougen;

import com.kusukaze.japanese.utils.DictionaryUtils;
import com.kusukaze.japanese.utils.GojuuonzuUtils;
import com.kusukaze.japanese.utils.constants.NekoEmojiConstants;
import com.kusukaze.japanese.utils.constants.VerbTypeConstants;

/**
 * 动词类（继承用言类）
 * @author Hanatsuki Kokome
 * @since 2024-09-20
 */
public class Verb implements Yougen {
    private String name;
    private String verbType;
    public Verb() {super();}
    public Verb(String verbName) {
        name = verbName;
        verbType = DictionaryUtils.getVerbType(verbName);
    }

    @Override
    public String getName() {
        return name;
    }
    /**
     * 动词的通用工具函数
     * @param specificDan 一类动词最后的假名需要变为哪一段
     * @param godanSuffix 一类动词后缀
     * @param ichidanSuffix 二类动词后缀
     * @param suruSuffix する后缀
     * @param kuruSuffix 来る后缀
     * @param kuruNewKana 来る写作假名くる时，く对应的新假名
     * @return 变化后的字符串
     */
    private String verbUtil(char specificDan,String godanSuffix,String ichidanSuffix,
                            String suruSuffix,String kuruSuffix,char kuruNewKana) {
        if(verbType == null) {
            return null;
        }
        int n = name.length();

        // 一类动词：将最后一个假名改为specificDan段，并添加后缀godanSuffix
        if(verbType.startsWith(VerbTypeConstants.GODAN)) {
            return name.substring(0,n-1) +
                    GojuuonzuUtils.toSpecificDan(name.charAt(n-1),specificDan) + godanSuffix;
        }
        // 二类动词：去掉最后的假名る，并添加后缀ichidanSuffix
        if(verbType.startsWith(VerbTypeConstants.ICHIDAN)) {
            return name.substring(0,n-1) + ichidanSuffix;
        }
        // する：去掉最后的する，并添加后缀suruSuffix
        if(verbType.startsWith(VerbTypeConstants.SURU)) {
            return name.substring(0,n-2) + suruSuffix;
        }
        // 来る：去掉最后的くる，并添加kuruNewKana和后缀kuruSuffix。若写作汉字 来る，则直接去る加后缀
        if(verbType.startsWith(VerbTypeConstants.KURU)) {
            char lastButOneChar = name.charAt(n-2);
            if(lastButOneChar != 'く') {
                return name.substring(0,n-1) + kuruSuffix;
            }
            return name.substring(0,n-2) + kuruNewKana + kuruSuffix;
        }
        // 古语-ずる：转化为じる，再视为二类动词
        if(verbType.startsWith(VerbTypeConstants.ZURU)) {
            return name.substring(0,n-2) + "じ" + ichidanSuffix;
        }
        // 古语-上二段：将最后一个假名改为い段+る，再视为二类动词。若只有一个汉字，则保留汉字不变化
        if(verbType.startsWith(VerbTypeConstants.NIDAN) && verbType.endsWith("k")) {
            return name.substring(0,n-1) +
                    GojuuonzuUtils.toSpecificDan(name.charAt(n-1),'い') + ichidanSuffix;
        }
        // 古语-下二段：将最后一个假名改为え段+る，再视为二类动词。若只有一个汉字，则保留汉字不变化
        if(verbType.startsWith(VerbTypeConstants.NIDAN) && verbType.endsWith("s")) {
            return name.substring(0,n-1) +
                    GojuuonzuUtils.toSpecificDan(name.charAt(n-1),'え') + ichidanSuffix;
        }
        return null;
    }
    @Override
    public String teForm() {
        if(verbType == null) {
            return null;
        }
        int n = name.length();

        // 行く
        if(verbType.equals(VerbTypeConstants.IKU)) {
            return name.substring(0,n-1) + "って";
        }
        // 問う
        if(verbType.equals(VerbTypeConstants.TOU)) {
            return name + "て";
        }
        // 一类动词
        if(verbType.startsWith(VerbTypeConstants.GODAN)) {
            char last = name.charAt(n-1);   // 判断动词结尾
            if(last == 'る' || last == 'つ' || last == 'う') {
                return name.substring(0,n-1) + "って";
            }
            if(last == 'ぶ' || last == 'ぬ' || last == 'む') {
                return name.substring(0,n-1) + "んで";
            }
            if(last == 'す') {
                return name.substring(0,n-1) + "して";
            }
            if(last == 'く') {
                return name.substring(0,n-1) + "いて";
            }
            if(last == 'ぐ') {
                return name.substring(0,n-1) + "いで";
            }
            return null;
        }
        // 其余类型处理。因为已经处理过一类动词，所以前两个参数无影响
        return verbUtil('　',"","て","して","て",'き');
    }

    @Override
    public String naiForm() {
        if(verbType == null) {
            return null;
        }
        int n = name.length();

        // ある
        if(verbType.equals(VerbTypeConstants.ARU)) {
            return name.substring(0,n-2) + "ない";
        }
        return verbUtil('あ',"ない","ない","しない","ない",'こ');
    }

    /**
     * 返回此单词的ます形
     * @return 变为ます形后的字符串
     */
    public String masuForm() {
        if(verbType == null) {
            return null;
        }
        int n = name.length();

        // くださる
        if(verbType.equals(VerbTypeConstants.KUDASARU)) {
            return name.substring(0,n-1) + "います";
        }
        return verbUtil('い',"ます","ます","します","ます",'き');
    }

    @Override
    public String desuMasuForm() {
        return masuForm();
    }

    @Override
    public String taForm() {
        String teVerb = teForm();
        if(teVerb == null) {
            return null;
        }
        int n = teVerb.length();
        char lastChar = teVerb.charAt(n-1);
        if(lastChar == 'て') {
            return teVerb.substring(0,n-1) + "た";
        }
        if(lastChar == 'で') {
            return teVerb.substring(0,n-1) + "だ";
        }
        return null;
    }

    /**
     * 返回此单词的ば形
     * @return 变为ば形后的字符串
     */
    public String baForm() {
        return verbUtil('え',"ば","れば","すれば","れば",'く');
    }

    /**
     * 返回此单词的意志形
     * @return 变为意志形后的字符串
     */
    public String volitionalForm() {
        return verbUtil('お',"う","よう","しよう","よう",'こ');
    }

    /**
     * 返回此单词的命令形
     * @return 变为命令形后的字符串
     */
    public String imperativeForm() {
        if(verbType == null) {
            return null;
        }
        int n = name.length();
        // くださる
        if(verbType.equals(VerbTypeConstants.KUDASARU)) {
            return name.substring(0,n-1) + "い";
        }
        // くれる
        if(verbType.equals(VerbTypeConstants.KURERU)) {
            return name.substring(0,n-1);
        }
        // 古语-二段动词 呉る
        if(verbType.startsWith(VerbTypeConstants.NIDAN) && name.endsWith("呉る")) {
            return name.substring(0,n-1) + "れ";
        }
        return verbUtil('え',"","ろ","しろ","い",'こ');
    }

    /**
     * 返回此单词的可能形式
     * @return 变为可能形式后的字符串
     */
    public String potentialForm() {
        // 察する
        if(verbType != null && verbType.equals(VerbTypeConstants.SASSURU)) {
            return NekoEmojiConstants.VERB_CANNOT_CHANGE + " " + NekoEmojiConstants.UNKNOWN;
        }
        return verbUtil('え',"る","られる","できる","られる",'こ');
    }

    /**
     * 返回此单词的被动形式
     * @return 变为被动形式后的字符串
     */
    public String passiveForm() {
        // 察する
        if(verbType != null && verbType.equals(VerbTypeConstants.SASSURU)) {
            return NekoEmojiConstants.VERB_CANNOT_CHANGE + " " + NekoEmojiConstants.UNKNOWN;
        }
        return verbUtil('あ',"れる","られる","される","られる",'こ');
    }

    /**
     * 返回此单词的使役形式
     * @return 变为使役形式后的字符串
     */
    public String causativeForm() {
        // 察する
        if(verbType != null && verbType.equals(VerbTypeConstants.SASSURU)) {
            return NekoEmojiConstants.VERB_CANNOT_CHANGE + " " + NekoEmojiConstants.UNKNOWN;
        }
        return verbUtil('あ',"せる","させる","させる","させる",'こ');
    }

    /**
     * 返回此单词的使役被动形式
     * @return 变为使役被动形式后的字符串
     */
    public String causativePassiveForm() {
        // 察する
        if(verbType != null && verbType.equals(VerbTypeConstants.SASSURU)) {
            return NekoEmojiConstants.VERB_CANNOT_CHANGE + " " + NekoEmojiConstants.UNKNOWN;
        }
        return verbUtil('あ',"される","させられる",
                "させられる","させられる",'こ');
    }

    /**
     * 返回此单词的ず形
     * @return 变为ず形后的字符串
     */
    public String zuForm() {
        return verbUtil('あ',"ず","ず","せず","ず",'こ');
    }
}
