package phil.petrik.bindingfull.data;

import android.os.AsyncTask;

import java.io.IOException;

public class RequestTask extends AsyncTask<Void, Void, Response> {
    private RetoolConnection conn;
    private String params;
    private Response response;
    private Runnable lastTask;

    public RequestTask(String url, String method, String paramsJson, Runnable lastTask) throws IOException {
        this.conn = new RetoolConnection(url, method);
        this.params = paramsJson;
        this.response = null;
        this.lastTask = lastTask;
    }

    public RequestTask(String url, String method, String paramsJson) throws IOException {
        this.conn = new RetoolConnection(url, method);
        this.params = paramsJson;
        this.response = null;
        this.lastTask = null;
    }

    public RequestTask(String url, String method, Runnable lastTask) throws IOException {
        this.conn = new RetoolConnection(url, method);
        this.params = null;
        this.response = null;
        this.lastTask = lastTask;
    }

    public RequestTask(String url, String method) throws IOException {
        this.conn = new RetoolConnection(url, method);
        this.params = null;
        this.response = null;
        this.lastTask = null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(Response r) {
        this.response = r;
        lastTask.run();
    }

    @Override
    protected Response doInBackground(Void... voids) {
        Response r = null;
        try {
            if (params != null) {
                this.conn.putJSON(params);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (params == null) {
                if (conn.getrequestMethod().equals("GET")) {
                    return conn.getCall();
                }
                return conn.deleteCall();
            }
            if (conn.getrequestMethod().equals("POST")) {
                return conn.postCall(params);
            }
            return conn.patchCall(params);
        } catch (IOException e) {
            e.printStackTrace();
            return new Response(600, e.getMessage());
        }
    }

    public Response getResponse() {
        return this.response;
    }

    public void setLastTask(Runnable r) {
        this.lastTask = r;
    }
}
