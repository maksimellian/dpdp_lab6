import akka.actor.ActorRef;
import akka.http.javadsl.Http;

public class Router {
    private final ActorRef configActor;
    private final Http http;
    private final String url = "url";
    public Router(ActorRef configActor, Http http) {
        this.configActor = configActor;
        this.http = http;
    }
}
