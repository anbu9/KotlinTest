package com.test.allkotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.allkotlin.databinding.ListItemBinding
import com.test.allkotlin.generated.callback.OnClickListener
import com.test.allkotlin.room.Subscriber

class ListRecyclerViewAdapter(private val clickListener: (Subscriber)->Unit) :
    RecyclerView.Adapter<ListRecyclerViewAdapter.ListViewHolder>() {

    private val subscribers = ArrayList<Subscriber>()

    class ListViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(subscriber: Subscriber,clickListener: (Subscriber) -> Unit){
            binding.nameText.text = subscriber.name
            binding.mobileText.text = subscriber.mobile
            binding.bookText.text = subscriber.book
            binding.listItemLayout.setOnClickListener {
                clickListener(subscriber)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(subscribers[position],clickListener)
    }

    fun setList(subscriber: List<Subscriber>){
        subscribers.clear()
        subscribers.addAll(subscriber)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

}