package Subject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.timetable.R;
import com.example.timetable.SubjectActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import SubjectofDay.SubjectD;

public class SubjectAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private ArrayList<Subject> arrayList;
    private ArrayList<Subject> arrayListOld;
    int layout;

    public SubjectAdapter(Context mContext, ArrayList<Subject> arrayList, int layout) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.arrayListOld = arrayList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView txtTittle;
        ImageView img;
        ImageButton btnEdit,btnDelete;

        txtTittle= view.findViewById(R.id.tvSubject);
        img = view.findViewById(R.id.ivSubject);


        txtTittle.setText(arrayList.get(i).getTittle());
        Glide.with(mContext).load(arrayList.get(i).getHinhanh()).into(img);


         return view;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    arrayList = arrayListOld;
                }else{
                    ArrayList<Subject> list = new ArrayList<>();
                    for(Subject subject : arrayListOld){
                        if(subject.getTittle().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(subject);
                        }
                    }
                    arrayList = list;
                }
                FilterResults results = new FilterResults();
                results.values = arrayList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrayList = (ArrayList<Subject>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
