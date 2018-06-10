package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

public class Return_MissingActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.return_missing_page);


        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    public void goTo2Finish(View view) {
        Intent myIntent = new Intent(view.getContext(), SuccessActivity.class);
        startActivityForResult(myIntent, 0);
    }
}
