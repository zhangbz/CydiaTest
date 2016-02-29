package com.example.janiszhang.cydiatest;

import com.saurik.substrate.MS;

import java.lang.reflect.Method;

/**
 * Created by janiszhang on 2016/2/29.
 */
public class CydiaEntry {
    static void initialize() {
        MS.hookClassLoad("android.content.res.Resources", new MS.ClassLoadHook() {
            @Override
            public void classLoaded(Class<?> resouces) {
                Method getColor;
                try {
                    getColor = resouces.getDeclaredMethod("getColor", Integer.TYPE);
                } catch (NoSuchMethodException e) {
                    getColor = null;
                    e.printStackTrace();
                }

                if (getColor != null) {
                    final MS.MethodPointer old = new MS.MethodPointer();
                    MS.hookMethod(resouces, getColor, new MS.MethodHook() {
                        @Override
                        public Object invoked(Object o, Object... objects) throws Throwable {
                            return (int)old.invoke(o, objects) & ~0x0000ff00 | 0x00ff0000;
                        }
                    },old);
                }
            }
        });
    }
}
