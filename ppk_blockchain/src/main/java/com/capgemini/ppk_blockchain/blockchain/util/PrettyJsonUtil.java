package com.capgemini.ppk_blockchain.blockchain.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.nio.charset.StandardCharsets;

public class PrettyJsonUtil {

    Gson gson;

    public PrettyJsonUtil() {

        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public String prettyJson(final byte[] json) {
        return prettyJson(new String(json, StandardCharsets.UTF_8));
    }

    private String prettyJson(final String json) {
        var parsedJson = JsonParser.parseString(json);
        return gson.toJson(parsedJson);
    }
}
