package files;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import frame.Main;

public class FileUser {

    // Always use user home folder for config
    private static final String CONFIG_DIR =
            System.getProperty("user.home") + File.separator + ".myapp";
    private static final String SETTINGS_FILE = CONFIG_DIR + File.separator + "settings.txt";

    // ==========================
    // CHECK EMPLOYEE
    // ==========================
    public static boolean checkEmployee(String id) {
        String line;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Main.class.getResourceAsStream("/employees.txt"))
        )) {
            while ((line = br.readLine()) != null) {
                if (line.equals(id.trim())) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // ENSURE CONFIG EXISTS
    // ==========================
    private static void ensureSettingsFileExists() {
        try {
            File dir = new File(CONFIG_DIR);
            if (!dir.exists()) dir.mkdirs();

            File file = new File(SETTINGS_FILE);
            if (!file.exists()) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write("language=en");
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ==========================
    // READ SETTING
    // ==========================
    public static String settingsReader(String setting) {
        ensureSettingsFileExists();

        try (BufferedReader br = new BufferedReader(new FileReader(SETTINGS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(setting + "=")) {
                    return line.split("=", 2)[1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "en"; // default
    }

    // ==========================
    // WRITE SETTING
    // ==========================
    public static void settingsWriter(String setting, String newSetting) {
        ensureSettingsFileExists();

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(SETTINGS_FILE))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(setting + "=")) {
                    line = setting + "=" + newSetting;
                    found = true;
                }
                lines.add(line);
            }

            // If setting not found, add it
            if (!found) lines.add(setting + "=" + newSetting);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SETTINGS_FILE))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
