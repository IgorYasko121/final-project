package com.igoryasko.justmusic.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class PathGenerator {

    public String generateHash() {
        UUID random = UUID.randomUUID();
        return DigestUtils.md5Hex(random.toString());
    }
}
