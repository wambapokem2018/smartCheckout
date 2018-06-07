package makerspace.smartcheckout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(DashboardActivity.this, BorrowActivity.class));
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(DashboardActivity.this, ReturnActivity.class));
            }
        });

    }
}
