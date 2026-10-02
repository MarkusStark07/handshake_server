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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.handshake.server.rest.RestServer;

import java.io.IOException;

/**
 * Hlavná trieda projektu HandShake Server.
 * do toho javadocu sa budú postupne pridávať informácie
 */
public class HandShake {
    private static final String VERSION = "1.1.1-ALPHA-DEV"; //TODO: bolo by fajn nejako ťahať verziu z build.gradle

    private static final Logger log = LogManager.getLogger(HandShake.class);
    private static final ConfigManager configManager = ConfigManager.getInstance();

    public static void main(String[] args) throws IOException {
        // BOOT MESSAGE
        log.info("""
                
                ####################################################################
                #                                                                  #
                #    _   _                    _  ____   _             _            #
                #   | | | |  __ _  _ __    __| |/ ___| | |__    __ _ | | __ ___    #
                #   | |_| | / _` || '_ \\  / _` |\\___ \\ | '_ \\  / _` || |/ // _ \\   #
                #   |  _  || (_| || | | || (_| | ___) || | | || (_| ||   <|  __/   #
                #   |_| |_| \\__,_||_| |_| \\__,_||____/ |_| |_| \\__,_||_|\\_\\\\___|   #
                #                                                                  #
                #              ____                                                #
                #             / ___|   ___  _ __ __   __ ___  _ __                 #
                #             \\___ \\  / _ \\| '__|\\ \\ / // _ \\| '__|                #
                #              ___) ||  __/| |    \\ V /|  __/| |                   #
                #             |____/  \\___||_|     \\_/  \\___||_|                   #
                #                                                                  #
                ####################################################################""");
        log.info("Version: " + VERSION);

        // Inicializácia konfigurácie
        configManager.init();

        // Inicializácia rest servera
        RestServer server = new RestServer();
        server.start();

        // Bezpečné ukončenie s Ctrl+C / SIGTERM
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }
}