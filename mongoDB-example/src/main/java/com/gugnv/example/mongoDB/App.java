package com.gugnv.example.mongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	// To directly connect to a single MongoDB server (note that this will
    			// not auto-discover the primary even
    			// if it's a member of a replica set:
    			// MongoClient mongoClient = new MongoClient();
    			// or
    			// MongoClient mongoClient = new MongoClient( "localhost" );
    			// or
    			MongoClient mongoClient = new MongoClient("localhost", 27017);
    			// or, to connect to a replica set, with auto-discovery of the primary,
    			// supply a seed list of members
    			/*
    			 * MongoClient mongoClient = new MongoClient(Arrays.asList(new
    			 * ServerAddress("localhost", 27017), new ServerAddress("localhost",
    			 * 27018), new ServerAddress("localhost", 27019)));
    			 */
    			// 连接到mydb数据库
    			DB db = mongoClient.getDB("mydb");
    			DBCollection coll = db.getCollection("test");
    			BasicDBObject doc = new BasicDBObject("name", "MongoDBTest").append("type", "database").append("count", 1).append("info", new BasicDBObject("x", 203).append("y", 102));
    			// coll.insert(doc);
    			// coll.dropIndex("name");
    			// DBObject myDoc = coll.findOne();
    			// System.out.println(myDoc);
    			System.out.println(coll.getCount());

    			DBCursor cursor = coll.find();
    			/*
    			 * try { while(cursor.hasNext()) { System.out.println(cursor.next()); }
    			 * } finally { cursor.close(); }
    			 */
    			BasicDBObject query = new BasicDBObject("i", 71);

    			cursor = coll.find(query);

    			try {
    				while (cursor.hasNext()) {
    					System.out.println(cursor.next());
    				}
    			} finally {
    				cursor.close();
    			}
    }
}
