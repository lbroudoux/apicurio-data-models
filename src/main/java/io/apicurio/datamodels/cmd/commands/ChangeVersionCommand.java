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

package io.apicurio.datamodels.cmd.commands;

import io.apicurio.datamodels.cmd.AbstractCommand;
import io.apicurio.datamodels.cmd.util.ModelUtils;
import io.apicurio.datamodels.compat.LoggerCompat;
import io.apicurio.datamodels.core.models.Document;

/**
 * A command used to modify the version of a document.
 * @author eric.wittmann@gmail.com
 */
public class ChangeVersionCommand extends AbstractCommand {
    
    public String _newVersion;

    public String _oldVersion;
    public boolean _nullInfo;
    
    /**
     * Constructor.
     */
    ChangeVersionCommand() {
    }
    
    /**
     * Constructor.
     * @param newVersion
     */
    ChangeVersionCommand(String newVersion) {
        this._newVersion = newVersion;
    }
    
    /**
     * @see io.apicurio.datamodels.cmd.ICommand#execute(io.apicurio.datamodels.core.models.Document)
     */
    @Override
    public void execute(Document document) {
        LoggerCompat.info("[ChangeVersionCommand] Executing.");
        if (ModelUtils.isNullOrUndefined(document.info)) {
            document.info = document.createInfo();
            this._nullInfo = true;
            this._oldVersion = null;
        } else {
            this._oldVersion = document.info.version;
        }
        document.info.version = this._newVersion;
    }
    
    /**
     * @see io.apicurio.datamodels.cmd.ICommand#undo(io.apicurio.datamodels.core.models.Document)
     */
    @Override
    public void undo(Document document) {
        LoggerCompat.info("[ChangeVersionCommand] Reverting.");
        if (this._nullInfo) {
            document.info = null;
        } else {
            document.info.version = this._oldVersion;
        }
    }

}
