package Client_G;


import javafx.application.Application;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ClientMain {

    public static String Susrname;
    public static int Sport;
    public static String Cusrname;
    public static int Cport;


    public static void main(String[] args) throws IOException, ParseException {
        int counter = Integer.parseInt(args[0]);
        Object obj = new JSONParser().parse(new FileReader("src/Datas/users.json"));
        JSONArray array = (JSONArray) obj;
        JSONObject job = (JSONObject) array.get(0);
        Sport = Integer.parseInt(job.get("port").toString());
        Susrname = job.get("name").toString();
        System.out.println(Susrname + " --- " + Sport);

        job = (JSONObject) array.get(counter);
        Cport = Integer.parseInt(job.get("port").toString());
        Cusrname = job.get("name").toString();
        System.out.println(Cusrname + " --- " + Cport);

        Application.launch(C_Graphic.class, args);
    }


}
