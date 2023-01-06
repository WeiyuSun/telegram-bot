package com.weiyuproject.telegrambot.utils;

public class EmojiUtil {
    public static final String ZERO = "0️⃣";
    public static final String ONE = "1️⃣";
    public static final String TWO = "2️⃣";
    public static final String THREE = "3️⃣";
    public static final String FOUR = "4️⃣";
    public static final String FIVE = "5️⃣";
    public static final String SIX = "6️⃣";
    public static final String SEVEN = "7️⃣";
    public static final String EIGHT = "8️⃣";
    public static final String NINE = "9️⃣";

    public static String getPositiveNumberEmoji(int number) {
        if (number < 0) return String.valueOf(number);
        if (number == 0) return ZERO;

        StringBuilder result = new StringBuilder();

        while (number != 0) {
            int currNumber = number % 10;
            String emoji = switch (currNumber) {
                case 0 -> ZERO;
                case 1 -> ONE;
                case 2 -> TWO;
                case 3 -> THREE;
                case 4 -> FOUR;
                case 5 -> FIVE;
                case 6 -> SIX;
                case 7 -> SEVEN;
                case 8 -> EIGHT;
                case 9 -> NINE;
                default -> "";
            };

            result.insert(0, emoji);
            number = number / 10;
        }
        return result.toString();
    }

//    static String getNumberInString(Long number){
//
//        if(number <= 0)
//            return number.toString();
//
//        String result = "";
//        while(number != 0){
//            result = (number % 10) + result;
//            number = number / 10;
//        }
//        return result;
//    }
}
