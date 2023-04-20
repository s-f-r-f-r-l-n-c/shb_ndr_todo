package com.example.shbndrtodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shbndrtodo.databinding.FragmentListBinding
import com.example.shbndrtodo.TodoListAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    val todoId: ArrayList<String> = ArrayList()
    val todoTitle: ArrayList<String> = ArrayList()
    val todoDescription: ArrayList<String> = ArrayList()

    lateinit  var databaseHelper: TodoListDBHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    fun listTodos(){
        val todos: List<TodoModel> = databaseHelper.readAllTodos()

        val todoIds = ArrayList<String>()
        val todoTitles = ArrayList<String>()
        val todoDescriptions = ArrayList<String>()

        for(todo in todos){
            todoIds.add(todo.id.toString())
            todoTitles.add(todo.title.toString())
            todoDescriptions.add(todo.description.toString())
        }

        val todoListAdapter = TodoListAdapter(this, databaseHelper, todoIds, todoTitles, todoDescriptions)
        binding.todoListRView.layoutManager = LinearLayoutManager(requireContext())
        binding.todoListRView.adapter = todoListAdapter;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = TodoListDBHelper(requireContext());

        listTodos()

        binding.fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_ListFragment_to_AddFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}