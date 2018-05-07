import org.apache.log4j.Logger;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;


/**
 * Created by nirbo on 11/30/2015.
 */
public class ConvertorTest {

    private static final Logger _logger = Logger.getLogger(ConvertorTest.class);

    @Test
    public void HexStringToByteTest() {
        String input = "26";
        //String input = "FAFAFA01";
        byte[] s = DatatypeConverter.parseHexBinary(input);
        String s1 = Arrays.toString(s);
        String s2 = DatatypeConverter.printHexBinary(s);
        _logger.info(s2 + "-------------" + s1 + "------" + s);
    }




    /*
    * Read String from InputStream and closes it
    */
    private String streamToString(java.io.InputStream is, Charset encoding) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
        StringBuilder sb = new StringBuilder();
        try {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
        } catch (IOException io) {
            _logger.info("Failed to read from Stream");
            io.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException ioex) {
                _logger.error("Failed to close Streams");
                ioex.printStackTrace();
            }
        }
        return sb.toString();
    }
}
