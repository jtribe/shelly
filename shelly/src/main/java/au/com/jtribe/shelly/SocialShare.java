package au.com.jtribe.shelly;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a basic share of text, image, or video.
 */
public final class SocialShare {
  private String text;
  private Uri uri;
  private String mimeType;
  private final List<String> urlList;

  SocialShare() {
    this.mimeType = Mime.PLAIN_TEXT;
    this.urlList = new ArrayList<>();
  }

  /**
   * Set the text to share. This will overwrite any existing text that has been set.
   *
   * @param text Text to share.
   * @return Object this method was called on for method chaining.
   */
  public SocialShare text(String text) {
    if (text == null) {
      throw new IllegalArgumentException("text == null");
    }

    this.text = text;
    return this;
  }

  /**
   * Adds one or more urls to share. These will be appended to the end of the share text. e.g.
   * <pre>
   * Shelly.share(context)
   *      .text("text to share")
   *      .url("http://www.jtribe.com.au")
   *      .send();
   * </pre>
   * Will share the text: "text to share http://www.jtribe.com.au"
   * You can share as many urls as you want and they will be appended in the same way.
   *
   * @param url Url to share.
   * @return Object this method was called on for method chaining.
   */
  public SocialShare url(String... url) {
    if (url == null) {
      throw new IllegalArgumentException("url == null");
    }

    this.urlList.addAll(Arrays.asList(url));
    return this;
  }

  /**
   * Sets the image to share. This can only be called once, and you can only share a video or an
   * image. Not both.
   *
   * @param imageUri Uri that represents an image
   * @return Object this method was called on for method chaining.
   */
  public SocialShare image(Uri imageUri) {
    if (imageUri == null) {
      throw new IllegalArgumentException("imageUri == null");
    }
    if (this.uri != null) {
      throw new IllegalStateException("Not allowed multiple uri's");
    }

    this.uri = imageUri;
    this.mimeType = Mime.ANY_IMAGE;
    return this;
  }

  /**
   * Sets the video to share. This can only be called once, and you can only share a video or an
   * image. Not both.
   *
   * @param videoUri Uri that represents a video
   * @return Object this method was called on for method chaining.
   */
  public SocialShare video(Uri videoUri) {
    if (videoUri == null) {
      throw new IllegalArgumentException("videoUri == null");
    }
    if (this.uri != null) {
      throw new IllegalStateException("Not allowed multiple uri's");
    }

    this.uri = videoUri;
    this.mimeType = Mime.ANY_VIDEO;
    return this;
  }

  public Intent asIntent() {
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType(this.mimeType);

    shareIntent.putExtra(Intent.EXTRA_TEXT, buildText());

    if (this.uri != null) {
      shareIntent.putExtra(Intent.EXTRA_STREAM, this.uri);
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    } else {
      //noinspection deprecation
      shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    }
    return shareIntent;
  }

  public Intent asChooserIntent() {
    return Intent.createChooser(asIntent(), null);
  }

  public Intent asChooserIntent(CharSequence prompt) {
    return Intent.createChooser(asIntent(), prompt);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
  public Intent asChooserIntent(CharSequence prompt, IntentSender callback) {
    return Intent.createChooser(asIntent(), prompt, callback);
  }

  private String buildText() {
    if (this.urlList.isEmpty()) {
      return this.text;
    }

    StringBuilder text = new StringBuilder(this.text);
    for (int i = 0, count = this.urlList.size(); i < count; i++) {
      text.append(" ").append(this.urlList.get(i));
    }
    return text.toString();
  }
}
