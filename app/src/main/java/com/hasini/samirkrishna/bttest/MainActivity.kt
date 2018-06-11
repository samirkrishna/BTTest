package com.hasini.samirkrishna.bttest

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.ListView
import android.widget.Switch

class MainActivity : AppCompatActivity() {

    var switch:Switch?=null
    var lview:ListView?=null
    var bAdapter:BluetoothAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switch=findViewById(R.id.s1)
        lview=findViewById(R.id.lview)

        bAdapter= BluetoothAdapter.getDefaultAdapter()
        switch?.isChecked=bAdapter!!.isEnabled
        switch?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked)
                {
                    bAdapter?.enable()
                }
                else
                    bAdapter?.disable()
            }
        })

    }

    fun onclick(v: View)
    {
        var list = mutableListOf<String>()
        var adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,list)
        lview?.adapter=adapter

        bAdapter?.startDiscovery()//search for the devices with in the distance

        var filter=IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        registerReceiver(object : BroadcastReceiver() {//when any bt device is found it will call
            override fun onReceive(context: Context?, intent: Intent?) {

            var device:BluetoothDevice= intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
            list.add(device.name+"\n"+device.address)
            adapter.notifyDataSetChanged()//to reload the data
            }
        },filter)

    }



}
