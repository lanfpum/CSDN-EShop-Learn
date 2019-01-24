package lxpsee.top.eshop.consumer;

import org.apache.hadoop.fs.FSDataOutputStream;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/29 15:39.
 * <p>
 * 装饰流
 */
public class LPFSDataOutputStream extends FSDataOutputStream {
    private String               path;
    private FSDataOutputStream   out;
    private HDFSOutputStreamPool pool;

    public LPFSDataOutputStream(String path, FSDataOutputStream out, HDFSOutputStreamPool pool) throws IOException {
        super(null);
        this.path = path;
        this.out = out;
        this.pool = pool;
    }

    @Override
    public void close() throws IOException {
        out.close();
    }

    @Override
    public void hflush() throws IOException {
        out.flush();
    }

    @Override
    public void hsync() throws IOException {
        out.hsync();
    }

    @Override
    public void write(byte[] b) throws IOException {
        out.write(b);
    }

    /**
     * 回收
     */
    public void release() {
        pool.putBack(path, this);
    }
}
