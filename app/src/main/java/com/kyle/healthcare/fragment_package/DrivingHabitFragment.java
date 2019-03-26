package com.kyle.healthcare.fragment_package;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kyle.healthcare.R;
import com.kyle.healthcare.UIInterface;
import com.kyle.healthcare.controller_data.DataManger;
import com.kyle.healthcare.controller_data.HistoryCondition;

import java.util.ArrayList;
import java.util.List;

public class DrivingHabitFragment extends Fragment {
    private UIInterface uiInterface;
    private ActionBar actionBar;

    private RecyclerView recyclerViewHabitAdvice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.driving_habit_frag, container, false);
        this.recyclerViewHabitAdvice = view.findViewById(R.id.driving_habit_recycler_habit_advice);
        initData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiInterface = (UIInterface) getActivity();
        uiInterface.setTitle(R.string.driving_habit);
        this.linearLayoutManagerH = new LinearLayoutManager(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        uiInterface.setNavigationVisibility(View.GONE);
        actionBar = uiInterface.getBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        uiInterface.setNavigationVisibility(View.VISIBLE);
    }

    private ArrayList<String> habitStrings;
    private ArrayList<String> adviceStrings;
    private LinearLayoutManager linearLayoutManagerH;
    private DrivingHabit drivingHabit;
    //init recycler
    private void initData(){
        this.habitStrings = DataManger.dataManger.getStringHabit();
        this.adviceStrings = DataManger.dataManger.getStringsAdvice();
        this.linearLayoutManagerH.setOrientation(LinearLayoutManager.VERTICAL);
        this.drivingHabit = new DrivingHabit(this.habitStrings,this.adviceStrings);
        this.recyclerViewHabitAdvice.setAdapter(drivingHabit);
        this.recyclerViewHabitAdvice.setLayoutManager(this.linearLayoutManagerH);
    }


    class DrivingHabit extends RecyclerView.Adapter<DrivingHabitFragment.DrivingHabit.ViewHolder>{

        private List<String> strings;
        private List<String> strings1;

        class ViewHolder extends RecyclerView.ViewHolder{

            private TextView text;
            private TextView textView;

            private ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.text = itemView.findViewById(R.id.item_driving_habit_text);
                this.textView = itemView.findViewById(R.id.item_driving_habit_advice);
            }
        }

        private DrivingHabit(List<String> strings,List<String> strings1) {
            super();
            this.strings = strings;
            this.strings1 = strings1;
        }

        @NonNull
        @Override
        public DrivingHabitFragment.DrivingHabit.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_driving_habit,viewGroup,false);
            DrivingHabitFragment.DrivingHabit.ViewHolder viewHolder = new DrivingHabitFragment.DrivingHabit.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(DrivingHabitFragment.DrivingHabit.ViewHolder viewHolder, int i) {
            viewHolder.text.setText(this.strings.get(i));
            viewHolder.textView.setText(this.strings1.get(i));
        }

        @Override
        public int getItemCount() {
            return this.strings.size();
        }
    }

    public void updateRecyclerView(){
        this.drivingHabit.notifyDataSetChanged();
    }
}
