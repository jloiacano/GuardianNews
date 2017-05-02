package com.example.android.guardiannews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by J on 4/5/2017.
 * <p>
 * An {@link ArticleAdapter} adapts {@link Article} Objects to be placed into a ListView of
 * article_item.xml
 */

class ArticleAdapter extends ArrayAdapter<Article> {

    // A tag for log messages
    private static final String LOG_TAG = ArticleAdapter.class.getSimpleName();

    /**
     * Constructor for the new {@link ArticleAdapter}
     *
     * @param context  Context of the app
     * @param articles the list of articles to be adapted to the ListView
     */
    ArticleAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // A ViewHolder to make things run more smoothly
        final ArticleViewHolder articleVH;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.article_item, parent, false);

            articleVH = new ArticleViewHolder(convertView);
            convertView.setTag(articleVH);
        } else {
            articleVH = (ArticleViewHolder) convertView.getTag();
        }

        // Find the Article at the given position in the list of articles
        // and set to the Article Object currentArticle
        final Article currentArticle = getItem(position);

        // use ImageDownloader to download the articles image and put it in the ImageView
        String currentArticleThumb = currentArticle.getArticleImageUrl();
        if (!currentArticleThumb.equals("")) {
            new ImageDownloader(articleVH.mVHArticleImage).execute(currentArticleThumb);
        } else {
            articleVH.mVHArticleImage.setVisibility(View.GONE);
        }

        // Set tje article section to the TextView
        articleVH.mVHArticleSection.setText(currentArticle.getSection());

        // Set the article title to the TextView
        articleVH.mVHArticleTitle.setText(currentArticle.getTitle());

        // Set the article author to the author TextView
        if (currentArticle.getAuthor().equals("null") || currentArticle.getAuthor() == null) {
            articleVH.mVHArticleAuthor.setText("");
            articleVH.mVHArticleAuthor.setVisibility(View.GONE);
        } else {
            articleVH.mVHArticleAuthor.setText(currentArticle.getAuthor());
        }

        // Get the published date in String format
        String timeDate = currentArticle.getPublishedDate();

        // Create a CustomTimeDateObject from the published date string
        CustomTimeDateFormat customTimeDateFormat = new CustomTimeDateFormat(timeDate);

        // Set the number part the amount of time units passed since publication of the article to
        // the time_quantity TextView
        articleVH.mVHArticleTimePast.setText(customTimeDateFormat.getTimePastAttribute());

        // Set the time units part the amount of time passed since publication of the article to
        // the time_quantity TextView
        if (customTimeDateFormat.getUnitOfTimeStringResourceID() != R.string.no_resource) {
            articleVH.mVHArticlePublishedDate.setText(customTimeDateFormat.getUnitOfTimeStringResourceID());
        } else {
            articleVH.mVHArticlePublishedDate.setText(currentArticle.getPublishedDate());
        }

        // Set the article description to description TextView
        String currentDescription = stripHtml(currentArticle.getDescription());
        articleVH.mVHArticleDescription.setText(currentDescription);

        // Add an onClckListerner to the whole Article that will open it's web page
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openTheArticlesWebPage = new Intent(Intent.ACTION_VIEW);
                openTheArticlesWebPage.setData(Uri.parse(currentArticle.getArticleUrl()));
                getContext().startActivity(openTheArticlesWebPage);
            }
        });
        return convertView;
    }

    @SuppressWarnings("deprecation")
    public static String stripHtml(String html) {
        String stringToReturn;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            stringToReturn = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            stringToReturn = Html.fromHtml(html).toString();
        }
        stringToReturn = stringToReturn.trim();
        return stringToReturn;
    }
}

