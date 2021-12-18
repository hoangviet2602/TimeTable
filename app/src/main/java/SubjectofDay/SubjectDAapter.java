package SubjectofDay;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.timetable.R;
import com.example.timetable.SubjectofDay;

import java.util.ArrayList;

import Day.Day;

public class SubjectDAapter extends BaseAdapter {
    private SubjectofDay mContext;
    private ArrayList<SubjectD> arrayList;
    int layout;

    public SubjectDAapter(SubjectofDay mContext, ArrayList<SubjectD> arrayList, int layout) {
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
        TextView txtTittle,txtstartTime,txtEndTime;
        ImageView img;
        ImageButton btnEdit,btnDelete;

        txtTittle= view.findViewById(R.id.tvSubjectDayDetails);
        txtstartTime =view.findViewById(R.id.startTime);
        txtEndTime = view.findViewById(R.id.endTime);
        img = view.findViewById(R.id.ivDayDetails);
        btnDelete = view.findViewById(R.id.deleteSoD);
        btnEdit = view.findViewById(R.id.editSoD);

        txtTittle.setText(arrayList.get(i).getTittle());
        txtstartTime.setText(arrayList.get(i).getStartTime());
        txtEndTime.setText(arrayList.get(i).getEndTime());
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
