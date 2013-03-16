package jayray.net;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: jasonray
 * Date: 3/9/13
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class hw21 {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("students");
        DBCollection grades = db.getCollection("grades");

        System.out.println("number of scores: " + grades.count());

        DBObject query = QueryBuilder.start("type").is("exam").and("score").greaterThanEquals(65).get();
        DBCursor cursor = grades.find(query);

        DBObject sort = new BasicDBObject("score", 1);
        cursor.sort(sort).limit(10);

        while (cursor.hasNext()) {
            DBObject record = cursor.next();
            System.out.println("record=" + record);
        }

    }
}
