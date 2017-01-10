package felixgiov.gempabumidanprakiraancuacabmkg.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import felixgiov.gempabumidanprakiraancuacabmkg.R;
import felixgiov.gempabumidanprakiraancuacabmkg.model.Datum;

/**
 * Created by felix on 9 Jan 2017.
 */

public class GempaAdapter extends RecyclerView.Adapter<GempaAdapter.GempaViewHolder> {

    private List<Datum> data;
    private int rowLayout;
    private Context context;

    public static class GempaViewHolder extends RecyclerView.ViewHolder {
        LinearLayout gempaLayout;
        TextView tanggal;
        TextView waktu;
        TextView longlat;
        TextView kedalaman;
        TextView wilayah;
        TextView magnitude;
        ImageView bulet;


        public GempaViewHolder(View v) {
            super(v);
            gempaLayout = (LinearLayout) v.findViewById(R.id.gempa_layout);
            tanggal = (TextView) v.findViewById(R.id.tanggal);
            waktu = (TextView) v.findViewById(R.id.waktu);
            longlat = (TextView) v.findViewById(R.id.longlat);
            kedalaman = (TextView) v.findViewById(R.id.kedalaman);
            wilayah = (TextView) v.findViewById(R.id.wilayah);
            magnitude = (TextView) v.findViewById(R.id.magnitude);
            bulet = (ImageView) v.findViewById(R.id.circle_image);
        }
    }

    public GempaAdapter(List<Datum> data, int rowLayout, Context context) {
        this.data = data;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public GempaAdapter.GempaViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new GempaViewHolder(view);
    }


    @Override
    public void onBindViewHolder(GempaViewHolder holder, final int position) {
        String waktuString = data.get(position).getWaktu();
        String[] separated = waktuString.split(" ");

        String longlatString = data.get(position).getLintangBujur();
        String[] llseparated = longlatString.split(" ");

//        if(position==0) {
//            holder.bulet.setImageResource(R.drawable.ic_circle_shape_outline);
//        }
        holder.tanggal.setText(separated[0]);
        holder.waktu.setText(separated[1]+" "+separated[2]);
        holder.longlat.setText(llseparated[0]+" LS, "+llseparated[1]+llseparated[2]+" BT");
        holder.kedalaman.setText("Kedalaman: "+data.get(position).getKedalaman());
        holder.wilayah.setText(data.get(position).getWilayah());
        holder.magnitude.setText(data.get(position).getMagnitudo()+" Skala Richter");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Datum> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }
}
