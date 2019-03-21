package com.kyle.healthcare.fragment_package;

import android.os.Bundle;
import android.service.autofill.FillEventHistory;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HistoryLogFragment extends Fragment {

    private UIInterface uiInterface;
    private ActionBar actionBar;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiInterface = (UIInterface) getActivity();
        uiInterface.setTitle(R.string.settings);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_log_frag, container, false);
        this.recyclerView = view.findViewById(R.id.history_log_recycler);
        intData();
        return view;
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

    //数据测试
    private List<HistoryCondition> conditions;
    private HistoryLog historyLog;
    private LinearLayoutManager linearLayoutManager;

    private void intData(){
        this.conditions = new ArrayList<>();
        this.conditions.add(new HistoryCondition("2000/12/12","撞车"));
        this.conditions.add(new HistoryCondition("2000/12/12","撞车"));
        this.conditions.add(new HistoryCondition("2000/12/12","撞车"));
        this.conditions.add(new HistoryCondition("2000/12/12","撞车"));
        historyLog = new HistoryLog(conditions);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setAdapter(historyLog);
    }

    //init recycler
    class HistoryLog extends RecyclerView.Adapter<HistoryLog.ViewHolder>{

        private List<HistoryCondition> historyConditions;

       class ViewHolder extends RecyclerView.ViewHolder{

           private TextView date;
           private TextView situation;

           public ViewHolder(@NonNull View itemView) {
               super(itemView);
               this.date = itemView.findViewById(R.id.item_history_log_date);
               this.situation = itemView.findViewById(R.id.item_history_log_situation);
           }
       }

        public HistoryLog(List<HistoryCondition> conditions) {
            super();
            this.historyConditions = conditions;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history_log,viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
           viewHolder.situation.setText(this.historyConditions.get(i).getSituation());
           viewHolder.date.setText(this.historyConditions.get(i).getDate());
        }

        @Override
        public int getItemCount() {
            return this.historyConditions.size();
        }
    }

}
