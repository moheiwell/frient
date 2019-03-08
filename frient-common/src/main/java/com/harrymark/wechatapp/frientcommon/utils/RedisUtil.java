package com.harrymark.wechatapp.frientcommon.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * 使用Autowired
 * Created by haoweima on 2019/3/8.
 */

@Component
@SuppressWarnings("all")
public class RedisUtil {

    private final static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置缓存失效时间
     *
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("redis设置缓存失效时间失败:" + e);
            return false;
        }
    }

    /**
     * 获取key的过期时间
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 查询key是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("查询key:{}不存在:{}", key, e);
            return false;
        }
    }

    /**
     * 删除key缓存
     *
     * @param key
     */
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 取得key对应的值
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 插入数据
     * 默认30min
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            set(key, value, 1800);
            return true;
        } catch (Exception e) {
            logger.error("key: {}插入失败", key, e);
            return false;
        }
    }

    /**
     * 插入数据并设置失效时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time < 0) {
                return false;
            }
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            logger.error("key: {}插入失败", key, e);
            return false;
        }
    }

    /**
     * key对应的值递增
     *
     * @param key
     * @param delta 递增步长(<0 则递减)
     * @return
     */
    public long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * HashGet
     *
     * @param key
     * @param item
     * @return
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForHash().putAll(key, map);
                expire(key, time);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("Hash插入redis失败" + e);
            return false;
        }
    }

    /**
     * HashSet(默认失败时间)
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */

    public boolean hmset(String key, Map<String, Object> map) {
        return hmset(key, map, 1800);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForHash().put(key, item, value);
                expire(key, time);
                return true;
            } else
                return false;
        } catch (Exception e) {
            logger.error("hash表中放入key:{} 数据失败:{}", key, e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建,默认失效时间30min
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        return hset(key, item, value, 1800);
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdelete(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @return double
     */
    public double hincrement(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            logger.error("根据key:{} 获取Set中的所有值失败:{}", key, e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            logger.error("根据key:{} value:{} 从一个set中查询值失败:{}", key, value, e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            logger.error("将key: {}数据放入set缓存失败: {}", key, e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            if (time > 0) {
                Long count = sSet(key, values);
                expire(key, time);
                return count;
            } else return 0;
        } catch (Exception e) {
            logger.error("将set数据放入key:{}缓存失败:{}", key, e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            logger.error("获取set缓存的长度失败" + e);
            return 0;
        }
    }

    /**
     * 移除值为value的set
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            logger.error("移除值为key为{}的特定value的数据失败:{}", key, e);
            return 0;
        }
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            logger.error("获取key为{}的list缓存的内容失败:{}", key, e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.error("获取key为{}的list缓存的长度失败", key, e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            logger.error("获取key为{}的list缓存的值失败{}", key, e);
            return null;
        }
    }

    /**
     * 将list放入缓存, 默认30min
     *
     * @param key   键
     * @param value 值
     * @return
     */

    public boolean lSet(String key, Object value) {
        return lSet(key, value, 1800);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForList().rightPush(key, value);
                expire(key, time);
                return true;
            } else return false;
        } catch (Exception e) {
            logger.error("将key为{}的list放入缓存失败{}", key, e);
            return false;
        }
    }

    /**
     * 将list放入缓存 默认30min
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        return lSet(key, value, 1800);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForList().rightPushAll(key, value);
                expire(key, time);
                return true;
            } else return false;
        } catch (Exception e) {
            logger.error("将key为{}的list放入缓存失败{}", key, e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */

    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            logger.error("根据key{}修改list中的某条数据失败{}", key, e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            logger.error("移除N个值为value失败" + e);
            return 0;
        }
    }
}