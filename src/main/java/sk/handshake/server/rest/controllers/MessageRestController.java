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
 * REST kontrolér pre správu správ v HandShake systéme.
 * Poskytuje endpointy pre nahrávanie a sťahovanie správ.
 */
public class MessageRestController {
    private static final Logger log = LogManager.getLogger(MessageRestController.class);

    /**
     * Spracováva požiadavku na nahranie správy.
     *
     * @param ctx kontext HTTP požiadavky obsahujúci informácie o požiadavke a odpovedi
     */
    public void upload(Context ctx) {
        ctx.status(HttpStatus.CREATED).result("Uploaded!");
        log.info("Uploaded!");
    }

    /**
     * Spracováva požiadavku na stiahnutie správy.
     *
     * @param ctx kontext HTTP požiadavky obsahujúci informácie o požiadavke a odpovedi
     */
    public void download(Context ctx) {
        ctx.status(HttpStatus.OK).result("Downloaded!");
        log.info("Downloaded!");
    }
}
