package makerspace.smartcheckout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity implements AsyncResponse {

    Button button, button2;
    ImageView image;
    TextView infoText;


    final String LOG = "DashboardActivity";

    private String previousMessage = "";
    private final String TAG = MainActivity.class.getSimpleName();
    private Bluetooth.ConnectedThread current_connectedThread = Bluetooth.mConnectedThread;
    private boolean sendMessageThread = false;
    private boolean readyToReceive = true;
    Bitmap decodedByte = null;
    String decodedImage = "";
    FileOutputStream stream;

    public static Box currentBox = new Box("", "", null);

    private String NO_ID = "No ID found";
    public static int RENT_STATE = 0; //0 = borrow process, 1 = rent process
    private boolean check_RentState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FullScreencall();


        button = (Button)findViewById(R.id.returnbtn);
        button2 = (Button)findViewById(R.id.borrowbtn);
        image = (ImageView)findViewById(R.id.makerspaceLogo);
        infoText = (TextView) findViewById(R.id.signText);
        String infoTest = (String) infoText.getText();
        infoText.setText(infoTest + " " +  MainActivity.currentUser.getName() + " " + MainActivity.currentUser.getSurname());

        createBluetoothHandler();

        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                checkDatabase("76C5C33B");
            }
        });
    }

    public String filterMessage(String message) {

        String completeMessage = message;

        if (completeMessage.charAt(0) == 'C' && completeMessage.charAt(1) == 'a' && completeMessage.charAt(2) == 'r' && completeMessage.charAt(3) == 'd')
            completeMessage = "" + completeMessage.charAt(7) + completeMessage.charAt(8) + //first HEX ID Part
                    completeMessage.charAt(10) + completeMessage.charAt(11) + //second HEX ID Part
                    completeMessage.charAt(13) + completeMessage.charAt(14) + //third HEX ID Part
                    completeMessage.charAt(16) + completeMessage.charAt(17); //fourth HEX ID Part
        else
            completeMessage = NO_ID;

        return completeMessage;
    }

    private void createBluetoothHandler(){
        Bluetooth.mHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                String readMessage = null;
                try {
                    readMessage = new String((byte[]) msg.obj, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String arduinoCardInformation = NO_ID;

                if(!sendMessageThread && readyToReceive){
                    arduinoCardInformation = filterMessage(readMessage);
                    if(!arduinoCardInformation.equals(previousMessage) && !arduinoCardInformation.equals(NO_ID)){
                    Log.e(TAG, "New Tag ID found!");
                    sendMessageThread = true;
                    readyToReceive = false;
                    checkDatabase(arduinoCardInformation);
                    }
                    previousMessage = arduinoCardInformation;
                }
            }

        };
    }

    private void checkDatabase(String currentID){

        HashMap postData = new HashMap();
        postData.put("btnLogin", "Login");
        postData.put("mobile", "android");
        postData.put("txtBoxname", currentID);


        PostResponseAsyncTask checkBox =
                new PostResponseAsyncTask(DashboardActivity.this, postData,
                        DashboardActivity.this);
        //TODO: change IP Adresse or Database Connection
        checkBox.execute("http://192.168.178.32/client/scanBox.php");
    }

    @Override
    public void processFinish(String output) {

        if(check_RentState){
            check_RentState = false;
            JSONObject jsonObj = null;
            String returnDate = "";
            String borrowDate = "";
            try {
                    jsonObj = new JSONObject(output);
                    returnDate = jsonObj.getString("ReturnTime");
                    Log.e(TAG, "User want to return the Box.");
                    RENT_STATE = 1;
                    startActivity(new Intent(DashboardActivity.this, ReturnActivity.class));
            } catch (JSONException e) {
            Log.e(TAG, "User want to borrow the Box.");
            RENT_STATE = 0;
            startActivity(new Intent(DashboardActivity.this, BorrowActivity.class));
                e.printStackTrace();
            }

        } else {
            JSONObject jsonObj = null;
            String id = "";
            String name = "";
            String encodedImage = "";
            try {
                jsonObj = new JSONObject(output);
                id = jsonObj.getString("BoxID");
                name = jsonObj.getString("BoxName");
                encodedImage = jsonObj.getString("BoxImage");

                currentBox.setBoxID(id);
                currentBox.setBoxName(name);
                //decodedImage = encodedImage;
                //currentBox.setBoxImage(encodedImage);


                new ImageAsyncTask().execute(encodedImage);

                if (current_connectedThread != null && sendMessageThread) {
                    Log.e(TAG, "Send to Arduino...");
                    sendMessageThread = false;
                    current_connectedThread.write("1");
                    current_connectedThread.write("9");
                    readyToReceive = true;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (id.equals("") && current_connectedThread != null && sendMessageThread) {
                sendMessageThread = false;
                Log.e(TAG, "Send to Arduino...");
                current_connectedThread.write("0");
                current_connectedThread.write("9");
                readyToReceive = true;
                //current_connectedThread.write("");
            }
        }
    }

    /* public void processFinish(String s) {

        String fullText = "";
        String successMessage = "";
        for(int i=0; i < 7; i++)
            successMessage += s.charAt(i);

        for(int i=7; i < s.length(); i++)
            fullText += s.charAt(i);


        if (successMessage.equals("success")) {
            Toast.makeText(this, fullText,
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Login not Successfully",
                    Toast.LENGTH_LONG).show();
        }
    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //navigate to return page
    public void goToDashboard(View view) {
        Intent myIntent = new Intent(view.getContext(), ReturnActivity.class);
        startActivityForResult(myIntent, 0);
    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void unwindBack(View view) {
        logOut(view);
    }

    public void logOut(View view) {
        Intent myIntent = new Intent(view.getContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private class ImageAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... strings) {
            Log.e(TAG, "start loading image..." + strings[0]);
            byte[] decodedString = Base64.decode(strings[0], Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return null;
        }

        @Override

        protected void onPostExecute(Double result) {
            currentBox.setBoxImage(decodedByte);
            checkRentingProcess();
        }
    }

    private void checkRentingProcess(){
        check_RentState = true;
        HashMap postData = new HashMap();
        postData.put("userID", String.valueOf(MainActivity.currentUser.getID()));
        postData.put("boxID", currentBox.getBoxID());

        PostResponseAsyncTask checkBox =
                new PostResponseAsyncTask(DashboardActivity.this, postData,
                        DashboardActivity.this);
        //TODO: change IP Adresse or Database Connection
        checkBox.execute("http://192.168.178.32/client/rentProcess.php");
    }
}
