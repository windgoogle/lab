package com.woo.base.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClient {

    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    public String get(String url) throws Exception{
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public void testGet(String url) {

    }


    public static class Client implements Runnable {
        private HttpClient client=new HttpClient();

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println(client.get("http://127.0.0.1:8080/Servlet3Programmatic/1.JPG"));
                    System.out.println(client.get("http://127.0.0.1:8080/Servlet3Programmatic/Assembler.java"));

                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws Exception{
            ExecutorService service= Executors.newFixedThreadPool(10);
        for(int i=0;i<1000;i++){
            Client client=new Client();
            service.execute(client);
        }
    }
}
