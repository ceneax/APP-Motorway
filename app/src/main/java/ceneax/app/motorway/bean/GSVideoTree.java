package ceneax.app.motorway.bean;

import ceneax.app.motorway.base.BaseBean;

public class GSVideoTree extends BaseBean {

    private String id;
    private String subTypeId;
    private String online;
    private String longitude;
    private String latitude;
    private String mapLevelStart;
    private String mapLevelEnd;
    private int isNewVideo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(String subTypeId) {
        this.subTypeId = subTypeId;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMapLevelStart() {
        return mapLevelStart;
    }

    public void setMapLevelStart(String mapLevelStart) {
        this.mapLevelStart = mapLevelStart;
    }

    public String getMapLevelEnd() {
        return mapLevelEnd;
    }

    public void setMapLevelEnd(String mapLevelEnd) {
        this.mapLevelEnd = mapLevelEnd;
    }

    public int getIsNewVideo() {
        return isNewVideo;
    }

    public void setIsNewVideo(int isNewVideo) {
        this.isNewVideo = isNewVideo;
    }

}
