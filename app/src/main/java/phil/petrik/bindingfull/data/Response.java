package phil.petrik.bindingfull.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Response {
    private int code;
    private String content;

    public Response(int code,String content){
        this.code = code;
        this.content = content;
    }

    public Response(int code, InputStream content) throws IOException {
        this.code = code;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
        StringBuilder boby = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            boby.append(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        this.content = boby.toString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
