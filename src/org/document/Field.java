package org.document;

import java.util.ArrayList;
import java.util.List;

/**
 * When create an instance of the class 
 * using <code>DocUtils.createSchema(Class)</code> 
 * then a <code>SchemaType</code> is added to a list of supported 
 * types of that instance. Thus the field has at least one 
 * supported type registered.
 * 
 * 
 * @author V. Shyshkin
 */
public class Field {
    protected Object name;
    protected List<SchemaType> supportedTypes;
    protected boolean required;
    protected boolean notNull;
    protected SchemaType schemaType;
    protected boolean tail;
    protected boolean readOnly;
    protected boolean calculated;
    protected boolean declaredFinal;
    protected boolean hasNoWriteMethod;
    protected FieldCalculator calculator;
    
    public Field(Object name) {
        assert(name != null);
        this.name = name;
        supportedTypes = new ArrayList<SchemaType>();
        this.schemaType= new ValueType(Object.class);
    }
    public Field(Object name,boolean required,boolean notNull){//,Class ... supported) {
        this(name);
        this.name = name;
        this.required = required;
        this.notNull = notNull;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public FieldCalculator getCalculator() {
        return calculator;
    }

    public void setCalculator(FieldCalculator calculator) {
        this.calculator = calculator;
    }


    public void setCalculated(boolean calculated) {
        this.calculated = calculated;
    }

    public boolean isReadOnly() {
        if ( calculated || declaredFinal || hasNoWriteMethod ) {
            return true;
        }
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isTail() {
        return tail;
    }

    public void setTail(boolean tail) {
        this.tail = tail;
    }

    public void addSupported(SchemaType type) {
        supportedTypes.add(type);
    }
    
    public SchemaType getSchemaType() {
        return this.schemaType;
        
/*        if ( this.supportedTypes.isEmpty() ) {
            return new ValueType(Object.class);
        }
        return this.supportedTypes.get(0);
*/
    }

    public void setSchemaType(SchemaType schemaType) {
        this.schemaType = schemaType;
    }
    
    
    protected List<SchemaType> getSupportedTypes() {
        return supportedTypes;
    }

    public Object getName() {
        return name;
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean isNotNull() {
        return this.notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Field other = (Field) obj;
        if (this.name != other.name && (this.name == null || !this.name.equals(other.name))) {
            return false;
        }
        if (this.supportedTypes != other.supportedTypes && (this.supportedTypes == null || !this.supportedTypes.equals(other.supportedTypes))) {
            return false;
        }
        if (this.required != other.required) {
            return false;
        }
        if (this.notNull != other.notNull) {
            return false;
        }
        if (this.tail != other.tail) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.supportedTypes != null ? this.supportedTypes.hashCode() : 0);
        hash = 97 * hash + (this.required ? 1 : 0);
        hash = 97 * hash + (this.notNull ? 1 : 0);
        hash = 97 * hash + (this.tail ? 1 : 0);
        return hash;
    }
    
}
