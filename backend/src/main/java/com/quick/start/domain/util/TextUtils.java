package com.quick.start.domain.util;

import java.util.List;

/**
 * TextUtils
 */
public class TextUtils {

    public static String toSentence(List<String> stringList){
        if (stringList == null)
            return "";

        StringBuilder bb = new StringBuilder();

        for (int i = 0; i < stringList.size(); i++) {
            // adds a comma if not the last one
            if (i != 0) {
                // adds "and" if it's the last one
                if (i == stringList.size() - 1)
                    bb.append(" and ");
                else
                    bb.append(", ");
            }

            bb.append(stringList.get(i));
        }

        return bb.toString();
    }
}