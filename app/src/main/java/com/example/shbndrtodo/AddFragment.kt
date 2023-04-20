package com.example.shbndrtodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shbndrtodo.databinding.FragmentAddBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null

    lateinit  var databaseHelper: TodoListDBHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = TodoListDBHelper(requireContext());

        binding.addButton.setOnClickListener{
            val title = binding.titleEdit.text.toString()
            val description = binding.descriptionEdit.text.toString()

            val status = databaseHelper.addTodo(TodoModel(0, title, description))
            if(status > -1){
                Toast.makeText(requireContext(),"success add",Toast.LENGTH_SHORT).show()
            }

            findNavController().navigate(R.id.action_AddFragment_to_ListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}