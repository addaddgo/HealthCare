package com.kyle.healthcare.fragment_package;

import android.os.Bundle;
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
import com.kyle.healthcare.controller_data.HistoryCondition;

import java.util.ArrayList;
import java.util.List;

public class DrivingHabitFragment extends Fragment {
    private UIInterface uiInterface;
    private ActionBar actionBar;

    private RecyclerView recyclerViewHabit;
    private RecyclerView recyclerViewAdvice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.driving_habit_frag, container, false);
        this.recyclerViewHabit = view.findViewById(R.id.driving_habit_recycler_habit);
        this.recyclerViewAdvice = view.findViewById(R.id.driving_habit_recycler_advice);
        initData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiInterface = (UIInterface) getActivity();
        uiInterface.setTitle(R.string.driving_habit);
        this.habitStrings = new ArrayList<>();
        this.adviceStrings = new ArrayList<>();
        this.linearLayoutManagerH = new LinearLayoutManager(getContext());
        this.linearLayoutManagerA = new LinearLayoutManager(getContext());
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

    private List<String> habitStrings;
    private List<String> adviceStrings;
    private LinearLayoutManager linearLayoutManagerH;
    private LinearLayoutManager linearLayoutManagerA;
    //init recycler
    private void initData(){
        this.habitStrings.add("开车不良习惯");
        this.habitStrings.add("开车不良习惯");
        this.habitStrings.add("开车不良习惯");
        this.habitStrings.add("开车不良习惯");
        this.habitStrings.add("开车不良习惯");
        this.habitStrings.add("开车不良习惯");
        this.habitStrings.add("开车不良习惯");
        this.habitStrings.add("开车不良习惯");
        this.adviceStrings.add("我建议你好好开车");
        this.adviceStrings.add("我建议你好好开车");
        this.adviceStrings.add("我建议你好好开车");
        this.adviceStrings.add("我建议你好好开车");
        this.adviceStrings.add("我建议你好好开车");
        this.adviceStrings.add("我建议你好好开车");
        this.adviceStrings.add("我建议你好好开车");
        this.adviceStrings.add("我建议你好好开车");
        this.adviceStrings.add("我建议你好好开车");
        this.linearLayoutManagerH.setOrientation(LinearLayoutManager.VERTICAL);
        this.linearLayoutManagerA.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerViewHabit.setAdapter(new DrivingHabit(this.habitStrings,R.layout.item_driving_habit,R.id.driving_habit_text));
        this.recyclerViewHabit.setLayoutManager(this.linearLayoutManagerH);
        this.recyclerViewAdvice.setAdapter(new DrivingHabit(this.adviceStrings,R.layout.item_driving_advice,R.id.driving_habit_advice_text));
        this.recyclerViewAdvice.setLayoutManager(this.linearLayoutManagerA);
    }
    class DrivingHabit extends RecyclerView.Adapter<DrivingHabitFragment.DrivingHabit.ViewHolder>{

        private List<String> strings;
        private int layoutId;
        private int textId;

        class ViewHolder extends RecyclerView.ViewHolder{

            private TextView text;

            private ViewHolder(@NonNull View itemView,int textId) {
                super(itemView);
                this.text = itemView.findViewById(textId);;
            }
        }

        private DrivingHabit(List<String> strings,int layoutId,int textId) {
            super();
            this.strings = strings;
            this.layoutId = layoutId;
            this.textId = textId;
        }

        @NonNull
        @Override
        public DrivingHabitFragment.DrivingHabit.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId,viewGroup,false);
            DrivingHabitFragment.DrivingHabit.ViewHolder viewHolder = new DrivingHabitFragment.DrivingHabit.ViewHolder(view,this.textId);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(DrivingHabitFragment.DrivingHabit.ViewHolder viewHolder, int i) {
            viewHolder.text.setText(this.strings.get(i));
        }

        @Override
        public int getItemCount() {
            return this.strings.size();
        }
    }
}
