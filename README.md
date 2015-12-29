# Shelly
Fluent API for common Intent use-cases in Android.

This library wraps Intents with a clean and simple to understand interface for a number of specific use-cases.

## Sample usage
See the sample project in the `sample/` folder.

## Installation

Add this into your build.gradle to get the Shelly goodness, you'll need to have `jcenter()` in your list of repositories.

```groovy
dependencies {
    compile 'au.com.jtribe.shelly:shelly:0.1.6@aar'
}
```

### Sharing Generic Information

Sharing some text and a url, the url is appended onto the text such that this shares "text with url http://www.jtribe.com.au":
```java
Shelly.share(context)
  .text("text with url")
  .url("http://www.jtribe.com.au")
  .send();
```

Sharing some text and an image:
```java
Shelly.share(context)
  .text("text with image")
  .image(myImageUri)
  .send();
```

Sharing some text and a video:
```java
Shelly.share(context)
  .text("text with video")
  .video(myVideoUri)
  .send();
```

### Sending Emails
Sending an email to someone:
```java
Shelly.email(context)
  .to("angus@jtribe.com")
  .subject("Subject Text")
  .body("Email Body")
  .send();
```

 Sending an email to multiple people at once:
 ```java
Shelly.email(context)
  .to("angus@jtribe.com", "partyravi@jtribe.com", "markymark@jtribe.com")
  .subject("Subject Text")
  .body("Email Body")
  .send();
 ```

 Sending an email with a cc:
 ```java
Shelly.email(context)
  .to("angus@jtribe.com", "partyravi@jtribe.com", "markymark@jtribe.com")
  .cc("toccto@jtribe.com", "anothercc@jtribe.com")
  .subject("Subject Text")
  .body("Email Body")
  .send();
 ```

 Sending an email with a bcc:
 ```java
Shelly.email(context)
  .to("angus@jtribe.com", "partyravi@jtribe.com", "markymark@jtribe.com")
  .bcc("toccto@jtribe.com", "anothercc@jtribe.com")
  .subject("Subject Text")
  .body("Email Body")
  .send();
 ```

## Issues and Feedback
Please use [Github Issues](https://github.com/jtribe/shelly/issues "Github Issues") for feature requests or bug reports.

## License
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
