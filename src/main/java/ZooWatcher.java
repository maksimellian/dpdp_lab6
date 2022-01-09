import akka.actor.ActorRef;
import akka.pattern.Patterns;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ZooWatcher implements Watcher {
    private final String HOST = "127.0.0.1";
    private final String PORT = "2181";
    private final String ADDRESS = "127.0.0.1:2181";
    private final String PATH = "/servers";
    private final int TIMEOUT = 5000;
    private final ActorRef configActor;
    private final ZooKeeper zoo;
    private int activePort;


    public ZooWatcher(ActorRef configActor, int port) throws IOException, InterruptedException, KeeperException {
        this.configActor = configActor;
        this.zoo = new ZooKeeper(ADDRESS, TIMEOUT, this);
        this.activePort = port;
        String akkaAddress = "http://" + HOST + ":" + this.activePort;
        System.out.println("akkaAddress is " + akkaAddress);
        this.zoo.create(PATH, akkaAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    @Override
    public void process(WatchedEvent event) {
        List<String> servers;
        try {
            servers = this.zoo.getChildren(PATH.substring(0, PATH.length() - 2), this);
            ArrayList<String> urlsForAkka = new ArrayList<String>();
            for (String server: servers) {
                String url = new String(this.zoo.getData(PATH.substring(0, PATH.length() - 1) + server, false, null));
                urlsForAkka.add(url);
            }
            Patterns.ask(this.configActor, new ServersMessage(urlsForAkka), TIMEOUT);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
