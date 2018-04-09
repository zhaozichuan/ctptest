package models;

import javax.persistence.*;

import play.data.format.Formats;
import play.db.ebean.*;
import play.data.validation.*;
import com.avaje.ebean.*;
import javax.persistence.*;
import java.util.Date;

/*
 * 日线数据
 */
@Entity
@Table(name="daily_k")
public class DailyStockMarket extends Model {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2046797276130134347L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String symbol;

    public String name;

    public Date time;
    @Constraints.Required
    @Formats.DateTime(pattern="yyyyMMdd")

    public float open;

    public float high;

    public float low;

    public float close;

    public float volume;

    public float amount;
    
    public double money1;
    
    public double money2;
    
    public double money3;
    
    public double money4;

    public static Finder<Long, DailyStockMarket> find = new Finder<Long, DailyStockMarket>( "default",Long.class,  DailyStockMarket.class);

}
