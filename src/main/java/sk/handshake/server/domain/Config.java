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

package sk.handshake.server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import sk.handshake.server.ConfigManager;

/**
 * Doménový objekt konfigurácie HandShake Servera
 * Obsahuje nastavenia servera ako host a port, ktoré sa načítavajú
 * z konfiguračného súboru pomocou {@link ConfigManager}.
 *
 * <p>Konfigurácia sa serializuje a deserializuje pomocou Jackson knižnice.
 * Predvolené hodnoty sú host "127.0.0.1" a port 8080.</p>
 *
 * @see ConfigManager
 */
public class Config {
    public Config() {
    }

    // ATRIBÚTY KONFIGURÁCIE A ICH DEFAULTNÉ HODNOTY
    @JsonProperty(required = true, defaultValue = "127.0.0.1")
    private String host = "127.0.0.1";
    @JsonProperty(required = true, defaultValue = "8080")
    private int port = 8080;


    // GETTRE A SETTRE PRE ATRIBÚTY KONFIGURÁCIE
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
