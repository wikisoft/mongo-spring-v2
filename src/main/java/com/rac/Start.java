package com.rac;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;

import com.rac.config.AppConfig;

public class Start {

    public static void main(String[] args) {

	// For XML
	// ApplicationContext ctx = new
	// GenericXmlApplicationContext("SpringConfig.xml");

	// For Annotation
	// ApplicationContext ctx = new AnnotationConfigApplicationContext(
	// SpringMongoConfig.class);

	BeanFactory ctx = SpringApplication.run(AppConfig.class, args);

	// MongoOperations mongoOperation = (MongoOperations) ctx
	// .getBean("mongoTemplate");
	//
	// User user = new User("mkyong", "password123");
	//
	// // save
	// mongoOperation.save(user);
	//
	// // now user object got the created id.
	// System.out.println("1. user : " + user);
	//
	// // query to search user
	// Query searchUserQuery = new Query(Criteria.where("username").is(
	// "mkyong"));
	//
	// // find the saved user again.
	// User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
	// System.out.println("2. find - savedUser : " + savedUser);
	//
	// // update password
	// mongoOperation.updateFirst(searchUserQuery,
	// Update.update("password", "new password"), User.class);
	//
	// // find the updated user object
	// User updatedUser = mongoOperation.findOne(searchUserQuery,
	// User.class);
	//
	// System.out.println("3. updatedUser : " + updatedUser);
	//
	// // delete
	// // mongoOperation.remove(searchUserQuery, User.class);
	//
	// // List, it should be empty now.
	// List<User> listUser = mongoOperation.findAll(User.class);
	// System.out.println("4. Number of user = " + listUser.size());

    }

}
