package com.deng.pp;

import com.black.collect.fetcher.AbstractFetcher;
import com.black.collect.fetcher.GoubanjiaFetcher;

/**
 * Created by hcdeng on 2017/6/29.
 */
public class Main {
    public static void main(String[] args) {
        AbstractFetcher crawler = new GoubanjiaFetcher(100);
        crawler.fetchAll();
    }
}
