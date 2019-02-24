package tech.tablesaw.io.csv;

import org.junit.Test;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class CsvWriteOptionsTest {

    @Test
    public void testSettingsPropagation() {

        Table test = Table.create("test", StringColumn.create("t"));
        test.stringColumn(0).appendCell("testing");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        CsvWriteOptions options = new CsvWriteOptions.Builder(stream)
                .escapeChar('~')
                .header(true)
                .lineEnd("\r\n")
                .quoteChar('"')
                .separator('.')
                .build();
        assertEquals('~', options.escapeChar());
        assertTrue(options.header());
        assertEquals('"', options.quoteChar());
        assertEquals('.', options.separator());

        CsvWriter writer = new CsvWriter(test, options);
        assertEquals('~', writer.getEscapeChar());
        assertTrue(writer.getHeader());
        assertEquals("\r\n", writer.getLineEnd());
        assertEquals('"', writer.getQuoteCharacter());
        assertEquals('.', writer.getSeparator());
    }

    @Test
    public void testCharset() throws Exception {
        Table table = Table.create("test",
                StringColumn.create("何行目", new String[]{"一行目", "二行目"}),
                StringColumn.create("名字", new String[]{"田中", "佐藤"}));

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        CsvWriteOptions options = new CsvWriteOptions.Builder(stream, Charset.forName("Windows-31J")).build();
        new CsvWriter(table, options).write();

        Table tableReaded = Table.read().csv(CsvReadOptions
                .builder(new ByteArrayInputStream(stream.toByteArray()), Charset.forName("Windows-31J"), "test"));

        assertEquals(2, tableReaded.rowCount());

        // Look at the column names
        assertEquals("[何行目, 名字]", tableReaded.columnNames().toString());
    }
}
