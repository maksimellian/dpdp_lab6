import akka.actor.ActorRef;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
        List<String> servers;
        try {
            servers = this.zoo.getChildren(PATH.substring(0, PATH.length() - 2), this);
            List<String> urls = new List<String>() {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(Object o) {
                    return false;
                }

                @Override
                public Iterator<String> iterator() {
                    return null;
                }

                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @Override
                public <T> T[] toArray(T[] ts) {
                    return null;
                }

                @Override
                public boolean add(String s) {
                    return false;
                }

                @Override
                public boolean remove(Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public boolean addAll(Collection<? extends String> collection) {
                    return false;
                }

                @Override
                public boolean addAll(int i, Collection<? extends String> collection) {
                    return false;
                }

                @Override
                public boolean removeAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public boolean retainAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public String get(int i) {
                    return null;
                }

                @Override
                public String set(int i, String s) {
                    return null;
                }

                @Override
                public void add(int i, String s) {

                }

                @Override
                public String remove(int i) {
                    return null;
                }

                @Override
                public int indexOf(Object o) {
                    return 0;
                }

                @Override
                public int lastIndexOf(Object o) {
                    return 0;
                }

                @Override
                public ListIterator<String> listIterator() {
                    return null;
                }

                @Override
                public ListIterator<String> listIterator(int i) {
                    return null;
                }

                @Override
                public List<String> subList(int i, int i1) {
                    return null;
                }
            }
        } catch (KeeperException | InterruptedException e) {

        }
    }
}
