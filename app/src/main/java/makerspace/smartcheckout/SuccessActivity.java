package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SuccessActivity extends AppCompatActivity implements AsyncResponse {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_page);
        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if(DashboardActivity.RENT_STATE == 0)
            createBorrowProcess();
        else if(DashboardActivity.RENT_STATE == 1)
            returnBorrowProcess();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SuccessActivity.this, MainActivity.class));
            }
        }, 3000);   //5 seconds
    }

    private void createBorrowProcess(){

        HashMap postData = new HashMap();
        postData.put("userID", String.valueOf(MainActivity.currentUser.getID()));
        postData.put("boxID", DashboardActivity.currentBox.getBoxID());
        postData.put("estimated", "2");


        PostResponseAsyncTask checkBox =
                new PostResponseAsyncTask(SuccessActivity.this, postData,
                        SuccessActivity.this);
        //TODO: change IP Adresse or Database Connection
        checkBox.execute("http://192.168.178.32/client/borrow.php");
    }

    private void returnBorrowProcess(){

        HashMap postData = new HashMap();
        postData.put("userID", String.valueOf(MainActivity.currentUser.getID()));
        postData.put("boxID", DashboardActivity.currentBox.getBoxID());


        PostResponseAsyncTask checkBox =
                new PostResponseAsyncTask(SuccessActivity.this, postData,
                        SuccessActivity.this);
        //TODO: change IP Adresse or Database Connection
        checkBox.execute("http://192.168.178.32/client/return.php");
    }


    @Override
    public void processFinish(String s) {
    }
}
