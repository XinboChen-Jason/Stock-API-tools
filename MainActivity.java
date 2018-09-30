package u.com.example.chenx.tradeasapro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity {
    public static final int SHOW_RESPONSE = 0;

    private Button sendRequest;
    private TextView response;
    private TextView name, curPrice, lastClose, curOpen, high, low, volume, volumeInCurrency, upRate, upAmount;
    private EditText userIn;

    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    public static Context context;
    public static HttpService curHttpService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hide title bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        bindWidget();
        BindReceiver();
        context = this;

    }

    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(myBroadcastReceiver);
    }

    private void bindWidget() {
        sendRequest = (Button) findViewById(R.id.getButton);
        userIn = (EditText) findViewById(R.id.userIn);
        name = (TextView) findViewById(R.id.name);
        curPrice = (TextView) findViewById(R.id.curPrice);
        lastClose = (TextView) findViewById(R.id.lastClose);
        curOpen = (TextView) findViewById(R.id.curOpen);
        high = (TextView) findViewById(R.id.high);
        low = (TextView) findViewById(R.id.low);
        volume = (TextView) findViewById(R.id.volume);
        volumeInCurrency = (TextView) findViewById(R.id.volumeInCurrency);
        upRate = (TextView) findViewById(R.id.upRate);
        upAmount = (TextView) findViewById(R.id.upAmount);

        sendRequest.setOnClickListener(new OnClickListener() {

            //点击按钮时，执行sendRequestWithHttpClient()方法里面的线程
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!userIn.getText().toString().equals(""))
                    sendRequestWithHttpClient(userIn.getText().toString());
            }
        });
    }

    //after received data, refresh TextView
    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String response2 = (String) msg.obj;
                    anaMessage(response2);
                    break;
                default:
                    break;
            }
        }

    };

    private void BindReceiver() {
        IntentFilter intentFilter = new IntentFilter("response");
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction) {
                case "response":
                    String msg = intent.getStringExtra("response");
                    Message message = new Message();
                    message.what = 1;
                    message.obj = msg;
                    myHandler.sendMessage(message);
                    break;
            }
        }

    }

    private void sendRequestWithHttpClient(final String str) {
        curHttpService = new HttpService(str);
        Thread thread = new Thread(curHttpService);
        thread.start();
    }

    //refresh View
    private void anaMessage(String msg) {
        Stock curStock = new Stock(msg);
        if (curStock.getType().equals("sh")|| curStock.getType().equals("sz")) {
            name.setText(curStock.getName());
            curPrice.setText(curStock.getCurPrice());
            lastClose.setText(curStock.getLastClose());
            curOpen.setText(curStock.getCurOpen());
            high.setText(curStock.getHigh());
            low.setText(curStock.getLow());
            volume.setText(curStock.getVolume());
            volumeInCurrency.setText(curStock.getVolumeInCurrency());
            upRate.setText(curStock.getUpRate() + "%");
            upAmount.setText(curStock.getUpAmount());
        } else if (curStock.getType().equals("hk")) {
            name.setText(curStock.getName());
            curPrice.setText(curStock.getCurPrice());
            lastClose.setText(curStock.getLastClose());
            curOpen.setText(curStock.getCurOpen());
            high.setText(curStock.getHigh());
            low.setText(curStock.getLow());
            volume.setText(curStock.getVolume());
            volumeInCurrency.setText(curStock.getVolumeInCurrency());
            upRate.setText(curStock.getUpRate() + "%");
            upAmount.setText(curStock.getUpAmount());
        } else if (curStock.getType().equals("gb")) {
            name.setText(curStock.getName());
            curPrice.setText(curStock.getCurPrice());
            lastClose.setText(curStock.getLastClose());
            curOpen.setText(curStock.getCurOpen());
            high.setText(curStock.getHigh());
            low.setText(curStock.getLow());
            volume.setText(curStock.getVolume());
            volumeInCurrency.setText(curStock.getVolumeInCurrency());
            upRate.setText(curStock.getUpRate() + "%");
            upAmount.setText(curStock.getUpAmount());
        }
    }
}
