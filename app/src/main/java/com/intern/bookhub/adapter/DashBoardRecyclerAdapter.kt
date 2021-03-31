package com.intern.bookhub.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.intern.bookhub.R
import com.intern.bookhub.activity.DescriptionActivity

import com.intern.bookhub.model.Book
import com.squareup.picasso.Picasso
import java.util.ArrayList

class DashBoardRecyclerAdapter(val context:Context,val itemList:ArrayList<Book>) :RecyclerView.Adapter<DashBoardRecyclerAdapter.DashboardViewHolder>(){


    class DashboardViewHolder(view: View):RecyclerView.ViewHolder(view){

        val txtBookName: TextView =view.findViewById(R.id.txtBookName)
                val txtBookAuthor: TextView =view.findViewById(R.id.txtBookAuthor)
        val txtBookPrice: TextView =view.findViewById(R.id.txtBookPrice)
        val txtBookRating: TextView =view.findViewById(R.id.txtBookRating)
        val imgBookImage: ImageView =view.findViewById(R.id.imgBookImage)
        val llContent: LinearLayout =view.findViewById(R.id.llcontent)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycle_dashboard_single_name,parent, false)
        return DashboardViewHolder(view)

    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
val book=itemList[position]
        holder.txtBookName.text=book.BookName
        holder.txtBookAuthor.text=book.BookAuthor
        holder.txtBookPrice.text=book.bookprice
        holder.txtBookRating.text=book.bookRating

//        holder.imgBookImage.setImageResource(book.bookimage)
Picasso.get().load(book.bookimage).error(R.drawable.default_book_cover).into(holder.imgBookImage)

holder.llContent.setOnClickListener{


    val intent=Intent(context,DescriptionActivity::class.java)
    intent.putExtra("book_id",book.bookId)
    context.startActivity(intent)

}
    }


    override fun getItemCount(): Int {
        return itemList.size
    }



}