package VocabParsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public class Verb extends Vocab {

    private ArrayList<String> imperfekt;
    private ArrayList<String> perfekt;
    private ArrayList<String> plusquamperfekt;
    private ArrayList<String> praesens;
    private ArrayList<String> futurI;
    private ArrayList<String> futurII;

    public Verb(String latinInfo, List<String> translations, String lesson) {
        super("", translations, lesson);
        generateTimeForms(latinInfo);
    }

    private ArrayList<String> generateImperfektForms(String baseform) {
        ArrayList<String> generatedForms = new ArrayList<String>();

        generatedForms.add(baseform + "bam");
        generatedForms.add(baseform + "bas");
        generatedForms.add(baseform + "bat");
        generatedForms.add(baseform + "bamus");
        generatedForms.add(baseform + "batis");
        generatedForms.add(baseform + "bant");

        return generatedForms;
    }

    private ArrayList<String> generateFutureIFormsBoBiBu(String baseform) {
        ArrayList<String> generatedForms = new ArrayList<String>();

        generatedForms.add(baseform + "bo");
        generatedForms.add(baseform + "bis");
        generatedForms.add(baseform + "bit");
        generatedForms.add(baseform + "bimus");
        generatedForms.add(baseform + "bitis");
        generatedForms.add(baseform + "bunt");

        return generatedForms;
    }

    private ArrayList<String> generateFutureIFormsAmerica(String baseform) {
        ArrayList<String> generatedForms = new ArrayList<String>();

        generatedForms.add(baseform + "am");
        generatedForms.add(baseform + "es");
        generatedForms.add(baseform + "et");
        generatedForms.add(baseform + "emus");
        generatedForms.add(baseform + "etis");
        generatedForms.add(baseform + "ent");

        return generatedForms;
    }

    private ArrayList<String> generatePerfektForms(String baseform) {
        ArrayList<String> generatedForms = new ArrayList<String>();

        generatedForms.add(baseform + "i");
        generatedForms.add(baseform + "isti");
        generatedForms.add(baseform + "it");
        generatedForms.add(baseform + "imus");
        generatedForms.add(baseform + "istis");
        generatedForms.add(baseform + "erunt");

        return generatedForms;
    }

    private ArrayList<String> generatePlusquamperfektForms(String baseform) {
        ArrayList<String> generatedForms = new ArrayList<String>();

        generatedForms.add(baseform + "eram");
        generatedForms.add(baseform + "eras");
        generatedForms.add(baseform + "erat");
        generatedForms.add(baseform + "eramus");
        generatedForms.add(baseform + "eratis");
        generatedForms.add(baseform + "erant");

        return generatedForms;
    }

    private ArrayList<String> generateFuturIIForms(String baseform) {
        ArrayList<String> generatedForms = new ArrayList<String>();

        generatedForms.add(baseform + "ero");
        generatedForms.add(baseform + "eris");
        generatedForms.add(baseform + "erit");
        generatedForms.add(baseform + "erimus");
        generatedForms.add(baseform + "eritis");
        generatedForms.add(baseform + "erint");

        return generatedForms;
    }

    private void generateTimeForms(String latinInfo) {
        List<String> givenForms = Arrays.asList(latinInfo.split(", "));
        String basicForm = givenForms.get(0);
        this.setBasicForm(basicForm);

        praesens = new ArrayList<String>();

        if (basicForm.endsWith("re")) {

            if (givenForms.size() == 1) {

                if (basicForm.endsWith("are")) {

                    // A KONJUGATION

                    String praesensBaseform = basicForm.substring(0, basicForm.length() - 3);

                    praesens.add(praesensBaseform + "o");
                    praesens.add(praesensBaseform + "as");
                    praesens.add(praesensBaseform + "at");
                    praesens.add(praesensBaseform + "amus");
                    praesens.add(praesensBaseform + "atis");
                    praesens.add(praesensBaseform + "ant");

                    imperfekt = generateImperfektForms(praesensBaseform);
                    futurI = generateFutureIFormsBoBiBu(praesensBaseform);

                    String perfektBaseform = basicForm.substring(0, basicForm.length() - 2) + "v";
                    perfekt = generatePerfektForms(perfektBaseform);
                    plusquamperfekt = generatePlusquamperfektForms(perfektBaseform);
                    futurII = generateFuturIIForms(perfektBaseform);
                } else {

                    // E-KONJUGATION (?)

                    String praesensBaseform = basicForm.substring(0, basicForm.length() - 2);

                    praesens.add(praesensBaseform + "o");
                    praesens.add(praesensBaseform + "s");
                    praesens.add(praesensBaseform + "t");
                    praesens.add(praesensBaseform + "mus");
                    praesens.add(praesensBaseform + "tis");
                    praesens.add(praesensBaseform + "nt");

                    imperfekt = generateImperfektForms(praesensBaseform);
                    futurI = generateFutureIFormsBoBiBu(praesensBaseform);

                    String perfektBaseform = basicForm.substring(0, basicForm.length() - 3) + "u";
                    perfekt = generatePerfektForms(perfektBaseform);
                    plusquamperfekt = generatePlusquamperfektForms(perfektBaseform);
                    futurII = generateFuturIIForms(perfektBaseform);
                }

            } else if (givenForms.size() > 2) {

                // KONSONANTISCHE KONJUGATION

                String firstPersPraes = givenForms.get(1);
                String praesensBaseform = firstPersPraes.substring(0, firstPersPraes.length() - 1);

                praesens.add(praesensBaseform + "o");
                praesens.add(praesensBaseform + "is");
                praesens.add(praesensBaseform + "it");
                praesens.add(praesensBaseform + "imus");
                praesens.add(praesensBaseform + "itis");
                praesens.add(praesensBaseform + "unt");

                imperfekt = generateImperfektForms(praesensBaseform + "e");
                futurI = generateFutureIFormsAmerica(praesensBaseform);

                String firstPersPerf = givenForms.get(2);
                String perfektBaseform = firstPersPerf.substring(0, firstPersPerf.length() - 1);
                perfekt = generatePerfektForms(perfektBaseform);
                plusquamperfekt = generatePlusquamperfektForms(perfektBaseform);
                futurII = generateFuturIIForms(perfektBaseform);
            }
        }
    }

    public ArrayList<String> getImperfekt() {
        return imperfekt;
    }

    public ArrayList<String> getPerfekt() {
        return perfekt;
    }

    public ArrayList<String> getPlusquamperfekt() {
        return plusquamperfekt;
    }

    public ArrayList<String> getPraesens() {
        return praesens;
    }

    public ArrayList<String> getFuturI() {
        return futurI;
    }

    public ArrayList<String> getFuturII() {
        return futurII;
    }

    public HashMap<String, ArrayList<String>> getTimeForms() {
        HashMap<String, ArrayList<String>> allForms = new HashMap<String, ArrayList<String>>();
        allForms.put("Imperfekt", getImperfekt());
        allForms.put("Perfekt", getPerfekt());
        allForms.put("Plusquamperfekt", getPlusquamperfekt());
        allForms.put("Praesens", getPraesens());
        allForms.put("FuturI", getFuturI());
        allForms.put("FuturII", getFuturII());

        return allForms;
    }
}