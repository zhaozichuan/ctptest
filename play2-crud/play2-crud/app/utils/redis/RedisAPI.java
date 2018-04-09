package utils.redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
 

public class RedisAPI {
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
            System.out.println("1111");
            //控制�?个pool可分配多少个jedis实例，�?�过pool.getResource()来获取；
            //如果赋�?�为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)�?
             config.setMaxActive(30);
            //控制�?个pool�?多有多少个状态为idle(空闲�?)的jedis实例�?
            config.setMaxIdle(30);
            //表示当borrow(引入)�?个jedis实例时，�?大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException�?
         //   config.setMaxWait(1000 * 100);
            //在borrow�?个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            //config.setTestOnBorrow(true);
            pool = new JedisPool(config, "120.25.13.80", 6379,5000,"xmstock");
         //   pool = new JedisPool(config, "120.25.123.226", 6379);
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
            pool.returnBrokenResource(jedis);
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
    
    
}
