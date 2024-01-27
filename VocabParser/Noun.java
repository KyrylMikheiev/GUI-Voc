package VocabParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Noun extends Vocab {
    private String gender;
    private HashMap<String, ArrayList<String>> deklination;

    public Noun(String latinInfo, List<String> translations, String lesson) {
        super("", translations, lesson);
        generateDeklination(latinInfo);
    }

    private void generateDeklination(String latinInfo) {
        if (this.deklination == null) {
            this.deklination = new HashMap<String, ArrayList<String>>();
        }

        List<String> givenForms = Arrays.asList(latinInfo.split(", "));

        ArrayList<String> singular = new ArrayList<String>();
        ArrayList<String> plural = new ArrayList<String>();

        String nominativ = givenForms.get(0);
        String genitiv = "";

        HashMap<String, HashMap<String, ArrayList<String>>> irregularities = new HashMap<>();

        HashMap<String, ArrayList<String>> visForms = new HashMap<>();
        visForms.put("Singular", new ArrayList<>(Arrays.asList("vis", "", "", "vim", "vi")));
        visForms.put("Plural", new ArrayList<>(Arrays.asList("vires", "virium", "viribus", "vires", "viribus")));
        irregularities.put("vis", visForms);
            
        HashMap<String, ArrayList<String>> parentesForms = new HashMap<>();
        parentesForms.put("Singular", new ArrayList<>());
        parentesForms.put("Plural", new ArrayList<>(Arrays.asList("parentes", "parentium", "parentibus", "rentibus", "parentibus")));
        irregularities.put("parentes", parentesForms);

        if (irregularities.containsKey(nominativ)) {
            deklination = irregularities.get(nominativ);
            return;
        }

        boolean pluralWord = false;

        List<String> genList = Arrays.asList();
        try {
            genList = Arrays.asList(givenForms.get(1).split(" "));
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }

        if (genList.size() == 2) {
            // Regular word
            genitiv = genList.get(0);
            this.gender = genList.get(1);
        } else if (genList.size() > 2) {
            if (genList.get(genList.size() - 1).contains("Pluralwort")) {
                // Pluralword
                genitiv = genList.get(0);
                this.gender = genList.get(1);
                pluralWord = true;
            } else {
                // Vocab has more than one word (e.g. res publica)
                this.gender = genList.get(genList.size() - 1);
                genitiv = genList.stream().limit(genList.size() - 1).collect(Collectors.joining(" "));
                ;
            }
        }

        if (pluralWord) {
            plural.add(nominativ);
            plural.add(genitiv);

            if (genitiv.endsWith("orum")) {
                String base = genitiv.substring(0, genitiv.length() - 4);
                plural.add(base + "is");
                plural.add(base + "os");
                plural.add(base + "is");
            } else if (genitiv.endsWith("arum")) {
                String base = genitiv.substring(0, genitiv.length() - 4);
                plural.add(base + "is");
                plural.add(base + "as");
                plural.add(base + "is");
            } else {
                String base = genitiv.substring(0, genitiv.length() - 2);
                if (base.endsWith("i")) {
                    base = base.substring(0, base.length() - 1);
                }
                plural.add(base + "ibus");
                plural.add(nominativ);
                plural.add(base + "ibus");
            }

        } else if (nominativ.endsWith("a") && genitiv.endsWith("ae")) {
            // A DEKLINATION
            String base = genitiv.substring(0, genitiv.length() - 2);

            singular.add(nominativ);
            singular.add(genitiv);
            singular.add(base + "ae");
            singular.add(base + "am");
            singular.add(base + "a");

            plural.add(base + "ae");
            plural.add(base + "arum");
            plural.add(base + "is");
            plural.add(base + "as");
            plural.add(base + "is");
        } else if ((nominativ.endsWith("us") || nominativ.endsWith("um")) && genitiv.endsWith("i")) {
            // O DEKLINATION
            String base = nominativ.substring(0, nominativ.length() - 2);

            singular.add(nominativ);
            singular.add(genitiv);
            singular.add(base + "o");
            singular.add(base + "um");
            singular.add(base + "o");

            if (gender.contains("m")) {
                plural.add(base + "i");
            } else {
                plural.add(base + "a");
            }
            plural.add(base + "orum");
            plural.add(base + "is");
            if (gender.contains("m")) {
                plural.add(base + "os");
            } else {
                plural.add(base + "a");
            }
            plural.add(base + "is");
        } else if ((nominativ.endsWith("er") || nominativ.endsWith("ir")) && genitiv.endsWith("i")) {
            // O DEKLINATION auf -er
            String base = genitiv.substring(0, genitiv.length() - 1);

            singular.add(nominativ);
            singular.add(genitiv);
            singular.add(base + "o");
            singular.add(base + "um");
            singular.add(base + "o");

            plural.add(base + "i");
            plural.add(base + "orum");
            plural.add(base + "is");
            plural.add(base + "os");
            plural.add(base + "is");
        } else if (nominativ.endsWith("us") && genitiv.endsWith("us")) {
            // U DEKLINATION
            String base = nominativ.substring(0, nominativ.length() - 2);

            singular.add(nominativ);
            singular.add(genitiv);
            singular.add(base + "ui");
            singular.add(base + "um");
            singular.add(base + "u");

            plural.add(base + "us");
            plural.add(base + "uum");
            plural.add(base + "ibus");
            plural.add(base + "us");
            plural.add(base + "ibus");
        } else if (genitiv.endsWith("is")) {
            // KONSONANTISCHE DEKLINATION
            String base = genitiv.substring(0, genitiv.length() - 2);

            singular.add(nominativ);
            singular.add(genitiv);
            singular.add(base + "i");
            singular.add(base + "em");
            singular.add(base + "e");

            if (gender.contains("n")) {
                plural.add(base + "a");
            } else {
                plural.add(base + "es");
            }
            plural.add(base + "um");
            plural.add(base + "ibus");
            plural.add(plural.get(0));
            plural.add(base + "ibus");
        } else if (nominativ.endsWith("es") && genitiv.endsWith("ei")) {
            // E DEKLINATION
            String base = genitiv.substring(0, genitiv.length() - 1);

            singular.add(nominativ);
            singular.add(genitiv);
            singular.add(base + "i");
            singular.add(base + "m");
            singular.add(base);

            plural.add(base + "s");
            plural.add(base + "rum");
            plural.add(base + "bus");
            plural.add(base + "s");
            plural.add(base + "bus");
        } else {
            System.out.println(nominativ + " " + genitiv);
        }

        deklination.put("Singular", singular);
        deklination.put("Plural", plural);

        if (givenForms.size() > 2) {
            // Ausnahmen werden mitgegeben
            HashMap<String, Integer> caseMeaning = new HashMap<String, Integer>();
            caseMeaning.put("Nom.", 0);
            caseMeaning.put("Gen.", 1);
            caseMeaning.put("Dat.", 2);
            caseMeaning.put("Akk.", 3);
            caseMeaning.put("Abl.", 4);

            HashMap<String, String> numeraleMeaning = new HashMap<String, String>();
            numeraleMeaning.put("Sg.", "Singular");
            numeraleMeaning.put("Pl.", "Plural");

            for (int i = 2; i < givenForms.size(); ++i) {
                List<String> irregularityData = Arrays.asList(givenForms.get(i).split(" "));
                if (irregularityData.size() > 3) {
                    // Alternative Form
                } else {
                    List<String> irrCase = Arrays.asList(irregularityData.get(0).split("/"));
                    for (int j = 0; j < irrCase.size(); j++) {
                        ArrayList<String> modifyData = deklination.get(numeraleMeaning.get(irregularityData.get(1)));

                        modifyData.set(caseMeaning.get(irrCase.get(j)), irregularityData.get(2));
                        deklination.replace(
                                numeraleMeaning.get(irregularityData.get(1)),
                                modifyData);
                    }
                }
            }
        }
    }

    public String getGender() {
        return gender;
    }

    public HashMap<String, ArrayList<String>> getDeklination() {
        return deklination;
    }
}
