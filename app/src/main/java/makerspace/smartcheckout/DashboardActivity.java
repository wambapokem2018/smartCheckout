package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity implements AsyncResponse {

    Button button, button2;
    ImageView image;

    final String LOG = "DashboardActivity";

    private String previousMessage = "";
    private final String TAG = MainActivity.class.getSimpleName();
    private Bluetooth.ConnectedThread current_connectedThread = Bluetooth.mConnectedThread;
    private boolean sendMessageThread = false;
    private boolean readyToReceive = true;

    public static Box currentBox;

    private String NO_ID = "No ID found";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FullScreencall();


        button = (Button)findViewById(R.id.returnbtn);
        button2 = (Button)findViewById(R.id.borrowbtn);
        image = (ImageView)findViewById(R.id.makerspaceLogo);

        createBluetoothHandler();

        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                checkDatabase("85A17D96");
                startActivity(new Intent(DashboardActivity.this, BorrowActivity.class));
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

        JSONObject jsonObj = null;
        String id = "";
        String name = "";
        String encodedImage = "";
        try {
            jsonObj = new JSONObject(output);
            id = jsonObj.getString("BoxID");
            name = jsonObj.getString("BoxName");
            encodedImage = jsonObj.getString("BoxImage");
            //TODO: delete here the box
            currentBox = new Box(id, name, encodedImage);
            if(current_connectedThread != null && sendMessageThread) {
                Log.e(TAG, "Send to Arduino...");
                sendMessageThread = false;
                current_connectedThread.write("1");
                current_connectedThread.write("9");
                int test = 0;
                //TODO check if Box is currently borrowed or now
                if(test == 0) { //Box is borrowed
                    currentBox = new Box(id, name, encodedImage);
                    startActivity(new Intent(DashboardActivity.this, BorrowActivity.class));
                }
                else if (test == 1) //Box need to be returned
                    startActivity(new Intent(DashboardActivity.this, ReturnActivity.class));
                readyToReceive = true;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(id.equals("") && current_connectedThread != null && sendMessageThread) {
            sendMessageThread = false;
            Log.e(TAG, "Send to Arduino...");
            current_connectedThread.write("0");
            current_connectedThread.write("9");
            readyToReceive = true;
            //current_connectedThread.write("");
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
    
}
