import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.Random;

public class ConfigStorageActor extends AbstractActor {
    private ArrayList<String> servers;
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(ServersMessage.class, msg -> this.servers = msg.getUrls())
                .match(EmptyServersMessage.class, msg -> sender().tell(servers.get(new Random().nextInt(servers.size())), self()))
                .build();
    }
}
