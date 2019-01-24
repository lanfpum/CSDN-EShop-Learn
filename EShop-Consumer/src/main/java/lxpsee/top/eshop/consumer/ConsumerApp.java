package lxpsee.top.eshop.consumer;

import java.util.Timer;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2019/1/2 18:59.
 */
public class ConsumerApp {
    public static void main(String[] args) {
        new Timer().schedule(new CloseFSOuputStreamTask(), 0, 30000);

        new Thread() {
            @Override
            public void run() {
                HDFSRawConsumer hdfsRawConsumer = new HDFSRawConsumer();
                hdfsRawConsumer.processLog();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                HiveCleanedConsumer cleanedConsumer = new HiveCleanedConsumer();
                cleanedConsumer.processLog();
            }
        }.start();
    }
}
