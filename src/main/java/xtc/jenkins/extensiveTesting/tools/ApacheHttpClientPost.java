package xtc.jenkins.extensiveTesting.tools;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

public class ApacheHttpClientPost {

    public static String request(String url, String method, String params, String cookie) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url + method);
                //    "http://localhost:8080/RESTfulExample/json/product/post");

            StringEntity input = new StringEntity(params);
            if (null != cookie) {
                postRequest.addHeader(Const.COOKIE, cookie);
            }
            input.setContentType(Const.CONTENT_TYPE);
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                stringBuilder.append(output);
            }

            httpClient.getConnectionManager().shutdown();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return stringBuilder.toString();
    }

}

