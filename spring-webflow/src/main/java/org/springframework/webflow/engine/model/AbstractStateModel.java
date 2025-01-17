/*
 * Copyright 2004-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.webflow.engine.model;

import org.springframework.util.StringUtils;

import java.util.LinkedList;

/**
 * Model support for states.
 *
 * @author Scott Andrews
 */
public abstract class AbstractStateModel extends AbstractModel {

    private String id;

    private String parent;

    private LinkedList<AttributeModel> attributes;

    private SecuredModel secured;

    private LinkedList<AbstractActionModel> onEntryActions;

    private LinkedList<ExceptionHandlerModel> exceptionHandlers;

    public AbstractStateModel(String id) {
        setId(id);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        if (StringUtils.hasText(id)) {
            this.id = id;
        } else {
            this.id = null;
        }
    }

    /**
     * @return the parent
     */
    public String getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(String parent) {
        if (StringUtils.hasText(parent)) {
            this.parent = parent;
        } else {
            this.parent = null;
        }
    }

    /**
     * @return the attributes
     */
    public LinkedList<AttributeModel> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(LinkedList<AttributeModel> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the secured
     */
    public SecuredModel getSecured() {
        return secured;
    }

    /**
     * @param secured the secured to set
     */
    public void setSecured(SecuredModel secured) {
        this.secured = secured;
    }

    /**
     * @return the on entry actions
     */
    public LinkedList<AbstractActionModel> getOnEntryActions() {
        return onEntryActions;
    }

    /**
     * @param onEntryActions the on entry actions to set
     */
    public void setOnEntryActions(LinkedList<AbstractActionModel> onEntryActions) {
        this.onEntryActions = onEntryActions;
    }

    /**
     * @return the exception handlers
     */
    public LinkedList<ExceptionHandlerModel> getExceptionHandlers() {
        return exceptionHandlers;
    }

    /**
     * @param exceptionHandlers the exception handlers to set
     */
    public void setExceptionHandlers(LinkedList<ExceptionHandlerModel> exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
    }

    protected void fillCopy(AbstractStateModel copy) {
        copy.setParent(parent);
        copy.setAttributes(copyList(attributes));
        copy.setSecured((SecuredModel) copy(secured));
        copy.setOnEntryActions(copyList(onEntryActions));
        copy.setExceptionHandlers(copyList(exceptionHandlers));
    }

}
