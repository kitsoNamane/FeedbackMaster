package sdk.kitso.feedbackmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    public static List<String> answers;

    public RecycleAdapter(List<String> answers) {
        this.answers = answers;
    }

    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, int position) {
        CheckedTextView answer = holder.answer;
        answer.setText(answers.get(position));
    }

    @Override
    public int getItemCount() {
        return this.answers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public CheckedTextView answer;

        public MyViewHolder(View view) {
            super(view);
            //this.answer = view.findViewById(R.id.answer);
        }
    }
}
