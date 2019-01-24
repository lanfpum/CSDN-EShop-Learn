package lxpsee.top.eshop.consumer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import lxpsee.top.eshop.utils.StringUtil;

import java.io.IOException;
import java.util.*;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/28 08:03.
 * <p>
 * 原生数据消费者
 * <p>
 * 1.创建消费者连接器属性，无参构造中赋予创建消费者连接器初值
 * 2.处理log方法中
 * 指定消费的主题
 * 获取到消费的消息流
 * 得到指定主题的消息列表
 * 迭代日志消息
 * <p>
 * 优化后没有使用到自定义写入器类
 */
public class HDFSRawConsumer {
    private final String ESHOP_TOPIC = "eshop";

    private final String ESHOP_RAW_PREFIX_PATH = "/user/lanp/eshop/raw/";

    private final ConsumerConnector consumerConnector;

    public HDFSRawConsumer() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "ip202:2181");
        props.put("group.id", "raw");
        props.put("auto.offset.reset", "smallest");
        props.put("zookeeper.session.timeout.ms", "500");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");
        consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
    }

    /**
     * 进行优化：对路径进行判断，如果是同一个路径不释放链接
     * 不是 同一个路径，判断链接是否为空，不为空释放存在链接，将链接重置
     */
    public void processLog() {
        Map<String, Integer> topicCount = new HashMap<String, Integer>();
        topicCount.put(ESHOP_TOPIC, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumerConnector.createMessageStreams(topicCount);
        List<KafkaStream<byte[], byte[]>> streams = messageStreams.get(ESHOP_TOPIC);

        for (KafkaStream<byte[], byte[]> stream : streams) {
            ConsumerIterator<byte[], byte[]> consumerIterator = stream.iterator();
            LPFSDataOutputStream out = null;
            String oldPath = "";

            while (consumerIterator.hasNext()) {
                byte[] message = consumerIterator.next().message();
//                System.out.println("raw : " + new String(message));

                String log = new String(message);
                String[] arr = StringUtil.splitLog(log);

                if (arr == null || arr.length < 10) {
                    continue;
                }

                String hostName = StringUtil.getHostName(log);
                String datePath = StringUtil.getFormatTime2yyyyMMddHHmm4HDFSPath(log);
                String rawPath = ESHOP_RAW_PREFIX_PATH + datePath + "/" + hostName + ".log";
//                System.out.println("raw :------" + rawPath);

                if (!rawPath.equals(oldPath)) {

                    if (out != null) {
                        out.release();
                        out = null;
                    }

                    out = (LPFSDataOutputStream) HDFSOutputStreamPool.getInstance().getOutputStream(rawPath);
                    oldPath = rawPath;
                }

                try {
                    out.write(message);
                    out.write("\r\n".getBytes());
                    out.hsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     * 开启定时器任务，半分钟周期性关闭流
     */
    /*public static void main(String[] args) {
        new Timer().schedule(new CloseFSOuputStreamTask(), 0, 30000);
        HDFSRawConsumer hdfsRawConsumer = new HDFSRawConsumer();
        hdfsRawConsumer.processLog();
    }*/

}
