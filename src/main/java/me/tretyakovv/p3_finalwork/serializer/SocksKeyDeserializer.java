package me.tretyakovv.p3_finalwork.serializer;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.tretyakovv.p3_finalwork.model.Socks;
import nonapi.io.github.classgraph.json.JSONUtils;

import java.io.IOException;

public class SocksKeyDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String key, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(key, Socks.class);
    }
}
