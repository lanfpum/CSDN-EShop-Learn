package lxpsee.top.eshop.test;

import lxpsee.top.eshop.consumer.HDFSOutputStreamPool;
import lxpsee.top.eshop.consumer.LPFSDataOutputStream;
import lxpsee.top.eshop.utils.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/28 08:32.
 */
public class TsetConsumer {

    @Test
    public void testStringUtils() {
        String log = "ip201,192.168.217.1,-,22/Jan/2019:15:07:49 +0800,GET /eshop/phone/huawei.html HTTP/1.0,200,224,-,ApacheBench/2.3,-";
        DecimalFormat format = new DecimalFormat("00");

        System.out.println(StringUtil.getFormatTime2yyyyMMddHHmm4HDFSPath(log));
        System.out.println(StringUtil.getHostName(log));
        System.out.println(StringUtil.str2Date(log));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(StringUtil.str2Date(log));
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(format.format(calendar.get(Calendar.MONTH) + 1));
        System.out.println(format.format(calendar.get(Calendar.DAY_OF_MONTH) ));
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println(calendar.get(Calendar.MINUTE));

    }

    @Test
    public void testHDFSConn() {
        try {
            Configuration configuration = new Configuration();
            FileSystem fileSystem = FileSystem.get(configuration);
            FSDataInputStream inputStream = fileSystem.open(new Path("hdfs://lanpengcluster/user/lanp/test/customer.txt"));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IOUtils.copyBytes(inputStream, outputStream, 1024);
            System.out.println(new String(outputStream.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHiveConn() {
        String log = "ip201,192.168.217.1,-,02/Jan/2019:19:17:56 +0800,GET /eshop/phone/mi.html HTTP/1.0,200,213,-,ApacheBench/2.3,-";
        String[] arr = StringUtil.splitLog(log);

        if (arr == null || arr.length < 10) {
            System.out.println("出错了");
        }

        String request = arr[4];
        String[] requestArr = request.split(" ");

        if (requestArr == null || requestArr.length != 3) {
            System.out.println("出错了");
        }

        if (!requestArr[1].endsWith(".html")) {
            System.out.println("出错了");
        }

        String hostName = StringUtil.getHostName(log);
        Date reqDate = StringUtil.str2Date(log);
        Calendar c = Calendar.getInstance();
        c.setTime(reqDate);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int mi = c.get(Calendar.MINUTE);

        String rawPath = "/user/hive/warehouse/lp_eshop_db.db/logs/year=" + y
                + "/month=" + m
                + "/day=" + d
                + "/hour=" + h
                + "/minute=" + mi
                + "/" + hostName + ".log";

        String oldPath = "";
        LPFSDataOutputStream out = null;
        if (!rawPath.equals(oldPath)) {

            if (out != null) {
                out.release();
                out = null;
            }

            out = (LPFSDataOutputStream) HDFSOutputStreamPool.getInstance().getOutputStream(rawPath);
            oldPath = rawPath;
        }

        try {
            out.write(log.getBytes());
            out.write("\r\n".getBytes());
            out.hsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date());
            }
        }, 1000, 2000);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
   
