package lxpsee.top.eshop.utils;

import java.util.Collection;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/24 08:47.
 * <p>
 * 校验工具类
 */
public class ValidateUtil {

    /**
     * 判断集合的有效性
     */
    public static boolean isValid(Collection collection) {
        if (collection.isEmpty() || collection == null || collection.size() == 0) {
            return false;
        }

        return true;
    }
}
