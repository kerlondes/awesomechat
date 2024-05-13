package com.example.awesomechat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class AwessomeMessageAdapter extends ArrayAdapter<AwessomeMessage> {

    private List<AwessomeMessage> messages;
    private Activity activity;
    public AwessomeMessageAdapter(Activity context, int resource, List<AwessomeMessage> messages) {
        super(context, resource, messages);

        this.messages = messages;
        this.activity = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        LayoutInflater layoutInflater =
                (LayoutInflater) activity.getSystemService(
                        Activity.LAYOUT_INFLATER_SERVICE);

        AwessomeMessage awessomeMessage = getItem(position);
        int layoutResource = 0;
        int viewType = getItemViewType(position);

        if (viewType == 0) {
            layoutResource = R.layout.my_messager_item;
        }else{
            layoutResource = R.layout.your_messager_item;
        }

        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(
                    layoutResource,parent, false
            );
            viewHolder = new  ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        boolean isText = awessomeMessage.getImageUrl() == null;
        if (isText){
            viewHolder.messageTextView.setVisibility(View.VISIBLE);
            viewHolder.photoImageView.setVisibility(View.GONE);
            viewHolder.messageTextView.setText(awessomeMessage.getText());
        }else{
            viewHolder.messageTextView.setVisibility(View.GONE);
            viewHolder.photoImageView.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.photoImageView.getContext())
                    .load(awessomeMessage.getImageUrl())
                    .into(viewHolder.photoImageView);
        }

        return convertView;
    }

    @Nullable
    @Override
    public int getItemViewType(int position) {
        int flag;
        AwessomeMessage awessomeMessage = messages.get(position);
        if (awessomeMessage.isMine()){
            flag = 0;
        }else{
            flag = 1;
        }
        return flag;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private class ViewHolder {

        private ImageView photoImageView;
        private TextView messageTextView;
        public ViewHolder(View view){
            photoImageView = view.findViewById(R.id.photoImageView);
            messageTextView = view.findViewById(R.id.messageTextView);
        }

    }
}
