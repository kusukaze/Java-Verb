package com.kusukaze.japanese;

import com.kusukaze.japanese.utils.DictionaryUtils;
import com.kusukaze.japanese.utils.GojuuonzuUtils;
import com.kusukaze.japanese.utils.RomajiUtils;
import com.kusukaze.japanese.yougen.Adjective;
import com.kusukaze.japanese.yougen.AdjectiveNoun;
import com.kusukaze.japanese.yougen.Verb;
import com.kusukaze.japanese.yougen.Yougen;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用示例
 * @author Hanatsuki Kokome
 * @since 2024-09-20
 */
public class UsageDemo {
        private static void formChangeTest() {
        System.out.println("----------动词变形测试----------");

        // 新建不同类型的用言，保存在列表中
        Yougen[] list = {
                new Adjective("美しい"),
                new Adjective("かわいい"),
                new Adjective("かっこいい"),
                new AdjectiveNoun("静か"),
                new Verb("食べる"),
                new Verb("飲む"),
                new Verb("する"),
                new Verb("来る"),
                new Verb("問う"),
                new Verb("ある"),
                new Verb("下さる"),
                new Verb("呉れる"),
                new Verb("勉強する"),
                new Verb("コピーする"),
                new Verb("察する"),
                new Verb("演ずる"),
                new Verb("見ゆ"),
                new Verb("落つ"),
                new Verb("寝"),
                new Verb("答ふ"),
                new Verb("ぴりっとくる"),
        };

        for (Yougen yougen : list) {
            // 利用多态，自动根据词语种类执行不同的变形逻辑
            System.out.println(yougen.getName() + " → [て形] "
                    + yougen.teForm() + " → [ない形] " + yougen.naiForm() + " → [ます形] "
                    + yougen.desuMasuForm() + " → [た形] " + yougen.taForm()
            );
            // 动词的其他变形
            if(yougen instanceof Verb) {
                Verb verb = (Verb)yougen;
                System.out.println("\t" + " → [意志形] "
                        + verb.volitionalForm() + " → [命令形] " + verb.imperativeForm()
                        + " → [ば形] " + verb.baForm() + " → [可能形式] " + verb.potentialForm()
                        + "\n\t" +" → [被动形式] " + verb.passiveForm() + " → [使役形式] "
                        + verb.causativeForm() + " → [使役被动形式] " + verb.causativePassiveForm());
                System.out.println("\t" + " → (测试) [ず形] " + verb.zuForm());
            }
            // 形容词的其他变形
            else if(yougen instanceof Adjective) {
                Adjective adj = (Adjective)yougen;
                System.out.println("\t" + " → [推量形] "
                        + adj.volitionalForm() + " → [ば形] " + adj.baForm());
                System.out.println("\t" + " → (测试) [ず形] " + adj.zuForm());
            }
            // 形容动词的其他变形
            else if(yougen instanceof AdjectiveNoun) {
                AdjectiveNoun adjn = (AdjectiveNoun)yougen;
                System.out.println("\t" + " → (测试) [ず形] " + adjn.zuForm());
            }
            System.out.println();
        }
    }

    private static void dictionaryTest() {
        System.out.println("----------字典查询测试----------");

        // 查阅字典，找到所有读音为yomi的动词
        String yomi = RomajiUtils.romaji2Hiragana("iru");
        List<String> verbNames = DictionaryUtils.getVerbNameByKanjiOrKana(yomi);
        // 将动词写法保存至列表
        List<Yougen> list = verbNames.stream().map(Verb::new).collect(Collectors.toList());
        System.out.println(verbNames);
        // 展示每个动词的变形
        for (Yougen yougen : list) {
            System.out.println(yougen.getName() + " → "
                    + yougen.teForm() + " → " + yougen.naiForm() + " → "
                    + yougen.desuMasuForm() + " → " + yougen.taForm()
            );
        }
        System.out.println();
    }

    private static void romajiTest() {
        System.out.println("----------罗马字与假名转化测试----------");

        // 罗马字转平假名
        System.out.println(RomajiUtils.romaji2Hiragana("assarinanda,atsuinya,atuin'ya,na-,n'a,ann"));
        System.out.println(RomajiUtils.romaji2Hiragana("shure-dhinga-nonekogakawaisoudesu"));
        System.out.println(RomajiUtils.romaji2Hiragana("Etchuujima,Ecchuujima"));
        // 假名转罗马音
        System.out.println(RomajiUtils.kana2Romaji("シュレーディンガーのねこ"));
        System.out.println(RomajiUtils.kana2Romaji("えっちゅうじま"));
        // 片假名转平假名
        System.out.println(GojuuonzuUtils.katakana2Hiragana("サッカー、キャッチ、しゅれーでぃんがーのねこ"));
        // 平假名转片假名
        System.out.println(GojuuonzuUtils.hiragana2Katakana("さっかー、きゃっち、シュレーディンガーノネコ"));
        System.out.println();
    }

    public static void main(String[] args) {
        formChangeTest();
        dictionaryTest();
        romajiTest();
    }
}
