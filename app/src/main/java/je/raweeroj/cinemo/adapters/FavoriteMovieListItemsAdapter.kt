package je.raweeroj.cinemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import je.raweeroj.cinemo.R
import je.raweeroj.cinemo.databinding.ItemMovieBinding
import je.raweeroj.cinemo.models.Movie
import je.raweeroj.cinemo.models.MovieEntity

open class FavoriteMovieListItemsAdapter(private val context: Context,
                                         private var list: List<MovieEntity>):
    RecyclerView.Adapter<FavoriteMovieListItemsAdapter.MyViewHolder>() {

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
                var movies : Movie = Movie(id = model.id, movieCode = listOf(model.movieCode),model.title_en,model.title_th,
                model.rating,model.rating_id,model.duration,model.release_date,model.sneak_date,model.synopsis_th,model.synopsis_en,
                model.director,model.actor,model.genre,model.poster_ori,model.poster_url,model.trailer,model.tr_ios,model.tr_hd,
                model.tr_sd,model.tr_mp4,model.priority,model.now_showing,model.advance_ticket,model.date_update,model.show_buyticket,
                model.trailer_cms_id,model.trailer_ivx_key)
                onClickListener!!.onClick(position,movies)
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
        fun onClick(cardPosition: Int, movies : Movie)
    }

}