import java.util.ArrayList;

public class ServersMessage {
    private ArrayList<String> urls;
    public ServersMessage(ArrayList<String> urls) {
        this.urls = urls;
    }

    public ArrayList<String> getUrls() {
        return this.urls;
    }
}
