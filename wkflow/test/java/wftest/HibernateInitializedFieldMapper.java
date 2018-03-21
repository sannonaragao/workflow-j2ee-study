package wftest;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.hibernate.Hibernate;

public class HibernateInitializedFieldMapper implements CustomFieldMapper {
    public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
        //if field is initialized, Dozer will continue mapping
        return !Hibernate.isInitialized(sourceFieldValue);
    }
}