package me.tretyakovv.p3_finalwork.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import me.tretyakovv.p3_finalwork.model.Socks;

import java.io.IOException;

public class SocksKeySerializer  extends StdSerializer<Socks> {

    public SocksKeySerializer() {
        super(Socks.class);
    }

    @Override
    public void serialize(Socks value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        gen.writeFieldName(mapper.writeValueAsString(value));
    }
}
