@ECHO OFF
javac *.java
jar cvfe GPSClient.jar This *.class
PAUSE