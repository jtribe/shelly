package au.com.jtribe.shelly;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 *
 * To share an image and text:
 *
 * <code>
 *  Shelly.share(context)
 *      .text("text with image")
 *      .image(myImageUri)
 *      .send();
 * </code>
 *
 * To share a video and text:
 *
 * <code>
 *  Shelly.share(context)
 *      .text("text with video")
 *      .video(myVideoUri)
 *      .send();
 * </code>
 */
public final class SocialShare {
    private Context context;
    private String subject;
    private String text;
    private Uri uri;
    private String mimeType;

    SocialShare(Context context) {
        this.context = context;
        this.mimeType = Mime.PLAIN_TEXT;
    }

    public SocialShare text(String text) {
        if (text == null) throw new IllegalArgumentException("text == null");

        this.text = text;
        return this;
    }

    public SocialShare subject(String subject) {
        if (subject == null) throw new IllegalArgumentException("subject == null");

        this.subject = subject;
        return this;
    }

    public SocialShare image(Uri imageUri) {
        if (imageUri == null) throw new IllegalArgumentException("imageUri == null");
        if (this.uri != null) throw new IllegalStateException("Not allowed multiple uri's");

        this.uri = imageUri;
        this.mimeType = Mime.ANY_IMAGE;
        return this;
    }

    public SocialShare video(Uri videoUri) {
        if (videoUri == null) throw new IllegalArgumentException("videoUri == null");
        if (this.uri != null) throw new IllegalStateException("Not allowed multiple uri's");

        this.uri = videoUri;
        this.mimeType = Mime.ANY_VIDEO;
        return this;
    }

    public void send() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(this.mimeType);
        shareIntent.putExtra(Intent.EXTRA_TEXT, this.text);

        if (this.uri != null) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, this.uri);
        }

        if (this.subject != null) {
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, this.subject);
        }

        this.context.startActivity(shareIntent);
    }
}
