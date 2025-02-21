package com.kusukaze.japanese;

import com.kusukaze.japanese.utils.DictionaryUtils;
import com.kusukaze.japanese.utils.RomajiUtils;
import com.kusukaze.japanese.utils.constants.NekoEmojiConstants;
import com.kusukaze.japanese.yougen.Verb;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 动词查询 - 控制台输入
 * @author Hanatsuki Kokome
 * @since 2024-09-30
 */
public class VerbChange {
    public static void main(String[] args) {
        String inputString;
        Scanner scanner = new Scanner(System.in); // 创建Scanner对象
        while(true) {
            // 输入
            System.out.println("请输入动词原形（汉字、假名、罗马字均可，不输入直接按回车可结束程序）:");
            inputString = scanner.nextLine(); // 获取用户输入的字符串
            if(inputString.isEmpty()) {
                break;
            }
            long startTime = System.currentTimeMillis();
            int n = 0;
            // 处理动词
            String verbName = RomajiUtils.romaji2Hiragana(inputString);
            List<String> verbNames = DictionaryUtils.getVerbNameByKanjiOrKana(verbName);
            // 将动词写法保存至列表
            List<Verb> list = verbNames.stream().map(Verb::new).collect(Collectors.toList());
            if(verbNames.size() == 0) {
                System.out.println("没有找到这个动词喵 " + NekoEmojiConstants.FAILURE);
            }
            else {
                System.out.println("共搜索到" + verbNames.size() + "个动词：" + verbNames);
            }
            // 展示每个动词的变形
            for (Verb verb : list) {
                n++;
                System.out.println(n + ". " + verb.getName() + " → [て形] "
                        + verb.teForm() + " → [ない形] " + verb.naiForm() + " → [ます形] "
                        + verb.desuMasuForm() + " → [た形] " + verb.taForm()
                );
                System.out.println("\t" + " → [意志形] "
                        + verb.volitionalForm() + " → [命令形] " + verb.imperativeForm()
                        + " → [ば形] " + verb.baForm() + " → [可能形式] " + verb.potentialForm()
                        + "\n\t" +" → [被动形式] " + verb.passiveForm() + " → [使役形式] "
                        + verb.causativeForm() + " → [使役被动形式] " + verb.causativePassiveForm());
                System.out.println("\t" + " → (测试) [ず形] " + verb.zuForm());
            }
            long runTime = System.currentTimeMillis() - startTime;
            System.out.println("耗时：" + runTime + " ms");
            System.out.println();
        }
        System.out.println(NekoEmojiConstants.PROGRAM_ENDED + " " + NekoEmojiConstants.EXIT);
        scanner.close(); // 关闭 Scanner 对象，释放资源。
    }
}
