package hello.world;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.core.annotation.Blocking;
import io.micronaut.core.annotation.NonBlocking;
import io.micronaut.core.io.Writable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller("/hello")
public class HelloController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Get(produces = MediaType.TEXT_PLAIN)
    public String index() {
        return "Hello World";
    }

    @Get(produces = MediaType.APPLICATION_JSON, value = "/json")
    public Flowable<OurData> getJson() {
        return Flowable.fromArray(1,2,3)
                .map(item -> new OurData("number " + item));
    }

    Flowable<String> f = Flowable.generate(() -> new AtomicInteger(0),
            (integer, output) -> {
                if (integer.addAndGet(1) < 50_000_000) {
                    output.onNext("new number: " + integer);
                } else {
                    output.onComplete();
                }
            });

    public HelloController() {
        f = f.doOnSubscribe((s) -> log.info("started"))
                .doOnComplete(() -> log.info("finished"));
    }

    @Get(produces = MediaType.APPLICATION_JSON, value = "/json2")
    public Flowable<OurData> getJson2Streaming() {
        return f.map(OurData::new);
    }

    @Get(produces = MediaType.APPLICATION_JSON, value = "/json3")
    public Single<List<OurData>> getJson3Blocking() {
        return f.map(OurData::new)
                .collect(ArrayList::new, List::add);
    }
    @Get(produces = MediaType.APPLICATION_JSON, value = "/json4")
    public OurDataList getJson4Blocking() {
        OurDataList list = new OurDataList();
        list.setOurData(f.map(OurData::new));
        return list;
    }

    @Get(produces = MediaType.APPLICATION_JSON, value = "/json5")
    @Blocking
    public Writable getJson5Blocking() {
        OurDataList list = new OurDataList();
        list.setOurData(f.map(OurData::new));
        return (writer -> {
            writer.write("{ \"list\": [");
            list.getOurData().blockingForEach(item -> {
                writer.write('"');
                writer.write(item.getData());
                writer.write('"');
                writer.flush();
            });
            writer.write("]}");
        });
    }

    @Get(produces = MediaType.APPLICATION_JSON, value = "/json6")
    public Flowable<String> getJson6Chunks() {
        ObjectMapper objectMapper = new ObjectMapper();
        return f.map(OurData::new)
                .concatMap(item ->
                        Flowable.just(objectMapper.writeValueAsString(item), ","))
                .startWith("{\"list\": [")
                .concatWith(Flowable.just("],\"meta\":\"whatever\"}"));
    }

    @Get(produces = MediaType.APPLICATION_JSON, value = "/json7")
    public Flowable<OurDataList> getJson7() {
        OurDataList list = new OurDataList();
        list.setOurData(f.map(OurData::new));
        return Flowable.just(list);
    }
    @Get(value = "/json8")
    public Writable getJson8() {
        return writer -> {
            for (int i = 0; i < 500_000_000; i++) {
                writer.write("Hello");
                if (i % 5_000_000 == 0) {
                    log.info("requesting flush");
                    writer.flush();
                }
            }
        };
    }
}
