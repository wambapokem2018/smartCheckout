package makerspace.smartcheckout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity implements AsyncResponse {

    Button button, button2;

    final String LOG = "DashboardActivity";

    private String previousMessage = "";
    private final String TAG = MainActivity.class.getSimpleName();
    private Bluetooth.ConnectedThread current_connectedThread = Bluetooth.mConnectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FullScreencall();


        button = (Button)findViewById(R.id.returnbtn);
        button2 = (Button)findViewById(R.id.borrowbtn);

        createBluetoothHandler();

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                HashMap postData = new HashMap();
                postData.put("btnLogin", "Login");
                postData.put("mobile", "android");
                postData.put("txtBoxname", "7D96");


                PostResponseAsyncTask checkBox =
                        new PostResponseAsyncTask(DashboardActivity.this, postData,
                                DashboardActivity.this);
                //loginTask.execute("http://10.0.2.2/client/login.php");
                //Kevin IP: 10.183.50.32
                checkBox.execute("http://10.180.34.51/client/login.php");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                HashMap postData = new HashMap();
                postData.put("btnLogin", "Login");
                postData.put("mobile", "android");
                postData.put("txtBoxname", "85A17D96");


                PostResponseAsyncTask checkBox =
                        new PostResponseAsyncTask(DashboardActivity.this, postData,
                                DashboardActivity.this);
                //loginTask.execute("http://10.0.2.2/client/login.php");
                //Kevin IP: 10.183.50.32
                checkBox.execute("http://10.180.34.51/client/login.php");
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
            completeMessage = "No ID found";

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
                //mReadBuffer.setText(readMessage);
                String arduinoCardInformation = filterMessage(readMessage);
                Log.e(TAG, "Hier wäre die Nachricht: " + arduinoCardInformation);
                //mReadBuffer.setText(arduinoCardInformation);
                //TODO: check arduinoCardInformation with all entries in DB
                String cardID = "D5E07B96";

                    if(arduinoCardInformation.compareTo(cardID) == 0 && !arduinoCardInformation.equals(previousMessage)){
                        //TODO: give Arduino Board access means turn on green light
                        current_connectedThread.write("9");
                        //status.setText("TRUE");
                        //mConnectedThread.write("9"); //TODO: change into 'access' or something else
                    } else if(arduinoCardInformation.compareTo(cardID) != 0 && !arduinoCardInformation.equals(previousMessage)){
                        //TODO: deny Arduino Board access means turn on red light
                        current_connectedThread.write("0");
                        //status.setText("FALSE");
                        //mConnectedThread.write("deny")
                    }

                previousMessage = arduinoCardInformation;

            }

        };
    }

    public void loginFunction(View view) {


    }

    @Override
    public void processFinish(String output) {

        if (output.equals("success")) {

            Toast.makeText(this, "Login Successfully",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(DashboardActivity.this, ReturnActivity.class));

        } else {
            Toast.makeText(this, "Login not Successfully",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(DashboardActivity.this, BorrowActivity.class));
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
