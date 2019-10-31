# IdleIdea

Idle Idea is an idea tracking application. You can create, edit, and delete ideas. It keeps track of names, priority, notes, completion status, and time. It utilizes a local SQLite database to persist data, and is architected using the Model View Presenter pattern. There are also unit and integration tests using JUnit and Mockito.

<a href='https://play.google.com/store/apps/details?id=com.robbypark.android.idleidea&hl=en'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width="200px"/></a>

## Requirements

* Latest Android SDK tools
* Latest Android platform tools
* Android SDK 29

## Building

This project uses the Gradle build system and the Android gradle plugin support for unit testing. You can either benefit from IDEs integration such as Android studio or run the tests on the command line.

With Android Studio:
1. In Android Studio selection "Open an existing Android Studio Project"
2. When prompted, add the VCS root.
3. Build and Run app.

## Testing

1. Check out the relevant code:
    * The application code is located in `src/main/java`
    * Unit Tests are in `src/test/java`
1. Unit tests can be run by selecting a Test class, right click > Run all tests.
1. Create a test configuration with the JUnit4 runner: `org.junit.runners.JUnit4`
    * Open *Run* menu | *Edit Configurations*
    * Add a new *JUnit* configuration
    * Choose module *app*
    * Select the class to run by using the *...* button
1. Run the newly created configuration

After downloading the projects code using `git clone` you'll be able to run the
unit tests using the command line:

    ./gradlew test

If all the unit tests have been successful you will get a `BUILD SUCCESSFUL`
message.

A report in HTML format is generated in `app/build/reports/tests`



## Dependencies

See `build.gradle`.
