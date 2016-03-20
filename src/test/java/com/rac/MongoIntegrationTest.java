package com.rac;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rac.config.AppConfig;
import com.rac.dao.AuthorRepository;
import com.rac.model.Author;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;

@SpringApplicationConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoIntegrationTest {

    private static final int MONGO_PORT = 27777;

    private MongodExecutable mongodExe;
    private MongodProcess mongod;

    @Before
    public void beforeEach() throws Exception {
	// MongodStarter starter = MongodStarter.getDefaultInstance();
	// IMongodConfig mongodConfig = new MongodConfigBuilder()
	// .version(Version.Main.PRODUCTION)
	// .net(new Net(MONGO_PORT, Network.localhostIsIPv6())).build();
	// mongodExe = starter.prepare(mongodConfig);
	// mongod = mongodExe.start();
    }

    @After
    public void afterEach() throws Exception {
	// if (this.mongod != null) {
	// this.mongod.stop();
	// this.mongodExe.stop();
	// }
    }

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testCreateAuthorDao() {
	Author author = new Author();
	author.setName("THE_AUTHOR1");
	Author authorCreated = authorRepository.save(author);
	Assert.assertEquals(authorCreated.getName(), "THE_AUTHOR1");
    }

    @Test
    public void testUpdateAuthorDao() {
	Author author = new Author();
	author.setName("THE_AUTHOR2");
	Author authorCreated = authorRepository.save(author);
	Assert.assertEquals(authorCreated.getName(), "THE_AUTHOR2");

	Author findAuthor = authorRepository.findOne(authorCreated.getId());
	Assert.assertEquals(authorCreated.getName(), findAuthor.getName());

	findAuthor.setName("NEW_AUTHOR2_NAME");
	Author updateAuthor = authorRepository.save(findAuthor);

	Assert.assertEquals(updateAuthor.getName(), "NEW_AUTHOR2_NAME");
    }

    @Test
    public void testDeleteAuthorDao() {
	Author author = new Author();
	author.setName("THE_AUTHOR3");
	Author authorCreate = authorRepository.save(author);
	Assert.assertEquals(authorCreate.getName(), "THE_AUTHOR3");

	Author findAuthor = authorRepository.findOne(authorCreate.getId());
	Assert.assertEquals(authorCreate.getName(), findAuthor.getName());

	authorRepository.delete(findAuthor);
	Assert.assertEquals(authorRepository.findOne(findAuthor.getId()), null);
    }
}
