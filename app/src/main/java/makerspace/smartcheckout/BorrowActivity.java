package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class BorrowActivity extends AppCompatActivity{

    Button button, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_page);


        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        button = (Button)findViewById(R.id.goToFinish);
        button2 = (Button)findViewById(R.id.showObject);
        button3 = (Button)findViewById(R.id.back);
        button4 = (Button)findViewById(R.id.logout);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(BorrowActivity.this, SuccessActivity.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(BorrowActivity.this, Borrow_MissingActivity.class));
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(BorrowActivity.this, DashboardActivity.class));
            }
        });
        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(BorrowActivity.this, MainActivity.class));
            }
        });

    }

}
