package sdk.kitso.feedbackmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import sdk.kitso.feedbackmaster.model.MultipleChoiceOption;

public class MyListAdapter extends BaseAdapter {

    private List<MultipleChoiceOption> options;
    private LayoutInflater layoutInflater;

    public MyListAdapter(Context context, List<MultipleChoiceOption> options) {
        this.options = options;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return options.size();
    }
    @Override
    public Object getItem(int position) {
        return options.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.option_item, null);
            holder = new ViewHolder();
            holder.option = v.findViewById(R.id.option_item);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.option.setText(options.get(position).getOption());
        return v;
    }
    static class ViewHolder {
        MaterialButton option;
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
