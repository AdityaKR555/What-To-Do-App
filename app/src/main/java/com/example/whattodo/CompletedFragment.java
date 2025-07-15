package com.example.whattodo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;



public class CompletedFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<NewTaskModel> completedTasks = new ArrayList<>();
    RecyclerAdapter adapter;
    public static ArrayList<NewTaskModel> Tasks = MainActivity.Tasks;


    public CompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        // Step 3: Filter checked tasks
        completedTasks.clear();  // clear old data
        for (NewTaskModel task : Tasks) {
            if (task.isChecked()) {
                completedTasks.add(task);
            }
        }
        adapter = new RecyclerAdapter(getContext(), completedTasks, "Completed");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed, container, false);
    }


}
