import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class UserJsonParser {
    static ArrayList<Client> clients = new ArrayList<>();
    static Server server;

    public static void main(String[] args) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("src/users.json"));
        JSONArray array = (JSONArray) obj;

        for (int i = 0; i < array.size(); i++) {
            JSONObject job = (JSONObject) array.get(i);
            String type = job.get("type").toString();
            if (type.equals("host")) {
                server = new Server(job.get("type").toString(), Integer.parseInt(job.get("port").toString()), job.get("name").toString());
            } else {
                Client client = new Client(job.get("type").toString(), Integer.parseInt(job.get("port").toString()), job.get("name").toString());
                clients.add(client);
            }
        }
//        System.out.println(users.get(0).getport());
    }
}
