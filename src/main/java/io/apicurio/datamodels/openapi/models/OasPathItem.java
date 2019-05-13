/*
 * Copyright 2019 Red Hat
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

package io.apicurio.datamodels.openapi.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.apicurio.datamodels.compat.NodeCompat;
import io.apicurio.datamodels.core.models.ExtensibleNode;
import io.apicurio.datamodels.core.models.IReferenceNode;
import io.apicurio.datamodels.core.visitors.IVisitor;
import io.apicurio.datamodels.openapi.visitors.IOasVisitor;

/**
 * Models an OpenAPI path item.
 * @author eric.wittmann@gmail.com
 */
public abstract class OasPathItem extends ExtensibleNode implements IOasParameterParent, IReferenceNode {

    private String _path;
    public String $ref;
    public OasOperation get;
    public OasOperation put;
    public OasOperation post;
    public OasOperation delete;
    public OasOperation options;
    public OasOperation head;
    public OasOperation patch;
    public List<OasParameter> parameters;
    
    /**
     * Constructor.
     * @param path
     */
    public OasPathItem(String path) {
        this._path = path;
    }
    
    /**
     * @see io.apicurio.datamodels.openapi.models.IOasParameterParent#getParameters()
     */
    @Override
    public List<OasParameter> getParameters() {
        return parameters;
    }
    
    /**
     * Gets the path string.
     */
    public String getPath() {
        return this._path;
    }
    
    /**
     * @see io.apicurio.datamodels.core.models.Node#accept(io.apicurio.datamodels.core.visitors.IVisitor)
     */
    @Override
    public void accept(IVisitor visitor) {
        IOasVisitor oasVisitor = (IOasVisitor) visitor;
        oasVisitor.visitPathItem(this);
    }

    /**
     * Creates an OAS operation object.
     * @param method
     */
    public abstract OasOperation createOperation(String method);

    /**
     * Creates a child parameter.
     */
    public abstract OasParameter createParameter();

    /**
     * Adds a parameter.
     * @param param
     */
    public OasParameter addParameter(OasParameter param) {
        if (this.parameters == null) {
            this.parameters = new ArrayList<>();
        }
        this.parameters.add(param);
        return param;
    }

    /**
     * Returns a list of parameters with a particular value of "in" (e.g. path, formData, body, etc...).
     * @param _in
     */
    public List<OasParameter> getParameters(String in) {
        if (this.parameters == null && in == null) {
            return Collections.emptyList();
        }
        List<OasParameter> rval = new ArrayList<>();
        this.parameters.forEach(param -> {
            if (NodeCompat.equals(in, param.in)) {
                rval.add(param);
            }
        });
        return rval;
    }

    /**
     * Returns a single, unique parameter identified by "in" and "name" (which are the two
     * properties that uniquely identify a parameter).  Returns null if no parameter is found.
     * @param _in
     * @param name
     */
    public OasParameter getParameter(String in, String name) {
        if (this.parameters == null) {
            return null;
        }
        for (OasParameter param : this.parameters) {
            if (NodeCompat.equals(in, param.in) && NodeCompat.equals(name, param.name)) {
                return param;
            }
        }
        return null;
    }

}