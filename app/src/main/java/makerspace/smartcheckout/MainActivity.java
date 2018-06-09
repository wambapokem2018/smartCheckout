package makerspace.smartcheckout;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.widget.EditText;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements AsyncResponse {

    EditText etUsername, etPassword;
    Button btnLogin, button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FullScreencall();

        //btnLogin = (Button) findViewById(R.id.btnLogin);
        //etUsername = (EditText) findViewById(R.id.etUsername);


    }

    @Override
    protected void onResume() {
        super.onResume();
        FullScreencall();
    }

    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void loginFunction(View view) {

        HashMap postData = new HashMap();
        postData.put("btnLogin", "Login");
        postData.put("mobile", "android");
        postData.put("txtUsername", etUsername.getText().toString());


        PostResponseAsyncTask loginTask =
                new PostResponseAsyncTask(MainActivity.this, postData,
                        MainActivity.this);
        //loginTask.execute("http://10.0.2.2/client/login.php");
        //Kevin IP: 10.183.50.32
        loginTask.execute("http://10.183.86.178/client/login.php");
    }

    @Override
    public void processFinish(String output) {
        if(output.equals("success")){
            Toast.makeText(this, "Login Successfully",
                    Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Login not Successfully",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void unwindBluetooth(View view) {
        Intent myIntent = new Intent(view.getContext(), Bluetooth.class);
        startActivityForResult(myIntent, 0);
    }
    public void goToDashboard(View view) {
            Intent myIntent = new Intent(view.getContext(), DashboardActivity.class);
            startActivityForResult(myIntent, 0);
    }

}
