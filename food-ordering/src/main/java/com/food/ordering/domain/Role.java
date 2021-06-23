package com.food.ordering.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.ordering.dto.RoleDTO;

@Entity
@Table(name = "role")
public class Role {

	public enum RoleName {
		CUSTOMER("Customer"), RESTAURANT("Restaurant_Owner"), ADMIN("Admin");

		private String roleName;

		private RoleName(String roleName) {
			this.roleName = roleName;
		}

		public String getRoleNameString() {
			return this.roleName;
		}

		public static boolean getEnum(String value) throws Exception {
			for (RoleName v : values()) {
				if (v.getRoleNameString().equalsIgnoreCase(value)) {
					return true;
				}
			}
			return false;
			// throw new UnprocessableEntityException("Invalid Role");
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Role() {

	}

	public Role(RoleDTO roleDTO) {
		this.name = roleDTO.getName();

	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

//	@JsonIgnore
//	@ManyToMany(mappedBy = "roles")
//	private List<User> users;
}
