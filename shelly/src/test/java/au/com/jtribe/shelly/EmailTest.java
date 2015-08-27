package au.com.jtribe.shelly;

import android.content.Context;
import android.content.Intent;

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
public class EmailTest {

    @Test
    public void mime() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Shelly.email(currentContext)
                .send();

        verify(currentContext).startActivity(argument.capture());
        Intent result = argument.getValue();
        assertEquals("message/rfc822", result.getType());
    }

    @Test
    public void subject() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Shelly.email(currentContext)
                .subject("test")
                .send();

        verify(currentContext).startActivity(argument.capture());
        Intent result = argument.getValue();
        assertEquals("test", result.getStringExtra(Intent.EXTRA_SUBJECT));
    }

    @Test
    public void body() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Shelly.email(currentContext)
                .body("body")
                .send();

        verify(currentContext).startActivity(argument.capture());

        Intent result = argument.getValue();
        assertEquals("body", result.getStringExtra(Intent.EXTRA_TEXT));
    }

    @Test
    public void singleTo() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Shelly.email(currentContext)
                .to("test@test.com")
                .send();

        verify(currentContext).startActivity(argument.capture());

        Intent result = argument.getValue();
        String[] to = result.getStringArrayExtra(Intent.EXTRA_EMAIL);
        assertEquals(1, to.length);
        assertEquals("test@test.com", to[0]);
    }

    @Test
    public void manyToUsingVarargs() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Shelly.email(currentContext)
                .to("1", "2")
                .send();

        verify(currentContext).startActivity(argument.capture());

        Intent result = argument.getValue();
        String[] to = result.getStringArrayExtra(Intent.EXTRA_EMAIL);
        assertEquals(2, to.length);
        assertEquals("1", to[0]);
        assertEquals("2", to[1]);
    }

    @Test
    public void manyToUsingTwoCalls() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Shelly.email(currentContext)
                .to("1")
                .to("2")
                .send();

        verify(currentContext).startActivity(argument.capture());

        Intent result = argument.getValue();
        String[] to = result.getStringArrayExtra(Intent.EXTRA_EMAIL);
        assertEquals(2, to.length);
        assertEquals("1", to[0]);
        assertEquals("2", to[1]);
    }

    @Test
    public void manyCcUsingVarargs() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Shelly.email(currentContext)
                .cc("1", "2")
                .send();

        verify(currentContext).startActivity(argument.capture());

        Intent result = argument.getValue();
        String[] to = result.getStringArrayExtra(Intent.EXTRA_CC);
        assertEquals(2, to.length);
        assertEquals("1", to[0]);
        assertEquals("2", to[1]);
    }

    @Test
    public void manyCcUsingTwoCalls() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Shelly.email(currentContext)
                .cc("1")
                .cc("2")
                .send();

        verify(currentContext).startActivity(argument.capture());

        Intent result = argument.getValue();
        String[] to = result.getStringArrayExtra(Intent.EXTRA_CC);
        assertEquals(2, to.length);
        assertEquals("1", to[0]);
        assertEquals("2", to[1]);
    }

    @Test
    public void manyBccUsingVarargs() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Shelly.email(currentContext)
                .bcc("1", "2")
                .send();

        verify(currentContext).startActivity(argument.capture());

        Intent result = argument.getValue();
        String[] to = result.getStringArrayExtra(Intent.EXTRA_BCC);
        assertEquals(2, to.length);
        assertEquals("1", to[0]);
        assertEquals("2", to[1]);
    }

    @Test
    public void manyBccUsingTwoCalls() {
        Context currentContext = mock(Context.class);
        ArgumentCaptor<Intent> argument = ArgumentCaptor.forClass(Intent.class);

        Shelly.email(currentContext)
                .bcc("1")
                .bcc("2")
                .send();

        verify(currentContext).startActivity(argument.capture());

        Intent result = argument.getValue();
        String[] to = result.getStringArrayExtra(Intent.EXTRA_BCC);
        assertEquals(2, to.length);
        assertEquals("1", to[0]);
        assertEquals("2", to[1]);
    }
}