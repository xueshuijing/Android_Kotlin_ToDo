package com.commonsware.todo.ui.roster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.commonsware.todo.R
import com.commonsware.todo.databinding.TodoRosterBinding
import com.commonsware.todo.repo.ToDoModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RosterListFragment : Fragment() {
    private val motor: RosterMotor by viewModel()
    private var binding: TodoRosterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) //adding custom menu on the toolbar
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {//adding custom menu on the toolbar
        inflater.inflate(R.menu.actions_rooster, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.add -> {
                add()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = TodoRosterBinding.inflate(inflater, container, false)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RosterAdapter(
            layoutInflater,
            onCheckBoxToggle = { motor.save(it.copy(isCompleted = !it.isCompleted)) },
            onRowClick = ::display
        )

        //preparing the ryclerView to display the list
        binding?.items?.apply {
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(context)

            addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        //displaying the list that is collected from the viewmodel
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            motor.states.collect {state ->
                adapter.submitList(state.items)

                binding?.apply {
                    when { //to display and hide instruction when there is no todo list
                        state.items.isEmpty() -> {
                            empty.visibility = View.VISIBLE
                            empty.setText(R.string.msg_empty)
                        }
                        else -> empty.visibility = View.GONE

                    }
                }
            }
        }


        binding?.empty?.visibility = View.GONE


    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    //function to move to the displayFragment
    private fun display(model: ToDoModel){
        findNavController().navigate(RosterListFragmentDirections.displayModel(model.id))
    }


    //function to move to the NewtodoListFragment
    private fun add() {
        findNavController().navigate(RosterListFragmentDirections.createModel(null))
    }

}