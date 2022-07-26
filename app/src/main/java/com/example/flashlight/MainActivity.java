package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
    ImageView imagebtn;
    boolean state;
    TextView statusText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagebtn=findViewById(R.id.imageid);
        statusText=findViewById(R.id.statustext);
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Flashlightfun();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Camara Permission Required", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();

    }

    private void Flashlightfun() {
        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!state){
                    CameraManager cameraManager= (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String cameraId=cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId,true);
                        state=true;
                        imagebtn.setImageResource(R.drawable.on);
                        statusText.setText("ON");
                    }
                    catch (CameraAccessException e){
//
                    }
                }
                else {
                    CameraManager cameraManager= (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String cameraId=cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId,false);
                        state=false;
                        imagebtn.setImageResource(R.drawable.off);
                        statusText.setText("ON");
                    }
                    catch (CameraAccessException e){

                    }
                }
            }
        });
    }
}