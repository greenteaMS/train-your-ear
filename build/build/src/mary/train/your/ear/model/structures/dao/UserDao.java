/*
 *
 */
package mary.train.your.ear.model.structures.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import mary.train.your.ear.model.User;

// TODO: Auto-generated Javadoc
/**
 * UserDao class is responsible for access to user data - user profiles and settings.
 */
public class UserDao {

	private static final Logger LOG = LogManager.getLogger(UserDao.class);
	/** The Constant USERS_FILENAME. */
	private static final String USERS_FILENAME = "./files/users.xml";

	/**
	 * Gets the users.
	 *
	 * @return the users
	 * @throws Exception the exception
	 */
	public static LinkedList<User> getUsers() throws Exception {
		LOG.info("getUsers");
		Serializer reader = new Persister();
		InputStream source = new FileInputStream(USERS_FILENAME);
		UsersWrapper example = reader.read(UsersWrapper.class, source);
		source.close();
		return example.users;
	}

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @throws Exception the exception
	 */
	public static void addUser(User user) throws Exception {
		LinkedList<User> users = new LinkedList<>();
		try{
			users = getUsers();
		} catch (Exception e){
			LOG.error("loading users failed", e.getMessage());
		}
		users.add(user);
		serializeUsers(users);
	}

	private static void serializeUsers(LinkedList<User> users) throws FileNotFoundException, Exception, IOException {
		UsersWrapper wrapper = new UsersWrapper();
		wrapper.users = users;
		Serializer serializer = new Persister();
		OutputStream file = new FileOutputStream(USERS_FILENAME);
		serializer.write(wrapper, file);
		file.close();
	}

	/**
	 * Update user.
	 *
	 * @param users the users
	 * @throws Exception the exception
	 */
	public static void updateUser(LinkedList<User> users) throws Exception {
		serializeUsers(users);
	}

	public static void main(String[] args) throws Exception{
		addUser(new User("test"));
	}

}

@Root
class UsersWrapper {
	@ElementList
	LinkedList<User> users;

	public UsersWrapper() {
		users = new LinkedList<>();
	}
}
