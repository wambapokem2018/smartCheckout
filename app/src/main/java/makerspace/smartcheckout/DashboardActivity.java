package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kosalgeek.genasync12.AsyncResponse;

public class DashboardActivity extends AppCompatActivity implements AsyncResponse{

    Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

      //  FullScreencall();

       // button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);

     //   button.setOnClickListener(new View.OnClickListener(){
       //     public void onClick(View v){
         //       startActivity(new Intent(DashboardActivity.this, BorrowActivity.class));
           // }
       // });

        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(DashboardActivity.this, BorrowActivity.class));
            }
        });

    }

    @Override
    public void processFinish(String s) {

    }
}
