package makerspace.smartcheckout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReturnActivity extends AppCompatActivity implements View.OnClickListener, AsyncResponse{
    Button button, button2, button3, button4;
    CheckBox checkBox, checkBox2, checkBox3;
    final String LOG = "ReturnActivity";
    private     ArrayList<Box> boxes;
    JSONObject json = null;
    String str = "";
    HttpResponse response;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.return_page);


        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        checkBox = (CheckBox) findViewById(R.id.allFine);
        checkBox.setOnClickListener(this);
        checkBox2 = (CheckBox) findViewById(R.id.yesFixed);
        checkBox2.setOnClickListener(this);
        checkBox3 = (CheckBox) findViewById(R.id.yesNoFixed);
        checkBox3.setOnClickListener(this);

        button = (Button)findViewById(R.id.lost);
        button2 = (Button)findViewById(R.id.allGood);
        button3 = (Button)findViewById(R.id.back);
        button4 = (Button)findViewById(R.id.logout);

        HttpClient myClient = new DefaultHttpClient();
        HttpPost myConnection = new HttpPost("http://10.180.39.27/client/box.php");


        try {
            response = myClient.execute(myConnection);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(LOG, str);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ReturnActivity.this, Return_MissingActivity.class));
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ReturnActivity.this, SuccessActivity.class));
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ReturnActivity.this, DashboardActivity.class));
            }
        });
        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ReturnActivity.this, MainActivity.class));
            }
        });

    }
    @Override
    public void onClick(View view) {



    }

    @Override
    public void processFinish(String s) {
        Log.d(LOG, s);
    }
}
