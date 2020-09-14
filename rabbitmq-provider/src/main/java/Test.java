import com.rabbitmq.client.ConfirmCallback;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Michael
 * @create 8/5/2020 12:40 PM
 */
public class Test {
    public static void main(String[] args) {
        ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();
        outstandingConfirms.put(1L, "michael");
        outstandingConfirms.put(2L, "michael2");
        outstandingConfirms.put(3L, "michael3");
        outstandingConfirms.put(4L, "michael4");
        ConcurrentNavigableMap<Long, String> longStringConcurrentNavigableMap = outstandingConfirms.headMap(2L, true);
        System.out.println(outstandingConfirms);
        System.out.println(longStringConcurrentNavigableMap);
        longStringConcurrentNavigableMap.clear();
        System.out.println("------------");
        System.out.println(outstandingConfirms);

    }
}
