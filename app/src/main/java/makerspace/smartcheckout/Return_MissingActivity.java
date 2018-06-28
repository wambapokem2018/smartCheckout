package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class Return_MissingActivity extends AppCompatActivity  {

    Button button, button2, button3;
    ImageView image1, image2;
    boolean set = false;
    boolean set2 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.return_missing_page);


        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        button2 = (Button)findViewById(R.id.back);
        button = (Button)findViewById(R.id.logout);
        button3 = (Button) findViewById(R.id.confirmbtn);
        button3.setEnabled(false);

        image1 = (ImageView) findViewById(R.id.makerspaceLogo3);
        image2 = (ImageView) findViewById(R.id.makerspaceLogo4);


        image2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                button3.setEnabled(set);
                set = !set;
            }
        });
        image1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                button3.setEnabled(set2);
                set2 = !set2;
            }
        });

        if(button3.isEnabled()){
            button3.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    startActivity(new Intent(Return_MissingActivity.this, SuccessActivity.class));
                }
            });
        }
        //navigate to main activity page
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Return_MissingActivity.this, MainActivity.class));
            }
        });

        //navigate to return page
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Return_MissingActivity.this, ReturnActivity.class));
            }
        });
    }

}
