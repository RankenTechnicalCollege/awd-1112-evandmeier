package edu.ranken.emeier.chatterboxlab.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import edu.ranken.emeier.chatterboxlab.R;
import edu.ranken.emeier.chatterboxlab.models.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    // constants
    private final String TAG = "ArticleAdapter";

    // widgets
    private final Context mContext;
    private final List<Message> mMessageList;
    private final LayoutInflater mInflater;

    public MessageAdapter(Context context, List<Message> messageList) {
        mContext = context;
        mMessageList = messageList;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_message, parent, false);

        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        // get properties from current Article
        String user = mMessageList.get(position).getUser() + ":";
        String message = mMessageList.get(position).getMessage();
        String time = "[ " + mMessageList.get(position).getTime() + " ]";

        // set the text of the widgets
        holder.mTime.setText(time);
        holder.mUser.setText(user);
        holder.mMessage.setText(message);
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder  {

        private TextView mUser, mMessage, mTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            mTime = itemView.findViewById(R.id.text_time);
            mUser = itemView.findViewById(R.id.text_user);
            mMessage = itemView.findViewById(R.id.text_message);
        }
    }
}
