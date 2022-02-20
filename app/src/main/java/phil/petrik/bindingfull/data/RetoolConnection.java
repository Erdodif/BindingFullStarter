package phil.petrik.bindingfull.data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class RetoolConnection {
    private HttpURLConnection conn;

    public RetoolConnection(String urlExtension, String method) throws IOException{
        method = method.toUpperCase(Locale.ROOT);
        String finalUrl = "https://retoolapi.dev/Q304E6" + urlExtension;
        Log.d("Új kapcsolat / "+method, finalUrl);
        this.conn = (HttpURLConnection) (new URL( finalUrl)).openConnection();
        this.conn.setRequestMethod(method);
        this.conn.setRequestProperty("Accept", "application/json");
        if (method =="POST" || method == "PATCH"){
            conn.setDoOutput(true);
            this.conn.setRequestProperty("Content-Type", "application/json");
        }
    }

    public InputStream getResponseContent() {
        try {
            return this.conn.getInputStream();
        } catch (IOException e) {
            return this.conn.getErrorStream();
        }
    }

    public void putJSON(String json) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        bw.write(json);
        bw.flush();
    }

    public Response getCall() throws IOException {
        return new Response(conn.getResponseCode(), this.getResponseContent());
    }

    public Response deleteCall() throws IOException {
        return new Response(conn.getResponseCode(), this.getResponseContent());
    }

    public Response postCall(String jsonContent) throws IOException {
        this.putJSON(jsonContent);
        return new Response(conn.getResponseCode(), this.getResponseContent());
    }

    public Response patchCall(String jsonContent) throws IOException {
        this.putJSON(jsonContent);
        return new Response(this.conn.getResponseCode(), this.getResponseContent());
    }

    public String getContentString() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.getResponseContent()));
        StringBuilder boby = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            boby.append(line);
            line = bufferedReader.readLine();
        }
        return boby.toString();
    }

    public String getrequestMethod(){
        return this.conn.getRequestMethod();
    }
}