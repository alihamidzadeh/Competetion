package Server_G;


import javafx.application.Application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ServerMain {
    public static String Susrname;
    public static int Sport;

    public static void main(String[] args) throws IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader("src/Datas/users.json"));
        JSONArray array = (JSONArray) obj;
        JSONObject job = (JSONObject) array.get(0);
        Sport = Integer.parseInt(job.get("port").toString());
        Susrname = job.get("name").toString();
        System.out.println(Susrname + " --- " + Sport);
        Application.launch(S_Graphic.class, args);
    }
}
