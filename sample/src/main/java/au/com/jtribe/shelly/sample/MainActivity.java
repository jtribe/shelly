package au.com.jtribe.shelly.sample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import au.com.jtribe.shelly.Shelly;

public class MainActivity extends AppCompatActivity {
  private static final int IMAGE_REQUEST_CODE = 123;
  private static final int VIDEO_REQUEST_CODE = 321;
  private static final int PHONE_CALL_PERMISSION_CODE = 777;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.plain_text_share_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = Shelly.share().text("Testing Plain Text").asChooserIntent();
        startActivity(intent);
      }
    });

    findViewById(R.id.plain_text_and_url_share_button).setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = Shelly.share()
                .text("Testing Link Text")
                .url("http://www.jtribe.com.au")
                .asChooserIntent();
            startActivity(intent);
          }
        });

    findViewById(R.id.plain_text_and_many_urls_share_button).setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = Shelly.share()
                .text("Testing Link Text")
                .url("http://www.jtribe.com.au", "http://www.another.url.com")
                .asChooserIntent();
            startActivity(intent);
          }
        });

    findViewById(R.id.image_and_text_share_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivityForResult(
            new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
            IMAGE_REQUEST_CODE);
      }
    });

    findViewById(R.id.video_and_text_share_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivityForResult(
            new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI),
            VIDEO_REQUEST_CODE);
      }
    });

    findViewById(R.id.email_to_one_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = Shelly.email()
            .to("anemailaddress@jtribe.com.au")
            .subject("Testing Subject Text")
            .body("Testing Body Text")
            .asChooserIntent();
        startActivity(intent);
      }
    });

    findViewById(R.id.email_to_many_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = Shelly.email()
            .to("anemailaddress@jtribe.com.au", "yetanother@jtribe.com", "another@jtribe.com")
            .to("anotheranother@jtribe.com")
            .subject("Testing Subject Text")
            .body("Testing Body Text")
            .asChooserIntent();
        startActivity(intent);
      }
    });

    findViewById(R.id.email_with_cc_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = Shelly.email()
            .to("anemailaddress@jtribe.com.au")
            .cc("cc@jtribe.com", "anotherccaddress@jtribe.com")
            .subject("Testing Subject Text")
            .body("Testing Body Text")
            .asChooserIntent();
        startActivity(intent);
      }
    });

    findViewById(R.id.email_with_bcc_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = Shelly.email()
            .to("anemailadddress@jtribe.com.au")
            .cc("cc@jtribe.com", "anotherccaddress@jtribe.com")
            .bcc("bcc@jtribe.com", "anotherbcc@jtribe.com")
            .subject("Testing Subject Text")
            .body("Testing Body Text")
            .asChooserIntent();
        startActivity(intent);
      }
    });

    findViewById(R.id.phone_call_button).setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
          int hasPermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
          if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] { Manifest.permission.CALL_PHONE },
                PHONE_CALL_PERMISSION_CODE);
            return;
          }
        }
        shellyCall();
      }
    });

    findViewById(R.id.phone_dial_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = Shelly.dial().number("555-0101").asChooserIntent();
        startActivity(intent);
      }
    });
  }

  @RequiresPermission(Manifest.permission.CALL_PHONE)
  private void shellyCall() {
    Intent intent = Shelly.call().number("555-0101").asChooserIntent();
    startActivity(intent);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case (PHONE_CALL_PERMISSION_CODE):
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          shellyCall();
        } else {
          Toast.makeText(MainActivity.this, "PHONE_CALL permission denied", Toast.LENGTH_SHORT)
              .show();
        }
        break;
      default:
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK) {
      Intent intent = null;
      switch (requestCode) {
        case VIDEO_REQUEST_CODE:
          intent =
              Shelly.share().text("Testing Video Text").video(data.getData()).asChooserIntent();
          startActivity(intent);
          break;
        case IMAGE_REQUEST_CODE:
          intent =
              Shelly.share().text("Testing Image Text").image(data.getData()).asChooserIntent();
          startActivity(intent);
      }
    }
  }
}
