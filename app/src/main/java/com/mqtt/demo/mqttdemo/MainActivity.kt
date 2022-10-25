package com.mqtt.demo.mqttdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createClient()
    }

    private fun createClient() {
        val client = Mqtt5Client.builder()
            .serverHost("192.168.150.45")
            .serverPort(1883)
            .buildAsync()

        client.connectWith()
            .simpleAuth()
            .username("")
            .password("".toByteArray())
            .applySimpleAuth()
            .send()
            .whenComplete { connAck, throwable ->
                if (throwable != null) {
                    Log.e("123 Failed connection:", "$throwable")
                } else {
                    Log.e("123 Success connection:", "$connAck")
                }
            }

        client.subscribeWith()
            .topicFilter("android")
            .callback { publish: Mqtt5Publish? ->
                Log.e("123 Publish:", "${publish?.payloadAsBytes?.decodeToString()}")
            }
            .send()
            .whenComplete { subAck: Mqtt5SubAck?, throwable: Throwable? ->
                if (throwable != null) {
                    Log.e("123 Subscribe failed:", "$throwable")
                } else {
                    Log.e("123 Subscribe success:", "$subAck")
                }
            }
    }
}