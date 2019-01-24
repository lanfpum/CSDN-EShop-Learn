package lxpsee.top.eshop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/28 08:32.
 * <p>
 * ip201|||192.168.217.1|||-|||-|||27/Dec/2018:18:22:17 +0800|||
 * "GET /eshop/phone/iphone7.html HTTP/1.0"|||200|||424|||
 * "-"|||"ApacheBench/2.3"|||"-"
 * 字符串工具类,将日志转换成各类所需的字符串
 * 1.注意|是正则表示特殊字符，需要转义
 * 2.获取主机名
 * 3.获取日期字符串作为hdfs的路径
 */
public class StringUtil {

    private static final String SPLIT_REGEX = ",";

    public static String[] splitLog(String log) {
        return log.split(SPLIT_REGEX);
    }

    public static String getHostName(String log) {
        return log.split(SPLIT_REGEX)[0];
    }

    public static String getFormatTime2yyyyMMddHHmm4HDFSPath(String log) {
        try {
            SimpleDateFormat formatUS = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
            Date usDate = formatUS.parse(log.split(SPLIT_REGEX)[3].split(" ")[0]);
            SimpleDateFormat localFormat = new SimpleDateFormat("yyyy/MM/dd/HH/mm", Locale.US);
            return localFormat.format(usDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date str2Date(String log) {
        try {
            SimpleDateFormat formatUS = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
            return formatUS.parse(log.split(SPLIT_REGEX)[3].split(" ")[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
