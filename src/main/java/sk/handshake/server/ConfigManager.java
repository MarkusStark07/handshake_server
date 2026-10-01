/*
 * Created / Edited by Marek Gajdoš (gajdosm.sk@gmail.com)
 * Copyright (c) 2026 Marek Gajdoš & Lukáš Balážik
 * All rights reserved.
 *
 * This source code is part of a school project.
 * Redistribution, modification, or use outside the scope
 * of this academic project is not permitted without
 * explicit permission from the author(s).
 */

package sk.handshake.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.handshake.server.domain.Config;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Správca konfigurácie pre HandShake Server.
 * Implementuje singleton vzor pre centralizovanú správu konfigurácie aplikácie.
 *
 * <p>Trieda zabezpečuje načítanie konfigurácie zo súboru {@code config.json},
 * vytvorenie šablóny konfiguračného súboru, ak neexistuje, a overenie,
 * či aplikácia nepoužíva predvolené konfiguračné hodnoty.</p>
 *
 * <p>Konfiguračný súbor sa ukladá v aktuálnom pracovnom adresári aplikácie
 * a obsahuje nastavenia servera ako host a port.</p>
 *
 * @see Config
 */
public class ConfigManager {
    // Singleton inštancia ConfigManagera
    private static ConfigManager instance;

    // Privátny konštruktor pre zabránenie vytváraniu inštancií zvonka
    private ConfigManager() {
    }

    // Získanie singleton inštancie ConfigManagera (thread-safe)
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private Config config;
    private static final File configFile = new File(System.getProperty("user.dir") + "/config.json");
    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);

    /// Táto metóda sa pokúsi načítať konfiguračný súbor, v prípadeže sa jej to nepodarí vytvorí nový
    /// na základe default hodnôt atribút doménového objektu {@link Config}
    ///
    /// @throws IOException ak zlyhá vytvorenie alebo načítanie konfiguračného súboru
    public void init() throws IOException {
        try {
            config = loadConfig();
            log.info("Successfully loaded config file.");
        } catch (IOException e) {
            log.error("Failed to load config file:  {}", String.valueOf(e));
            createTemplate();
            config = loadConfig();
            log.info("Successfully created and loaded new config file.");
        }
        checkForDefaultConfig(config);
    }

    /// Táto metóda vytvára konfiguračný súbor s default konfiguráciou
    /// na základe default hodnôt atribút doménového objektu {@link Config}
    ///
    /// @throws IOException ak zlyhá vytvorenie súboru
    private static void createTemplate() throws IOException {
        Config template = new Config();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(configFile, template);
    }

    /// Táto metóda načíta konfiguračný súbor.
    ///
    /// @return načítaná konfigurácia servera
    /// @throws IOException ak zlyhá načítanie súboru
    private static Config loadConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(configFile, Config.class);
    }

    /// Táto metóda vracia entitu konfigurácie
    ///
    /// @return konfigurácia servera
    public Config getConfig() {
        return this.config;
    }

    /// Táto metóda kontroluje načítanú konfiguráciu, ak sa konfiguračné hodnoty zhodujú s default hodnotami vyhodí warn správu
    ///
    /// @param loadedConfig načítaná konfigurácia na kontrolu
    private void checkForDefaultConfig(Config loadedConfig) {
        Config defaultConfig = new Config();

        if (Objects.equals(loadedConfig.getHost(), defaultConfig.getHost()) && loadedConfig.getPort() == defaultConfig.getPort()) {
            log.warn("You are using default configuration! Please change it in: {}", configFile.getAbsolutePath());
        }
    }
}
