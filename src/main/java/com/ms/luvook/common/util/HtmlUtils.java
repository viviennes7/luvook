package com.ms.luvook.common.util;

public class HtmlUtils {
    public static String parseBrTag(String contents){
        return contents.replace("\n","<br>");
    }
}
