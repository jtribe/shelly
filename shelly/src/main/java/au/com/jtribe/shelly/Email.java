package au.com.jtribe.shelly;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an email to a person or group of people.
 */
public final class Email {
    private final Context context;

    private final List<String> toList;
    private final List<String> ccList;
    private final List<String> bccList;
    private String subject;
    private String body;

    Email(Context context) {
        this.context = context;
        this.toList = new ArrayList<>(4);   // arbitrary assume people won't be sending toList more than 4 people in most cases.
        this.ccList = new ArrayList<>();    // In most cases these will probably be empty anyway
        this.bccList = new ArrayList<>();
    }

    /**
     * Adds email addresses that the email should be sent to. Email addresses will be added into the To field.
     *
     * @param to Urls that the email should be sent to.
     * @return Object this method was called on for method chaining.
     */
    public Email to(String... to) {
        if (to == null) throw new IllegalArgumentException("to == null");

        this.toList.addAll(Arrays.asList(to));
        return this;
    }

    /**
     * Adds email addresses that the email should be sent to. Email addresses will be added to the CC field.
     *
     * @param cc Urls that the email should be cced to.
     * @return Object this method was called on for method chaining.
     */
    public Email cc(String... cc) {
        if (cc == null) throw new IllegalArgumentException("cc == null");

        this.ccList.addAll(Arrays.asList(cc));
        return this;
    }

    /**
     * Adds email addresses that the email should be sent to. Email addresses will be added to the bcc field.
     *
     * @param bcc Urls that the email should be bcced to.
     * @return Object this method was called on for method chaining.
     */
    public Email bcc(String... bcc) {
        if (bcc == null) throw new IllegalArgumentException("bc == null");

        this.bccList.addAll(Arrays.asList(bcc));
        return this;
    }

    /**
     * Adds subject to the email.
     *
     * @param subject Subject of the email
     * @return Object this method was called on for method chaining.
     */
    public Email subject(String subject) {
        if (subject == null) throw new IllegalArgumentException("subject == null");

        this.subject = subject;
        return this;
    }

    /**
     * Adds a body to the email.
     *
     * @param body Body of the email.
     * @return Object this method was called on for method chaining.
     */
    public Email body(String body) {
        if (body == null) throw new IllegalArgumentException("body == null");

        this.body = body;
        return this;
    }

    /**
     * Creates an email intent with the configured details
     * @return Intent configured with this object's fields
     */
    public Intent intent()
      {
          Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
          emailIntent.setType(Mime.EMAIL);
          emailIntent.setData(Uri.parse("mailto:"));

          if (!this.toList.isEmpty()) {
              String[] toArray = new String[this.toList.size()];
              emailIntent.putExtra(Intent.EXTRA_EMAIL, this.toList.toArray(toArray));
          }

          if (!this.ccList.isEmpty()) {
              String[] ccArray = new String[this.ccList.size()];
              emailIntent.putExtra(Intent.EXTRA_CC, this.ccList.toArray(ccArray));
          }

          if (!this.bccList.isEmpty()) {
              String[] bccArray = new String[this.bccList.size()];
              emailIntent.putExtra(Intent.EXTRA_BCC, this.bccList.toArray(bccArray));
          }

          emailIntent.putExtra(Intent.EXTRA_SUBJECT, this.subject);
          emailIntent.putExtra(Intent.EXTRA_TEXT, this.body);

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
              emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
          } else {
              //noinspection deprecation
              emailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
          }

          return emailIntent;
      }

    /**
     * Gets the email intent with the configured details.
     * Starts a activity using intent chooser
     *
     * @return Boolean true if there is an activity to handle the Intent and it was started, false otherwise.
     */
    public boolean send() {
        Intent emailIntent = intent();
        if (emailIntent.resolveActivity(this.context.getPackageManager()) != null) {
            this.context.startActivity(emailIntent);
            return true;
        }
        return false;
    }
}
