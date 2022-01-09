import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;

public class ConfigStorageActor extends AbstractActor {
    private ArrayList<String> servers;
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(ServersMessage.class, msg -> this.servers = msg.getUrls())
                .match()
                .build();
    }
}
