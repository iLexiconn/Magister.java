package net.ilexiconn.magister.util;

import java.lang.reflect.InvocationTargetException;

public class AndroidUtil {
    private static boolean runningOnAndroid = false;
    private static boolean androidSupportCache = false;

    public static boolean isRunningOnAndroid(){
        try{
            Class.forName("android.view.View");
            runningOnAndroid = true;
            isCacheAvailableOnAndroid();
        }catch (ClassNotFoundException e){
            runningOnAndroid = false;
        }
        return runningOnAndroid;
    }

    public static boolean isCacheAvailableOnAndroid(){
        try{
            Class cache = Class.forName("android.net.http.HttpResponseCache");
            cache.getMethod("getInstalled").invoke(null);
            androidSupportCache = true;
        }catch (ClassNotFoundException e){
            LogUtil.printError("Could not find Class: android.net.http.HttpResponseCache", e.getCause());
            androidSupportCache = false;
        }catch (NoSuchMethodException e){
            LogUtil.printError("Could not find Method: getInstalled", e.getCause());
            androidSupportCache = false;
        }catch (IllegalAccessException e){
            LogUtil.printError("Could not access Method: getInstalled", e.getCause());
            androidSupportCache = false;
        }catch (InvocationTargetException e){
            LogUtil.printError("Failed to invoke Method: getInstalled", e.getCause());
            androidSupportCache = false;
        }
        return androidSupportCache;
    }

    public static boolean getRunningOnAndroid(){
        return runningOnAndroid;
    }

    public static boolean getAndroidSupportCache(){
        return androidSupportCache;
    }
}
