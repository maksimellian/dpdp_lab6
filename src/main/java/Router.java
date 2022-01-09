import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.http.javadsl.model.Uri;
import akka.http.javadsl.server.Route;
import akka.japi.Pair;
import akka.pattern.Patterns;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

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

    public String createUrl(String serverUrl, String url, int count) {
        String link = String.valueOf(Uri.create(serverUrl).query(Query.create(
                Pair.create(URL, url),
                Pair.create(COUNT, String.valueOf(count - 1)))));
        System.out.println("router line 29 " + link);
        return link;
    }

    public CompletionStage<HttpResponse> sendToRandomServer(String url, String count) {
         return Patterns.ask(this.configActor, new EmptyServersMessage(), TIMEOUT)
                .thenApply(serverUrl -> (String)serverUrl)
                .thenCompose((serverUrl) ->
                        this.http.singleRequest(HttpRequest
                                .create(this.createUrl(serverUrl, url, Integer.parseInt(count)))));
    }

    public Route createRoute() {
        return route(
                get(() ->
                        parameter(URL, url -> parameter(COUNT, count -> {
                            if (Integer.parseInt(count) == 0){
                                return completeWithFuture(this.http.singleRequest(HttpRequest.create(url)));
                            } else {
                                return completeWithFuture(sendToRandomServer(url, count));
                            }
                        }))
                )
        );
    }
}
