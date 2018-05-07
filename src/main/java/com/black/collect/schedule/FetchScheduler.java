package com.black.collect.schedule;

import com.black.collect.entity.ProxyEntity;
import com.black.collect.fetcher.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hcdeng on 2017/6/29.
 */
public class FetchScheduler extends Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(FetchScheduler.class);

    public FetchScheduler(long defaultInterval, TimeUnit defaultUnit) {
        super(defaultInterval, defaultUnit);
    }

    @Override
    public void run() {

        logger.info("fetch scheduler running...");

        List<AbstractFetcher<List<ProxyEntity>>> fetchers =
                Arrays.asList(
                        new KuaiDailiFetcher(1),
                        new Www66IPFetcher(1),
                        new XichiDailiFetcher(1)
                );


        for (AbstractFetcher<List<ProxyEntity>> fetcher : fetchers) {
            fetcher.fetchAll((list)->{
                ProxyVerifier.verifyAll(list);
            });
        }

        logger.info("finish fetch scheduler");
    }
}
