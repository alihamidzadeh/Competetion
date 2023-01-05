import java.util.ArrayList;
import java.util.HashMap;


public class Question {
    static ArrayList<Question> questions = QuestionsJsonParser.getQuestions();
    private String quest;
    private HashMap<Integer, String> choices;
    private int ans;

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public HashMap<Integer, String> getChoices() {
        return choices;
    }

    public void setChoices(HashMap<Integer, String> choices) {
        this.choices = choices;
    }


    public Question(String quest, String c1, String c2, String c3, String c4, int ans) {
        setQuest(quest);
        setCh(c1, c2, c3, c4);
        setAns(ans);
    }

    private void setCh(String c1, String c2, String c3, String c4) {
        this.choices = new HashMap<Integer, String>();
        this.choices.put(1, c1);
        this.choices.put(2, c2);
        this.choices.put(3, c3);
        this.choices.put(4, c4);
    }
}
