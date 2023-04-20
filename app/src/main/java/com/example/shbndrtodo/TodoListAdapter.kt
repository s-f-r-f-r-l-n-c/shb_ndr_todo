package com.example.shbndrtodo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter(
    private val parentFragment: Fragment,
    private val databaseHelper: TodoListDBHelper,
    private val id: ArrayList<String>,
    private val title: ArrayList<String>,
    private val description: ArrayList<String>) :
    RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView
        val descriptionText: TextView
        val editButton: Button
        val deleteButton: Button

        init {
            // Define click listener for the ViewHolder's View.
            titleText = view.findViewById(R.id.titleText)
            descriptionText = view.findViewById(R.id.descriptionText)
            editButton = view.findViewById(R.id.editButton)
            deleteButton = view.findViewById(R.id.deleteButton)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.todo_list_view_content, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.titleText.setText(title.get(position).toString());
        viewHolder.descriptionText.setText(description.get(position).toString());

        val self = this
        viewHolder.editButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("id", id.get(position).toInt())
            bundle.putString("title", title.get(position).toString())
            bundle.putString("description", description.get(position).toString())

            parentFragment.findNavController().navigate(R.id.action_ListFragment_to_EditFragment, bundle)
        }

        viewHolder.deleteButton.setOnClickListener{
            databaseHelper.deleteTodo(TodoModel(id.get(position).toInt(),"",""))
            self.id.removeAt(position)
            self.title.removeAt(position)
            self.description.removeAt(position)
            self.notifyDataSetChanged()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = id.size

}
