package com.kusukaze.japanese;

import com.kusukaze.japanese.utils.GojuuonzuUtils;
import com.kusukaze.japanese.utils.RomajiUtils;
import com.kusukaze.japanese.utils.constants.NekoEmojiConstants;

import java.util.Scanner;

/**
 * 假名罗马字转换 - 控制台输入
 * @author Hanatsuki Kokome
 * @since 2024-09-30
 */
public class KanaRomajiChange {
    public static void main(String[] args) {
        String inputString;

        Scanner scanner = new Scanner(System.in); // 创建Scanner对象
        while(true) {
            // 输入
            System.out.println("请输入假名或罗马字（汉字无法转化，将保留；不输入直接按回车可结束程序）:");
            inputString = scanner.nextLine(); // 获取用户输入的字符串
            if(inputString.isEmpty()) {
                break;
            }

            long startTime = System.currentTimeMillis();
            String hiragana = GojuuonzuUtils.katakana2Hiragana(RomajiUtils.romaji2Hiragana(inputString));
            String katakana = GojuuonzuUtils.hiragana2Katakana(hiragana);
            String romaji = RomajiUtils.kana2Romaji(hiragana);
            System.out.println("平假名：" + hiragana);
            System.out.println("片假名：" + katakana);
            System.out.println("罗马字：" + romaji);
            long runTime = System.currentTimeMillis() - startTime;
            System.out.println("耗时：" + runTime + " ms");
            System.out.println();
        }
        System.out.println(NekoEmojiConstants.PROGRAM_ENDED + " " + NekoEmojiConstants.EXIT);
        scanner.close(); // 关闭 Scanner 对象，释放资源。
    }
}
