package au.com.jtribe.shelly;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static au.com.jtribe.shelly.Preconditions.checkNotNull;

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
  @NonNull
  @CheckResult
  public SocialShare text(@NonNull String text) {
    checkNotNull(text, "text == null");
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
  @NonNull
  @CheckResult
  public SocialShare url(@NonNull String... url) {
    checkNotNull(url, "url == null");
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
  @NonNull
  @CheckResult
  public SocialShare image(@NonNull Uri imageUri) {
    checkNotNull(imageUri, "imageUri == null");

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
  @NonNull
  @CheckResult
  public SocialShare video(@NonNull Uri videoUri) {
    checkNotNull(videoUri, "videoUri == null");

    if (this.uri != null) {
      throw new IllegalStateException("Not allowed multiple uri's");
    }

    this.uri = videoUri;
    this.mimeType = Mime.ANY_VIDEO;
    return this;
  }

  /**
   * Creates and returns an Intent that will allow the user to share content.
   *
   * <b>Note: This may not be an appropriate user experience, since the user may not be able to
   * choose their preferred share app</b>
   */
  @NonNull
  @CheckResult
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

  /**
   * Creates and returns an Intent that will display a chooser to the user, allowing them to pick an
   * app to share with.
   */
  @NonNull
  @CheckResult
  public Intent asChooserIntent() {
    return asChooserIntent(null);
  }

  /**
   * Creates and returns an Intent that will display a chooser to the user with the specified
   * title, allowing them to pick an app to call with.
   *
   * @param prompt The chooser's title is set by prompt
   */
  @NonNull
  @CheckResult
  public Intent asChooserIntent(@Nullable CharSequence prompt) {
    return Intent.createChooser(asIntent(), prompt);
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
