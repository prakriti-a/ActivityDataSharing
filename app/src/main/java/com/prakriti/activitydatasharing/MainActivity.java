package com.prakriti.activitydatasharing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
// passing data from activity 1 to activity 2
// CHECK CRASH ERRORS ???

    private final static int IMAGE_REQ_CODE = 4;
    private String name, email, password, age = "";
    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtName = findViewById(R.id.edtName);
        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtPassword = findViewById(R.id.edtPassword);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
//        RadioButton rbMinor = findViewById(R.id.rbMinor);
//        RadioButton rbMajor = findViewById(R.id.rbMajor);
        radioGroup.setOnCheckedChangeListener(this);

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(this);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(v -> {
            if(edtName.getText().toString().equals("") || edtEmail.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "Fields Cannot Be Empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(age.equals("")) {
                Toast.makeText(MainActivity.this, "Please specify your age!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(bitmap == null) {
                Toast.makeText(MainActivity.this, "Please select an image!", Toast.LENGTH_SHORT).show();
                // make edits to allow blank image
                return;
            }
            try {
                name = edtName.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();

//                    edtName.setText("");
//                    edtEmail.setText("");
//                    edtPassword.setText("");
//                    rbMajor.setChecked(false);
//                    rbMinor.setChecked(false);

                sendDataToSecondActivity();
            }
            catch (Exception e) {
                Log.e("DATA_APP", e.getMessage());
                e.printStackTrace();
            }
        });

//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.person);
//        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imageView) {
            // clicking on image view to change image, access device storage
            Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT); // open photos on device
            imageIntent.setType("image/*");
            startActivityForResult(imageIntent, IMAGE_REQ_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // result for image selection
        if(requestCode == IMAGE_REQ_CODE && resultCode == RESULT_OK && data != null) {
            Uri chosenImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), chosenImage);
                imageView.setImageBitmap(bitmap);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendDataToSecondActivity() {
        // on submitting info
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("NAME", name);
        intent.putExtra("EMAIL", email);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("AGE", age);

        // convert image to byte[] which is accepted by the intent object
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        intent.putExtra("IMAGE", bytes);

        startActivity(intent);
        finish();
//        imageView.setImageResource(R.drawable.person);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbMinor:
                age = "Minor";
                break;
            case R.id.rbMajor:
                age = "18+";
                break;
        }
    }
}