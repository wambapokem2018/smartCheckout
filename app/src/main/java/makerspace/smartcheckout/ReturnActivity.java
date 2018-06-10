package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class ReturnActivity extends AppCompatActivity implements View.OnClickListener {
    Button button, button2, button3, button4;
    CheckBox checkBox, checkBox2, checkBox3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.return_page);

        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(this);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox2.setOnClickListener(this);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);

        button = (Button)findViewById(R.id.lost);
        button2 = (Button)findViewById(R.id.allGood);
        button3 = (Button)findViewById(R.id.back);
        button4 = (Button)findViewById(R.id.logout);

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

        if (checkBox.isChecked()) {
            checkBox2.setChecked(false);
            checkBox3.setChecked(false);
        }else{
            checkBox2.setChecked(true);
            checkBox3.setChecked(true);
        }
        if (checkBox2.isChecked()) {
            checkBox.setChecked(false);
            checkBox3.setChecked(false);
        }else{
            checkBox.setChecked(true);
            checkBox3.setChecked(true);
        }
        if (checkBox3.isChecked()) {
            checkBox2.setChecked(false);
            checkBox.setChecked(false);
        }else{
            checkBox2.setChecked(true);
            checkBox.setChecked(true);
        }



    }
    public void goToNext(View view) {
        Intent myIntent = new Intent(view.getContext(), Return_MissingActivity.class);
        startActivityForResult(myIntent, 0);
    }
}
