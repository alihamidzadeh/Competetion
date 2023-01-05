import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class QuestionsJsonParser {
    public static void main(String[] args) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("src/questions.json"));
        JSONArray array = (JSONArray) obj;
        ArrayList<Question> questions = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            JSONObject job = (JSONObject) array.get(i);
            JSONArray options = (JSONArray) job.get("options");
            Question q = new Question(job.get("question").toString(), options.get(0).toString(), options.get(1).toString(), options.get(2).toString(), options.get(3).toString(), Integer.parseInt(job.get("answer").toString()));
            //TODO
            //questions.add(q);
            //questions.add(q);
        }
//        System.out.println(questions.get(0));
    }
}
