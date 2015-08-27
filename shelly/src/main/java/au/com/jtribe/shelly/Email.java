package au.com.jtribe.shelly;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
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
    private String subject;
    private String body;

    Email(Context context) {
        this.context = context;
        this.toList = new ArrayList<>(4);     // arbitrary assume people won't be sending toList more than 4 people in most cases.
    }

    public Email to(String to) {
        if (to == null) throw new IllegalArgumentException("toList == null");

        this.toList.add(to);
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
        String[] toArray = new String[this.toList.size()];
        emailIntent.putExtra(Intent.EXTRA_EMAIL, this.toList.toArray(toArray));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, this.subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, this.body);

        this.context.startActivity(emailIntent);
    }
}
