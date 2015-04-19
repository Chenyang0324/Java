import com.mongodb.*;

public class DataAccess {
	public static Object GetAnswer(BasicDBObject dbo) {
		Object obj = null;
		DB db = (new MongoClient("Nigel-PC", 27017)).getDB("test");
		try {
			DBCollection dBCollection = db.getCollection("Channel");
			DBCursor dbCursor = dBCollection.find(dbo);
			obj = dbCursor;
		} catch (Exception ex) {
			throw ex;
		}

		return obj;
	}

	public static void Ins(BasicDBObject dbo) {

		DB db = (new MongoClient("Nigel-PC", 27017)).getDB("test");
		try {
			DBCollection dBCollection = db.getCollection("Channel");
			dBCollection.insert(dbo);
		} catch (Exception ex) {
			throw ex;
		}

	}
	public static Object FindAll() {
		Object obj = null;
		DB db = (new MongoClient("Nigel-PC", 27017)).getDB("test");
		try {
			DBCollection dBCollection = db.getCollection("Channel");
			DBCursor dbCursor = dBCollection.find();
			obj = dbCursor;
		} catch (Exception ex) {
			throw ex;
		}

		return obj;
	}
}
