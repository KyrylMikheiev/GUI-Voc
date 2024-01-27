package VocabParser;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Adjective extends Vocab {

    public Adjective(String basicForm, List<String> translations, String lesson) {
        super(basicForm, translations, lesson);
    }

    public ArrayList<String> getMaskulinumDeklination() {
        return new ArrayList<String>();
    }

    public ArrayList<String> getFemininumDeklination() {
        return new ArrayList<String>();
    }

    public ArrayList<String> getNeutrumDeklination() {
        return new ArrayList<String>();
    }

    public HashMap<String, ArrayList<String>> getAllDeklination() {
        HashMap<String, ArrayList<String>> allDekl = new HashMap<String, ArrayList<String>>();
        allDekl.put("m", getMaskulinumDeklination());
        allDekl.put("f", getFemininumDeklination());
        allDekl.put("n", getNeutrumDeklination());

        return allDekl;
    }
}