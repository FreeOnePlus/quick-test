package com.freeoneplus.quick_test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        String fliter="(uid='mdn765' and session_id ='sess10'  and num = 'E17'  and  chapter_id = 'chapter8')  \n" +
                "or  (uid='mdn606' and session_id ='sess8'  and num = 'E14'  and  chapter_id = 'chapter19')  \n" +
                "or  (uid='mdn324' and session_id ='sess3'  and num = 'E2'  and  chapter_id = 'chapter9')";
        Pattern pattern= Pattern.compile("\\(.*\\)");
        Matcher m = pattern.matcher(fliter);
        ArrayList<String> list = new ArrayList<String>();
        while (m.find()) {
            String trim = m.group().trim();
            String replace =trim.replace("'", "");
            list.add(replace+" ");
        }
        for (String s : list) {
            System.out.println(s);
        }
    }
}
