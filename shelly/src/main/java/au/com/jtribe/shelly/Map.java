package au.com.jtribe.shelly;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static au.com.jtribe.shelly.Preconditions.checkNotNull;

/**
 * Represents a Map which can be set to a specific address or coordinate (lat, long)
 */
public final class Map {

  //Can use either lat/long combination or address
  private Double latitude;
  private Double longitude;
  private String address;

  //Optionals:
  private Integer zoom;
  //private String label;

  Map() {
  }

  /**
   * Adds a latitude to the Map
   *
   * @param latitude The latitude coordinate for the Map
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Map latitude(@NonNull Double latitude) {
    checkNotNull(latitude, "latitude == null");
    this.latitude = latitude;
    return this;
  }

  /**
   * Adds a longitude to the Map
   *
   * @param longitude the longitude coordinate for the Map
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Map longitude(@NonNull Double longitude) {
    checkNotNull(longitude, "longitude == null");
    this.longitude = longitude;
    return this;
  }

  //Zoom parameter accepts values between 1-23

  /**
   * Adds a zoom parameter to the Map, higher values increase the zoom magnification.
   * Appropriate zoom values are between 1 and 23
   *
   * @param zoom the zoom parameter for the Map.
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Map zoom(@NonNull Integer zoom) {
    checkNotNull(zoom, "zoom == null");
    if (zoom < 1) {
      zoom = 1;
    }
    if (zoom > 23) {
      zoom = 23;
    }
    this.zoom = zoom;
    return this;
  }

  /**
   * Adds an address or search term to the Map.
   * May be a specific address or a search term such as "Restaurant"
   *
   * @param address the address for the Map
   * @return Object this method was called on for method chaining.
   */
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
      latLongScheme = String.format("%s,%s", this.latitude.toString(), this.longitude.toString());
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
      dataScheme = dataScheme.concat(String.format("?z=%s", zoom.toString()));
    }

    Log.d("map URI", Uri.parse(dataScheme).toString());
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
