import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.time.Duration;

public class ZooWatcher implements Watcher {
    private final String HOST = "127.0.0.1";
    private final String PORT = "2181";
    private final Duration TIMEOUT = Duration.ofSeconds(5);
    @Override
    public void process(WatchedEvent event) {
    }
}
