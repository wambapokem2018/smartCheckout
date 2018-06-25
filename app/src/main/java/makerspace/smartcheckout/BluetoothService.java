package makerspace.smartcheckout;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

public class BluetoothService extends Service {

    private final String TAG = MainActivity.class.getSimpleName();

    private BluetoothAdapter mBTAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    private ArrayAdapter<String> mBTArrayAdapter;

    public static Handler mHandler; // Our main handler that will receive callback notifications
    public static ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier 00001101-0000-1000-8000-00805F9B34FB

    // #defines for identifying shared types between calling functions
    public final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    public final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    public final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status

    private BluetoothAdapter mBluetoothAdapter;
    public static final String B_DEVICE = "DSD TECH HC-05";

    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;


    /*
    public void toast(String mess) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }*/

    public class LocalBinder extends Binder {
        BluetoothService getService() {
            // Return this instance of LocalService so clients can call public methods
            return BluetoothService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String deviceg = intent.getStringExtra("00:14:03:06:62:E7");

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }

        connectToDevice("00:14:03:06:62:E7");

        return START_STICKY;
    }

    private synchronized void connectToDevice(String macAddress) {

        //generateHandler();

        //final String currentMacAddress = macAddress;
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);

        // Spawn a new thread to avoid blocking the GUI one
        new Thread()
        {
            public void run() {
                boolean fail = false;

                //BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(currentMacAddress);

                try {
                    mBTSocket = createBluetoothSocket(device);
                    Log.e(TAG, "Sockett created!");
                } catch (IOException e) {
                    fail = true;
                    //Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Sockett creation failed!");
                }
                // Establish the Bluetooth socket connection.
                try {
                    mBTSocket.connect();
                    Log.e(TAG, "Sockett connected!");
                } catch (IOException e) {
                    Log.e(TAG, "Sockett connection fail!");
                    try {
                        fail = true;
                        mBTSocket.close();
                        mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                .sendToTarget();
                    } catch (IOException e2) {
                        //insert code to deal with this
                        //Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                }
                if(fail == false) {
                    mConnectedThread = new ConnectedThread(mBTSocket);
                    //toast(mConnectedThread.getName() + " Thread been created!");
                    mConnectedThread.start();

                    mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, device.getName())
                            .sendToTarget();
                }
            }
        }.start();
    }

    public void sendData(String message) {
        if (mConnectedThread != null) {
            mConnectedThread.write(message);
            //toast("sent data");
        } else {
            //Toast.makeText(BluetoothService.this, "Failed to send data", Toast.LENGTH_SHORT).show();
        }
    }

    public static void sendInformation(String messsage) {
        if(mConnectedThread != null) { //First check to make sure thread created
            //Log.d(TAG, "Message send to: " + mConnectedThread.getName() + " " + mConnectedThread.getId());
            mConnectedThread.write(messsage);
        } else{
            //Log.d(TAG, "Nothing to send!");
        }
    }

    @Override
    public boolean stopService(Intent name) {
        /*

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mBluetoothAdapter.cancelDiscovery(); */
        return super.stopService(name);

    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        startService(new Intent(this, BluetoothService.class));
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BTMODULEUUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            stopService(new Intent(this, BluetoothService.class));
        }
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }


    public class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.available();
                    if(bytes != 0) {
                        buffer = new byte[1024];
                        SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
                        bytes = mmInStream.available(); // how many bytes are ready to be read?
                        bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget(); // Send the obtained bytes to the UI activity
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */

        public void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }

        /* Call this from the main activity to shutdown the connection */

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}