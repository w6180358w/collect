package com.black.collect.fetcher;

/**
 * Created by hcdeng on 2017/6/29.
 */
public interface Fetcher {

    boolean hasNextPage();

    String nextPage();
}
