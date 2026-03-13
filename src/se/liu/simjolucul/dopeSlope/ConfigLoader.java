package se.liu.simjolucul.dopeSlope;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("resources/config.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDebug() {
        return Boolean.parseBoolean(props.getProperty("debug", "false"));
    }
}
