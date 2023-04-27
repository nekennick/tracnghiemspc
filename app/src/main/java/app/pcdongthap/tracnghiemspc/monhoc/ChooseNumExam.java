package app.pcdongthap.tracnghiemspc.monhoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.pcdongthap.tracnghiemspc.R;

public class ChooseNumExam extends BaseAdapter {

    ArrayList lsData;
    LayoutInflater inflater;

    public ChooseNumExam(ArrayList lsData, Context context) {
        this.lsData = lsData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return lsData.size();
    }

    @Override
    public Object getItem(int position) {
        return lsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_num_exam, null);
            holder.tvNumAns = convertView.findViewById(R.id.tv_Num_Exam);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int i = position + 2;
        holder.tvNumAns.setText("Đề " + i);
       // holder.tvYourAns.setText(data.getTraloi());
        return convertView;


    }

    private static class ViewHolder {
        TextView tvNumAns;
    }
}