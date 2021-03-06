/*
 * Copyright 2020 Red Hat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.datamodels.asyncapi.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.apicurio.datamodels.compat.NodeCompat;
import io.apicurio.datamodels.core.models.common.ExternalDocumentation;
import io.apicurio.datamodels.core.models.common.IExternalDocumentationParent;
import io.apicurio.datamodels.core.models.common.Schema;

/** 
 * Models and AsyncAPI schema.
 * @author laurent.broudoux@gmail.com
 */
public abstract class AaiSchema extends Schema implements IExternalDocumentationParent {

    public String format;
    public String title;
    public String description;
    public Object default_;
    public Number multipleOf;
    public Number maximum;
    public Boolean exclusiveMaximum;
    public Number minimum;
    public Boolean exclusiveMinimum;
    public Number maxLength;
    public Number minLength;
    public String pattern;
    public Number maxItems;
    public Number minItems;
    public Boolean uniqueItems;
    public Number maxProperties;
    public Number minProperties;
    public List<String> required;
    public List<Object> enum_;
    public String type;

    public Object items; // May be an AaiSchema or List<AaiSchema>
    public List<AaiSchema> allOf;
    public List<AaiSchema> oneOf;
    public List<AaiSchema> anyOf;
    public AaiSchema not;
    public Map<String, AaiSchema> properties;
    public Object additionalProperties; // May be a Boolean or AaiSchema

    public Boolean readOnly;
    public Boolean writeOnly;
    public String discriminator;
    public Boolean deprecated;

    public ExternalDocumentation externalDocs;
    public Object example;


    /**
     * Creates a child external documentation model.
     */
    public abstract ExternalDocumentation createExternalDocumentation();

    /**
     * @see io.apicurio.datamodels.core.models.common.IExternalDocumentationParent#setExternalDocumentation(io.apicurio.datamodels.core.models.common.ExternalDocumentation)
     */
    @Override
    public void setExternalDocumentation(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }

    /**
     * Creates a child schema model.
     */
    public abstract AaiSchema createAllOfSchema();

    /**
     * Creates a child schema model.
     */
    public abstract AaiSchema createOneOfSchema();

    /**
     * Creates a child schema model.
     */
    public abstract AaiSchema createAnyOfSchema();

    /**
     * Creates a child schema model.
     */
    public abstract AaiSchema createNotSchema();

    /**
     * Creates a child schema model.
     */
    public abstract AaiSchema createItemsSchema();

    /**
     * Creates a child schema model.
     */
    public abstract AaiSchema createAdditionalPropertiesSchema();

    /**
     * Creates a child schema model.
     */
    public abstract AaiSchema createPropertySchema(String propertyName);

    /**
     * Gets a list of all property names.
     */
    public List<String> getPropertyNames() {
        List<String> rval = new ArrayList<>();
        if (this.properties != null) {
            rval.addAll(this.properties.keySet());
        }
        return rval;
    }

    /**
     * Gets a list of all the properties.
     */
    public List<AaiSchema> getProperties() {
        List<AaiSchema> rval = new ArrayList<>();
        if (this.properties != null) {
            rval.addAll(this.properties.values());
        }
        return rval;
    }

    /**
     * Add a property.
     * @param propertyName
     * @param schema
     */
    public AaiSchema addProperty(String propertyName, AaiSchema schema) {
        if (this.properties == null) {
            this.properties = new LinkedHashMap<>();
        }
        this.properties.put(propertyName, schema);
        return schema;
    }

    /**
     * Removes a property by name.
     * @param propertyName
     */
    public AaiSchema removeProperty(String propertyName) {
        if (this.properties != null) {
            return this.properties.remove(propertyName);
        }
        return null;
    }

    /**
     * Gets a single property.
     * @param propertyName
     */
    public AaiSchema getProperty(String propertyName) {
        if (this.properties != null) {
            return this.properties.get(propertyName);
        }
        return null;
    }
    
    /**
     * Returns true if there is a single items schema.
     */
    public boolean hasItemsSchema() {
        return NodeCompat.isNode(this.items);
    }

    /**
     * Returns true if there is a list of items schemas.  Even if there is only one schema in the list.
     */
    public boolean hasItemsSchemas() {
        return NodeCompat.isList(this.items);
    }
    
    /**
     * Returns true if there is an additional properties schema.
     */
    public boolean hasAdditionalPropertiesSchema() {
        return NodeCompat.isNode(this.additionalProperties);
    }
    
    /**
     * Returns true if there is an "additionalProperties" property that is a boolean value and not a schema.
     */
    public boolean hasAdditionalPropertiesBoolean() {
        return this.additionalProperties != null && !NodeCompat.isNode(this.additionalProperties);
    }

    /**
     * Adds an AllOf schema.
     * @param schema
     */
    public void addAllOfSchema(AaiSchema schema) {
        if (this.allOf == null) {
            this.allOf = new ArrayList<>();
        }
        this.allOf.add(schema);
    }

    /**
     * Adds a OneOf schema.
     * @param schema
     */
    public void addOneOfSchema(AaiSchema schema) {
        if (this.oneOf == null) {
            this.oneOf = new ArrayList<>();
        }
        this.oneOf.add(schema);
    }

    /**
     * Adds an AnyOf schema.
     * @param schema
     */
    public void addAnyOfSchema(AaiSchema schema) {
        if (this.anyOf == null) {
            this.anyOf = new ArrayList<>();
        }
        this.anyOf.add(schema);
    }
    
    /**
     * Removes a allOf schema.
     * @param schema
     */
    public void removeAllOfSchema(AaiSchema schema) {
        if (this.allOf != null) {
            this.allOf.remove(schema);
        }
    }

    /**
     * Removes a oneOf schema.
     * @param schema
     */
    public void removeOneOfSchema(AaiSchema schema) {
        if (this.oneOf != null) {
            this.oneOf.remove(schema);
        }
    }

    /**
     * Removes a anyOf schema.
     * @param schema
     */
    public void removeAnyOfSchema(AaiSchema schema) {
        if (this.anyOf != null) {
            this.anyOf.remove(schema);
        }
    }
}
