package com.black.collect.schedule;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.black.collect.db.repositor.ProxyRepository;
import com.black.collect.entity.ProxyEntity;

/**
 * Created by hcdeng on 2017/6/29.
 */
public class VerifyScheduler extends Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(VerifyScheduler.class);

    public VerifyScheduler(long defaultInterval, TimeUnit defaultUnit) {
        super(defaultInterval, defaultUnit);
    }

    @Override
    public void run() {
        List<ProxyEntity> proxys = ProxyRepository.getInstance().getAll();
        logger.info("verify scheduler running, proxys:"+proxys.size());
        ProxyVerifier.refreshAll(proxys);
    }
}
