package mx.dezkareid.lora;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DezkaReid on 21/06/16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.WeatherViewHolder> {

    List<Weather> weathers;

    RVAdapter(List<Weather> weathers){
        this.weathers = weathers;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.temperature_item, parent, false);
        WeatherViewHolder wvh = new WeatherViewHolder(v);
        return wvh;

    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        holder.weatherTemperature.setText(weathers.get(position).getTemperature());
        holder.weatherPhoto.setImageResource(weathers.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView weatherTemperature;
        ImageView weatherPhoto;

        WeatherViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            weatherTemperature = (TextView)itemView.findViewById(R.id.weather_temperature);
            weatherPhoto = (ImageView)itemView.findViewById(R.id.weather_photo);
        }
    }
}
