package tech.tablesaw.io.csv;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class CsvWriteOptions {

    private final Writer writer;
    private final boolean header;
    private final char separator;
    private final char quotechar;
    private final char escapechar;
    private final String lineEnd;

    private CsvWriteOptions(Builder builder) {
        this.writer = builder.writer;
        this.header = builder.header;
        this.separator = builder.separator;
        this.quotechar = builder.quoteChar;
        this.escapechar = builder.escapeChar;
        this.lineEnd = builder.lineEnd;
    }

    public Writer writer() {
        return writer;
    }

    public boolean header() {
        return header;
    }

    public char separator() {
        return separator;
    }

    public char escapeChar() {
        return escapechar;
    }

    public char quoteChar() {
        return quotechar;
    }

    public String lineEnd() {
        return lineEnd;
    }

    public static Builder builder(File file) throws IOException {
        return new Builder(file);
    }

    public static Builder builder(String fileName) throws IOException {
        return builder(new File(fileName));
    }

    public static class Builder {

        private Writer writer;
        private boolean header = true;
        private char separator = ',';
        private String lineEnd = System.lineSeparator();
        private char escapeChar = '\\';
        private char quoteChar = '"';

        public Builder(String fileName) throws IOException {
            this(fileName, Charset.defaultCharset());
        }

        public Builder(String fileName, Charset charset) throws IOException {
            File file = Paths.get(fileName).toFile();
            this.writer = new OutputStreamWriter(new FileOutputStream(file), charset);
        }

        public Builder(File file) throws IOException {
            this(file, Charset.defaultCharset());
        }

        public Builder(File file, Charset charset) throws IOException {
            this.writer = new OutputStreamWriter(new FileOutputStream(file), charset);
        }

        public Builder(Writer writer) {
            this.writer = writer;
        }

        public Builder(OutputStream stream) {
            this(stream, Charset.defaultCharset());
        }

        public Builder(OutputStream stream, Charset charset) {
            this.writer = new OutputStreamWriter(stream, charset);
        }

        public CsvWriteOptions.Builder separator(char separator) {
            this.separator = separator;
            return this;
        }

        public CsvWriteOptions.Builder quoteChar(char quoteChar) {
            this.quoteChar = quoteChar;
            return this;
        }

        public CsvWriteOptions.Builder escapeChar(char escapeChar) {
            this.escapeChar = escapeChar;
            return this;
        }

        public CsvWriteOptions.Builder lineEnd(String lineEnd) {
            this.lineEnd = lineEnd;
            return this;
        }

        public CsvWriteOptions.Builder header(boolean header) {
            this.header = header;
            return this;
        }

        public CsvWriteOptions build() {
            return new CsvWriteOptions(this);
        }
    }
}
