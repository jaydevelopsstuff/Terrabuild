package net.guardiandev.terrabuild;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import net.guardiandev.terrabuild.backend.ModManager;
import net.guardiandev.terrabuild.backend.api.export.Template;
import net.guardiandev.terrabuild.gui.Stages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TerrabuildApplication extends Application {
    @Getter
    private static TerrabuildApplication Instance;


    public static final String Name = "Terrabuild";
    public static final String Version = "0.6.0";
    public static final Image Icon = new Image("icon.png");


    public static final Logger Logger = LoggerFactory.getLogger("Terrabuild");

    public static final ModManager modManager = new ModManager();

    @Override
    public void start(Stage stage) throws Exception {
        Instance = this;
        Logger.info("Initializing GUI");
        Stages.terrabuild.show();
    }

    @Override
    public void stop() {
        modManager.exit();
        System.exit(0);
    }

    public URL getResource(String resName) {
        return getClass().getResource(resName);
    }

    public String getResourceFileAsString(String resName) throws IOException {
        try (InputStream is = getResource(resName).openStream()) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }


    public static void main(String[] args) {
        Application.launch();
    }
}
