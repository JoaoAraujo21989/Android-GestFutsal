package com.example.gestfutsal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestfutsal.DBHelper;
import com.example.gestfutsal.model.Athelete;
import com.example.gestfutsal.R;
import com.example.gestfutsal.model.Team;
import com.example.gestfutsal.views.DetailsTeamActivity;

import java.util.ArrayList;
import java.util.List;

public class AtheletesListAdapter extends RecyclerView.Adapter<AtheletesListAdapter.AtheletesViewHolder> {

    private DBHelper db;
    List<Team> listTeam;


    private List<Athelete> listaAtheletes;

    private OnIntemClickListener listener;

    public AtheletesListAdapter(List<Athelete> list) {
        listaAtheletes = list;
    }


    @NonNull
    @Override
    public AtheletesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        db = new DBHelper(parent.getContext());
        listTeam = db.Team_SelectAll();

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View atheleteView = inflater.inflate(R.layout.row_atheletes_list, parent, false);
        return new AtheletesViewHolder(atheleteView);

    }

    @Override
    public void onBindViewHolder(@NonNull AtheletesViewHolder holder, int position) {
        Athelete athelete = listaAtheletes.get(position);
        holder.bindData(athelete);
    }

    @Override
    public int getItemCount() {

        return listaAtheletes.size();
    }

    public class AtheletesViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_row_athelete,tv_row_atheleteNun, tv_row_atheleteTeam;

        public AtheletesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_row_athelete = itemView.findViewById(R.id.tv_row_athelete);
            tv_row_atheleteNun = itemView.findViewById(R.id.tv_row_atheleteNun);
            tv_row_atheleteTeam = itemView.findViewById(R.id.tv_row_atheleteTeam);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(getAdapterPosition());
                }
            });
        }

        public void bindData(Athelete athelete) {
            tv_row_athelete.setText(athelete.getName());
            tv_row_atheleteNun.setText(String.valueOf(athelete.getNumber()));

            int idTeam = athelete.getIdTeam();

            if (idTeam==0){
                tv_row_atheleteTeam.setVisibility(View.GONE);
            }
            for (int j = 0; j < listTeam.size(); j++) {
                if (listTeam.get(j).getId()==idTeam){
                    tv_row_atheleteTeam.setText("Team: " + listTeam.get(j).getName());
                }
            }





        }
    }

    public interface OnIntemClickListener {
        void OnItemClick(int pos);
    }

    public void setOnItemClickListener(OnIntemClickListener listener) {
        this.listener = listener;
    }
}
