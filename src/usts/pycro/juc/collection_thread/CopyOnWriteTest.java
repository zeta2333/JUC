package usts.pycro.juc.collection_thread;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-23 10:35 AM
 */
public class CopyOnWriteTest {
    public static void main(String[] args) {
        // java.util.ConcurrentModificationException
        // List<String> list = new ArrayList<>();
        // List<String> list = new CopyOnWriteArrayList<>();
        // Collection<String> collection = new CopyOnWriteArraySet<>();
        // for (int i = 0; i < 5; i++) {
        //     new Thread(() -> {
        //         for (int j = 0; j < 5; j++) {
        //             collection.add(UUID.randomUUID().toString().replace("-", "").substring(0, 5));
        //             System.out.println(collection);
        //         }
        //     }, "thread-" + i).start();
        // }
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    String key = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
                    String value = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
                    map.put(key, value);
                    System.out.println("map = " + map);
                }
            }, "thread" + i).start();
        }
    }
}