package backend.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Course.class)
public abstract class Course_ extends backend.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Course, String> name;
	public static volatile SingularAttribute<Course, User> user;
	public static volatile SingularAttribute<Course, Long> courseId;

	public static final String NAME = "name";
	public static final String USER = "user";
	public static final String COURSE_ID = "courseId";

}

