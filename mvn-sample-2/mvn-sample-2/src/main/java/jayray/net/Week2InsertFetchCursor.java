package jayray.net;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

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

            System.out.println("start count: " + myCollection.count());

            myCollection.drop();

            System.out.println("post-drop count: " + myCollection.count());
            Random rand = new Random();

            for (int i = 0; i < 10; i++) {
                DBObject doc = new BasicDBObject().append("data", "something").append("num", i).append("x", rand.nextInt(2)).append("y", rand.nextInt(100)).append("z", rand.nextInt(20));
                System.out.println(doc);
                myCollection.insert(doc);
            }

            System.out.println("post-insert count: " + myCollection.count());

            DBCursor cursor = myCollection.find();
            try {
                while (cursor.hasNext()) {
                    DBObject record = cursor.next();
                    System.out.println("found:" + record);
                }
            } finally {
                cursor.close();
            }

            QueryBuilder builder = QueryBuilder.start("x").is(0).and("y").greaterThan(10).lessThan(90);
            DBObject fieldSelection = new BasicDBObject("x", false);

            cursor = myCollection.find(builder.get(), fieldSelection);
            try {
                while (cursor.hasNext()) {
                    DBObject record = cursor.next();
                    System.out.println("found this with 0:" + record);
                }
            } finally {
                cursor.close();
            }


        } finally {
            client.close();
        }
    }

}
