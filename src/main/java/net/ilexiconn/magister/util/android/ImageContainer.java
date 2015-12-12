/*
 * Copyright (c) 2015 iLexiconn
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package net.ilexiconn.magister.util.android;

import net.ilexiconn.magister.util.AndroidUtil;
import net.ilexiconn.magister.util.LogUtil;

import java.io.InputStream;
import java.lang.reflect.Method;

public class ImageContainer {
    private Class classT;
    private Class classImage;
    private Class classBitmap;
    private Object image;

    public ImageContainer(InputStream stream) throws ClassNotFoundException {
        Class c;
        if (AndroidUtil.getRunningOnAndroid()) {
            c = Class.forName("android.graphics.Bitmap");
        } else {
            c = Class.forName("java.awt.image.BufferedImage");
        }
        if (c == null) {
            throw new NullPointerException();
        }
        this.classT = c;
        try {
            classBitmap = Class.forName("android.graphics.Bitmap");
            if (!classT.equals(classBitmap)) {
                throw new Exception();
            }
            Method m = Class.forName("android.graphics.BitmapFactory").getMethod("decodeStream", InputStream.class);
            image = classT.cast(m.invoke(null, stream));
            return;
        } catch (Exception e) {
            if (!(e instanceof ClassNotFoundException)) {
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
            if (!(e instanceof ClassNotFoundException)) {
                LogUtil.printError(e.getMessage(), e.getCause());
            }
        }
        throw new ClassNotFoundException("Neither the BufferedImage class nor the Bitmap class were found!");
    }

    /**
     * Get the image object. You can check its type bu using {@link ImageContainer#isBitmap()} and
     * {@link ImageContainer#isBufferedImage()}.
     *
     * @return the image object.
     */
    public Object getImage() {
        return image;
    }

    /**
     * Get the class of the image object.
     *
     * @return the class of the image object.
     */
    public Class getImageClass() {
        return classT;
    }

    public boolean isBitmap() {
        return getImageClass().equals(classBitmap);
    }

    public boolean isBufferedImage() {
        return getImageClass().equals(classImage);
    }
}
