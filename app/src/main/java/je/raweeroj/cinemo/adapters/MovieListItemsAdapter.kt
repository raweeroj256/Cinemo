package je.raweeroj.cinemo.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import je.raweeroj.cinemo.R
import je.raweeroj.cinemo.databinding.ItemMovieBinding
import je.raweeroj.cinemo.models.Movie
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException

open class MovieListItemsAdapter(private val context: Context,
                            private var list: List<Movie>):
    RecyclerView.Adapter<MovieListItemsAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding:ItemMovieBinding):RecyclerView.ViewHolder(binding.root){
        val myViewBinding = binding
    }

    fun setFilteredList(list : List<Movie>){
        this.list = list
        notifyDataSetChanged()
    }

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        var date  : Any? = null

        Glide
                .with(context)
            .load(model.poster_url)
            .placeholder(R.drawable.ic_movie_place_holder)
            .into(holder.myViewBinding.ivMovieImage)

        holder.myViewBinding.tvGenre.text = model.genre
        holder.myViewBinding.tvMovieTitle.text = model.title_en
try{
    date = LocalDate.parse(model.release_date)

}catch (e:DateTimeParseException){
    try{
        date = LocalDateTime.parse(model.release_date)
    }catch (e:DateTimeParseException){
        e.printStackTrace()
    }
}

        var formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")

        var formattedDate = when (date){
            is LocalDate -> date.format(formatter)
            is LocalDateTime -> date.format(formatter)
            else -> ""
        }
        holder.myViewBinding.tvReleaseDate.text = formattedDate

        holder.myViewBinding.tvViewMore.setOnClickListener{
            if (onClickListener != null) {
                onClickListener!!.onClick(position,model)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(cardPosition: Int,movies : Movie)
    }

}