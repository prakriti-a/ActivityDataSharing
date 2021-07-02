package com.prakriti.activitydatasharing;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // receive data from MainActivity
        Bundle extras = getIntent().getExtras();

        ImageView imageData = findViewById(R.id.imageData);
        TextView nameData = findViewById(R.id.nameData);
        TextView emailData = findViewById(R.id.emailData);
        TextView passwordData = findViewById(R.id.passwordData);
        TextView ageData = findViewById(R.id.ageData);

        nameData.setText("NAME: " + extras.getString("NAME"));
        emailData.setText("EMAIL: " + extras.getString("EMAIL"));
        passwordData.setText("PASSWORD: " + extras.getString("PASSWORD"));
        ageData.setText("AGE:  " + extras.getString("AGE"));

        byte[] imageArray = extras.getByteArray("IMAGE");
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
        imageData.setImageBitmap(bitmap);

//        ImageView imageView = findViewById(R.id.imageView);
//        imageView.setImageResource(R.drawable.person);

    }
}