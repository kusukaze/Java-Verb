package com.kusukaze.japanese.utils.constants;

/**
 * 动词类型常量类
 * @author Hanatsuki Kokome
 * @since 2024-09-28
 */
public final class VerbTypeConstants {
    private VerbTypeConstants() {}

    // 常规动词分类
    public static final String GODAN = "v5";        // 一类动词
    public static final String ICHIDAN = "v1";      // 二类动词
    public static final String SURU = "vs";         // する
    public static final String KURU = "vk";         // 来る

    // 特殊动词分类
    public static final String IKU = "v5k-s";       // 行く（て形）
    public static final String TOU = "v5u-s";       // 問う（て形）
    public static final String ARU = "v5r-i";       // ある（ない形）
    public static final String KUDASARU = "v5aru";  // くださる（ます形）
    public static final String KURERU = "v1-s";     // くれる（命令形）

    // 古语动词分类
    public static final String ZURU = "vz";         // ずる
    public static final String SASSURU = "vs-s";    // 察する
    public static final String NIDAN = "v2";        // 二段动词
}
