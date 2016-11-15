package au.com.jtribe.shelly;

import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static au.com.jtribe.shelly.Preconditions.checkNotNull;

/**
 * Represents a Calendar event
 */
public final class Calendar {

  //times are based on ms since epoch
  private Long eventStartTime;
  private Long eventEndTime;

  private String eventTitle;
  private String eventDescription;
  private String eventLocation;
  private final List<String> attendeeEmails;
  private Boolean isAllDayEvent;

  Calendar() {
    attendeeEmails = new ArrayList<>();
  }

  /**
   * Adds a title to the Calendar event
   *
   * @param eventTitle The title of the Calendar event
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Calendar eventTitle(@NonNull String eventTitle) {
    checkNotNull(eventTitle, "eventTitle == null");
    this.eventTitle = eventTitle;
    return this;
  }

  /**
   * Adds a description to the Calendar event
   *
   * @param eventDescription The description of the Calendar event
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Calendar eventDescription(@NonNull String eventDescription) {
    checkNotNull(eventDescription, "eventDescription == null");
    this.eventDescription = eventDescription;
    return this;
  }

  /**
   * Adds a location to the Calendar event. Can be a formal street address or any other string
   * Some Calendar apps will include a map of the eventLocation
   *
   * @param eventLocation The location of the Calendar event.
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Calendar eventLocation(@NonNull String eventLocation) {
    checkNotNull(eventLocation, "eventLocation == null");
    this.eventLocation = eventLocation;
    return this;
  }

  /**
   * Adds a start time to the Calendar event.
   * Takes in a java.util.Calendar object but stores the start time as a long (time since epoch)
   *
   * @param calendar A java.util.Calendar object representing the start time and date of the
   * Calendar event
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Calendar eventStartTime(@NonNull java.util.Calendar calendar) {
    checkNotNull(calendar, "calendar == null");
    this.eventStartTime = calendar.getTime().getTime();
    return this;
  }

  /**
   * Adds an end time to the Calendar event.
   * Takes in a java.util.Calendar object but stores the end time as a long (time since epoch)
   *
   * @param calendar a Java.util.Calendar representing the end time of the Calendar event
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Calendar eventEndTime(@NonNull java.util.Calendar calendar) {
    checkNotNull(calendar, "calendar == null");
    this.eventEndTime = calendar.getTime().getTime();
    return this;
  }

  /**
   * Adds a flag to turn the Calendar event into an all day event.
   *
   * @param isAllDayEvent determines whether the Calendar event is an all day event
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Calendar isAllDayEvent(@NonNull Boolean isAllDayEvent) {
    checkNotNull(isAllDayEvent, "isAllDayEvent == null");
    this.isAllDayEvent = isAllDayEvent;
    return this;
  }

  /**
   * Adds email addresses that this Calendar event invitation should be sent to.
   *
   * @param attendeeEmails Email addresses to invite people to this Calendar event.
   * @return Object this method was called on for method chaining.
   */
  @NonNull
  @CheckResult
  public Calendar attendeeEmails(@NonNull String... attendeeEmails) {
    checkNotNull(attendeeEmails, "attendeeEmails == null");
    this.attendeeEmails.addAll(Arrays.asList(attendeeEmails));
    return this;
  }

  /**
   * Creates and returns an Intent that will create and configure an alarm with the values
   * provided.
   */
  @NonNull
  @CheckResult
  public Intent asIntent() {
    Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
    //Don't need both, one or the other seem to achieve the same thing, maybe api < 14 need the setType
    calendarIntent.setData(CalendarContract.Events.CONTENT_URI);
    calendarIntent.setType("vnd.android.cursor.dir/event");

    if (this.eventTitle != null) {
      calendarIntent.putExtra(CalendarContract.Events.TITLE, this.eventTitle);
    }
    if (this.eventDescription != null) {
      calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, this.eventDescription);
    }
    if (this.eventLocation != null) {
      calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, this.eventLocation);
    }
    if (this.eventStartTime != null) {
      calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, this.eventStartTime);
    }
    if (this.eventEndTime != null) {
      calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, this.eventEndTime);
    }
    if (this.isAllDayEvent != null) {
      calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, this.isAllDayEvent);
    }
    if (!this.attendeeEmails.isEmpty()) {
      String[] emailsArray = new String[this.attendeeEmails.size()];
      calendarIntent.putExtra(Intent.EXTRA_EMAIL, this.attendeeEmails.toArray(emailsArray));
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      calendarIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    } else {
      //noinspection deprecation
      calendarIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    }

    return calendarIntent;
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
