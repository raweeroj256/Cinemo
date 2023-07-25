package je.raweeroj.cinemo.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import je.raweeroj.cinemo.R
import je.raweeroj.cinemo.adapters.FavoriteMovieListItemsAdapter
import je.raweeroj.cinemo.app.MovieApplication
import je.raweeroj.cinemo.databinding.ActivityMyFavoriteBinding
import je.raweeroj.cinemo.models.Movie
import je.raweeroj.cinemo.models.MovieDAO
import je.raweeroj.cinemo.models.MovieEntity
import je.raweeroj.cinemo.utils.Constants
import kotlinx.coroutines.launch

class MyFavoriteActivity : AppCompatActivity() {
    private var binding : ActivityMyFavoriteBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupActionBar()

        val movieDao = (application as MovieApplication).db.movieDao()

        lifecycleScope.launch{
            movieDao.fetchAllMovie().collect{
                val list = ArrayList(it)
                setupListofDataIntoRecyclerView(list.toList())
            }
        }

    }

    private fun  setupActionBar(){
        setSupportActionBar(binding?.toolbarMyFavorite)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_color_white_24dp)
            actionBar.title = resources.getString(R.string.app_name)

            binding?.toolbarMyFavorite?.setNavigationOnClickListener { onBackPressed() }
        }

    }

    private fun setupListofDataIntoRecyclerView(movieList:List<MovieEntity>){
        if(movieList.isNotEmpty()){
            val adapter = FavoriteMovieListItemsAdapter(this,movieList)
            binding?.rvMoviesFavoriteList?.adapter = adapter
            binding?.rvMoviesFavoriteList?.layoutManager = LinearLayoutManager(this)
            binding?.rvMoviesFavoriteList?.visibility = View.VISIBLE

            adapter.setOnClickListener(object :FavoriteMovieListItemsAdapter.OnClickListener{
                override fun onClick(position: Int, model: Movie) {
                    val intent = Intent(this@MyFavoriteActivity,MovieDetailActivity::class.java)
                    intent.putExtra(Constants.MOVIE_ID,model)
                    intent.putExtra(Constants.FROM_SCREEN,Constants.FROM_FAVORITE)
                    startActivity(intent)
                }

            })
            binding?.tvNoFavorite?.visibility = View.GONE
        }else{
            binding?.rvMoviesFavoriteList?.visibility = View.GONE
            binding?.tvNoFavorite?.visibility = View.VISIBLE
        }
    }
}