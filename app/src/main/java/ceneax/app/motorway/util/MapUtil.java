package ceneax.app.motorway.util;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

import ceneax.app.motorway.bean.ProvinceInfo;

public class MapUtil {

    /**
     * 切换地图中心坐标，更改地图缩放级别
     * @param aMap
     * @param provinceInfo
     */
    public static void changeProvince(AMap aMap, ProvinceInfo provinceInfo) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(provinceInfo.getLat(), provinceInfo.getLng()), provinceInfo.getScale(), 0, 0)));
    }

    /**
     * 显示或隐藏指定类型的marker
     * @param aMap
     * @param clazz
     * @param show
     */
    public static void showOrHideTypeMarker(AMap aMap, Class<?> clazz, boolean show) {
        for (Marker marker : aMap.getMapScreenMarkers()) {
            if(marker.getObject().getClass().getName().equals(clazz.getName())) {
                marker.setVisible(show);
            }
        }
    }

    /**
     * 清除所有marker
     * @param aMap
     */
    public static void clearAllMarkers(AMap aMap) {
        aMap.clear();
    }

}
