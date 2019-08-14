package fr.thomas.lefebvre.mynews.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import fr.thomas.lefebvre.mynews.model.ViewedArticle
import fr.thomas.lefebvre.mynews.R
import fr.thomas.lefebvre.mynews.utils.DateUtils

class ViewedArticleAdapter (private val articleOnView:List<ViewedArticle>, private val listener:(ViewedArticle)-> Unit): RecyclerView.Adapter<ViewedArticleAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v: View = LayoutInflater.from(p0.context).inflate(R.layout.rv_item,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return articleOnView.size
    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bind(articleOnView[p1],listener)

    class ViewHolder(elementList: View): RecyclerView.ViewHolder(elementList){

        private var dateUtils=DateUtils()

        fun bind(article: ViewedArticle, listener:(ViewedArticle)->Unit){
            //INIT GRAPHICS ELEMENTS
            val tvSection: TextView =itemView.findViewById(R.id.tv_section)
            val tvDate: TextView =itemView.findViewById(R.id.tv_date)
            val tvAbstract: TextView =itemView.findViewById(R.id.tv_abstract)
            val imageArticle: ImageView =itemView.findViewById(R.id.imageViewArticle)

            tvSection.text=(article.section+" >")//SECTION TITLE
            tvDate.text = dateUtils.dateFormatMostPopular(article.published_date)//SET DATE FORMAT
            tvAbstract.text=article.abstract//SET ABSTRACT ARTICLE
            //SET IMAGE ARTICLE
            if(article.media.size==0) { //IF NO IMAGE
                imageArticle.setImageResource(R.drawable.logo_nyt)
            }
            else{ //IF IMAGE
                val urlImage: String = article.media.get(0).media_metadata[1].url
                Picasso.get().load(urlImage).into(imageArticle)
            }
            itemView.setOnClickListener {//CLICK LISTENER
                listener(article) }
        }
    }
}