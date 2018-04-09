package models;

import javax.persistence.*;

import com.avaje.ebean.annotation.CreatedTimestamp;

import play.db.ebean.*;
import play.data.format.Formats;
import play.data.validation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 股票
 */
@Entity
public class Stock extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    /**
     * 股票代码
     */
    @Constraints.Required
    public String code;

    /**
     * 股票名称
     */
    public String name;
    
    
    /**
     * 股票简称
     */
    public String simpleName;
    

    /**
	 * 创建时间
	 */
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreatedTimestamp
    public Date updateTime;
    
    /**
     * 版本
     */
    public double version;
    
    
    /**
     * 股本
     */
    public long stocktotalnum;
    
    public Stock(String code) {
        this.code = code;
    }

    public static Finder<String, Stock> find = new Finder<String, Stock>(
           "default", String.class, Stock.class
    );
}
