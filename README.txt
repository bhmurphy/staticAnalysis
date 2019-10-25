In order to run our tool, run:

java -jar bugfinder.jar srcDir

where srcDir is the directory that contains the code you'd like to analyze.
You can also give a single Java file, as long as it does not rely upon any classes that
are not in the standard Java library.

If you would like to compile the code yourself, and run that jar, you can do so with
the following command:

mvn clean compile assembly:single

This will produce a jar file located at target/bugfinder.jar.