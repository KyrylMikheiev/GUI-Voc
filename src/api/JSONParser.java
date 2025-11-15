package src.api;

import java.util.*;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;

public class JSONParser {

    private static class Reader {
        private int index;
        private String target;

        public Reader(String target)  {
            this.index = 0;
            this.target = target.trim();
        }

        public void skipWhitespace() {
            while (index < target.length() && Character.isWhitespace(peek())) {
                index++;
            }
        }

        public char peek() {
            if (index >= target.length())
                throw new RuntimeException("Unexpected end of input");
            return target.charAt(index);
        }

        public char next() {
            return target.charAt(index++);
        }

        public void expect(String s) {
            if (!target.startsWith(s, index))
                throw new RuntimeException("Expected '" + s + "'");
            index += s.length();
        }
    }

    public static JSONObject parse(String json) {
        Reader reader = new Reader(json);

        return JSONParser.parseNext(reader);
    }

    public static JSONObject parse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();

        InputStream inputStream;
        if (responseCode >= 400) {
            inputStream = connection.getErrorStream(); // <-- für Fehlerfälle
        } else {
            inputStream = connection.getInputStream();
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        String json = response.toString();
        return JSONParser.parse(json);
    }

    private static JSONObject parseNext(Reader reader) {
        reader.skipWhitespace();
        char c = reader.peek();

        switch (c) {
            case '{': 
                Map<String, JSONObject> obj = new LinkedHashMap<>();
                reader.next(); // '{'
                reader.skipWhitespace();
                    
                if (reader.peek() == '}') { 
                    reader.next(); 
                    return new JSONObject(obj); 
                }
            
                while (true) {
                    reader.skipWhitespace();
                    JSONObject key = parseNext(reader);
                    reader.skipWhitespace();
                    reader.next(); // ':'
                    reader.skipWhitespace();
                    JSONObject value = parseNext(reader);
                    obj.put(key.getString(), value);
                    reader.skipWhitespace();
                
                    reader.skipWhitespace();
                    char check = reader.peek(); // nur anschauen, nicht verbrauchen
                                    
                    if (check == '}') {
                        reader.next(); // jetzt wirklich das '}' verbrauchen
                        break;
                    }
                    
                    if (check != ',')
                        throw new RuntimeException("Expected ',' or '}'");
                    
                    reader.next(); // ',' überspringen
                }
                return new JSONObject(obj);
            case '[': 
                List<JSONObject> arr = new ArrayList<>();
                reader.next(); // '['
                reader.skipWhitespace();

                if (reader.peek() == ']') { reader.next(); return new JSONObject(arr); }

                while (true) {
                    arr.add(parseNext(reader));
                    reader.skipWhitespace();
                    char check = reader.peek();
                    if (check == ']') { reader.next(); break; }
                    if (check != ',') throw new RuntimeException("Expected ',' or ']'");
                    reader.next(); // ',' überspringen

                }
                return new JSONObject(arr);
            case '"': 
                StringBuilder sb = new StringBuilder();
                reader.next(); // '"'

                while (true) {
                    c = reader.next();
                    if (c == '"') break;
                    if (c == '\\') {
                        char esc = reader.next();
                        switch (esc) {
                            case '"': sb.append('"'); break;
                            case '\\': sb.append('\\'); break;
                            case '/': sb.append('/'); break;
                            case 'b': sb.append('\b'); break;
                            case 'f': sb.append('\f'); break;
                            case 'n': sb.append('\n'); break;
                            case 'r': sb.append('\r'); break;
                            case 't': sb.append('\t'); break;
                            case 'u':
                                StringBuilder hex = new StringBuilder();
                                for (int i=0; i<4; i++) {
                                    hex.append(reader.next());
                                }
                                sb.append((char) Integer.parseInt(hex.toString(), 16));
                                break;
                            default: throw new RuntimeException("Invalid escape sequence");
                        }
                    } else {
                        sb.append(c);
                    }
                }
                return new JSONObject(sb.toString());
            case 't': 
                reader.expect("true");
                return new JSONObject(true);
            case 'f': 
                reader.expect("false"); 
                return new JSONObject(false);
            case 'n': 
                reader.expect("null"); 
                return new JSONObject();
            default: 
                StringBuilder numberBuilder = new StringBuilder();
                if (reader.peek() == '-') {
                    numberBuilder.append(reader.next());
                }
            
                while (reader.index < reader.target.length() && Character.isDigit(reader.peek())) {
                    numberBuilder.append(reader.next());
                }
            
                if (reader.index < reader.target.length() && reader.peek() == '.') {
                    numberBuilder.append(reader.next());
                    while (reader.index < reader.target.length() && Character.isDigit(reader.peek())) {
                        numberBuilder.append(reader.next());
                    }
                }
            
                String num = numberBuilder.toString();
                return num.contains(".")
                    ? new JSONObject(Double.parseDouble(num))
                    : new JSONObject(Long.parseLong(num));
        }
    }
}
