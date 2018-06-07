package makerspace.smartcheckout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class BorrowActivity extends AppCompatActivity{

    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_page);

        button4 = (Button)findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(BorrowActivity.this, DashboardActivity.class));
            }
        });


    }
}
