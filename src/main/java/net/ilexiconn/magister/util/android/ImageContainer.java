package net.ilexiconn.magister.util.android;

import net.ilexiconn.magister.util.LogUtil;

import java.io.InputStream;
import java.lang.reflect.Method;

public class ImageContainer {
    private Class classT;
    private Class classImage;
    private Class classBitmap;
    private Object image;

    public ImageContainer(Class classT, InputStream stream) throws ClassNotFoundException {
        if (classT == null) {
            throw new NullPointerException();
        }
        this.classT = classT;
        try {
            classBitmap = Class.forName("android.graphics.Bitmap");
            if (!classT.equals(classBitmap)) {
                throw new Exception();
            }
            Method m = Class.forName("android.graphics.BitmapFactory").getMethod("decodeStream", InputStream.class);
            image = classT.cast(m.invoke(null, stream));
            return;
        } catch (Exception e) {
            if(!(e instanceof  ClassNotFoundException)){
                LogUtil.printError(e.getMessage(), e.getCause());
            }
        }
        try {
            classImage = Class.forName("java.awt.image.BufferedImage");
            if (!classT.equals(classImage)) {
                throw new Exception();
            }
            Method m = Class.forName("javax.imageio.ImageIO").getMethod("read", InputStream.class);
            image = classT.cast(m.invoke(null, stream));
            return;
        } catch (Exception e) {
            if(!(e instanceof  ClassNotFoundException)){
                LogUtil.printError(e.getMessage(), e.getCause());
            }
        }
        throw new ClassNotFoundException("Neither the BufferedImage class nor the Bitmap class were found!");
    }

    public Object getImage() {
        return image;
    }

    public Class getImageClass() {
        return classT;
    }

    public boolean isBitmap() {
        return getImageClass().equals(classBitmap);
    }

    public boolean isBufferImage() {
        return getImageClass().equals(classImage);
    }

}
