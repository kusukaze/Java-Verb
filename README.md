# Java-Verb
用Java实现的日语用言变形

核心思想：
* 动词先查字典判断类型，再根据类型输出变形
* 形容词和形容动词不用查字典，直接变形
* 动词、形容词、形容动词类分别实现用言接口
* 注意各种处理特例（VerbTypeConstants中的特殊类型）

demo类：
* KanaRomajiChange：假名罗马字转换示例（控制台输入）
* UsageDemo：动词变形示例（固定内容）
* VerbChange：动词变形示例（控制台输入）