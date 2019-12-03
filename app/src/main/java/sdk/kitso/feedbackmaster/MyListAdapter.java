package sdk.kitso.feedbackmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {

    private ArrayList<String> answers;
    private LayoutInflater layoutInflater;

    public MyListAdapter(Context context, ArrayList<String> answers) {
        this.answers = answers;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return answers.size();
    }
    @Override
    public Object getItem(int position) {
        return answers.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.answer, null);
            holder = new ViewHolder();
            holder.answer = v.findViewById(R.id.answer_item);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.answer.setText(answers.get(position));
        return v;
    }
    static class ViewHolder {
        CheckedTextView answer;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
}
}
