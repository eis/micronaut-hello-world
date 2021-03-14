package hello.world;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.reactivex.Flowable;

@JsonSerialize(using = OurDataListSerializer.class)
public class OurDataList {
    private Flowable<OurData> ourData;
    private String metadata;

    public Flowable<OurData> getOurData() {
        return ourData;
    }

    public void setOurData(Flowable<OurData> ourData) {
        this.ourData = ourData;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

}
