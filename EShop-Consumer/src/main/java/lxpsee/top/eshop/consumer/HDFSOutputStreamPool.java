package lxpsee.top.eshop.consumer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/28 10:52.
 * <p>
 * 输出流池（单例）
 * 构造函数中对属性赋初值
 */
public class HDFSOutputStreamPool {
    private static HDFSOutputStreamPool instance;

    private FileSystem fileSystem;

    //存放的所有的输出流
    private Map<String, FSDataOutputStream> pool = new HashMap<String, FSDataOutputStream>();

    private HDFSOutputStreamPool() {
        try {
            fileSystem = FileSystem.get(new Configuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HDFSOutputStreamPool getInstance() {
        if (instance == null) {
            instance = new HDFSOutputStreamPool();
        }

        return instance;
    }

    /**
     * 通过路径得到对应的输出流(同步)
     */
    public synchronized FSDataOutputStream getOutputStream(String path) {
        try {
            FSDataOutputStream out = pool.remove(path);

            if (out == null) {
                Path newPath = new Path(path);

                if (!fileSystem.exists(newPath)) {
                    fileSystem.createNewFile(newPath);
                }

                out = fileSystem.append(newPath);
            }

            return new LPFSDataOutputStream(path, out, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 回收流
     */
    public synchronized void putBack(String path, FSDataOutputStream outputStream) {
        pool.put(path, outputStream);
    }

    /**
     * 释放池子
     */
    public synchronized void releasePool() {
        try {

            for (FSDataOutputStream out : pool.values()) {
                out.close();
            }

            pool.clear();
            System.out.println("池子已经释放。。。。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
