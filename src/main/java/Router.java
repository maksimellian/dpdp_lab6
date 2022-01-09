import akka.actor.ActorRef;
import akka.http.javadsl.Http;

import java.time.Duration;

public class Router {
    private final ActorRef configActor;
    private final Http http;
    private final String URL = "url";
    private final String COUNT = "count";
    private final Duration TIMEOUT = Duration.ofSeconds(5);
    public Router(ActorRef configActor, Http http) {
        this.configActor = configActor;
        this.http = http;
    }
}
