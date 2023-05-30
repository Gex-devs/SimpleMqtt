package gex.home.simplemqtt;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.android.service.*;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MqttHandler _mqttHandler;
    private EditText Topic,MqttAdr,CustomCmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Assign Text fields
        Topic = findViewById(R.id.mqttTopicField);
        MqttAdr = findViewById(R.id.mqttAddrField);
        CustomCmd = findViewById(R.id.customCmdField);

        // set All Buttons Handler
        findViewById(R.id.onBtn).setOnClickListener(this::onClick);
        findViewById(R.id.offBtn).setOnClickListener(this::onClick);
        findViewById(R.id.connectBtn).setOnClickListener(this::onClick);
        findViewById(R.id.customCmd).setOnClickListener(this::onClick);


    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v)  {
        switch (v.getId()) {
            case R.id.connectBtn:
                String Address = MqttAdr.getText().toString();
                try {
                    _mqttHandler = new MqttHandler(getApplicationContext(),Address);
                }catch (MqttException ex){
                    Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.onBtn:
                _mqttHandler.publishMessage(Topic.getText().toString(),"on");
                break;
            case R.id.offBtn:
                _mqttHandler.publishMessage(Topic.getText().toString(),"off");
                break;
            case R.id.customCmd:
                _mqttHandler.publishMessage(Topic.getText().toString(),CustomCmd.getText().toString());
                Toast.makeText(getApplicationContext(),"Sent Custom Command",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
