package models;

import java.util.HashMap;
import java.util.Map;

/**
 * 按手机端的返回格式
 * 
 * @author Beni
 *
 */
public class ResultRtn {

	

	public int errCode;
	public String msg = "ok";
	public Map<String,Object> business=new HashMap<String,Object>();
}
