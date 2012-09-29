package uk.co.tfd.sm.api.search;

/**
 * An adaptable Repository session.
 */
public interface RepositorySession {
	/**
	 * Adapts the RepositorySession to the type requested, if this is not
	 * possible a null will be returned.
	 * 
	 * @param <T>
	 *            The type of the Repository Session to be adapted to.
	 * @param c
	 *            the class of <T>
	 * @return the adapted repository session.
	 */
	<T> T adaptTo(Class<T> c);

	/**
     * Logout of the session. 
     */
	void logout();
}
