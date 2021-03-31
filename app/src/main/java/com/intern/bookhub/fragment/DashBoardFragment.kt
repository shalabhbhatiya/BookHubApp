package com.intern.bookhub.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.intern.bookhub.R
import com.intern.bookhub.adapter.DashBoardRecyclerAdapter
import com.intern.bookhub.model.Book
import com.intern.bookhub.util.ConnectionManager
import org.json.JSONException
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DashBoardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerDashBoard:RecyclerView

lateinit var layoutmanager:RecyclerView.LayoutManager
var  bookInfoList= arrayListOf<Book>()
    lateinit var progressBar:ProgressBar
    lateinit var ProgressLayout:RelativeLayout
    var ratingComparator= Comparator<Book>{book1, book2->
book1.bookRating.compareTo(book2.bookRating,true)


    }

//     val bookInfoList = arrayListOf<Book>(
//            Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
//            Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
//            Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
//            Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
//            Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
//            Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
//            Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
//            Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
//            Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
//            Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)
//    )



    lateinit var recyclerAdapter:DashBoardRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_dash_board, container, false)
        setHasOptionsMenu(true)
        recyclerDashBoard = view.findViewById(R.id.RecyclerDashboard)

        ProgressLayout=view.findViewById(R.id.progressLayout)
        progressBar=view.findViewById(R.id.progressBar)

        ProgressLayout.visibility=View.VISIBLE
//        btnCheckInternet.setOnClickListener{
//
//            if(ConnectionManager().isNetworkAvailable(activity as Context ))
//            {
//                val dialog=AlertDialog.Builder(activity as Context)
//                dialog.setTitle("Success")
//                dialog.setMessage("Internet Connection Found")
//                dialog.setPositiveButton("ok"){ text,listener->
//                }
//                dialog.setNegativeButton("Cancle"){ text,listener->
//
//                }
//                dialog.create()
//                dialog.show()
//
//
//            }else{
//                val dialog=AlertDialog.Builder(activity as Context)
//                dialog.setTitle("Error")
//                dialog.setMessage("Internet Connection is not Found")
//                dialog.setPositiveButton("ok"){ text,listener->
//                }
//                dialog.setNegativeButton("Cancle"){ text,listener->
//
//                }
//                dialog.create()
//                dialog.show()
//
//
//            }
//
//
//        }
        if(ConnectionManager().isNetworkAvailable(activity as Context ))
        {
        val queue = Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v1/book/fetch_books/"
        val jsonObjectRequest=object :JsonObjectRequest(Request.Method.GET,url,null,Response.Listener{
try {
    ProgressLayout.visibility=View.GONE
    val success=it.getBoolean("success")

    if (success){
        val data=it.getJSONArray("data")
        for(i in 0 until data.length())
        {

            val bookJasonObject=data.getJSONObject(i)

            val bookObject=Book(

                    bookJasonObject.getString("book_id"),
                    bookJasonObject.getString("name"),

                    bookJasonObject.getString("author"),

                    bookJasonObject.getString("rating"),
                    bookJasonObject.getString("price"),
                    bookJasonObject.getString("image")
            )
            bookInfoList.add(bookObject)
            layoutmanager=LinearLayoutManager(activity)



            recyclerDashBoard.layoutManager=layoutmanager

//         recyclerDashBoard.addItemDecoration(DividerItemDecoration(recyclerDashBoard.context,(layoutmanager as LinearLayoutManager).orientation))
            recyclerAdapter=DashBoardRecyclerAdapter(activity as Context,bookInfoList)
            recyclerDashBoard.adapter=recyclerAdapter
            recyclerDashBoard.layoutManager=layoutmanager

        }}
    else {
        Toast.makeText(activity as Context,"Some Error Occur",Toast.LENGTH_SHORT).show()}

}catch (e : JSONException)
{
    Toast.makeText(activity as Context,"Some UnExpected Error Occur",Toast.LENGTH_SHORT).show()

}
}


                ,Response.ErrorListener {

            if(activity!=null){
Toast.makeText(activity as Context,"Volley error occur",Toast.LENGTH_SHORT).show()}
        }){
            override fun getHeaders(): MutableMap<String, String> {


                val headers=HashMap<String,String>()
headers["Content-type"]="application/json"
                headers["token"] ="56fa713574abf1"

                return headers
            }

        }

        queue.add(jsonObjectRequest)}
        else{
            val dialog=AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open Settings"){ text,listener->

                val settingIntent = Intent(Settings.ACTION_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()

            }
            dialog.setNegativeButton("Cancel"){ text,listener->

                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()

        }

   return view
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
                DashBoardFragment().apply {

                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_dashboard,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item?.itemId
        if(id==R.id.action_sort){

            Collections.sort(bookInfoList,ratingComparator)
            bookInfoList.reverse()
        }
        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }


}