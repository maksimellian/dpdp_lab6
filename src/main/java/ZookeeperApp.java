import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.dsl.Creators;

import java.time.Duration;

public class ZookeeperApp {
    private static final String HOST = "localhost";
    private static int PORT = 8080;
    private static final String URL = "url";
    private final static Duration TIMEOUT = Duration.ofSeconds(5);
    private static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("routes");
        ActorRef configStorageActor = system.actorOf(Props.create(ConfigStorageActor.class));
        if (args.length != 0) {
            PORT = Integer.parseInt(args[0]);
            
        }
    }
}
