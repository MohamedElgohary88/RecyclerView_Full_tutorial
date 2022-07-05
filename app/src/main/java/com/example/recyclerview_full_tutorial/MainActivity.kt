package com.example.recyclerview_full_tutorial

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter

    private var countryList = mutableListOf<String>()
    private var displayList = mutableListOf<String>()

    private lateinit var deleteCountry: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addCountryOnList()

        displayList.addAll(countryList)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerAdapter = RecyclerAdapter(displayList)

        recyclerView.adapter = recyclerAdapter

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // ************  refreshLayout to update   *************
        refreshLayout.setOnRefreshListener {
            displayList.clear()
            displayList.addAll(countryList)
            recyclerView.adapter!!.notifyDataSetChanged()
            refreshLayout.isRefreshing = false
        }
    }

    private var simpleCallback = object : ItemTouchHelper
    .SimpleCallback(
        ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)
    ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder): Boolean {

            var startPosition = viewHolder.adapterPosition
            var endPosition = target.adapterPosition

            Collections.swap(displayList, startPosition, endPosition)
            recyclerView.adapter?.notifyItemChanged(startPosition, endPosition)
            return true
        }


      // ************  Function for Swipe left  => ( remove )  *************
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    deleteCountry = displayList.get(position)
                    displayList.removeAt(position)
                    recyclerAdapter.notifyItemRemoved(position)

                    Snackbar.make(recyclerView,"$deleteCountry is deleted " , Snackbar.LENGTH_LONG).setAction("Undo",View.OnClickListener {
                        displayList.add(position,deleteCountry)
                        recyclerAdapter.notifyItemInserted(position)
                    }).show()
                }

      // ************  Function for Swipe right => ( Edit & Update )   *************
                ItemTouchHelper.RIGHT ->{
                    var editText = EditText(this@MainActivity)
                    editText.setText(displayList[position])

                    // ************  AlertDialog   *************

                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Update an Item ")
                    builder.setCancelable(true)
                    builder.setView(editText)

                    builder.setNegativeButton("Cancel ",DialogInterface.OnClickListener { dialog, which ->
                        displayList.clear()
                        displayList.addAll(countryList)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    })

                    builder.setPositiveButton("Update",DialogInterface.OnClickListener { dialog, which ->
                        displayList.set(position,editText.text.toString())
                        recyclerView.adapter!!.notifyItemChanged(position)
                    })
                    builder.show()
                }
            }
        }
    }

    // ************  Function for menu for Search   *************
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        var item: MenuItem = menu!!.findItem(R.id.search)

        if (item != null) {
            var searchView = item.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        displayList.clear()
                        var search = newText.toLowerCase(Locale.getDefault())

                        for (country in countryList) {
                            if (country.toLowerCase(Locale.getDefault()).contains(search)) {
                                displayList.add(country)
                            }
                            recyclerView.adapter!!.notifyDataSetChanged()
                        }
                    } else {
                        displayList.clear()
                        displayList.addAll(countryList)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    // ************  addCountryOnList   *************
    fun addCountryOnList() {
        countryList.add(" Albania ")
        countryList.add(" Argentina ")
        countryList.add(" Austria ")
        countryList.add(" Bahrain ")
        countryList.add(" Bulgaria ")
        countryList.add(" Cuba ")
        countryList.add(" Costa Rica ")
        countryList.add(" Egypt ")
        countryList.add(" El Salvador ")
        countryList.add(" Iceland ")
        countryList.add(" Jordan ")
        countryList.add(" Japan ")
        countryList.add(" Moldova ")
        countryList.add(" Niger ")
        countryList.add(" Honduras ")
        countryList.add(" Liberia ")
        countryList.add(" Kenya ")
        countryList.add(" Kiribati ")
        countryList.add(" Kuwait ")
        countryList.add(" Malawi ")
        countryList.add(" Montenegro ")
        countryList.add(" Niger ")
        countryList.add(" Paraguay ")
        countryList.add(" Rwanda ")
        countryList.add(" Russia ")
        countryList.add(" Senegal ")
        countryList.add(" Saudi Arabia ")
        countryList.add(" Zambia ")
        countryList.add(" Yemen ")
    }
}