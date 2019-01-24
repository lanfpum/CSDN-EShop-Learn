package lxpsee.top.eshop.consumer;

import org.apache.hadoop.fs.FSDataOutputStream;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/28 10:40.
 * <p>
 * hdfs写入器
 * <p>
 * 写入log到hdfs文件
 */
public class HDFSWriter {
    public void writerLog2HDFS(String path, byte[] log) {
        try {
            FSDataOutputStream outputStream = HDFSOutputStreamPool.getInstance().getOutputStream(path);
            outputStream.write(log);
            outputStream.write("\r\n".getBytes());
            outputStream.hsync();
            outputStream.hflush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
