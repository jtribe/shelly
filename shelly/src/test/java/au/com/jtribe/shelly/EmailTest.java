package au.com.jtribe.shelly;

import android.content.Intent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Created by angus on 27/08/2015.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class EmailTest {

  @Test
  public void subject() {
    Intent result = Shelly.email().subject("test").asIntent();
    assertEquals("test", result.getStringExtra(Intent.EXTRA_SUBJECT));
  }

  @Test
  public void body() {
    Intent result = Shelly.email().body("body").asIntent();
    assertEquals("body", result.getStringExtra(Intent.EXTRA_TEXT));
  }

  @Test
  public void singleTo() {
    Intent result = Shelly.email().to("test@test.com").asIntent();
    String[] to = result.getStringArrayExtra(Intent.EXTRA_EMAIL);
    assertEquals(1, to.length);
    assertEquals("test@test.com", to[0]);
  }

  @Test
  public void manyToUsingVarargs() {
    Intent result = Shelly.email().to("1", "2").asIntent();
    String[] to = result.getStringArrayExtra(Intent.EXTRA_EMAIL);
    assertEquals(2, to.length);
    assertEquals("1", to[0]);
    assertEquals("2", to[1]);
  }

  @Test
  public void manyToUsingTwoCalls() {
    Intent result = Shelly.email().to("1").to("2").asIntent();
    String[] to = result.getStringArrayExtra(Intent.EXTRA_EMAIL);
    assertEquals(2, to.length);
    assertEquals("1", to[0]);
    assertEquals("2", to[1]);
  }

  @Test
  public void manyCcUsingVarargs() {
    Intent result = Shelly.email().cc("1", "2").asIntent();
    String[] to = result.getStringArrayExtra(Intent.EXTRA_CC);
    assertEquals(2, to.length);
    assertEquals("1", to[0]);
    assertEquals("2", to[1]);
  }

  @Test
  public void manyCcUsingTwoCalls() {
    Intent result = Shelly.email().cc("1").cc("2").asIntent();
    String[] to = result.getStringArrayExtra(Intent.EXTRA_CC);
    assertEquals(2, to.length);
    assertEquals("1", to[0]);
    assertEquals("2", to[1]);
  }

  @Test
  public void manyBccUsingVarargs() {
    Intent result = Shelly.email().bcc("1", "2").asIntent();
    String[] to = result.getStringArrayExtra(Intent.EXTRA_BCC);
    assertEquals(2, to.length);
    assertEquals("1", to[0]);
    assertEquals("2", to[1]);
  }

  @Test
  public void manyBccUsingTwoCalls() {
    Intent result = Shelly.email().bcc("1").bcc("2").asIntent();
    String[] to = result.getStringArrayExtra(Intent.EXTRA_BCC);
    assertEquals(2, to.length);
    assertEquals("1", to[0]);
    assertEquals("2", to[1]);
  }
}