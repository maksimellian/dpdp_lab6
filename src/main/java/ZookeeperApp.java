import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.dsl.Creators;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ZookeeperApp {
    private static final String HOST = "localhost";
    private static int port;
    private static final String URL = "url";
    private static final String COLON = ":";
    private final static Duration TIMEOUT = Duration.ofSeconds(5);
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final ActorSystem system = ActorSystem.create("routes");
        ActorRef configStorageActor = system.actorOf(Props.create(ConfigStorageActor.class));
        port = Integer.parseInt(args[0]);
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        new ZooWatcher(configStorageActor, port);
        final Flow<HttpRequest, HttpResponse, NotUsed> flow = new Router(configStorageActor, http)
                .createRoute()
                .flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                flow, ConnectHttp.toHost(HOST, port), materializer);
        System.out.println("Servers listens on " + HOST + COLON + port);
        System.in.read();
        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }
}
