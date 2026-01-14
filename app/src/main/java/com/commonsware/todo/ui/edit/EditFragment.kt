package com.commonsware.todo.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.commonsware.todo.R
import com.commonsware.todo.databinding.TodoEditBinding
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.ui.SingleModelMotor
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditFragment : Fragment() {

    private val args: EditFragmentArgs by navArgs()
    private val motor: SingleModelMotor by viewModel { parametersOf(args.modelId) }
    private var binding: TodoEditBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions_edit, menu)
        menu.findItem(R.id.delete).isVisible = args.modelId != null
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.save -> {
                save()
                return true
            }
            R.id.delete -> {
                delete()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = TodoEditBinding.inflate(inflater, container, false)
        .apply {binding = this}
        .root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            motor.states.collect { state ->
                if (savedInstanceState == null) {
                    state.item?.let {
                        binding?.apply {
                            isCompleted.isChecked = it.isCompleted
                            descET.setText(it.description)
                            notesET.setText(it.notes)
                        }
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun save() {
        binding?.apply {
            val model = motor.states.value.item
            val edited = model?.copy(
                description = descET.text.toString(),
                isCompleted = isCompleted.isChecked,
                notes = notesET.text.toString()
            )?: ToDoModel(//if new then the following is saved instead
                description = descET.text.toString(),
                isCompleted = isCompleted.isChecked,
                notes = notesET.text.toString()
            )
            edited.let { motor.save(it) }
        }
        navToDisplay()
    }

    private fun delete() {
        val model = motor.states.value.item
        model?.let { motor.delete(it)}
        navToList()
    }

    private fun navToDisplay() {
        hideKeyboard()
        findNavController().popBackStack()
    }

    private fun navToList() {
        hideKeyboard()
        findNavController().popBackStack(R.id.rosterListFragment, false)
    }

    private fun hideKeyboard() {
        view?.let {
            val imm = context?.getSystemService<InputMethodManager>()

            imm?.hideSoftInputFromWindow(
                it.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }


}