package utils.redis;


import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
 

public class RedisAPI_K {
    private static JedisPool pool = null;
    
    /**   
     * 构建redis连接�?
     * 
     * @param ip
     * @param port
     * @return JedisPool
     */
    public static JedisPool getPool() {
    	//System.out.println(pool);
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
          //  System.out.println("1111");
            //控制�?个pool可分配多少个jedis实例，�?�过pool.getResource()来获取；
            //如果赋�?�为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)�?
             config.setMaxActive(-1);
            //config.
            //控制�?个pool�?多有多少个状态为idle(空闲�?)的jedis实例�?
            config.setMaxIdle(10000);
            //表示当borrow(引入)�?个jedis实例时，�?大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException�?
         //   config.setMaxWait(1000 * 100);
            //在borrow�?个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            //config.setTestOnBorrow(true);
            
            pool = new JedisPool(config, "120.25.123.226", 6379);
          //  pool = new JedisPool(config, Receiver.redis_ip, Integer.parseInt(Receiver.redis_port));
           //   pool = new JedisPool(config, "120.25.123.226", 6379);
            //System.out.println("++"+Receiver.redis_ip);
        }
        return pool;
    }
      
    /**
     * 返还到连接池
     * 
     * @param pool 
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            pool.returnResource(redis);
        }
    }
    
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public static String get(String key){
        String value = null;
        
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        
        return value;
    }
    
    
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public static void set(String key,String value){
        //String value = null;
        
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            
            Pipeline pipeline = jedis.pipelined();
            pipeline.set(key,value );
           // jedis.set(key,value );
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        
    }
    
    
//    public void Pipelined() {
//    	
//        Jedis jedis = new Jedis("localhost");
//        Pipeline pipeline = jedis.pipelined();
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            pipeline.set("p" + i, "p" + i);
//        }
//        List<Object> results = pipeline.syncAndReturnAll();
//        long end = System.currentTimeMillis();
//        System.out.println("Pipelined SET: " + ((end - start)/1000.0) + " seconds");
//        jedis.disconnect();
//    }
    
    
    
    public static String hget(String key,String field){
        String value = null;
        
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.hget(key,field);
        } catch (Exception e) {
            //释放redis对象
            pool.returnResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        
        return value;
    }
    
    
    public static String piphget(String key,String field){
        String value = null;
        
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.hget(key,field);
        } catch (Exception e) {
            //释放redis对象
            pool.returnResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        
        return value;
    }
    
    
    public static void hset(String key,String field,String value){
        
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            
            Pipeline pipeline = jedis.pipelined();
            pipeline.hset(key,field,value);
            // jedis.hset(key,field,value);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        
    }
    
    
    /**
     * 存储REDIS队列 顺序存储
     * @param byte[] key reids键名
     * @param byte[] value 键值
     */
    public static void lpush(String key, String []value) {
 
        Jedis jedis = null;
        try {
 
        	 pool = getPool();
             jedis = pool.getResource();
            jedis.lpush(key, value);
 
        } catch (Exception e) {
 
            //释放redis对象
        	pool.returnBrokenResource(jedis);
            e.printStackTrace();
 
        } finally {
 
            //返还到连接池
        	  returnResource(pool, jedis);
 
        }
    }
 
//    /**
//     * 存储REDIS队列 反向存储
//     * @param byte[] key reids键名
//     * @param byte[] value 键值
//     */
//    public static void rpush(byte[] key, byte[] value) {
// 
//        Jedis jedis = null;
//        try {
// 
//            jedis = jedisPool.getResource();
//            jedis.rpush(key, value);
// 
//        } catch (Exception e) {
// 
//            //释放redis对象
//            jedisPool.returnBrokenResource(jedis);
//            e.printStackTrace();
// 
//        } finally {
// 
//            //返还到连接池
//            close(jedis);
// 
//        }
//    }
// 
//    /**
//     * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端
//     * @param byte[] key reids键名
//     * @param byte[] value 键值
//     */
//    public static void rpoplpush(byte[] key, byte[] destination) {
// 
//        Jedis jedis = null;
//        try {
// 
//            jedis = jedisPool.getResource();
//            jedis.rpoplpush(key, destination);
// 
//        } catch (Exception e) {
// 
//            //释放redis对象
//            jedisPool.returnBrokenResource(jedis);
//            e.printStackTrace();
// 
//        } finally {
// 
//            //返还到连接池
//            close(jedis);
// 
//        }
//    }
// 
    /**
     * 获取队列数据
     * @param byte[] key 键名
     * @return
     */
    public static List lpopList(String key) {
 
        List list = null;
        Jedis jedis = null;
        try {
 
            jedis = pool.getResource();
            list = jedis.lrange(key, 0, -1);
 
        } catch (Exception e) {
 
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
 
        } finally {
 
            //返还到连接池
        	pool.returnBrokenResource(jedis);
 
        }
        return list;
    }
 
//    /**
//     * 获取队列数据
//     * @param byte[] key 键名
//     * @return
//     */
 //    public static byte[] rpop(byte[] key) {
// 
//        byte[] bytes = null;
//        Jedis jedis = null;
//        try {
// 
//            jedis = jedisPool.getResource();
//            bytes = jedis.rpop(key);
// 
//        } catch (Exception e) {
// 
//            //释放redis对象
//            jedisPool.returnBrokenResource(jedis);
//            e.printStackTrace();
// 
//        } finally {
// 
//            //返还到连接池
//            close(jedis);
// 
//        }
//        return bytes;
//    }
    
    
 //解决redis pool
    
//    while(true){
//        Jedis jedis = null;
//        boolean broken = false;
//        try {
//            jedis = jedisPool.getResource();
//            return jedisAction.action(jedis); //模板方法
//        } catch (JedisException e) {
//            broken = handleJedisException(e);
//            throw e;
//        } finally {
//            closeResource(jedis, broken);
//        }
//    }
//      
//    /**
//     * Handle jedisException, write log and return whether the connection is broken.
//     */
//    protected boolean handleJedisException(JedisException jedisException) {
//        if (jedisException instanceof JedisConnectionException) {
//          System.out.println("Redis connection " + " lost."+ jedisException);
//        } else if (jedisException instanceof JedisDataException) {
//            if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
//            	 System.out.println("Redis connection " + " are read-only slave."+ jedisException);
//            } else {
//                // dataException, isBroken=false
//                return false;
//            }
//        } else {
//        	 System.out.println("Jedis exception happen."+jedisException);
//        }
//        return true;
//    }
//    /**
//     * Return jedis connection to the pool, call different return methods depends on the conectionBroken status.
//     */
//    protected void closeResource(Jedis jedis, boolean conectionBroken) {
//        try {
//            if (conectionBroken) {
//                pool.returnBrokenResource(jedis);
//            } else {
//            	pool.returnResource(jedis);
//            }
//        } catch (Exception e) {
//          //  logger.error("return back jedis failed, will fore close the jedis.", e);
//            JedisUtils.destroyJedis(jedis);
//        }
//    }
}
