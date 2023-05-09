package com.example.sweproj;





import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

    public class vehicleAdapter extends RecyclerView.Adapter<vehicleAdapter.ViewHolder> {
        Context context;
        List<VehicleModel> vehiclesList;
        RecyclerView rvPrograms;
        final View.OnClickListener onClickListener = new MyOnClickListener();
        public static class ViewHolder extends RecyclerView.ViewHolder{
            TextView rowModel;
            TextView rowRent;
            TextView rowDescription;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                rowModel = itemView.findViewById(R.id.item_name);
                rowRent = itemView.findViewById(R.id.item_email);
            }
        }

        public vehicleAdapter(Context context, List<VehicleModel> vehiclesList, RecyclerView rvPrograms){
            this.context = context;
            this.vehiclesList = vehiclesList;
            this.rvPrograms = rvPrograms;
        }

        @NonNull
        @Override
        public vehicleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.single_item, viewGroup, false);
            view.setOnClickListener(onClickListener);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull vehicleAdapter.ViewHolder viewHolder, int i) {
            VehicleModel contact = vehiclesList.get(i);

            viewHolder.rowModel.setText(contact.getModel());
            viewHolder.rowRent.setText(contact.getRent());
            viewHolder.rowDescription.setText(contact.getDescription());
        }

        @Override
        public int getItemCount() {
            return vehiclesList.size();
        }

        private class MyOnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                int itemPosition = rvPrograms.getChildLayoutPosition(v);
                String item = vehiclesList.get(itemPosition).getModel();
                Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
            }
        }
    }

