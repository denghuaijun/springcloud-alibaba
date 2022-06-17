package com.denghuaijun.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * RedisLock
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/17 11:13
 * @Description redis分布式锁
 */
@Slf4j
@Component
public class RedisLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 尝试获取锁的次数
     */
    private static int retryCount =5;
    /**
     * 每次获取锁重试的时间间隔 毫秒
     */
    private static int waitInterValTime = 100;

    /**
     * 利用redis实现简单的分布式锁，允许数据丢失，并发访问在重试次数内没有拿到锁的就会丢失
     * @param redisKey
     * @param expireTime
     * @return
     */
    public  String tryLock(final String redisKey,final int expireTime)throws DistributionLockException{
        String lockVal = UUID.randomUUID().toString()+"-"+System.currentTimeMillis();
        boolean flag = false;
        if (StringUtils.isEmpty(redisKey)){
            throw new DistributionLockException("分布式redis锁key不能为空");
        }
        if (expireTime <= 0){
            throw new DistributionLockException("分布式redis锁过期时间要大于等0");
        }
        try {
            for (int i = 0; i < retryCount; i++) {
                Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(redisKey, lockVal, expireTime, TimeUnit.SECONDS);
                if (ifAbsent){
                    flag = true;
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(waitInterValTime);
                }catch (Exception ignore){
                    LOGGER.error("redis lock fail:{}",ignore.getMessage());
                }
            }
            if (!flag){
                throw new DistributionLockException(Thread.currentThread().getName()+"cannot acquire lock now ...");
            }
            return lockVal;
        }catch (DistributionLockException e){
            throw e;
        }catch (Exception e){
            LOGGER.error("get redis lock error, exception:{}",e.getMessage());
            throw e;
        }
    }

    /**
     * 利用redis实现分布式锁，不允许数据丢失，并在获取锁的超时时间范围内一直等待重试，超时时间一定要小于等于过期时间，
     * @param redisKey
     * @param expireTime
     * @param timeOutSecond 超时时间
     * @return
     */
    public  String tryDistibutionLock(final String redisKey,final int expireTime,final int timeOutSecond)throws DistributionLockException{
        String lockVal = UUID.randomUUID().toString()+"-"+System.currentTimeMillis();
        boolean flag = false;
        if (StringUtils.isEmpty(redisKey)){
            throw new DistributionLockException("分布式redis锁key不能为空");
        }
        if (expireTime <= 0){
            throw new DistributionLockException("分布式redis锁过期时间要大于等0");
        }
        if (timeOutSecond <= 0 ){
            throw new DistributionLockException("获取分布式redis锁等待时间要大于等0");
        }
        if (timeOutSecond >=expireTime){
            throw new DistributionLockException("获取分布式redis锁等待超时时间要小于过期时间");
        }
        try {
            long timeOutAt = System.currentTimeMillis() + timeOutSecond * 1000;
            while (true){
                Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(redisKey, lockVal, expireTime, TimeUnit.SECONDS);
                if (ifAbsent){
                    flag = true;
                    break;
                }
                if (System.currentTimeMillis() >= timeOutAt){
                    LOGGER.info(Thread.currentThread().getName()+"--get lock timeout..");
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(waitInterValTime);
                }catch (Exception ignore){
                    LOGGER.error("redis lock fail:{}",ignore.getMessage());
                }
            }
            if (!flag){
                throw new DistributionLockException(Thread.currentThread().getName()+"cannot acquire lock now ...");
            }
            return lockVal;
        }catch (DistributionLockException e){
            throw e;
        }catch (Exception e){
            LOGGER.error("get redis lock error, exception:{}",e.getMessage());
            throw e;
        }
    }

    /**
     * 释放锁
     * @param redisKey
     * @param lockValue
     */
    public void unLock(final String redisKey,final String lockValue){
        if (StringUtils.isEmpty(redisKey)){
            return;
        }
        if (StringUtils.isEmpty(lockValue)){
            return;
        }
        try {
            String curLockValue = (String) redisTemplate.opsForValue().get(redisKey);
            if (curLockValue !=null && curLockValue.equals(lockValue)){
                Boolean delete = redisTemplate.delete(redisKey);
                if (!delete){
                    LOGGER.info(Thread.currentThread().getName()+"--unlock redis lock fail..");
                }else {
                    LOGGER.info(Thread.currentThread().getName()+"--unlock redis lock success..");
                }
            }
        }catch (Exception e){
            LOGGER.error(Thread.currentThread().getName()+"--unlock redis lock exception:"+e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString()+"_"+ System.currentTimeMillis());
    }
}
