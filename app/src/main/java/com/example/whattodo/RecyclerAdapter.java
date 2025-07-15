package com.example.whattodo;

import static java.security.AccessController.getContext;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    String sourceFragment;
    Context context;
    ArrayList<NewTaskModel> Tasks;



    public RecyclerAdapter(Context context, ArrayList<NewTaskModel> Tasks, String sourceFragment){
        this.context = context;
        this.Tasks = Tasks;
        this.sourceFragment = sourceFragment;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (sourceFragment.equals("All")) {
            view = LayoutInflater.from(context).inflate(R.layout.customrows, parent, false);
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.customrows_completed, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

        NewTaskModel task = Tasks.get(position);
        holder.title.setText(task.getTitle());
        holder.content.setText(task.getContent());


        //setting for updation
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.updatetask_dialog);
                Button save = dialog.findViewById(R.id.btnSave);
                EditText edtTitle = dialog.findViewById(R.id.edtTitle);
                EditText edtContent = dialog.findViewById(R.id.edtContent);
                TextView tvTitle = dialog.findViewById(R.id.tvTitle);
                TextView tvContent = dialog.findViewById(R.id.tvContent);


                tvTitle.setText(task.getTitle());
                tvContent.setText(task.getContent());
                dialog.show();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Edit Mode: When TextViews are visible
                        if (tvTitle.getVisibility() == View.VISIBLE) {
                            // Switch to edit mode
                            tvTitle.setVisibility(View.GONE);
                            tvContent.setVisibility(View.GONE);
                            edtTitle.setVisibility(View.VISIBLE);
                            edtContent.setVisibility(View.VISIBLE);
                            save.setText("Save");

                            // Fill EditTexts with current text
                            edtTitle.setText(tvTitle.getText().toString());
                            edtContent.setText(tvContent.getText().toString());
                        }
                        // Save Mode: When EditTexts are visible
                        else {
                            String title = edtTitle.getText().toString().trim();
                            String content = edtContent.getText().toString().trim();

                            if (title.isEmpty() && content.isEmpty()) {
                                Toast.makeText(context, "Enter at least Title or Content", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Update UI and model
                            if (title.isEmpty()) {
                                title = "Untitled";
                            }

                            task.setTitle(title);
                            task.setContent(content);

                            // Update TextViews with new content
                            tvTitle.setText(title);
                            tvContent.setText(content);

                            // Switch back to view mode
                            edtTitle.setVisibility(View.GONE);
                            edtContent.setVisibility(View.GONE);
                            tvTitle.setVisibility(View.VISIBLE);
                            tvContent.setVisibility(View.VISIBLE);
                            save.setText("Edit");

                            notifyItemChanged(holder.getAdapterPosition());
                            dialog.dismiss();
                        }
                    }
                });



            }
        });

        if (holder.checkBox != null) {
            holder.checkBox.setOnCheckedChangeListener(null); // reset listener
            holder.checkBox.setChecked(task.isChecked()); // set current checked state

            // 🔹 Apply or remove strike-through based on isChecked
            if (task.isChecked()) {
                holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.content.setPaintFlags(holder.content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.title.setPaintFlags(holder.title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.content.setPaintFlags(holder.content.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                task.setChecked(isChecked);
                notifyItemChanged(holder.getAdapterPosition());

                if (isChecked) {
                    holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.content.setPaintFlags(holder.content.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.title.setPaintFlags(holder.title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    holder.content.setPaintFlags(holder.content.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            });
        }

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    NewTaskModel taskToDelete = Tasks.get(currentPosition);
                    taskToDelete.setChecked(false);
                    Tasks.remove(currentPosition);
                    if(sourceFragment.equals("Completed")) { AllFragment.Tasks.remove(taskToDelete); }
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, Tasks.size());

                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return Tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;
        CheckBox checkBox;
        ImageButton deletebtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            deletebtn = itemView.findViewById(R.id.deletebtn);
            try {
                checkBox = itemView.findViewById(R.id.checkbox);
            }
            catch (Exception e){
                checkBox = null;
            }
        }
    }
}
