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

package sk.handshake.server.rest;

import io.javalin.Javalin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sk.handshake.server.ConfigManager;
import sk.handshake.server.rest.controllers.DeviceRestController;
import sk.handshake.server.rest.controllers.MessageRestController;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

/**
 * Trieda, ktorá predstavuje RESTful server vytvorený pomocou Javalin. Server poskytuje
 * endpointy pre správu správ a zariadení a podporuje spustenie a zastavenie
 * inštancie servera.
 * <p>
 * Metódy:
 * - {@link #start()}: Inicializuje a spustí Javalin server.
 * - {@link #stop()}: Zastaví bežiacu inštanciu Javalin servera.
 * - {@link #registerRoutes()}: Registruje cesty endpointov.
 */
public class RestServer {
    private static final Logger log = LogManager.getLogger(RestServer.class);
    private static final ConfigManager configManager = ConfigManager.getInstance();

    private Javalin javalin;

    public RestServer() {
    }

    /**
     * Spustí REST server.
     * <p>
     * Táto metóda inicializuje Javalin server s registrovanými routes a spustí ho
     * na IP adrese a porte definovaných v konfigurácií získanej cez {@link ConfigManager}.
     * Po úspešnom spustení zaloguje informačnú správu.
     * </p>
     *
     * @return Spustená inštancia {@link Javalin} servera
     */
    public Javalin start() {
        this.javalin = Javalin.create(javalinConfig -> {
            javalinConfig.routes.apiBuilder(this::registerRoutes);
        });

        javalin.start(configManager.getConfig().getHost(), configManager.getConfig().getPort());
        log.info("Started RestServer");
        return javalin;
    }

    /**
     * Zastaví REST server.
     * <p>
     * Táto metóda bezpečne zastaví bežiacu inštanciu Javalin servera, ak existuje.
     * Po úspešnom zastavení zaloguje informačnú správu. Ak server nie je spustený
     * (inštancia je {@code null}), metóda nevykoná žiadnu akciu.
     * </p>
     */
    public void stop() {
        if (javalin != null) {
            javalin.stop();
            log.info("Stopped RestServer");
        }
    }


    /**
     * Registruje všetky REST endpointy servera.
     * <p>
     * Táto metóda inicializuje kontroléry pre správu správ a zariadení a následne
     * definuje REST API endpointy pomocou Javalin API builderu. Vytvárajú sa dve
     * hlavné skupiny endpointov:
     * </p>
     *
     * <h3>Message endpointy (/message):</h3>
     * <ul>
     *   <li><b>POST /message/upload</b> - Endpoint pre nahrávanie správ na server</li>
     *   <li><b>GET /message/download</b> - Endpoint pre sťahovanie správ zo servera</li>
     * </ul>
     *
     * <h3>Device endpointy (/device):</h3>
     * <ul>
     *   <li><b>POST /device/register</b> - Endpoint pre registráciu nového zariadenia</li>
     * </ul>
     *
     * <p>
     * Táto metóda je volaná automaticky pri štarte servera cez konfiguráciu Javalin
     * routes v metóde {@link #start()}.
     * </p>
     *
     * @see MessageRestController
     * @see DeviceRestController
     */
    private void registerRoutes() {
        MessageRestController messageRestController = new MessageRestController();
        DeviceRestController deviceRestController = new DeviceRestController();

        path("message", () -> {
            post("upload", messageRestController::upload);
            get("download", messageRestController::download);
        });

        path("device", () -> {
            post("register", deviceRestController::register);
        });
    }
}