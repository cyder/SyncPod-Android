package com.cyder.atsushi.youtubesync.json_data;

/**
 * Created by chigichan24 on 2017/11/28.
 */

public class SearchResponse extends JsonParameter{
    public String next_page_token;
    public String prev_page_token;
    public String total_results;
    public String results_per_page;
    public Video[] items;
}
