package au.com.jtribe.shelly;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by angus on 27/08/2015.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SocialShareTest {

    @Test
    public void text() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Shelly.share(currentContext)
                .text("test")
                .send();

        verify(currentContext).startActivity(argument.capture());

        Intent result = argument.getValue();
        assertEquals("test", result.getStringExtra(Intent.EXTRA_TEXT));
    }

    @Test
    public void imageMime() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Uri mockUri = mock(Uri.class);

        Shelly.share(currentContext)
                .image(mockUri)
                .send();

        verify(currentContext).startActivity(argument.capture());

        Intent result = argument.getValue();
        assertEquals("image/*", result.getType());
    }

    @Test
    public void videoMime() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Uri mockUri = mock(Uri.class);

        Shelly.share(currentContext)
                .video(mockUri)
                .send();

        verify(currentContext).startActivity(argument.capture());

        Intent result = argument.getValue();
        assertEquals("video/*", result.getType());
    }
}