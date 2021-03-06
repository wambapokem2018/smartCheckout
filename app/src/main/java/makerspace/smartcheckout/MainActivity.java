package makerspace.smartcheckout;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.widget.EditText;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements AsyncResponse {

    EditText etUsername, etPassword;
    Button btnLogin, button2;

    public static User currentUser = new User(15, "Franziska", "Frankenstein",
            "Valleystraße 17, 81371 München", "FrankensteinchenFran@googlemail.com", 1);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FullScreencall();

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

    public void goToDashboard(View view) {
            Intent myIntent = new Intent(view.getContext(), DashboardActivity.class);
            startActivityForResult(myIntent, 0);
    }

    public void goToAdvanceMode(View view) {
        Intent myIntent = new Intent(view.getContext(), Bluetooth.class);
        startActivityForResult(myIntent, 0);
    }

}
