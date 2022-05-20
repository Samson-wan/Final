import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class Networking {
    private String APIKey;

    public Networking() {
        APIKey = "";
    }

    public Player getPlayer(String name){
        return new Player(name);
    }

    public Profile getProfile(String name){
        String url = "https://sky.shiiyu.moe/api/v2/coins/" + name;
        String json = Networking.makeAPICall(url);
        JSONObject jsonObj = new JSONObject(json);
        JSONObject jsonProfile = jsonObj.getJSONObject("profiles");
        JSONObject jsonProfileID = jsonProfile.getJSONObject(""); // make an array;
        String cute_name = jsonProfile.getString("cute_name");
        double money = jsonProfile.getDouble("purse");
        return new Profile(cute_name, money);
    }

    public static String makeAPICall(String url) {
        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
