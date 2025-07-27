package com.alias.domain.activity.service.trial.thread;

import com.alias.domain.activity.adapter.repository.IActivityRepository;
import com.alias.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.util.concurrent.Callable;

public class QueryGroupBuyActivityDiscountVOThreadTask implements Callable<GroupBuyActivityDiscountVO> {

    private final String source;
    private final String channel;
    private final IActivityRepository activityRepository;

    public QueryGroupBuyActivityDiscountVOThreadTask(String source, String channel, IActivityRepository activityRepository) {
        this.source = source;
        this.channel = channel;
        this.activityRepository = activityRepository;
    }

    @Override
    public GroupBuyActivityDiscountVO call() throws Exception {
        return activityRepository.queryGroupBuyActivityDiscountVO(source, channel);
    }
}
