package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FullScreencall();
    }

    public void loginFunction(View view) {

        HashMap postData = new HashMap();
        postData.put("btnLogin", "Login");
        postData.put("mobile", "android");
        postData.put("txtBoxname", "0001");


        PostResponseAsyncTask loginTask =
                new PostResponseAsyncTask(DashboardActivity.this, postData,
                        DashboardActivity.this);
        //loginTask.execute("http://10.0.2.2/client/login.php");
        //Kevin IP: 10.183.50.32
        loginTask.execute("http://131.159.216.99/client/login.php");
    }

    @Override
    public void processFinish(String s) {

        if (s.equals("success")) {
            Toast.makeText(this, "Login Successfully",
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Login not Successfully",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void unwindBack(View view) {
        logOut(view);
    }

    public void logOut(View view) {
        Intent myIntent = new Intent(view.getContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
