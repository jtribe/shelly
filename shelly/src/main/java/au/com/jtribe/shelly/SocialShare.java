package au.com.jtribe.shelly;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/**
 * Represents a basic share of text, image, or video.
 */
public final class SocialShare {
    private Context context;
    private String text;
    private Uri uri;
    private String mimeType;

    SocialShare(Context context) {
        this.context = context;
        this.mimeType = Mime.PLAIN_TEXT;
    }

    /**
     * Set the text to share. This will overwrite any existing text that has been set.
     *
     * @param text Text to share.
     * @return Object this method was called on for method chaining.
     */
    public SocialShare text(String text) {
        if (text == null) throw new IllegalArgumentException("text == null");

        this.text = text;
        return this;
    }

    /**
     * Sets the image to share. This can only be called once, and you can only share a video or an image. Not both.
     *
     * @param imageUri Uri that represents an image
     * @return Object this method was called on for method chaining.
     */
    public SocialShare image(Uri imageUri) {
        if (imageUri == null) throw new IllegalArgumentException("imageUri == null");
        if (this.uri != null) throw new IllegalStateException("Not allowed multiple uri's");

        this.uri = imageUri;
        this.mimeType = Mime.ANY_IMAGE;
        return this;
    }

    /**
     * Sets the video to share. This can only be called once, and you can only share a video or an image. Not both.
     *
     * @param videoUri Uri that represents a video
     * @return Object this method was called on for method chaining.
     */
    public SocialShare video(Uri videoUri) {
        if (videoUri == null) throw new IllegalArgumentException("videoUri == null");
        if (this.uri != null) throw new IllegalStateException("Not allowed multiple uri's");

        this.uri = videoUri;
        this.mimeType = Mime.ANY_VIDEO;
        return this;
    }

    /**
     * Starts an activity to share with the configured details.
     */
    public void send() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(this.mimeType);
        shareIntent.putExtra(Intent.EXTRA_TEXT, this.text);

        if (this.uri != null) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, this.uri);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        } else {
            //noinspection deprecation
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        }

        this.context.startActivity(shareIntent);
    }
}
