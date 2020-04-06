package com.xd.kobepjpa.util;

/**
 * 命名工具类.
 */
public class NamingUtils {
    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->helloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            //记录首字母是否是大写
            boolean isFirst = false;
            if (name.substring(0, 1).equals(name.substring(0, 1).toUpperCase())) {
                isFirst = true;
            }
            // 不含下划线，仅将首字母小写
            name = name.substring(0, 1).toLowerCase() + name.substring(1);


            //不含下划线，开头是连续大写时，全部改成小写；中间部分，只保留第一个大写
            boolean isContinueUpperCase = false;
            for (int i = 0; name.length() > i; i++) {

                if (name.substring(i, i + 1).equals(name.substring(i, i + 1).toUpperCase())) {
                    if (isContinueUpperCase || isFirst) {
                        name = name.substring(0, i) + name.substring(i, i + 1).toLowerCase() + (name.length() >= (i + 1) ? name.substring(i + 1, name.length()) : "");
                    }
                    isContinueUpperCase = true;
                } else {
                    isContinueUpperCase = false;
                    if (i > 0 && isFirst) {
                        isFirst = false;
                    }
                }

            }
            return name;
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 将某片段中的字符转换成标准驼峰命名
                boolean tempIsContinueUpperCase = false;
                for (int k = 0; k < camel.length(); k++) {
                    if (camel.substring(k, k + 1).equals(camel.substring(k, k + 1).toUpperCase())) {
                        if (tempIsContinueUpperCase) {
                            camel = camel.substring(0, k) + camel.substring(k, k + 1).toLowerCase() + (camel.length() >= (k + 1) ? camel.substring(k + 1, camel.length()) : "");
                        }
                        tempIsContinueUpperCase = true;
                    } else {
                        tempIsContinueUpperCase = false;
                    }
                }

                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1));
            }
        }
        return result.toString();
    }
}
