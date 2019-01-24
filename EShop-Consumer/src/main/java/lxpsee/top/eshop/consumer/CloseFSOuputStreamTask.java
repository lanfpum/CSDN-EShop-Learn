package lxpsee.top.eshop.consumer;

import java.util.TimerTask;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/29 16:05.
 * <p>
 * 关闭线程池定时器类（底层是线程）
 * 清空池子中的所有链接，因为链接是剪切过去的，所以在使用的链接不会存在池子中
 */
public class CloseFSOuputStreamTask extends TimerTask {
    public void run() {
        HDFSOutputStreamPool.getInstance().releasePool();
    }
}
