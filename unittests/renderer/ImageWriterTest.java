package renderer;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * The type Image writer test.
 */
public class ImageWriterTest {
    /**
     * Test build image blue and white net.
     */
    @Test
    public void testBuildImage() {
        //colors of Israel flag
        ImageWriter im = new ImageWriter("testImage", 1600, 1000, 800, 500);
        for (int row = 0; row < 500; row++) {
            for (int col = 0; col < 800; col++) {
                if (col % 50 == 0 || row % 50 == 0) {
                    im.writePixel(col, row, Color.WHITE);
                } else {
                    im.writePixel(col, row, Color.BLUE);
                }
            }
        }
        im.writeToImage();

    }

}