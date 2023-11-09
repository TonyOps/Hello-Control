package com.example.admin

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.app.Activity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.app.admin.DeviceAdminReceiver

class MainActivity: FlutterActivity() {
    private val CHANNEL = "flutter.native/adminOn"
    private val RESULT_ENABLE = 1
    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var componentName: ComponentName

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        componentName = ComponentName(this, MyDeviceAdminReceiver::class.java)
        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            if (call.method == "checkAdminPermission") {
                val hasPermission = devicePolicyManager.isAdminActive(componentName)
                result.success(hasPermission)
            } else if (call.method == "requestAdminPermission") {
                if (devicePolicyManager.isAdminActive(componentName)) {
                    // Implemente a lógica necessária
                } else {
                    val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Você deve habilitar o aplicativo!")
                    
                    startActivityForResult(intent, RESULT_ENABLE)
                }
                result.success(null)
            } else if (call.method == "adminOn") {
                if (devicePolicyManager.isAdminActive(componentName)) {
                   // Implemente a lógica necessária
                }
                result.success(null)
            } else {
                result.notImplemented()
            }
        }
        super.configureFlutterEngine(flutterEngine)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_ENABLE) {
            if (resultCode == Activity.RESULT_OK) {
                // Implemente a lógica necessária
            }
        }
    }
}

class MyDeviceAdminReceiver : DeviceAdminReceiver() {
    // Implemente a lógica necessária no método onReceive
}
