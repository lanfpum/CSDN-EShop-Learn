package lxpsee.top.eshop.consumer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import lxpsee.top.eshop.utils.StringUtil;

import java.text.DecimalFormat;
import java.util.*;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2019/1/2 18:41.
 * <p>
 * Hive数据清洗消费者
 */
public class HiveCleanedConsumer {

    private final String ESHOP_TOPIC = "eshop";

    private final String ESHOP_HIVE_CLEAN_PREFIX_PATH = "/user/lanp/eshop/cleaned/";

    private final ConsumerConnector consumerConnector;

    public HiveCleanedConsumer() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "ip202:2181");
        props.put("group.id", "hive");
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
//                System.out.println("Hive :" + new String(message));

                String log = new String(message);
                String[] arr = StringUtil.splitLog(log);

                if (arr == null || arr.length < 10) {
                    continue;
                }

                String request = arr[4];
                String[] requestArr = request.split(" ");

                if (requestArr == null || requestArr.length != 3) {
                    continue;
                }

                if (!requestArr[1].endsWith(".html")) {
                    continue;
                }

                String hostName = StringUtil.getHostName(log);
                Date reqDate = StringUtil.str2Date(log);
                Calendar c = Calendar.getInstance();
                c.setTime(reqDate);
                DecimalFormat decimalFormat = new DecimalFormat("00");  // 注意格式化
                int y = c.get(Calendar.YEAR);
                String m = decimalFormat.format(c.get(Calendar.MONTH) + 1);
                String d = decimalFormat.format(c.get(Calendar.DAY_OF_MONTH));
                String h = decimalFormat.format(c.get(Calendar.HOUR_OF_DAY));
                String mi = decimalFormat.format(c.get(Calendar.MINUTE));

                String cleanPath = ESHOP_HIVE_CLEAN_PREFIX_PATH + y + "/" + m + "/" + d + "/" + h + "/" + mi + "/" + hostName + ".log";
//                System.out.println("hive path ---------" + cleanPath);

                if (!cleanPath.equals(oldPath)) {

                    if (out != null) {
                        out.release();
                        out = null;
                    }

                    out = (LPFSDataOutputStream) HDFSOutputStreamPool.getInstance().getOutputStream(cleanPath);
                    oldPath = cleanPath;
                }

                try {
                    out.write(message);
                    out.write("\r\n".getBytes());
                    out.hsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
