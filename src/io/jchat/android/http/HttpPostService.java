package io.jchat.android.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import android.util.Log;

public class HttpPostService {

    /**
     * 
     * @param method
     *            SOAP_UTILS
     * @param property_nm
     * @param property_va
     * @return
     */
    public static Object data(String url,String method, String[] property_nm,
            Object[] property_va) {
        try {

            NewHttpClient httpClient = new NewHttpClient();

            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("User-Agent", "imgfornote");

            Map<String, String> params = new HashMap<String, String>();
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < property_va.length; i++) {
                jsonObject.put(property_nm[i], property_va[i]);
                params.put(property_nm[i], property_va[i].toString());
            }
            httpPost.setEntity(
                    new StringEntity(jsonObject.toString(), "UTF-8"));

            String response = httpClient.post(url, params);
            // BufferedReader reader = new BufferedReader(new InputStreamReader(
            // response.getEntity().getContent(), "UTF-8"));
            // String sResponse = reader.readLine();

            return response;
        } catch (Exception e) {
            Log.v("ImgPostService", "Some error came up");
            return null;

        }
    }
}
