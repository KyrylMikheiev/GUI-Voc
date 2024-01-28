package VocabParser;
import java.util.List;

public class Vocab {
    private String basicForm;
    private List<String> translations;
    private String lesson;

    public Vocab(String basicForm, List<String> translations, String lesson) {
        this.basicForm = basicForm;
        this.translations = translations;
        this.lesson = lesson;
    }

    protected void setBasicForm(String form) {
        this.basicForm = form;
    }

    public String getBasicForm() {
        return basicForm;
    }
    
    public List<String> getGerman() {
        return translations;
    }
    
    public String getLesson() {
        return lesson;
    }
}
