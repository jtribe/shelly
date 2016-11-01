package au.com.jtribe.shelly;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static au.com.jtribe.shelly.Preconditions.checkNotNull;

/**
 * Created by Mitch on 15/10/2016.
 */

public final class Map {

  //Either use lat long or address to open a map
  private Double latitude;
  private Double longitude;

  private String address;

  //Optionals:
  private Integer zoom;
  private String label;

  Map() {
  }

  @NonNull
  @CheckResult
  public Map latitude(@NonNull Double latitude) {
    checkNotNull(latitude, "latitude == null");
    this.latitude = latitude;
    return this;
  }

  @NonNull
  @CheckResult
  public Map longitude(@NonNull Double longitude) {
    checkNotNull(longitude, "longitude == null");
    this.longitude = longitude;
    return this;
  }

  @NonNull
  @CheckResult
  public Map zoom(@NonNull Integer zoom) {
    checkNotNull(zoom, "zoom == null");
    this.zoom = zoom;
    return this;
  }

  @NonNull
  @CheckResult
  public Map label(@NonNull String label) {
    checkNotNull(label, "label == null");
    this.label = label;
    return this;
  }

  @NonNull
  @CheckResult
  public Map address(@NonNull String address) {
    checkNotNull(address, "address == null");
    this.address = address;
    return this;
  }

  /**
   * Creates and returns an Intent that will create and configure an alarm with the values
   * provided.
   */
  @NonNull
  @CheckResult
  public Intent asIntent() {


    //Main string
    String dataScheme = "geo:";

    //init default latLong
    String latLongScheme = "0,0";
    //Build the data URI string
    if (this.longitude != null && this.latitude != null) {
      latLongScheme = String.format("%s,%s", this.latitude.toString(),this.longitude.toString());
      //dataScheme = dataScheme.concat(String.format("%s,%s", this.latitude.toString(), this.longitude.toString()));
    }
    dataScheme = dataScheme.concat(latLongScheme);

    if (this.address != null) {
      //need to format address to remove spaces and add "+" symbol
      String address = this.address.replace(' ', '+');
      dataScheme = dataScheme.concat(String.format("?q=%s", address));
      //dataScheme = dataScheme.concat(String.format("0,0?q=%s", address));
    }

    if (this.zoom != null) {

    }
    if (this.label != null) {

    }

    Log.d("map URI",Uri.parse(dataScheme).toString());
    Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataScheme));
    return mapIntent;
  }

  /**
   * Creates a chooser intent that will dial the phone number provided, the chooser will have no
   * specified title.
   *
   * <b>Note: This may not be an appropriate user experience, since there is most likely a default
   * app configured by the user.</b>
   */
  @NonNull
  @CheckResult
  public Intent asChooserIntent() {
    return asChooserIntent(null);
  }

  /**
   * Creates and returns an Intent that will display a chooser to the user with the specified
   * title, allowing them to pick an app to dial with.
   *
   * <b>Note: This may not be an appropriate user experience, since there is most likely a default
   * app configured by the user.</b>
   *
   * @param prompt The chooser's title is set by prompt
   */
  @NonNull
  @CheckResult
  public Intent asChooserIntent(@Nullable CharSequence prompt) {
    return Intent.createChooser(asIntent(), prompt);
  }
}