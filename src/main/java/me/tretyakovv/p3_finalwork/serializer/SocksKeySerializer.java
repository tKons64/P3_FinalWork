package me.tretyakovv.p3_finalwork.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import me.tretyakovv.p3_finalwork.model.Sock;

import java.io.IOException;

public class SocksKeySerializer  extends StdSerializer<Sock> {

    public SocksKeySerializer() {
        super(Sock.class);
    }

    @Override
    public void serialize(Sock value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        gen.writeFieldName(mapper.writeValueAsString(value));
    }
}
