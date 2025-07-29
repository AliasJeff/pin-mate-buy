package com.alias.test.domain.tag;

import com.alias.domain.tag.service.ITagService;
import com.alias.infrastructure.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBitSet;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TagServiceTest {

    @Resource
    private ITagService tagService;
    @Resource
    private IRedisService redisService;

    @Test
    public void test_tag_job() {
        tagService.execTagBatchJob("RQ_KJHKL98UU78H66554GFDV", "10001");
    }

    @Test
    public void test_get_tag_bitmap() {
        RBitSet bitSet = redisService.getBitSet("RQ_KJHKL98UU78H66554GFDV");
        // 是否存在
        log.info("alias 存在，预期结果为 true，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("alias")));
        log.info("zhexun 不存在，预期结果为 false，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("zhexun")));
    }

}

