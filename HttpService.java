package u.com.example.chenx.tradeasapro;

import android.content.Intent;
import android.os.Message;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import static u.com.example.chenx.tradeasapro.MainActivity.SHOW_RESPONSE;

/**
 * Created by CHENX on 2018-07-24.
 */

public class HttpService implements Runnable{
    private HttpClient httpCient = null;
    private HttpGet httpGet = null;

    public HttpService(String str){
        httpCient = new DefaultHttpClient();
        httpGet = new HttpGet("http://hq.sinajs.cn/list=" + str);
    }

    @Override
    public void run(){
        Intent intent = new Intent();
        intent.setAction("response");
        try {
            HttpResponse httpResponse = httpCient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity, "utf-8");
                intent.putExtra("response", response);
                MainActivity.context.sendBroadcast(intent);
            }
            if (httpResponse.getStatusLine().getStatusCode() == 404) {
                intent.putExtra("response", "User Error");
                MainActivity.context.sendBroadcast(intent);
            }
            if (httpResponse.getStatusLine().getStatusCode() == 505) {
                intent.putExtra("response", "Server Error");
                MainActivity.context.sendBroadcast(intent);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
