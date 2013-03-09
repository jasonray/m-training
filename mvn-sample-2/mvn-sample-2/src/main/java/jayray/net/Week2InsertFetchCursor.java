package jayray.net;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: jasonray
 * Date: 3/9/13
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class Week2InsertFetchCursor {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = null;
        try {
            client = new MongoClient();
            DB db = client.getDB("test");
            DBCollection myCollection = db.getCollection("w2collection");

            myCollection.drop();

            for (int i = 0; i < 10; i++) {
                DBObject doc = new BasicDBObject().append("data", "something").append("num", i);
                System.out.println(doc);
                myCollection.insert(doc);
            }

            DBCursor cursor = myCollection.find();
            try {
                while (cursor.hasNext()) {
                    DBObject record = cursor.next();
                    System.out.println("found:" + record);
                }
            } finally {
                cursor.close();
            }


        } finally {
            client.close();
        }
    }

}
