package fr.thomas.lefebvre.mynews.model

data class MainResponseSearch(
    var status:String,
    var copyright:String,
    var section: String,
    var last_updated:String,
    var num_results:Int,
    var response:Response
)
data class Response(
    var docs:List<Docs>,
    var meta:Meta
)
data class Docs(
    var abstract:String,
    var news_desk:String,
    var multimedia:List<Multimedia>,
    var print_page:Int,
    var web_url:String,
    var pub_date:String,
    var section_name:String

)
data class Meta(
    var hits:Int
)


