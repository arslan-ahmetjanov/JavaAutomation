package lib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

/**
 * Единая точка чтения настроек тестов.
 *
 * <p>Приоритет значений: системное свойство ({@code -Dkey=value}) → переменная окружения
 * ({@code UPPER_SNAKE} от ключа, например {@code appium.url} → {@code APPIUM_URL}) →
 * {@code config.properties} из classpath (или {@code src/test/resources}) → значение по умолчанию.
 */
public final class Config {

    private static final String CONFIG_FILE_NAME = "config.properties";
    private static final Properties PROPERTIES = loadProperties();

    private Config() {
    }

    public static String get(String key) {
        return get(key, null);
    }

    public static String get(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (isNotEmpty(value)) {
            return value.trim();
        }
        value = System.getenv(toEnvironmentName(key));
        if (isNotEmpty(value)) {
            return value.trim();
        }
        value = PROPERTIES.getProperty(key);
        if (isNotEmpty(value)) {
            return value.trim();
        }
        return defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        if (!isNotEmpty(value)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Configuration value '" + key + "' must be an integer, but was: " + value, e);
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        if (!isNotEmpty(value)) {
            return defaultValue;
        }
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        throw new IllegalArgumentException("Configuration value '" + key + "' must be 'true' or 'false', but was: " + value);
    }

    /**
     * Возвращает путь из конфигурации. Относительные пути разрешаются от {@code user.dir}.
     */
    public static String getPath(String key, String defaultValue) {
        String value = get(key, defaultValue);
        if (!isNotEmpty(value)) {
            return null;
        }
        Path path = Paths.get(value);
        if (path.isAbsolute()) {
            return path.normalize().toString();
        }
        return Paths.get(System.getProperty("user.dir")).resolve(path).normalize().toString();
    }

    public static String getPlatform() {
        return get("platform", "");
    }

    public static int getDefaultTimeout() {
        return getInt("timeout.default", 15);
    }

    static String toEnvironmentName(String key) {
        return key.toUpperCase(Locale.ROOT).replace('.', '_').replace('-', '_');
    }

    private static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        InputStream classpathStream = Config.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
        if (classpathStream != null) {
            try (InputStream stream = classpathStream) {
                properties.load(stream);
            } catch (IOException e) {
                throw new IllegalStateException("Cannot read " + CONFIG_FILE_NAME + " from the classpath", e);
            }
            return properties;
        }

        File file = new File(System.getProperty("user.dir"), "src/test/resources/" + CONFIG_FILE_NAME);
        if (!file.isFile()) {
            return properties;
        }
        try (InputStream stream = Files.newInputStream(file.toPath())) {
            properties.load(stream);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + file.getAbsolutePath(), e);
        }
        return properties;
    }
}
