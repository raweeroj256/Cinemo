package je.raweeroj.cinemo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import je.raweeroj.cinemo.R
import je.raweeroj.cinemo.app.MovieApplication
import je.raweeroj.cinemo.databinding.ActivityMovieDetailBinding
import je.raweeroj.cinemo.models.Movie
import je.raweeroj.cinemo.models.MovieDAO
import je.raweeroj.cinemo.models.MovieEntity
import je.raweeroj.cinemo.utils.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {
    private var binding:ActivityMovieDetailBinding? = null
    private var movies :Movie? = null
    private var fromScreen : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupActionBar()

       var movieDao = (application as MovieApplication).db.movieDao()

        if(intent.hasExtra(Constants.MOVIE_ID)){
            movies = intent.getSerializableExtra(Constants.MOVIE_ID)!! as Movie
        }

        if(intent.hasExtra(Constants.FROM_SCREEN)){
            fromScreen = intent.getStringExtra(Constants.FROM_SCREEN)!!
        }

        if(movies != null){
            Glide
                .with(this)
                .load(movies!!.poster_url)
                .placeholder(R.drawable.ic_movie_place_holder)
                .into(binding!!.ivMovieDetailImage)


            binding?.tvDetailGenre?.text = movies!!.genre
            binding?.tvDetailMovieTitle?.text = movies!!.title_en
            binding?.tvMovieDetail?.text = movies!!.synopsis_en

            if(fromScreen == Constants.FROM_MAIN){
                binding?.btnAddToFavorite?.visibility = View.VISIBLE
            }else{
                binding?.btnAddToFavorite?.visibility = View.GONE
            }

            binding?.btnAddToFavorite?.setOnClickListener{
                try{
                    lifecycleScope.launch{
                        movieDao.fetchMovieById(movies!!.id).first().let {
                            var list = ArrayList(it)
                            Log.i("temp list", list.toString())
                            if (list.size > 0) {
                                Toast.makeText(
                                    this@MovieDetailActivity,
                                    "You Already Added this movie as favorite!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                try{
                                    addToFavorite(movieDao)
                                }catch (e:Exception){
                                    Toast.makeText(this@MovieDetailActivity,"OOPS! something went wrong please try again!",
                                        Toast.LENGTH_SHORT).show()
                                    e.printStackTrace()
                                }

                            }
                        }
                    }
                }catch (e:Exception){
                    Toast.makeText(this@MovieDetailActivity,"OOPS! something went wrong please try again!",
                        Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }



            }
        }


    }

    private fun  setupActionBar(){
        setSupportActionBar(binding?.toolbarMovieDetailActivity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_color_white_24dp)
            actionBar.title = resources.getString(R.string.app_name)

            binding?.toolbarMovieDetailActivity?.setNavigationOnClickListener { onBackPressed() }
        }

    }

    private fun addToFavorite(movieDAO: MovieDAO){
        var moviecode = movies!!.movieCode[0]
        if(movies!=null){
            lifecycleScope.launch{
                movieDAO.insert(MovieEntity(movies!!.id ,moviecode, movies!!.title_en ,
                movies!!.title_th,movies!!.rating,movies!!.rating_id,movies!!.duration,movies!!.release_date,
                movies!!.sneak_date,movies!!.synopsis_th,movies!!.synopsis_en,movies!!.director,movies!!.actor,
                    movies!!.genre,movies!!.poster_ori,movies!!.poster_url,movies!!.trailer,movies!!.tr_ios,
                    movies!!.tr_hd,movies!!.tr_sd,movies!!.tr_mp4,movies!!.priority,movies!!.now_showing,
                    movies!!.advance_ticket,movies!!.date_update,movies!!.show_buyticket,movies!!.trailer_cms_id
                    ,movies!!.trailer_ivx_key
                    ))
                Toast.makeText(applicationContext,"Added to favorite!", Toast.LENGTH_LONG).show()
            }

        }else{
            Toast.makeText(applicationContext,"Error!", Toast.LENGTH_LONG).show()
        }
    }


}