package gex.home.simplemqtt;


import android.content.Context;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttHandler {

    private MqttAndroidClient _mqttAndroidClient;
    private MqttConnectOptions _Mqttoptions;
    private Context _applicationContext;

    public MqttHandler(Context applicationContext,String server) throws MqttException {

        _applicationContext = applicationContext;
        _mqttAndroidClient = new MqttAndroidClient(applicationContext,"tcp://"+server,"Mobile_Client");
        initialize();

    }

    public  void initialize(){
        // Set up MQTT connection options
        _Mqttoptions = new MqttConnectOptions();
        _Mqttoptions.setCleanSession(true);


        try {
            //addToHistory("Connecting to " + serverUri);
            _mqttAndroidClient.connect(_Mqttoptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(_applicationContext,"Worked",Toast.LENGTH_SHORT).show();
                    System.out.println("Sucess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(_applicationContext,"Failed",Toast.LENGTH_SHORT).show();
                    System.out.println("Failed "+exception);
                }
            });
        } catch (MqttException ex){
            ex.printStackTrace();
        }

    }


    public void publishMessage(String topic, String payload) {
        try {
            // Create an MQTT message
            MqttMessage message = new MqttMessage();
            message.setPayload(payload.getBytes());

            // Publish the message to the specified topic
            _mqttAndroidClient.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return  _mqttAndroidClient.isConnected();
    }
}
