/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.List;

/**
 *
 * @author Valery
 */
public class Field {

    protected List supportedTypes;
    protected boolean required;
    protected boolean notNull;

    public void add(ValueType type) {
        supportedTypes.add(type);
    }

    public void add(EmbeddedDocument embedded) {
        supportedTypes.add(embedded);
    }
    public void add(DocumentReference ref) {
        supportedTypes.add(ref);
    }

    public List getSupportedTypes() {
        return supportedTypes;
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
}
