package hello.world;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class OurDataListSerializer extends JsonSerializer<OurDataList> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void serialize(OurDataList ourDataList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        log.info("Starting to serialize");
        jsonGenerator.writeStartArray();
        ourDataList.getOurData()
                .blockingForEach(item -> serializeString(jsonGenerator, item));
        jsonGenerator.writeEndArray();
        log.info("Serialize ended");
    }

    private void serializeString(JsonGenerator jsonGenerator, OurData item) {
        try {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeRaw(item.getData());
            jsonGenerator.writeEndObject();
            jsonGenerator.flush();
        } catch (IOException ex) {
            throw new IllegalStateException("Exception writing", ex);
        }
    }
}
