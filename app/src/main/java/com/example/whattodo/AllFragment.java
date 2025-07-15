package com.example.whattodo;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class AllFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView emptyimage;
    //    ArrayList<NewTaskModel> Tasks = new ArrayList<>();
    public static ArrayList<NewTaskModel> Tasks = MainActivity.Tasks;

    FloatingActionButton actionButton;

    public AllFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actionButton = view.findViewById(R.id.actionButton);
        recyclerView = view.findViewById(R.id.recyclerView);
        emptyimage = view.findViewById(R.id.emptyImage);

        if (Tasks.isEmpty()) {
            emptyimage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyimage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), Tasks, "All");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.addtasks_dialog);
                EditText edtTitle = dialog.findViewById(R.id.edtTitle);
                EditText edtContent = dialog.findViewById(R.id.edtContent);
                Button save = dialog.findViewById(R.id.btnSave);
                dialog.show();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = edtTitle.getText().toString().trim();
                        String content = edtContent.getText().toString().trim();
                        if(title.isEmpty() && content.isEmpty()){
                            Toast.makeText(getContext(), "Enter atleast Title or Content", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (title.isEmpty()) {
                            title = "Untitled";
                        }

                        Tasks.add(new NewTaskModel(title, content));
                        adapter.notifyDataSetChanged();

                        if (Tasks.isEmpty()) {
                            emptyimage.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            emptyimage.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        edtTitle.setText("");
                        edtContent.setText("");
                        dialog.dismiss();
                    }
                });

            }
        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        return view;
    }
}