package com.rac;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rac.dao.AuthorRepository;
import com.rac.dao.CommentRepository;
import com.rac.dao.PostRepository;
import com.rac.dao.UserRepository;
import com.rac.model.Author;
import com.rac.model.Comment;
import com.rac.model.Post;

//@SpringApplicationConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@IntegrationTest("spring.data.mongodb.uri=mongodb://localhost:12345/test")
//@EnableAutoConfiguration(exclude = { EmbeddedMongoAutoConfiguration.class })
public class TestDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testAuthorDao() {
	Author author = new Author();
	author.setName("rac");
	author.setDescription("desc");
	Author authorCreated = authorRepository.save(author);
	Assert.assertEquals(authorCreated.getName(), author.getName());

	Author findAuthor = authorRepository.findOne(authorCreated.getId());
	Assert.assertEquals(authorCreated.getName(), findAuthor.getName());

	authorRepository.delete(findAuthor);
	Assert.assertEquals(authorRepository.findAll().size(), 0);
    }

    @Test
    public void getAll() {
	System.err.println(userRepository.findAll().size());
    }

    @Test
    public void savePost() {
	Post post = postRepository.findAll().get(1);
	System.err.println(post.getId());
	// post.setAuthor(authorRepository.findAll().get(0));
	// post.setBody("body");
	// post.setTitle("title");

	Comment comment = new Comment();
	comment.setComment("comment2");
	comment.setDate(new Date());
	comment.setRating(5);
	// comment.setPost(post);
	commentRepository.save(comment);
	// List<Comment> c = post.getComments();
	// c.add(comment);
	// post.setComments(c);

	post.addComment(comment);

	postRepository.save(post);
    }

    @Test
    public void findComment() {
	Post post = postRepository.findAll().get(1);
	// List<Comment> comments =
	// commentRepository.findCommentByPostId(post.getId());
	// System.err.println(comments.get(0));

    }

}
