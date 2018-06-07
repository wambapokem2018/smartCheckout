package makerspace.smartcheckout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ReturnActivity extends AppCompatActivity {
    Button button12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.return_page);

        button12 = (Button)findViewById(R.id.button12);

        button12.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ReturnActivity.this, DashboardActivity.class));
            }
        });

    }
}
