package Subject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.timetable.R;

import Subject.Subject;
import java.util.List;
import java.util.Locale;

public class SubjectItemAdapter extends ArrayAdapter<Subject> {
    public SubjectItemAdapter(@NonNull Context context, int resource, @NonNull List<Subject> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_selected,parent,false);
        TextView tvSubject = convertView.findViewById(R.id.tvselected);
        Subject subject = this.getItem(position);

        if(subject != null){
            tvSubject.setText(subject.getTittle());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_spiner,parent,false);
        TextView tvSubject = convertView.findViewById(R.id.itemSubject);
        Subject subject = this.getItem(position);

        if(subject != null){
            tvSubject.setText(subject.getTittle());
        }

        return convertView;
    }
}
