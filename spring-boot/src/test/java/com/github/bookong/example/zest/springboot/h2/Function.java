package com.github.bookong.example.zest.springboot.h2;

import net.sf.json.JSONObject;

/**
 * @author Jiang Xu
 */
public class Function {

    public static String jsonSet(String str, String cond, String value) {
        if (!cond.startsWith("$.")) {
            throw new UnsupportedOperationException("only support \"$.prop\"");
        }

        String key = cond.substring(2);
        JSONObject obj = JSONObject.fromObject(str);
        obj.put(key, value);
        return obj.toString();
    }
}
