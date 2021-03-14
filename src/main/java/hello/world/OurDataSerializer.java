package hello.world;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class OurDataSerializer extends JsonSerializer<OurData> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    boolean first = true;
    @Override
    public void serialize(OurData o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (first) {
            jsonGenerator.writeRaw("\"list\": [");
            first = false;
        }
        jsonGenerator.writeRaw('"');
        jsonGenerator.writeRaw(o.getData());
        jsonGenerator.writeRaw('"');
    }
}
