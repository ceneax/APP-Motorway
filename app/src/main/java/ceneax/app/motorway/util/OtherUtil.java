package ceneax.app.motorway.util;

public class OtherUtil {

    public static String parseWeather(String skycon) {
        String weather = "未知";

        switch (skycon) {
            case "CLEAR_DAY":
                weather = "晴天";
                break;
            case "CLEAR_NIGHT":
                weather = "晴夜";
                break;
            case "PARTLY_CLOUDY_DAY":
                weather = "多云";
                break;
            case "PARTLY_CLOUDY_NIGHT":
                weather = "多云";
                break;
            case "CLOUDY":
                weather = "阴";
                break;
            case "HEAVY_RAIN":
                weather = "大雨";
                break;
            case "LIGHT_RAIN":
                weather = "大雨";
                break;
            case "MODERATE_RAIN":
                weather = "中雨";
                break;
            case "RAIN":
                weather = "雨";
                break;
            case "SNOW":
                weather = "雪";
                break;
            case "WIND":
                weather = "风";
                break;
            case "HAZE":
                weather = "雾霾沙尘";
                break;
        }

        return weather;
    }

}
