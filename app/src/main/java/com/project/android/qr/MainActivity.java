package com.project.android.qr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private ProgressBar progressBar;
    private ImageView resultView;
    Button btn_get_data, btn_scan, btn_contact;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);
        resultView = findViewById(R.id.result_view);
        btn_get_data = findViewById(R.id.btn_fetch_api);
        btn_scan = findViewById(R.id.btn_scan);
        btn_contact = findViewById(R.id.btn_contact);
        btn_get_data.setOnClickListener(this);
        btn_scan.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager = new DBManager(this);
        dbManager.open();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                letsScan();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_fetch_api) {
            progressBar.setVisibility(View.VISIBLE);
            btn_scan.setEnabled(false);
            btn_get_data.setEnabled(false);
            btn_contact.setEnabled(false);
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            okHttpHandler.execute("https://randomuser.me/api/");
        } else if (view.getId() == R.id.btn_scan) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                letsScan();
            }
        } else if (view.getId() == R.id.btn_contact) {
            startActivity(new Intent(getApplicationContext(), ContactActivity.class));
        }
    }

    private void letsScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("scan");
        integrator.setCameraId(1);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            String result = scanResult.getContents();
            if (result != null) {
                if (result.contains(getPackageName())) {
                    result = result.replace(getPackageName(), "");
                    saveToDb(result);
                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Sorry, but this code is not valid.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    Bitmap encodeAsBitmap(@NonNull String str) throws WriterException {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int size = 512;
        BitMatrix bitMatrix = qrCodeWriter.encode(str, BarcodeFormat.QR_CODE, size, size);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                bmp.setPixel(y, x, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    void saveToDb(String http_result) {
        try {
            JSONObject jsonObject = new JSONObject(http_result);
            JSONObject resultObj = jsonObject.getJSONArray("results").getJSONObject(0);
            JSONObject nameObj = resultObj.getJSONObject("name");
            String name = nameObj.getString("title") + " " + nameObj.getString("first") + " " + nameObj.getString("last");
            String email = resultObj.getString("email");
            String phone = resultObj.getString("phone");
            String cell = resultObj.getString("cell");
            String nat = resultObj.getString("nat");
            String dob = resultObj.getJSONObject("dob").getString("date");
            String uid = resultObj.getJSONObject("login").getString("uuid");
            String img_lg = resultObj.getJSONObject("picture").getString("large");
            String img_md = resultObj.getJSONObject("picture").getString("medium");
            String img_sm = resultObj.getJSONObject("picture").getString("thumbnail");
            JSONObject locationObj = resultObj.getJSONObject("location");
            String street = locationObj.getJSONObject("street").getString("number") + " " + locationObj.getJSONObject("street").getString("name");
            String city = locationObj.getString("city");
            String state = locationObj.getString("state");
            String country = locationObj.getString("country");
            String post_code = locationObj.getString("postcode");
            ContactModel model = new ContactModel();
            model.setName(name);
            model.setEmail(email);
            model.setPhone(phone);
            model.setCell(cell);
            model.setDob(dob);
            model.setUid(uid);
            model.setNat(nat);
            model.setStreet(street);
            model.setCity(city);
            model.setState(state);
            model.setCountry(country);
            model.setPostcode(post_code);
            model.setImg_lg(img_lg);
            model.setImg_md(img_md);
            model.setImg_sm(img_sm);
            dbManager.insert(model);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "An error occured.", Toast.LENGTH_SHORT).show();
        }
    }

    private class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String...params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String content = s + getPackageName();
            try {
                saveToDb(s);
                resultView.setImageBitmap(encodeAsBitmap(content));
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
            } catch (WriterException e) {
                Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
            btn_scan.setEnabled(true);
            btn_get_data.setEnabled(true);
            btn_contact.setEnabled(true);
        }

    }
}