package jayray.net;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: jasonray
 * Date: 3/9/13
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetCounter {


    public static void main(String[] args) throws UnknownHostException {
        DBCollection counterCollection = initializeCollection();
        ;


        final String counterName = "abc";
        int numNeeded = 2;

        int first = getRange(counterName, numNeeded, counterCollection);
        System.out.println("Range " + first + "-" + (first + numNeeded - 1));

        numNeeded = 3;
        first = getRange(counterName, numNeeded, counterCollection);
        System.out.println("Range " + first + "-" + (first + numNeeded - 1));

        numNeeded = 10;
        first = getRange(counterName, numNeeded, counterCollection);
        System.out.println("Range " + first + "-" + (first + numNeeded - 1));

        numNeeded = 1;
        first = getRange(counterName, numNeeded, counterCollection);
        System.out.println("Range " + first + "-" + (first + numNeeded - 1));

        numNeeded = 1000;
        first = getRange(counterName, numNeeded, counterCollection);
        System.out.println("Range " + first + "-" + (first + numNeeded - 1));


    }

    private static int getRange(String counterName, int range, DBCollection counterCollection) {
        DBObject query = new BasicDBObject("_id", counterName);
        DBObject update = new BasicDBObject("$inc", new BasicDBObject("counter", range));

        DBObject result = counterCollection.findAndModify(query, null, null, false, update, true, true);
        return (Integer) result.get("counter") - range;
    }

    private static DBCollection initializeCollection() {
        MongoClient client = null;
        try {
            client = new MongoClient();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        DB db = client.getDB("test");
        DBCollection counterCollection = db.getCollection("w2collection");
        counterCollection.drop();

        return counterCollection;
    }
}
