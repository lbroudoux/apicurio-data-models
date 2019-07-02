package io.apicurio.datamodels.asyncapi.models;

import io.apicurio.datamodels.core.models.Node;

/**
 * Represents `Operation Trait Object | Message Trait Object` type.
 *
 * @author Jakub Senko <jsenko@redhat.com>
 * @see <a href="https://www.asyncapi.com/docs/specifications/2.0.0-rc1/#componentsObject">AsyncAPI 2.0.0 spec</a>
 */
public abstract class AaiTraitItem extends Node {

    public String _name;

    // is either
    public AaiMessageTrait _messageTrait;
    // or
    public AaiOperationTrait _operationTrait;
    // if we cannot detect the trait type
    public Object _unknownTrait;

    public AaiTraitItem(Node parent) {
        if(parent != null) {
            this._parent = parent;
            this._ownerDocument = parent.ownerDocument();
        }
    }

    public AaiTraitItem(Node parent, String name) {
        this(parent);
        this._name = name;
    }

    public String getName() {
        return _name;
    }
}