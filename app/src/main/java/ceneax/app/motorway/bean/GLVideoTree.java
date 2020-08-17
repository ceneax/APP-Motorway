package ceneax.app.motorway.bean;

import ceneax.app.motorway.base.BaseBean;

public class GLVideoTree extends BaseBean {

    private String mapLevelStart;
    private String cameraId;
    private double latitude;
    private String cameraOnline;
    private String cameraName;
    private double longitude;

    public String getMapLevelStart() {
        return mapLevelStart;
    }

    public void setMapLevelStart(String mapLevelStart) {
        this.mapLevelStart = mapLevelStart;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCameraOnline() {
        return cameraOnline;
    }

    public void setCameraOnline(String cameraOnline) {
        this.cameraOnline = cameraOnline;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
