package by.sapra.newsservice.testUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public interface TestDataBuilder<T> {
    T build();

    default  <U extends TestDataBuilder<W>, W> List<W> getEntityCollection(List<U> builders, Consumer<W> peek) {
        return builders.stream().map(TestDataBuilder::build).peek(peek).collect(Collectors.toList());
    }
}
