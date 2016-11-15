package au.com.jtribe.shelly;

import android.content.Intent;
import android.net.Uri;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by angus on 27/08/2015.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SocialShareTest {

  @Test
  public void text() {
    Intent result = Shelly.share().text("test").asIntent();
    assertEquals("test", result.getStringExtra(Intent.EXTRA_TEXT));
  }

  @Test
  public void imageMime() {
    Uri mockUri = mock(Uri.class);
    Intent result = Shelly.share().image(mockUri).asIntent();
    assertEquals("image/*", result.getType());
  }

  @Test
  public void videoMime() {
    Uri mockUri = mock(Uri.class);
    Intent result = Shelly.share().video(mockUri).asIntent();
    assertEquals("video/*", result.getType());
  }
}