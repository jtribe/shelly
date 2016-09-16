package au.com.jtribe.shelly;

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

  private final List<String> toList;
  private final List<String> ccList;
  private final List<String> bccList;
  private String subject;
  private String body;

  Email() {
    this.toList = new ArrayList<>(
        4);   // arbitrary assume people won't be sending toList more than 4 people in most cases.
    this.ccList = new ArrayList<>();    // In most cases these will probably be empty anyway
    this.bccList = new ArrayList<>();
  }

  /**
   * Adds email addresses that the email should be sent to. Email addresses will be added into the
   * To field.
   *
   * @param to Urls that the email should be sent to.
   * @return Object this method was called on for method chaining.
   */
  public Email to(String... to) {
    if (to == null) {
      throw new IllegalArgumentException("to == null");
    }

    this.toList.addAll(Arrays.asList(to));
    return this;
  }

  /**
   * Adds email addresses that the email should be sent to. Email addresses will be added to the CC
   * field.
   *
   * @param cc Urls that the email should be cced to.
   * @return Object this method was called on for method chaining.
   */
  public Email cc(String... cc) {
    if (cc == null) {
      throw new IllegalArgumentException("cc == null");
    }

    this.ccList.addAll(Arrays.asList(cc));
    return this;
  }

  /**
   * Adds email addresses that the email should be sent to. Email addresses will be added to the
   * bcc
   * field.
   *
   * @param bcc Urls that the email should be bcced to.
   * @return Object this method was called on for method chaining.
   */
  public Email bcc(String... bcc) {
    if (bcc == null) {
      throw new IllegalArgumentException("bc == null");
    }

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
    if (subject == null) {
      throw new IllegalArgumentException("subject == null");
    }

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
    if (body == null) {
      throw new IllegalArgumentException("body == null");
    }

    this.body = body;
    return this;
  }

  /**
   * Creates an ACTION_SENDTO intent adding this object's fields into the intent
   *
   * @return The intent storing this objects data, can be used to open an email client
   */
  public Intent asIntent() {
    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
    //emailIntent.setType(Mime.EMAIL);  //Doesn't seem to be needed with SENDTO and setData mailto:
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
   * Creates an ACTION_SENDTO chooser intent adding this object's fields into the intent
   * The chooser has no title
   *
   * @return The intent storing this object's data, can be used to open apps capable of sending
   * emails
   */
  public Intent asChooserIntent() {
    return Intent.createChooser(asIntent(), null);
  }

  /**
   * Creates an ACTION_SENDTO chooser intent with this object's fields as data in the intent
   *
   * @param prompt The chooser's title is set by prompt
   */
  public Intent asChooserIntent(CharSequence prompt) {
    return Intent.createChooser(asIntent(), prompt);
  }
}
