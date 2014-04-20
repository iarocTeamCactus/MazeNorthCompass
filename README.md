MazeNorthCompass
================

Magnetic Compass with secondary compass which points to a secondary direction when "Set Maze North" button is pressed

================


Hi June,

Here is a github link to code for a Maze North Compass.  This is a complete application unconnected with the 
Roomba programming.  The only portion that's needed for the Roomba is the AndroidCompass.java file.  That is the 
class that connects to the compass sensor and delivers readings.  We noticed last year that occasionally the
compass will send out crazy set of results and then within a few seconds settle down and start sending reliable
results again.  The original code from http://android-er.blogspot.com/2010/08/simple-compass-sensormanager-and.html
uses some now deprecated calls to get the compass, but for the older phones, this might be for the best.

To use the AndroidCompass class:

// these instructions in the git README
// 
// instantiate AndroidCompass (requires that the android activity Context be passed in)
// check that the sensor is running
// manually point phone and robot toward maze north
//    perhaps tap the phone a few times like one might on a normal compass
// read the compass until magnetic north settles down
// calibrate MazeNorth
// read MazeNorth while travelling through the maze.
//    expected results while facing northerly would be (float) < 5 or > 355.
//    MazeNorth and CompassNorth both wrap from 359.99999 back to 0.

// SAMPLE CODE BELOW UNTESTED
// Sample code will be checked when I get the latest version of Erik's Dashboard code or Vic's IOIOActivity subclass.

// instantiate AndroidCompass (requires that the android activity Context be passed in)
myCompass = new AndroidCompass(this.getBaseContext());  //requires an android Context from an android Activity

// check that the sensor is running
while (!myCompass.isSensorRunning())   //don't continue until sensor is running
     Thread.sleep(1000);

// manually point phone and robot toward maze north
//    perhaps tap the phone a few times like one might on a normal compass

// read the compass until magnetic north settles down
while (/* student exercise using myCompass.getMagneticNorth() */)

// calibrate MazeNorth
myCompass.calibrateMazeNorth();

// read MazeNorth while travelling through the maze.
//    expected results while facing very northerly would be (float) < 5.0 or > 355.0
//    MazeNorth and CompassNorth both wrap from 359.99999 back to 0.
myCompass.getMazeNorth();

Thanks,
Rob

