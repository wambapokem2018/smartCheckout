package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class BorrowActivity extends AppCompatActivity{

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_page);


        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(BorrowActivity.this, SuccessActivity.class));
            }
        });


    }
    public void goToFinish(View view) {
        Intent myIntent = new Intent(view.getContext(), SuccessActivity.class);
        startActivityForResult(myIntent, 0);
    }

    public void goToMissing(View view) {
        Intent myIntent = new Intent(view.getContext(), Borrow_MissingActivity.class);
        startActivityForResult(myIntent, 0);
    }



}
