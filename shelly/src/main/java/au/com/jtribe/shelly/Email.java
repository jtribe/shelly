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
 * Represents an email to a person or group of people.
 */
public final class Email {

  private final List<String> toList;
  private final List<String> ccList;
  private final List<String> bccList;
  private String subject;
  private String body;

  Email() {
    // arbitrary assume people won't be sending toList more than 4 people in most cases.
    this.toList = new ArrayList<>(4);
    // In most cases these will probably be empty anyway
    this.ccList = new ArrayList<>();
    this.bccList = new ArrayList<>();
  }

  /**
   * Adds email addresses that the email should be sent to. Email addresses will be added into the
   * To field.
   *
   * @param to Urls that the email should be sent to.
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Email to(@NonNull String... to) {
    checkNotNull(to, "to == null");
    this.toList.addAll(Arrays.asList(to));
    return this;
  }

  /**
   * Adds email addresses that the email should be cc'd to. Email addresses will be added to the CC
   * field.
   *
   * @param cc Urls that the email should be cc'd to.
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Email cc(String... cc) {
    checkNotNull(cc, "cc == null");
    this.ccList.addAll(Arrays.asList(cc));
    return this;
  }

  /**
   * Adds email addresses that the email should be bcc'd to. Email addresses will be added to the
   * bcc field.
   *
   * @param bcc Urls that the email should be bcc'd to.
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Email bcc(@NonNull String... bcc) {
    checkNotNull(bcc, "bcc == null");
    this.bccList.addAll(Arrays.asList(bcc));
    return this;
  }

  /**
   * Adds a subject to the email.
   *
   * @param subject Subject of the email
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Email subject(@NonNull String subject) {
    checkNotNull(subject, "subject == null");
    this.subject = subject;
    return this;
  }

  /**
   * Adds a body to the email.
   *
   * @param body Body of the email.
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Email body(@NonNull String body) {
    checkNotNull(body, "body == null");
    this.body = body;
    return this;
  }

  /**
   * Creates and returns an intent that will allow the user to send an email using the
   * configuration
   * provided.
   *
   * @return The intent that can be used to open an email client
   */
  @NonNull
  @CheckResult
  public Intent asIntent() {
    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
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
   * Creates and returns an intent that will allow the user to send an email using the
   * configuration
   * provided.
   *
   * <b>Note: Since most users will have a preferred email client this may not be the appropriate
   * behaviour</b>
   *
   * @return The intent that can be used to open a chooser to select an email client
   */
  @NonNull
  @CheckResult
  public Intent asChooserIntent() {
    return asChooserIntent(null);
  }

  /**
   * Creates and returns an intent that will allow the user to send an email using the
   * configuration
   * provided.
   *
   * <b>Note: Since most users will have a preferred email client this may not be the appropriate
   * behaviour</b>
   *
   * @return The intent that can be used to open a chooser to select an email client
   */
  @NonNull
  @CheckResult
  public Intent asChooserIntent(@Nullable CharSequence prompt) {
    return Intent.createChooser(asIntent(), prompt);
  }
}
