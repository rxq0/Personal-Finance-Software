public class Launcher {

    // Launches the main class, having a launcher class that doesn't extend from Application module is mandatory to be able to pack JavaFX projects into a JAR.
    // More info about this workaround @ https://stackoverflow.com/questions/53533486/how-to-open-javafx-jar-file-with-jdk-11?rq=1
 public static void main(String[] args) {
    Main.main(args);
  }
}