package com.example.android.guardiannews;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by J on 4/5/2017.
 * <p>
 * An {@link ArticleViewHolder} to make ListView scrolling more responsive
 */

class ArticleViewHolder {

    final ImageView mVHArticleImage;
    final TextView mVHArticleSection;
    final TextView mVHArticleTitle;
    final TextView mVHArticleAuthor;
    final TextView mVHArticleTimePast;
    final TextView mVHArticlePublishedDate;
    final TextView mVHArticleDescription;

    /**
     * An ArticleViewHolder constructor
     *
     * @param view the View of one Article to be reused
     */
    ArticleViewHolder(View view) {
        mVHArticleImage = (ImageView) view.findViewById(R.id.article_image);
        mVHArticleSection = (TextView) view.findViewById(R.id.article_section);
        mVHArticleTitle = (TextView) view.findViewById(R.id.article_title);
        mVHArticleAuthor = (TextView) view.findViewById(R.id.article_author);
        mVHArticleTimePast = (TextView) view.findViewById(R.id.time_quantity);
        mVHArticlePublishedDate = (TextView) view.findViewById(R.id.article_published_date);
        mVHArticleDescription = (TextView) view.findViewById(R.id.article_description);
    }
}
