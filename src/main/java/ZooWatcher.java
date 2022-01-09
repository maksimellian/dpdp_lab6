import akka.actor.ActorRef;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.time.Duration;

public class ZooWatcher implements Watcher {
    private final String HOST = "127.0.0.1";
    private final String PORT = "2181";
    private final String ADDRESS = "127.0.0.1:2181";
    private final String PATH = "/servers/s";
    private final int TIMEOUT = 5000;
    private final ActorRef configActor;
    private final ZooKeeper zoo;
    private int activePort;


    public ZooWatcher(ActorRef configActor, int port) throws IOException, InterruptedException, KeeperException {
        String akkaAddress = "http://" + HOST + ":" + this.activePort;
        System.out.println("akkaAddress is " + akkaAddress);
        this.configActor = configActor;
        this.zoo = new ZooKeeper(ADDRESS, TIMEOUT, this);
        this.activePort = port;
        this.zoo.create(PATH, akkaAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    @Override
    public void process(WatchedEvent event) {
    }
}
