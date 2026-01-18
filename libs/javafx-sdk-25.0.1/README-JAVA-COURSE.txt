JavaFX lärs inte ut i TDDD78/TDDE30, och kan användas på egen risk.
Det finns med i libs för att förenkla för de som redan känner till
JavaFX, eftersom vi inte har möjlighet att använda Gradle eller Maven.

Detta är Linux-versionen.  För Windows eller MacOS behöver man själv
ladda ner en egen JavaFX-version om man tänker använda detta.

Filen libjfxwebkit.so är borttagen eftersom den är stor (>100 MB)
och enbart behövs om man vill bädda in websidor i sin Java-applikation.

--------------------------------------------------------------------------------

Såvitt vi förstår behöver man också göra följande för att detta ska fungera
**för de Run Configurations som man själv skapar i IDEA**:

Because JavaFX is no longer part of the standard JDK, the Java Virtual Machine (JVM) needs to be told where to find the modules at runtime.

    Go to the top-right of the IntelliJ window and click the Run Configuration dropdown (where your main class name is).

    Select Edit Configurations....

    Select your application’s configuration on the left.

    Click Modify options (usually a blue link or button) and select Add VM options.

    In the VM options field, paste the following:

        --module-path "path/to/your/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml

    Note: Replace path/to/your/javafx-sdk/lib with the actual absolute path to the lib folder on your machine. If the path contains spaces, keep the double quotes.
