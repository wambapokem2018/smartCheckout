package makerspace.smartcheckout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class BorrowActivity extends AppCompatActivity implements AsyncResponse {

    private final String TAG = MainActivity.class.getSimpleName();
    Button button, button2, button3, button4;
    ImageView boxImage;
    TextView boxName;
    final String LOG = "BorrowActivity";
    private ArrayList<Box> boxes;
    private Box box = new Box(DashboardActivity.currentBox.getBoxID(), DashboardActivity.currentBox.getBoxName(), DashboardActivity.currentBox.getBoxImage());
    String test;
    Bitmap bm = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_page);


        //TODO: optimize layout cause only Landscape Orientation should be allowed
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        button = (Button) findViewById(R.id.goToFinish);
        button2 = (Button) findViewById(R.id.showObject);
        button3 = (Button) findViewById(R.id.back);
        button4 = (Button) findViewById(R.id.logout);
        boxImage = (ImageView) findViewById(R.id.boxImage);
        boxName = (TextView) findViewById(R.id.boxName);

        boxImage.setImageBitmap(box.getBoxImage());
        boxName.setText(box.getBoxName());

        //getBoxImageDatabase(box.getBoxID());
        //Intent intent = getIntent();
        //Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("key");
        //Log.e(TAG, "hier wären etwas: " + bitmap.toString());
        //test = getIntent().getExtras("image");
        /*
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                test= null;
            } else {
                test= extras.getString("image");
                Log.e(TAG, "Image found!");
            }
        } else {
            test= (String) savedInstanceState.getSerializable("image");
        }*/



        //navigate to success page
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BorrowActivity.this, SuccessActivity.class));
            }
        });

        //navigate to borrow missing page
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BorrowActivity.this, Borrow_MissingActivity.class));
            }
        });

        //navigate to dashboard page
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BorrowActivity.this, DashboardActivity.class));
            }
        });

        //navigate to main activity page
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(BorrowActivity.this, MainActivity.class));
            }
        });


    }

    private void getBoxImageDatabase(String currentID){

        HashMap postData = new HashMap();
        postData.put("btnLogin", "Login");
        postData.put("mobile", "android");
        postData.put("txtBoxname", currentID);


        PostResponseAsyncTask checkBox =
                new PostResponseAsyncTask(BorrowActivity.this, postData,
                        BorrowActivity.this);
        //TODO: change IP Adresse or Database Connection
        checkBox.execute("http://192.168.178.32/client/imageBox.php");
    }


    @Override
    public void processFinish(String s) {
        JSONObject jsonObj = null;
        String encodedImage = "";
        try {
            jsonObj = new JSONObject(s);
            encodedImage = jsonObj.getString("BoxImage");
            test = encodedImage;
            new ImageAsyncTask().execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class ImageAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... strings) {
            Log.e(TAG, "start loading image..." + test);
            byte[] decodedString = Base64.decode(test, Base64.DEFAULT);
            bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return null;
        }

        @Override

        protected void onPostExecute(Double result) {
            box.setBoxImage(bm);
            boxImage.setImageResource(R.drawable.back_gray);
            //boxImage.setImageResource(R.id.backButton);
        }
    }
}


