package br.com.devdojo.examgenerator.persistence.model;

import java.io.Serializable;
import java.util.Objects;

public class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6031554529383293939L;

	protected Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
