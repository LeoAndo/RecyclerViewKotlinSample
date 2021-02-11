package com.template.simplerecyclerviewkotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val items: ArrayList<Item> = arrayListOf()
    private var adapter: MyAdapter? = null
    private val resources =
        listOf(
            OrderItem(R.drawable.ic_emoji_food_beverage, "beverage", 1000, 1),
            OrderItem(R.drawable.ic_cake, "cake", 1000, 3),
            OrderItem(R.drawable.ic_fastfood, "fastFood", 1000, 5),
            OrderItem(R.drawable.ic_food_bank, "foodBank", 1000, 10)
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.apply {
            this.setHasFixedSize(false)
            this.layoutManager = LinearLayoutManager(this@MainActivity)
        }
        generateDummyItems(10)
        adapter = MyAdapter(items) { position ->
            showToast("$position Item Clicked!!")
            updateItem(position)
        }.also {
            recyclerView.adapter = it
        }
        displayTotalPrice()
    }

    private fun displayTotalPrice() {
        val totalPrice = items.sumBy { it.price * it.amount }
        val format = NumberFormat.getCurrencyInstance()
        val totalPriceStr = format.format(totalPrice)
        val textTotalPrice = findViewById<TextView>(R.id.total_price)
        textTotalPrice?.text = totalPriceStr
    }

    private fun updateItem(position: Int) {
        val updateItem = items[position]
        updateItem.title = "update: ${updateItem.title}"
        adapter?.notifyItemChanged(position)
    }

    fun insertItem(view: View) {
        val until = if (items.size == 0) 1 else items.size
        val index = Random.nextInt(until)
        val resIndex = Random.nextInt(resources.size)
        val newItem = Item(
            resources[resIndex].imageResource,
            "insert: ${resources[resIndex].title}",
            resources[resIndex].price,
            resources[resIndex].amount
        )
        items.add(index, newItem)
        adapter?.notifyItemInserted(index) // 変更がある箇所だけ差分更新する.
        // adapter?.notifyDataSetChanged() // これだとリスト全体の更新が走る.
        displayTotalPrice()
        Log.d("aaa", "title: ${resources[resIndex].title} : index: $index")
    }

    fun removeItem(view: View) {
        if (items.size == 0) {
            showToast("list is empty and cannot be deleted.")
            return
        }
        val index = Random.nextInt(items.size)
        items.removeAt(index)
        adapter?.notifyItemRemoved(index)
        displayTotalPrice()
        Log.d("aaa", "index: $index")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun generateDummyItems(size: Int) {

        for (i in 0 until size) {
            val resIndex = Random.nextInt(resources.size)
            items.add(
                Item(
                    resources[resIndex].imageResource,
                    resources[resIndex].title,
                    resources[resIndex].price,
                    resources[resIndex].amount
                )
            )
        }
    }


}