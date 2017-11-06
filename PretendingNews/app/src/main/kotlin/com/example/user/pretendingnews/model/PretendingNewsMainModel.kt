package com.example.user.pretendingnews.model

/**
 * Created by dsa28s on 05/11/2017.
 */

open class PretendingNewsMainModel {
    var title = ""
    var originalLink = ""
    var link = ""
    var imageLink = ""

    constructor(title: String, originalLink: String, link: String, imageLink: String) {
        this.title = title
        this.originalLink = originalLink
        this.link = link
        this.imageLink = imageLink
    }
}