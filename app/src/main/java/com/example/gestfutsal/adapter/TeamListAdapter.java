package com.example.gestfutsal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestfutsal.R;
import com.example.gestfutsal.model.Team;

import java.util.List;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.TeamViewHolder> {

    private List<Team> listaTeams;

    public TeamListAdapter(List<Team> lista) {
        listaTeams = lista;
    }

    private OnIntemClickListener listener;

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View teamView = inflater.inflate(R.layout.row_team_list, parent, false);
        return new TeamViewHolder(teamView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = listaTeams.get(position);
        holder.binData(team);
    }

    @Override
    public int getItemCount() {
        return listaTeams.size();
    }
    public class TeamViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_row_team, tv_row_teamAge;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_row_team = itemView.findViewById(R.id.tv_row_team);
            tv_row_teamAge = itemView.findViewById(R.id.tv_row_teamAge);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(getAdapterPosition());
                }
            });
        }

        public void binData(Team team) {
            tv_row_team.setText(team.getName());
            int age = team.getUnderAge();
            if (age==0){
                tv_row_teamAge.setVisibility(View.GONE);
            }
            tv_row_teamAge.setText("Sub: " + team.getUnderAge());
        }

    }
    public interface OnIntemClickListener {
        void OnItemClick(int pos);
    }

    public void setOnItemClickListener(OnIntemClickListener listener) { this.listener = listener;}
}
