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
    private val resources =
        listOf(
            Item(R.drawable.ic_emoji_food_beverage, "beverage", 1000, 1),
            Item(R.drawable.ic_cake, "cake", 1000, 3),
            Item(R.drawable.ic_fastfood, "fastFood", 1000, 5),
            Item(R.drawable.ic_food_bank, "foodBank", 1000, 10)
        )
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MyAdapter(generateDummyItems(10).toMutableList(), onItemClick = { position ->
            showToast("$position Item Clicked!!")
            adapter.updateItem(position)
        }, onItemLongClick = this::showToast)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.also {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(this@MainActivity)
            it.adapter = this@MainActivity.adapter
        }
        displayTotalPrice()
    }

    private fun displayTotalPrice() {
        val totalPrice = adapter.totalPrice
        val format = NumberFormat.getCurrencyInstance()
        val totalPriceStr = format.format(totalPrice)
        val textTotalPrice = findViewById<TextView>(R.id.total_price)
        textTotalPrice?.text = totalPriceStr
    }

    fun insertItem(view: View) {
        val until = if (adapter.items.size == 0) 1 else adapter.items.size
        val index = Random.nextInt(until)
        val randomItem = resources.random()
        val newItem = randomItem.let {
            Item(
                it.imageResource,
                "insert: ${it.title}",
                it.price,
                it.amount
            )
        }
        adapter.insertItem(index, newItem)
        displayTotalPrice()
        Log.d("aaa", "title: ${newItem.title} : index: $index")
    }

    fun removeItem(view: View) {
        if (adapter.items.size == 0) {
            showToast("list is empty and cannot be deleted.")
            return
        }
        val index = Random.nextInt(adapter.items.size)
        adapter.removeItem(index)
        displayTotalPrice()
        Log.d("aaa", "index: $index")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun generateDummyItems(size: Int): List<Item> {
        val items: ArrayList<Item> = arrayListOf()
        for (i in 0 until size) {
            val randomItem = resources.random()
            items.add(
                randomItem.let {
                    Item(
                        it.imageResource,
                        it.title,
                        it.price,
                        it.amount
                    )
                }
            )
        }
        return items
    }
}