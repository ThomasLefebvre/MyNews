package fr.thomas.lefebvre.mynews.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import fr.thomas.lefebvre.mynews.model.Docs
import fr.thomas.lefebvre.mynews.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.*


class DocsAdapter (private val articleOnView:List<Docs>, private val listener:(Docs)-> Unit):RecyclerView.Adapter<DocsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v:View= LayoutInflater.from(p0.context).inflate(fr.thomas.lefebvre.mynews.R.layout.rv_item,p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return articleOnView.size
    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bind(articleOnView[p1],listener)

    class ViewHolder(elementList: View):RecyclerView.ViewHolder(elementList){

        var dateUtils:DateUtils= DateUtils()

        fun bind(docs:Docs, listener:(Docs)->Unit){
            val tvSection: TextView =itemView.findViewById(fr.thomas.lefebvre.mynews.R.id.tv_section)
            val tvDate: TextView =itemView.findViewById(fr.thomas.lefebvre.mynews.R.id.tv_date)
            val tvAbstract: TextView =itemView.findViewById(fr.thomas.lefebvre.mynews.R.id.tv_abstract)
            val imageArticle:ImageView=itemView.findViewById(fr.thomas.lefebvre.mynews.R.id.imageViewArticle)
            tvSection.text=docs.section_name//SECTION TITLE
            tvDate.text = dateUtils.dateFormat(docs.pub_date)//SET DATE FORMAT

            tvAbstract.text=docs.abstract//ABSTRACT
            //SET IMAGE ARTICLE
            if(docs.multimedia.size==0) { //IF NO IMAGE
                imageArticle.setImageResource(fr.thomas.lefebvre.mynews.R.drawable.logo_nyt)
            }
            else{ //IF IMAGE
                val urlImage: String = urlImageStart+ docs.multimedia.get(1).url
                Picasso.get().load(urlImage).into(imageArticle)
            }
            itemView.setOnClickListener {//CLICK LISTENER
                listener(docs) }
        }
    }
    companion object{
        var urlImageStart:String="https://static01.nyt.com/"
    }
}

