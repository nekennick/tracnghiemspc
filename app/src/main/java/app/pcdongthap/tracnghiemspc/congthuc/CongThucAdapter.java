package app.pcdongthap.tracnghiemspc.congthuc;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.pcdongthap.tracnghiemspc.R;

public class CongThucAdapter extends CursorAdapter {
    public CongThucAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    private String searchKeyword; // Biến lưu từ khoá tìm kiếm

    // Constructor và các phương thức khác của adapter

    // Phương thức để tô sáng từ khoá trong danh sách
    public void highlightKeyword(String keyword) {
        searchKeyword = keyword;
        notifyDataSetChanged(); // Cập nhật lại danh sách
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_list_congthuc,parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtCongthuc=(TextView) view.findViewById(R.id.tvCongThuc);
        LinearLayout linCongthuc= (LinearLayout) view.findViewById(R.id.linCongthuc);


        // Tô sáng từ khoá trong view
        TextView textView = view.findViewById(R.id.tvCongThuc); // Thay thế R.id.textView bằng id của view hiển thị từ khoá

        String itemText = cursor.getString(cursor.getColumnIndexOrThrow("congthuc"));
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            int startIndex = itemText.toLowerCase().indexOf(searchKeyword.toLowerCase());
            int endIndex = startIndex + searchKeyword.length();
            if (startIndex >= 0) {
                SpannableString spannableString = new SpannableString(itemText);
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED); // Màu sắc tô sáng (ở đây là màu vàng)
                spannableString.setSpan(foregroundColorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
            } else {
                textView.setText(itemText);
            }
        } else {
            textView.setText(itemText);


            if (cursor.getPosition() % 2 == 0) {
                linCongthuc.setBackgroundColor(Color.parseColor("#FFE2DFDF"));
            } else linCongthuc.setBackgroundColor(Color.parseColor("#ffffff"));
            txtCongthuc.setText(cursor.getString(1));
        }


    }
}

