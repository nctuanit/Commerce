package com.bach.Commerce.site;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.bach.Commerce.repo.jpa.BlogRepository;
import com.bach.Commerce.entity.Blog;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BlogRepositoryTests {

	@Autowired
	private BlogRepository blogRepo;

	@Autowired
	private TestEntityManager entityManager;

	/*
	 * @Test public void testCreateTag() { Tag tagType = new Tag("Trend");
	 * 
	 * entityManager.persist(tagType);
	 * 
	 * }
	 * 
	 * @Test public void testCreateNewBlogWithOneTag() {
	 * 
	 * Tag tag = entityManager.find(Tag.class, 1); Blog blog = new
	 * Blog("Test thử phát", "Nội dung test", new Date());
	 * 
	 * blog.addTag(tag);
	 * 
	 * blogRepo.save(blog); }
	 * 
	 * @Test public void testCreateNewBlogWithTwoTag() {
	 * 
	 * Tag tag1 = entityManager.find(Tag.class, 1); Tag tag2 =
	 * entityManager.find(Tag.class, 2); Blog blog = new Blog("Test thử phát",
	 * "Nội dung test", new Date());
	 * 
	 * blog.addTag(tag1); blog.addTag(tag2);
	 * 
	 * blogRepo.save(blog); }
	 * 
	 * @Test public void testAssignTagToExistBlog() { Blog blog =
	 * blogRepo.findById(1).get(); Tag tag = entityManager.find(Tag.class, 4);
	 * 
	 * blog.addTag(tag); }
	 * 
	 * 
	 * @Test public void testRemoveTagFromExistingBlog() { Blog blog =
	 * blogRepo.findById(1).get(); Tag tag = new Tag(2);
	 * 
	 * blog.removeTag(tag); }
	 */

	/*
	 * @Test public void createNewBlogWithNewTag() { Tag tag = new Tag("Models");
	 * Blog blog = new Blog("Test cháy máy", "Đã test là test cháy máy", new
	 * Date());
	 * 
	 * blog.addTag(tag); blogRepo.save(blog); }
	 */

	/*
	 * @Test public void testGetBlog() { Blog blog = blogRepo.findById(1).get();
	 * 
	 * entityManager.detach(blog);
	 * 
	 * System.out.println(blog.getTitle()); System.out.println(blog.getContent()); }
	 * 
	 * @Test public void testRemoveUser() { blogRepo.deleteById(8); }
	 */
	
	@Test 
	public void testGetAllProduct() {
		List<Blog> blogDTOSs = blogRepo.findAll();
		
		System.out.println(blogDTOSs);
	}
	
}
