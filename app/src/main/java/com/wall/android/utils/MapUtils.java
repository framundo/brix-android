package com.wall.android.utils;

import android.util.Log;

import com.wall.android.instances.CoordUv;
import com.wall.android.instances.UserPixel;

public class MapUtils {

    private static final String MAP_LOG = "MapUtils";

    public static CoordUv map(int width, int height, UserPixel userPixel) {
        float onePixelX = 1.f / (float) width;
        float onePixelY = 1.f / (float) height;

        Log.d(MAP_LOG, "OnePixel X " + onePixelX);
        Log.d(MAP_LOG, "OnePixel Y " + onePixelY);

        float valueX = (onePixelX * userPixel.getX()) - (onePixelX * 0.5f);
        float valueY = (onePixelY * userPixel.getY()) - (onePixelY * 0.5f);

        Log.d(MAP_LOG, "UV X " + valueX);
        Log.d(MAP_LOG, "UV Y " + valueY);

        return new CoordUv(valueX, valueY);
    }

}
