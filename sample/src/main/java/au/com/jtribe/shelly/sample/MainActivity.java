package au.com.jtribe.shelly.sample;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import au.com.jtribe.shelly.Shelly;

public class MainActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST_CODE = 123;
    private static final int VIDEO_REQUEST_CODE = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.plain_text_share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shelly.share(MainActivity.this)
                        .text("Testing Plain Text")
                        .send();
            }
        });

        findViewById(R.id.image_and_text_share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), IMAGE_REQUEST_CODE);

            }
        });

        findViewById(R.id.video_and_text_share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI), VIDEO_REQUEST_CODE);
            }
        });

        findViewById(R.id.email_to_one_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shelly.email(MainActivity.this)
                        .to("angus@jtribe.com")
                        .subject("Testing Subject Text")
                        .body("Testing Body Text")
                        .send();
            }
        });

        findViewById(R.id.email_to_many_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shelly.email(MainActivity.this)
                        .to("angus@jtribe.com", "yetanother@jtribe.com", "another@jtribe.com")
                        .to("anotheranother@jtribe.com")
                        .subject("Testing Subject Text")
                        .body("Testing Body Text")
                        .send();
            }
        });

        findViewById(R.id.email_with_cc_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shelly.email(MainActivity.this)
                        .to("angus@jtribe.com")
                        .cc("cc@jtribe.com", "anotherccaddress@jtribe.com")
                        .subject("Testing Subject Text")
                        .body("Testing Body Text")
                        .send();
            }
        });

        findViewById(R.id.email_with_bcc_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shelly.email(MainActivity.this)
                        .to("angus@jtribe.com")
                        .cc("cc@jtribe.com", "anotherccaddress@jtribe.com")
                        .bcc("bcc@jtribe.com", "anotherbcc@jtribe.com")
                        .subject("Testing Subject Text")
                        .body("Testing Body Text")
                        .send();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case VIDEO_REQUEST_CODE:
                    Shelly.share(MainActivity.this)
                            .text("Testing Video Text")
                            .subject("My Video Subject")
                            .video(data.getData())
                            .send();
                    break;
                case IMAGE_REQUEST_CODE:
                    Shelly.share(MainActivity.this)
                            .text("Testing Image Text")
                            .image(data.getData())
                            .send();
            }
        }
    }
}
