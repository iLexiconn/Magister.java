package net.ilexiconn.magister.util.android;


import java.io.InputStream;

public class ImageWrapper {
    ImageContainer container;

    public ImageWrapper(InputStream stream) throws ClassNotFoundException {
        if (AndroidUtil.getRunningOnAndroid()) {
            Class c = Class.forName("android.graphics.Bitmap");
            container = new ImageContainer(c, stream);
        } else {
            Class c = Class.forName("java.awt.image.BufferedImage");
            container = new ImageContainer(c, stream);
        }
    }

    public ImageContainer getImageContainer() {
        return container;
    }


}
