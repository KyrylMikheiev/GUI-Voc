package VocabParser;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CSVParser {
    private String filePath;
    private String splitChar;
    private Integer estimatedSize;

    public CSVParser(String filePath, String splitChar)
    {
        this.filePath = filePath;
        this.splitChar = splitChar;
        this.estimatedSize = 1150;
    }

    public CSVParser(String filePath, String splitChar, Integer estimatedSize)
    {
        this.filePath = filePath;
        this.splitChar = splitChar;
        this.estimatedSize = estimatedSize;
    }

    public List<List<String>> parse() {
        List<List<String>> data = new ArrayList<>(this.estimatedSize);
    
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;

            while ((line = br.readLine()) != null) {
                data.add(Arrays.asList(line.split(this.splitChar)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return data;
    }
}
