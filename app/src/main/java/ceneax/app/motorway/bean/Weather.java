package ceneax.app.motorway.bean;

import com.google.gson.annotations.SerializedName;

import ceneax.app.motorway.base.BaseBean;

public class Weather extends BaseBean {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result extends BaseBean {
        @SerializedName("forecast_keypoint")
        private String forecastKeypoint;
        private Realtime realtime;

        public String getForecastKeypoint() {
            return forecastKeypoint;
        }

        public void setForecastKeypoint(String forecastKeypoint) {
            this.forecastKeypoint = forecastKeypoint;
        }

        public Realtime getRealtime() {
            return realtime;
        }

        public void setRealtime(Realtime realtime) {
            this.realtime = realtime;
        }

        public class Realtime extends BaseBean {
            private String skycon;
            private double temperature;
            private double visibility;
            private Wind wind;

            public String getSkycon() {
                return skycon;
            }

            public void setSkycon(String skycon) {
                this.skycon = skycon;
            }

            public double getTemperature() {
                return temperature;
            }

            public void setTemperature(double temperature) {
                this.temperature = temperature;
            }

            public double getVisibility() {
                return visibility;
            }

            public void setVisibility(double visibility) {
                this.visibility = visibility;
            }

            public Wind getWind() {
                return wind;
            }

            public void setWind(Wind wind) {
                this.wind = wind;
            }

            public class Wind extends BaseBean {
                private double speed;

                public double getSpeed() {
                    return speed;
                }

                public void setSpeed(double speed) {
                    this.speed = speed;
                }
            }
        }
    }

}
