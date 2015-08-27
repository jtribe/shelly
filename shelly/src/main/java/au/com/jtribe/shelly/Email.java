package au.com.jtribe.shelly;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * To send an email to one person
 *
 * <code>
 *  Shelly.email(context)
 *      .to("angus@jtribe.com")
 *      .subject("Hello")
 *      .text("Talk about how cool I am")
 *      .send();
 * </code>
 *
 * To send an email to multiple people
 * <code>
 *  Shelly.email(context)
 *      .to("angus@jtribe.com")
 *      .to("mark@jtribe.com")
 *      .to("another@email.com")
 *      .to("yetanother@email.com")
 *      .subject("Hello")
 *      .text("Talk about how cool I am")
 *      .send();
 * </code>
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

    public Email to(String... to) {
        if (to == null) throw new IllegalArgumentException("to == null");

        this.toList.addAll(Arrays.asList(to));
        return this;
    }

    public Email cc(String... cc) {
        if (cc == null) throw new IllegalArgumentException("cc == null");

        this.ccList.addAll(Arrays.asList(cc));
        return this;
    }

    public Email bcc(String... bc) {
        if (bc == null) throw new IllegalArgumentException("bc == null");

        this.bccList.addAll(Arrays.asList(bc));
        return this;
    }

    public Email subject(String subject) {
        if (subject == null) throw new IllegalArgumentException("subject == null");

        this.subject = subject;
        return this;
    }

    public Email body(String body) {
        if (body == null) throw new IllegalArgumentException("body == null");

        this.body = body;
        return this;
    }

    public void send() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType(Mime.EMAIL);

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

        this.context.startActivity(emailIntent);
    }
}
