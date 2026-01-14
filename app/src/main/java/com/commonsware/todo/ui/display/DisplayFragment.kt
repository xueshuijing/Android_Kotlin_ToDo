package com.commonsware.todo.ui.display

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.commonsware.todo.R
import com.commonsware.todo.databinding.TodoDisplayBinding
import com.commonsware.todo.ui.SingleModelMotor
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DisplayFragment : Fragment() {
    private val args: DisplayFragmentArgs by navArgs()
    private val motor: SingleModelMotor by viewModel { parametersOf(args.modelId) }
    private var binding: TodoDisplayBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)//adding custom menu to the toolbar
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions_display, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                edit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = TodoDisplayBinding.inflate(inflater, container, false)
    .apply {binding = this}
    .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            motor.states.collect{ state ->
                state.item?.let {
                    binding?.apply {
                        completedIC.visibility = if (it.isCompleted) View.VISIBLE else View.GONE
                        descTV.text = it.description
                        createdOnTV.text = DateUtils.getRelativeDateTimeString(
                            requireContext(),
                            it.createdOn.toEpochMilli(),
                            DateUtils.MINUTE_IN_MILLIS,
                            DateUtils.WEEK_IN_MILLIS,
                            0
                        )
                        notesTV.text = it.notes
                    }
                }

            }
        }


    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    //goto the editFragment
    private fun edit() {
        findNavController().navigate(
            DisplayFragmentDirections.editModel(
                args.modelId
            )
        )
    }




}