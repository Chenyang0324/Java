import java.util.ArrayList;
import java.util.List;

import com.mongodb.*;
public class Repository {

	public void InsertNew(String Name, String Price, String URL)
	{
		try{
			BasicDBObject dbobject=new BasicDBObject();
			dbobject.put("Name", Name);
			dbobject.put("Price", Price);
			dbobject.put("URL", URL);
			DataAccess.Ins(dbobject);
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
	public List<CellPhone>GetAllInfo()
	{
		List<CellPhone> allInfo=new ArrayList<CellPhone>();
		try{
		DBCursor dbc=(DBCursor)DataAccess.FindAll();
		
		while(dbc.hasNext())
		{
			CellPhone temp=new CellPhone();
			DBObject to=dbc.next();
//	    System.out.println(to.get("Price").getClass());
			temp.setName((String)to.get("Name"));
			temp.setPrice(to.get("Price").toString());
			temp.setURL(to.get("URL").toString());
			allInfo.add(temp);
		}
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return allInfo;
	}
	public List<CellPhone>SingleQuery(String Name)

	{
		BasicDBObject dbobject=new BasicDBObject();
		dbobject.put("Name", new BasicDBObject("$regex",Name));
		List<CellPhone> res=new ArrayList<CellPhone>();
		try{
		DBCursor dbc=(DBCursor)DataAccess.GetAnswer(dbobject);
		while(dbc.hasNext())
		{
			CellPhone temp=new CellPhone();
			DBObject to=dbc.next();
			temp.setName((String)to.get("Name"));
			temp.setPrice(to.get("Price").toString());
			temp.setURL(to.get("URL").toString());
			res.add(temp);
		}
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return res;
		
	}
	
}
