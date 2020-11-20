package com.jisoozz.soosapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jisoozz.soosapp.Model.Chat;
import com.jisoozz.soosapp.R;

import java.util.HashMap;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private Context context;
    private List<Chat> mChat;
    private String imgURL;

    // Firebase

    FirebaseUser fuser;


    //메세지 타입

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;


    //생성자


    public MessageAdapter(Context context, List<Chat> mChat,String imgURL) {
        this.context = context;
        this.mChat = mChat;
        this.imgURL = imgURL;
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == MSG_TYPE_RIGHT){
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right,
                parent,false);
        return new MessageAdapter.ViewHolder(view);}
        else{
          View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left,
                  parent,false);
          return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, final int position) {

        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());

        if(imgURL.equals("default")){
            holder.profile_image.setImageResource(R.drawable.personicon);

        }else{
            Glide.with(context).load(imgURL).into(holder.profile_image);
        }

        holder.time.setText(chat.getTime());

    }




    @Override
    public int getItemCount() {
        return mChat.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message,time;
        public ImageView profile_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            time = itemView.findViewById(R.id.time);

        }
    }


    @Override
    public int getItemViewType(int position) {

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){  //보내는 사람이  fuser 와 같다면

            return MSG_TYPE_RIGHT;

        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
