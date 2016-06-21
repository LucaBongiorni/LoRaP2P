package mx.dezkareid.lora;

/**
 * Created by DezkaReid on 21/06/16.
 */
public class Weather {

    private String temperature;
    private int photo;

    public Weather(){}

    public Weather(String temperature, int photo){
        this.temperature = temperature;
        this.photo = photo;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
