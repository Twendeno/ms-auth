package com.twendeno.msauth.securityConfig;

import io.github.cdimascio.dotenv.Dotenv;

public class DotEnvConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public static String get(String key) {
        return dotenv.get(key);
    }

    public static void loadEnv() {
        Dotenv dotenv = Dotenv.configure().load();

        String springDatasourceUrl = dotenv.get("SPRING_DATASOURCE_URL");
        String postgresUser = dotenv.get("DATABASE_USERNAME");
        String postgresPassword = dotenv.get("DATABASE_PASSWORD");

        String adminUsername = dotenv.get("ADMIN_USERNAME");
        String adminPassword = dotenv.get("ADMIN_PASSWORD");

        String serverPort = dotenv.get("SERVER_PORT");

        System.setProperty("SPRING_DATASOURCE_URL", springDatasourceUrl);
        System.setProperty("DATABASE_USERNAME", postgresUser);
        System.setProperty("DATABASE_PASSWORD", postgresPassword);

        System.setProperty("ADMIN_USERNAME", adminUsername);
        System.setProperty("ADMIN_PASSWORD", adminPassword);

        System.setProperty("SERVER_PORT", serverPort);
    }

}
