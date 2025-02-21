package com.kusukaze.japanese.utils.constants;

import java.util.HashMap;

/**
 * 罗马字映射工具类，用于存储假名和罗马字的映射关系
 * @author Maki Kusukaze
 * @since 2024-09-21
 */
public class RomajiMapUtils {

    private static final HashMap<String,String> hiraganaRomajiTable = new HashMap<>();
    private static final HashMap<String,String> romajiHiraganaTable = new HashMap<>();

    static {
        loadTable();
    }

    /**
     * 返回单个罗马字对应的平假名
     * @param romaji 待转化的罗马字
     * @return 转化后的平假名
     */
    public static String getHiraganaByRomaji(String romaji) {
        return romajiHiraganaTable.get(romaji);
    }

    /**
     * 返回单个平假名对应的罗马字
     * @param hiragana 待转化的平假名
     * @return 转化后的罗马字
     */
    public static String getRomajiByHiragana(String hiragana) {
        return hiraganaRomajiTable.get(hiragana);
    }

    /**
     * 将平假名和罗马字的对照关系读入内存
     */
    private static void loadTable() {
        String[][] romajiRawData = {
            {"あ","a"},{"い","i"},{"う","u"},{"え","e"},{"お","o"},{"ん","n"},

            {"ふ","fu"}, {"じ","ji"}, {"じゃ","ja"}, {"じゅ","ju"}, {"じょ","jo"},
            {"ふぁ","fa"}, {"ふぃ","fi"}, {"ふぇ","fe"}, {"ふぉ","fo"}, {"ぁ","xa"},
            {"ぃ","xi"}, {"ぅ","xu"}, {"ぇ","xe"}, {"ぉ","xo"},

            {"か","ka"}, {"き","ki"}, {"く","ku"}, {"け","ke"}, {"こ","ko"},
            {"さ","sa"}, {"し","si"}, {"す","su"}, {"せ","se"}, {"そ","so"},
            {"た","ta"}, {"ち","ti"}, {"つ","tu"}, {"て","te"}, {"と","to"},
            {"な","na"}, {"に","ni"}, {"ぬ","nu"}, {"ね","ne"}, {"の","no"},
            {"は","ha"}, {"ひ","hi"}, {"ふ","hu"}, {"へ","he"}, {"ほ","ho"},
            {"ま","ma"}, {"み","mi"}, {"む","mu"}, {"め","me"}, {"も","mo"},
            {"や","ya"}, {"ゆ","yu"}, {"よ","yo"},
            {"ら","ra"}, {"り","ri"}, {"る","ru"}, {"れ","re"}, {"ろ","ro"},
            {"わ","wa"}, {"ゐ","wi"}, {"ゑ","we"}, {"を","wo"},
            {"が","ga"}, {"ぎ","gi"}, {"ぐ","gu"}, {"げ","ge"}, {"ご","go"},
            {"ざ","za"}, {"じ","zi"}, {"ず","zu"}, {"ぜ","ze"}, {"ぞ","zo"},
            {"だ","da"}, {"ぢ","di"}, {"づ","du"}, {"で","de"}, {"ど","do"},
            {"ば","ba"}, {"び","bi"}, {"ぶ","bu"}, {"べ","be"}, {"ぼ","bo"},
            {"ぱ","pa"}, {"ぴ","pi"}, {"ぷ","pu"}, {"ぺ","pe"}, {"ぽ","po"},

            {"し","shi"}, {"ち","chi"}, {"つ","tsu"},
            {"じゃ","jya"}, {"じゅ","jyu"}, {"じょ","jyo"},
            {"つぁ","tsa"}, {"つぃ","tsi"}, {"つぇ","tse"}, {"つぉ","tso"},
            {"うぁ","wha"}, {"うぃ","whi"}, {"うぇ","whe"}, {"うぉ","who"},
            {"てぃ","thi"}, {"てゅ","thu"}, {"てょ","tho"},
            {"しぇ","she"}, {"ちぇ","che"}, {"でぃ","dhi"}, {"でゅ","dhu"}, {"でょ","dho"},
            {"ゃ","xya"}, {"ゅ","xyu"}, {"ょ","xyo"}, {"っ","xtu"}, {"ヶ","xke"},

            {"きゃ","kya"}, {"きゅ","kyu"}, {"きょ","kyo"},
            {"しゃ","sha"}, {"しゅ","shu"}, {"しょ","sho"},
            {"ちゃ","cha"}, {"ちゅ","chu"}, {"ちょ","cho"},
            {"にゃ","nya"}, {"にゅ","nyu"}, {"にょ","nyo"},
            {"ひゃ","hya"}, {"ひゅ","hyu"}, {"ひょ","hyo"},
            {"みゃ","mya"}, {"みゅ","myu"}, {"みょ","myo"},
            {"りゃ","rya"}, {"りゅ","ryu"}, {"りょ","ryo"},
            {"ぎゃ","gya"}, {"ぎゅ","gyu"}, {"ぎょ","gyo"},
            {"じゃ","jya"}, {"じゅ","jyu"}, {"じょ","jyo"},
            {"びゃ","bya"}, {"びゅ","byu"}, {"びょ","byo"},
            {"ぴゃ","pya"}, {"ぴゅ","pyu"}, {"ぴょ","pyo"}
        };

        // 一个假名对应多种罗马字，则指定默认罗马字
        String[][] defaultRomajiRawData = {
            {"ふ","fu"}, {"じ","ji"}, {"し","shi"}, {"ち","chi"}, {"つ","tsu"},
            {"じゃ","ja"}, {"じゅ","ju"}, {"じょ","jo"},
        };

        for (String[] romajiRawDatum : romajiRawData) {
            hiraganaRomajiTable.put(romajiRawDatum[0],romajiRawDatum[1]);
            romajiHiraganaTable.put(romajiRawDatum[1],romajiRawDatum[0]);
        }
        for (String[] romajiRawDatum : defaultRomajiRawData) {
            hiraganaRomajiTable.put(romajiRawDatum[0],romajiRawDatum[1]);
        }
    }

}
