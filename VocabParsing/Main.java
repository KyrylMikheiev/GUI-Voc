package VocabParsing;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        VocabParser.getAllVocabs();

        /*VocabTestParser parser = new VocabTestParser();
        List<Vocab> vocabulary = parser.getAllVocabs();
        Vocab test = vocabulary.get(3);
        //System.out.println(test.getGerman());*/

        new Verb("merere", Arrays.asList("hinzufügen"), "10");
        //System.out.println(vtest.getTimeForms());

        new Noun("sol, solis m", Arrays.asList("die Sonne"), "1");
        //System.out.println(ntest.getGender());
    }
}
