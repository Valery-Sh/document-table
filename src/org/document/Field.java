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
public interface Field {
      void add(ValueType type);
      void addEmbedded(EmbeddedDocument embedded);
      
      List getSupportedTypes();
      //ValueType getPrefferedType();
      
      boolean isRequired();
      boolean isNotNull();
      void setRequiered(boolean required);
      void setNotNull(boolean notNull);
      
}
