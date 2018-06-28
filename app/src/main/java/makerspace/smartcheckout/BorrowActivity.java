package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;

public class BorrowActivity extends AppCompatActivity implements AsyncResponse {

    Button button, button2, button3, button4;
    final String LOG = "BorrowActivity";
    private ArrayList<Box> boxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_page);


        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        button = (Button) findViewById(R.id.goToFinish);
        button2 = (Button) findViewById(R.id.showObject);
        button3 = (Button) findViewById(R.id.back);
        button4 = (Button) findViewById(R.id.logout);

        //navigate to success page
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BorrowActivity.this, SuccessActivity.class));
            }
        });

        //navigate to borrow missing page
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BorrowActivity.this, Borrow_MissingActivity.class));
            }
        });

        //navigate to dashboard page
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BorrowActivity.this, DashboardActivity.class));
            }
        });

        //navigate to main activity page
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BorrowActivity.this, MainActivity.class));
            }
        });



    }


    @Override
    public void processFinish(String s) {
        Log.d(LOG, s);
    }
}


