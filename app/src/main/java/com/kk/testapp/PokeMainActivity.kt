package com.kk.testapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kk.testapp.model.Pokemon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokeMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestDto()
    }

    private fun requestDto() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val map = HashMap<String, Any>()
                map["token"] = "123"
                val response = TestAPI.defaultInstance(Constants.URL).getPokemonAsync(map).await()
                withContext(Dispatchers.Main) {
                    val adapter = PokeAdapter(response.results!!)
                    pokemonList.adapter = adapter
                    pokemonList.layoutManager = LinearLayoutManager(this@PokeMainActivity)
                    adapter.setOnItemClickListener(object : OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            val intent = Intent(this@PokeMainActivity, PokeDetailActivity::class.java)
                            intent.putExtra("pos", position)
                            this@PokeMainActivity.startActivity(intent)
                        }
                    })
                }
                println(response)
            } catch (e: Exception) {
                println("okhttp exception = " + e.message)
            }
        }
    }

    class PokeAdapter(private val data: List<Pokemon>) : RecyclerView.Adapter<PokeViewHolder>() {

        private var itemClickListener: OnItemClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return PokeViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
            (holder.itemView as TextView).text = data[position].name
            holder.itemView.setOnClickListener {
                itemClickListener?.onItemClick(it, position)
            }
        }

        fun setOnItemClickListener(listener: OnItemClickListener) {
            itemClickListener = listener
        }
    }

    class PokeViewHolder(view: View) : ViewHolder(view)
}
