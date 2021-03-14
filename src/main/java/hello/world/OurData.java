package hello.world;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

//@JsonSerialize(using = OurDataSerializer.class)
public class OurData {
    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "OurData{" +
                "data='" + data + '\'' +
                '}';
    }

    public OurData(String data) {
        this.data = data;
    }

    private final String data;
}
