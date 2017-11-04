package com.example.user.pretendingnews.model

/**
 * Created by dsa28s on 05/11/2017.
 */

class PretendingNewsDetailModel {
    open class NewsDetailModel

    class TitleModel: NewsDetailModel {
        var title = ""

        constructor(title: String) {
            this.title = title
        }
    }

    class SummaryModel: NewsDetailModel {
        var summary = ""

        constructor(summary: String) {
            this.summary = summary
        }
    }

    class GonggamModel: NewsDetailModel {
        var likeCount = 0
        var hateCount = 0
        var angryCount = 0

        constructor(likeCount: Int, hateCount: Int, angryCount: Int) {
            this.likeCount = likeCount
            this.hateCount = hateCount
            this.angryCount = angryCount
        }
    }
}