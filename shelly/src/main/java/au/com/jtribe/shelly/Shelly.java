package au.com.jtribe.shelly;

import android.content.Context;

/**
 * Shelly is a factory that creates instances of SocialShare's.
 */
public final class Shelly {

    private Shelly() {

    }

    public static SocialShare share(Context context) {
        if (context == null) throw new IllegalArgumentException("context == null");
        return new SocialShare(context);
    }

    public static Email email(Context context) {
        if (context == null) throw new IllegalArgumentException("context == null");
        return new Email(context);
    }
}
