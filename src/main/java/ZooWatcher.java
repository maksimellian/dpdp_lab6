import akka.actor.ActorRef;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.time.Duration;

public class ZooWatcher implements Watcher {
    private final String HOST = "127.0.0.1";
    private final String PORT = "2181";
    private final String ADDRESS = "127.0.0.1:2181";
    private final int TIMEOUT = 5000;
    private final ActorRef configActor;
    private final ZooKeeper zoo;


    public ZooWatcher(ActorRef configActor) throws IOException {
        this.configActor = configActor;
        this.zoo = new ZooKeeper(ADDRESS, TIMEOUT, this);
    }

    @Override
    public void process(WatchedEvent event) {
    }
}
