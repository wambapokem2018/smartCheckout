package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

public class Borrow_MissingActivity extends AppCompatActivity implements View.OnClickListener{

             CheckBox checkBox, checkBox2;
             Button button, button2;
             ImageView image1, image2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_missing_page);

        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        checkBox = (CheckBox) findViewById(R.id.yes);
        checkBox.setOnClickListener(this);

        checkBox2 = (CheckBox) findViewById(R.id.no);
        checkBox2.setOnClickListener(this);

        button2 = (Button)findViewById(R.id.back);
        button = (Button)findViewById(R.id.logout);

        image1 = (ImageView) findViewById(R.id.makerspaceLogo);
        image2 = (ImageView) findViewById(R.id.makerspaceLogo2);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Borrow_MissingActivity.this, MainActivity.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Borrow_MissingActivity.this, BorrowActivity.class));
            }
        });

        image1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Borrow_MissingActivity.this, SuccessActivity.class));
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Borrow_MissingActivity.this, SuccessActivity.class));
            }
        });

    }

    @Override
    public void onClick(View view) {

        if (checkBox.isChecked()) {
            checkBox2.setChecked(false);
        }else{
            checkBox2.setChecked(true);
        }

        if (checkBox2.isChecked()) {
            checkBox.setChecked(false);
        }else {
            checkBox.setChecked(true);
        }

    }



}
