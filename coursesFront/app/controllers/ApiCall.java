package controllers;

import play.mvc.Controller;
import play.mvc.Http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiCall extends Controller {

    public static void list(int pageSize, int pageNumber) throws IOException {
        URL getRequest = new URL("http://localhost:9001/course/list?pageSize="+ params.get("pageSize") +"&pageNumber=" +params.get("pageNumber"));
        String read = null;
        HttpURLConnection connection = (HttpURLConnection) getRequest.openConnection();
        connection.setRequestMethod("GET");
        int responsecode = connection.getResponseCode();

        if (responsecode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((read = in.readLine()) != null) {
                response.append(read);
            } in.close();

            renderJSON(response.toString());
        }

        else {
            System.out.println("List is Empty");
        }
    }

    public static void listS(long id) throws IOException {
        URL getRequest = new URL("http://localhost:9001/course/listC/" + params.get("id"));
        String read = null;
        HttpURLConnection connection = (HttpURLConnection) getRequest.openConnection();
        connection.setRequestMethod("GET");
        int responsecode = connection.getResponseCode();

        if (responsecode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((read = in.readLine()) != null) {
                response.append(read);
            } in.close();

            renderJSON(response.toString());
        }

        else {
            System.out.println("Id is not present");
        }
    }

    public static void add(Long id, String name, String description) throws IOException {
        URL postRequest = new URL("http://localhost:9001/course/add");
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("id", id);
        params.put("name", name);
        params.put("description", description);


        StringBuilder addData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (addData.length() != 0) addData.append('&');
            addData.append((URLEncoder.encode(param.getKey(), "UTF-8")));
            addData.append('=');
            addData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDatabytes = addData.toString().getBytes("UTF-8");

        String read = null;
        HttpURLConnection connection = (HttpURLConnection) postRequest.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(postDatabytes.length));
        connection.setDoOutput(true);
        connection.getOutputStream().write(postDatabytes);

        int responseCode = connection.getResponseCode();
        System.out.println("POST response code:: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputline;
            StringBuffer response = new StringBuffer();

            while ((inputline = in.readLine()) != null) {
                response.append(inputline);
            }
            in.close();

            renderText("Course has been added : " + "\n" +
                    id + "\n" +
                    name + "\n" +
                    description);
        } else {
            System.out.println("Request failed");
        }
    }
}
