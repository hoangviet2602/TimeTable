package Subject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.timetable.R;
import com.example.timetable.SubjectActivity;

import java.util.ArrayList;

import SubjectofDay.SubjectD;

public class SubjectAdapter extends BaseAdapter {
    private SubjectActivity mContext;
    private ArrayList<Subject> arrayList;
    int layout;

    public SubjectAdapter(SubjectActivity mContext, ArrayList<Subject> arrayList, int layout) {
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
        btnDelete = view.findViewById(R.id.delete);
        btnEdit = view.findViewById(R.id.edit);

        txtTittle.setText(arrayList.get(i).getTittle());
        Glide.with(mContext).load(arrayList.get(i).getHinhanh()).into(img);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.Update(i);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.Delete(i);
            }
        });
         return view;
    }
}
