package com.sra.searchfda.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'user,role')
@ToString(includeNames = true)
class UserRole implements Serializable {

	private static final long serialVersionUID = 1

	User user
	Role role

	static UserRole get(long userId, long roleId) {
		UserRole.where {
			user == User.load(userId) &&
			role == Role.load(roleId)
		}.get()
	}

	static UserRole create(User user, Role role, boolean flush = false) {
		new UserRole(user: user, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(User u, Role r) {

		int rowCount = UserRole.where {
			user == User.load(u.id) &&
			role == Role.load(r.id)
		}.deleteAll()

		rowCount > 0
	}

	static void removeAll(User u) {
		UserRole.where {
			user == User.load(u.id)
		}.deleteAll()
	}

	static void removeAll(Role r) {
		UserRole.where {
			role == Role.load(r.id)
		}.deleteAll()
	}

	static mapping = {
		id composite: ['role', 'user']
		version false
	}
}
