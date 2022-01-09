import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import java.time.Duration;

import static akka.http.javadsl.server.Directives.*;

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

    public String createUrl()

    public Route createRoute() {
        return route(
                get(() ->
                        parameter(URL, url -> parameter(COUNT, count -> {
                            if (Integer.parseInt(count) == 0){
                                return completeWithFuture(this.http.singleRequest(HttpRequest.create(url)));
                            } else {
                                return completeWithFuture(
                                        Patterns.ask(this.configActor, new EmptyServersMessage(), TIMEOUT)
                                                .thenApply(serverUrl -> (String)serverUrl)
                                                .thenCompose((serverUrl) ->
                                                        this.http.singleRequest(HttpRequest.create())))
                            }
                        }))
                )
        );
    }
}
