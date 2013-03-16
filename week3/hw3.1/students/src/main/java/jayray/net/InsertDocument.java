package jayray.net;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: jasonray
 * Date: 3/9/13
 * Time: 8:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class InsertDocument {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("test");
        DBCollection accountCollection = db.getCollection("account");

        DBObject doc = new BasicDBObject().append("data", "something");
        DBObject doc2 = new BasicDBObject().append("data", "something - 2");

        System.out.println(doc);
        System.out.println(doc2);

        accountCollection.insert(doc, doc2);

        System.out.println(doc);
        System.out.println(doc2);

        accountCollection.insert(doc);
    }
}
