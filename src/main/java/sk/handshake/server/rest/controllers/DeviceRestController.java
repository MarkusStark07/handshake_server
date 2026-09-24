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

package sk.handshake.server.rest.controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * REST kontrolér pre správu zariadení v HandShake systéme.
 * Poskytuje endpointy pre registráciu zariadení.
 */
public class DeviceRestController {
    private static final Logger log = LogManager.getLogger(DeviceRestController.class);

    /**
     * Spracováva požiadavku na registráciu zariadenia.
     *
     * @param ctx kontext HTTP požiadavky obsahujúci informácie o požiadavke a odpovedi
     */
    public void register(Context ctx) {
        ctx.status(HttpStatus.OK).result("Device registered!");
        log.info("Registering device!");
    }
}