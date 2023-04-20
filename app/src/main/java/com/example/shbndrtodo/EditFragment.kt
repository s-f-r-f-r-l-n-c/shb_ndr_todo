package com.example.shbndrtodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shbndrtodo.databinding.FragmentEditBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null

    lateinit  var databaseHelper: TodoListDBHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = TodoListDBHelper(requireContext());

        val id = requireArguments().getInt("id")
        val title = requireArguments().getString("title")
        val description = requireArguments().getString("description")

        binding.titleEdit.setText(title)
        binding.descriptionEdit.setText(description)

        binding.editButton.setOnClickListener{
            val targetId = id
            val targetTitle = binding.titleEdit.text.toString()
            val targetDescription = binding.descriptionEdit.text.toString()

            val status = databaseHelper.updateTodo(TodoModel(targetId, targetTitle, targetDescription))
            if(status > -1){
                Toast.makeText(requireContext(),"success update",Toast.LENGTH_SHORT).show()
            }
            findNavController().navigate(R.id.action_EditFragment_to_ListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}