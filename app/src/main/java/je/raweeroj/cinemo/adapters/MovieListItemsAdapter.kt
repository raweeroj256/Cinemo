package je.raweeroj.cinemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import je.raweeroj.cinemo.R
import je.raweeroj.cinemo.databinding.ItemMovieBinding
import je.raweeroj.cinemo.models.Movie

open class MovieListItemsAdapter(private val context: Context,
                            private var list: List<Movie>):
    RecyclerView.Adapter<MovieListItemsAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding:ItemMovieBinding):RecyclerView.ViewHolder(binding.root){
        val myViewBinding = binding
    }

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        Glide
                .with(context)
            .load(model.poster_url)
            .placeholder(R.drawable.ic_movie_place_holder)
            .into(holder.myViewBinding.ivMovieImage)

        holder.myViewBinding.tvGenre.text = model.genre
        holder.myViewBinding.tvMovieTitle.text = model.title_en
        holder.myViewBinding.tvReleaseDate.text = model.release_date

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