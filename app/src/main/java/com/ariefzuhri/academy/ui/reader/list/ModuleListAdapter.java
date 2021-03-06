package com.ariefzuhri.academy.ui.reader.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ariefzuhri.academy.data.source.local.entity.ModuleEntity;
import com.ariefzuhri.academy.databinding.ItemsModuleListCustomBinding;

import java.util.ArrayList;
import java.util.List;

public class ModuleListAdapter extends RecyclerView.Adapter<ModuleListAdapter.ModuleViewHolder> {

    private final MyAdapterClickListener listener;
    private final List<ModuleEntity> listModules = new ArrayList<>();

    ModuleListAdapter(MyAdapterClickListener listener) {
        this.listener = listener;
    }

    void setModules(List<ModuleEntity> listModules) {
        if (listModules == null) return;
        this.listModules.clear();
        this.listModules.addAll(listModules);
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsModuleListCustomBinding binding = ItemsModuleListCustomBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ModuleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ModuleViewHolder viewHolder, int position) {
        ModuleEntity module = listModules.get(position);
        viewHolder.bind(module);
        viewHolder.itemView.setOnClickListener(v ->
                listener.onItemClicked(viewHolder.getAdapterPosition(), listModules.get(viewHolder.getAdapterPosition()).getModuleId())
        );
    }

    @Override
    public int getItemCount() {
        return listModules.size();
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder {
        private final ItemsModuleListCustomBinding binding;

        ModuleViewHolder(ItemsModuleListCustomBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        void bind(ModuleEntity module) {
            binding.textModuleTitle.setText(module.getTitle());
        }
    }
}

interface MyAdapterClickListener {
    void onItemClicked(int position, String moduleId);
}