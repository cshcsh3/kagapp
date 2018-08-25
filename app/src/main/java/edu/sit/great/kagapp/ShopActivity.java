package edu.sit.great.kagapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class ShopActivity extends AppCompatActivity {


    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    TextView description_view;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        cameraView = findViewById(R.id.surface_view);
        textView = findViewById(R.id.text_view);
        description_view = findViewById(R.id.description_view);

        final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available");
        } else {

            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(ShopActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size() != 0)
                    {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i =0;i<items.size();++i)
                                {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }

                                Log.d("XIAOJEM", "Stringbuilder: " + stringBuilder.toString());
                                //textView.setText(stringBuilder.toString());

                                if (stringBuilder.toString().contains("Calbee")){
                                    textView.setText("Calbee Roasted Potato Chips");
                                    description_view.setVisibility(View.VISIBLE);
                                    description_view.setText("Salted roasted chips \n" +
                                            "Has salt and butter");
                                } else {
                                    description_view.setVisibility(View.GONE);
                                }

                                /*
                                //------ Call method to translate stringbuilder here
                                //https://translation.googleapis.com/language/translate/v2?key={YOUR_API_KEY}
                                String urlToParse = "https://translation.googleapis.com/language/translate/v2?key={YOUR_API_KEY}";

                                try{
                                    JSONObject jsonObject = getJSONObjectFromURL(urlToParse);
                                    //
                                    // Parse your json here
                                    //
                                    Log.d("XIAOJEM", jsonObject.toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                */
                            }
                        });
                    }
                }
            });
        }
    }

    //------Method to Translate
    public String translateOCR(String QueryLanguage, String ResultLanguage, String StringToTranslate){

        String ResultantString = "";

        //If same language return original string
        if(QueryLanguage.equals(ResultLanguage)){
            return StringToTranslate;

            //English to Japanese Translations
        } else if (QueryLanguage.equals("English") && ResultLanguage.equals("日本語")){


            //Japanese to English Translations
        } else if (QueryLanguage.equals("日本語") && ResultLanguage.equals("English")){


            //Japanese to Chinese Translations
        } else if (QueryLanguage.equals("日本語") && ResultLanguage.equals("中文")){

        }
        return ResultantString;
    }
}
