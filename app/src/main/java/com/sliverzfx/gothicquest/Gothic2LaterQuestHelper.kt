package com.sliverzfx.gothicquest
internal fun g2later(id:String, chapter:Int, order:Int, title:String, type:String, region:String, category:String, stage:String, objective:String, walkthrough:String, notes:String) = Quest(
    id=id, chapter=chapter, playOrder=order, title=title, category="$type / $category",
    giver="Not listed in the master guide", location=region, prerequisites=stage, summary=objective,
    walkthroughSteps=walkthrough.split(Regex("(?<=[.!?])\\s+(?=[A-Z])")),
    reward="Not specified in the master guide", warnings=notes,
    searchTags=listOf(title.lowercase(), region.lowercase(), category.lowercase())
)