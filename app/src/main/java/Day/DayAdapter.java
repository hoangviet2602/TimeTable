package Day;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.timetable.R;

import java.util.ArrayList;

public class DayAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Day>  arrayList;
    int layout;

    public DayAdapter(Context mContext, ArrayList<Day> arrayList, int layout) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView txtTittle = view.findViewById(R.id.tvday);
        ImageView img = view.findViewById(R.id.imgday);

        txtTittle.setText(arrayList.get(i).getTittle());
        Glide.with(mContext).load(arrayList.get(i).getHinhanh()).into(img);
        return view;
    }
}
