package com.commonsware.todo.ui.roster

import androidx.recyclerview.widget.RecyclerView
import com.commonsware.todo.databinding.TodoRowBinding
import com.commonsware.todo.repo.ToDoModel

//to hold a single row in the RecycleView, bind with the todo_row.xml
class RosterRowHolder (private val binding: TodoRowBinding,
                       val onCheckBoxToggle: (ToDoModel) -> Unit,
                       val onRowClick: (ToDoModel) -> Unit):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ToDoModel) {
        binding.apply {
            root.setOnClickListener{onRowClick(model)}
            isCompletedCB.isChecked = model.isCompleted
            isCompletedCB.setOnCheckedChangeListener { _, _ -> onCheckBoxToggle(model) }
            desc.text = model.description
        }

    }

}