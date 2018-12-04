# log
Android log util, automatically log class name, code line, using pre-defined or customized tag, and also save log to sdcard

Usage:  
~~~~
Log.setCustomTagPrefix(YOUR_LOG_TAG); // customize your log tag, otherwish, the lib will use default tag
Log.setPrint(BuildConfig.DEBUG); //set whether print log to console
Log.setWrite(BuildConfig.DEBUG);//set whether save logcat to sdcard
Log.setError(BuildConfig.Debug);//set whether save exception to sdcard
Log.setPath(BuildConfig.APPLICATION_ID);//set sdcard path to save the logcat
~~~~

Gradle:  
~~~~
allprojects {
    repositories {
      ...
      maven { url 'https://www.jitpack.io' }
    }
}
dependencies {
    implementation 'com.github.anoreg:log:v1.2'
}
~~~~

Maven:  
~~~~
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://www.jitpack.io</url>
    </repository>
</repositories>
<dependency>
    <groupId>com.github.anoreg</groupId>
    <artifactId>log</artifactId>
    <version>v1.2</version>
</dependency>
~~~~
  
