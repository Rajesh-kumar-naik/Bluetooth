package com.example.iteradmin.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    val blA:BluetoothAdapter= BluetoothAdapter.getDefaultAdapter()
    val request_code:Int=100
    var listView:ListView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val active=findViewById<Button>(R.id.active1)
        listView=findViewById<ListView>(R.id.list_v1)

        val  data:Array<String> = arrayOf()
        active.setOnClickListener{
            if (blA==null){
                Toast.makeText(this,"Bluetooth not Supported",Toast.LENGTH_SHORT).show()
            }
            else{
                if(blA.isEnabled == false){
                    active.text="off"
                    val i=Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(i,request_code)
                }
                else{
                    blA.disable()
                    active.text="On"

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==request_code && resultCode == Activity.RESULT_OK){
            Toast.makeText(this,"Intent is working",Toast.LENGTH_SHORT).show()
            var data:Array<String> = arrayOf()
            val devices:Set<BluetoothDevice> = blA.bondedDevices
            for (device in devices) {
                val name:String = device.name
                data += name
            }
            val adp = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, data)
            listView?.adapter = adp
        }else{
            Toast.makeText(this,"Intent Not working",Toast.LENGTH_SHORT).show()
        }
    }
}
