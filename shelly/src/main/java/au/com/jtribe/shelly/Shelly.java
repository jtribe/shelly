package au.com.jtribe.shelly;

import android.content.Context;

/**
 * Shelly is a factory that creates instances of SocialShare's
 */
public final class Shelly {

    private Shelly() {
        // No instances
    }

    /**
     * Begin a social share.
     *
     * @param context Context to create the share with.
     * @return SocialShare that can be used to perform a social share.
     */
    public static SocialShare share(Context context) {
        if (context == null) throw new IllegalArgumentException("context == null");
        return new SocialShare(context);
    }

    /**
     * Begins composing an email template for the user.
     *
     * @param context Context to create the email Intent with.
     * @return Email that can be configured and displayed to the user.
     */
    public static Email email(Context context) {
        if (context == null) throw new IllegalArgumentException("context == null");
        return new Email(context);
    }
}
