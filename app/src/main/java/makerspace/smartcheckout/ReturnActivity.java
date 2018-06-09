package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        button12 = (Button)findViewById(R.id.button12);

        button12.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ReturnActivity.this, DashboardActivity.class));
            }
        });

    }

    public void goToNext(View view) {
        Intent myIntent = new Intent(view.getContext(), Return_MissingActivity.class);
        startActivityForResult(myIntent, 0);
    }
}
