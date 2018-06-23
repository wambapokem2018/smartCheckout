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

    private Handler mHandler;
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status
    // private Bluetooth.ConnectedThread this_mConnectedThread = Bluetooth.mConnectedThread; // bluetooth background worker thread to send and receive data



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FullScreencall();

        mHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == MESSAGE_READ){
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //mReadBuffer.setText(readMessage);
                    String arduinoCardInformation = filterMessage(readMessage);
                    //TODO: check arduinoCardInformation with all entries in DB
                    String cardID = "D5E07B96";
                    if(arduinoCardInformation.compareTo(cardID) == 0){
                        //TODO: give Arduino Board access means turn on green light
                        //status.setText("TRUE");
                        System.out.println("Something found == TRUE");
                      //  this_mConnectedThread.write("9"); //TODO: change into 'access' or something else
                    } else{
                        //TODO: deny Arduino Board access means turn on red light
                        //status.setText("FALSE");
                        System.out.println("Nothing found == FALSE");
                   //     this_mConnectedThread.write("deny");
                    }

                }

                if(msg.what == CONNECTING_STATUS){
                    if(!(msg.arg1 == 1))
                        System.out.println("There is no connection cause Connection Fail!");
                }
            }
        };

        button = (Button)findViewById(R.id.returnbtn);
        button2 = (Button)findViewById(R.id.borrowbtn);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                HashMap postData = new HashMap();
                postData.put("btnLogin", "Login");
                postData.put("mobile", "android");
                postData.put("txtBoxname", "0001");


                PostResponseAsyncTask checkBox =
                        new PostResponseAsyncTask(DashboardActivity.this, postData,
                                DashboardActivity.this);
                //loginTask.execute("http://10.0.2.2/client/login.php");
                //Kevin IP: 10.183.50.32
                checkBox.execute("http://10.41.17.102/client/login.php");
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                HashMap postData = new HashMap();
                postData.put("btnLogin", "Login");
                postData.put("mobile", "android");
                postData.put("txtBoxname", "0011");


                PostResponseAsyncTask checkBox =
                        new PostResponseAsyncTask(DashboardActivity.this, postData,
                                DashboardActivity.this);
                //loginTask.execute("http://10.0.2.2/client/login.php");
                //Kevin IP: 10.183.50.32
                checkBox.execute("http://10.41.17.102/client/login.php");
            }
        });
    }

    public String filterMessage(String message){

        String completeMessage = message;

        if(completeMessage.charAt(0) == 'C' && completeMessage.charAt(1) == 'a' && completeMessage.charAt(2) == 'r' && completeMessage.charAt(3) == 'd')
            completeMessage = "" + completeMessage.charAt(7) + completeMessage.charAt(8) + //first HEX ID Part
                    completeMessage.charAt(10) + completeMessage.charAt(11) + //second HEX ID Part
                    completeMessage.charAt(13) + completeMessage.charAt(14) + //third HEX ID Part
                    completeMessage.charAt(16) + completeMessage.charAt(17); //fourth HEX ID Part
        else
            completeMessage = "No ID found";

        return completeMessage;
    }

    public void loginFunction(View view) {


    }

    @Override

    public void processFinish(String output) {
        Log.d(LOG, output);
        if(!output.equals(null)){
            Toast.makeText(this, "Login Successfully",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(DashboardActivity.this, ReturnActivity.class));
        }
        else{
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
