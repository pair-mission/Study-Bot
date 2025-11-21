package global.utils.parser;

import java.io.BufferedWriter;
import java.io.IOException;

public interface CsvParser<T> {
    T parse(String[] csv);

    void write(T t, BufferedWriter writer, boolean isEmptyFile) throws IOException;

    void update(T t, BufferedWriter writer, String[] csv) throws IOException;
}
