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
package org.springframework.webflow.definition.registry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.Assert;
import org.springframework.webflow.definition.FlowDefinition;

import java.util.Map;
import java.util.TreeMap;

/**
 * A generic registry implementation for housing one or more flow definitions.
 *
 * @author Keith Donald
 * @author Scott Andrews
 */
public class FlowDefinitionRegistryImpl implements FlowDefinitionRegistry {

    private static final Log logger = LogFactory.getLog(FlowDefinitionRegistryImpl.class);

    /**
     * The map of loaded Flow definitions maintained in this registry.
     */
    private Map<String, FlowDefinitionHolder> flowDefinitions;

    /**
     * An optional parent flow definition registry.
     */
    private FlowDefinitionRegistry parent;

    public FlowDefinitionRegistryImpl() {
        flowDefinitions = new TreeMap<>();
    }

    // implementing FlowDefinitionLocator

    public FlowDefinition getFlowDefinition(String id) throws NoSuchFlowDefinitionException,
        FlowDefinitionConstructionException {
        Assert.hasText(id, "An id is required to lookup a FlowDefinition");
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Getting FlowDefinition with id '" + id + "'");
            }
            return getFlowDefinitionHolder(id).getFlowDefinition();
        } catch (NoSuchFlowDefinitionException e) {
            if (parent != null) {
                // try parent
                return parent.getFlowDefinition(id);
            }
            throw e;
        }
    }

    // implementing FlowDefinitionRegistry

    public boolean containsFlowDefinition(String flowId) {
        boolean containsFlow = flowDefinitions.containsKey(flowId);
        if (!containsFlow && parent != null) {
            containsFlow = parent.containsFlowDefinition(flowId);
        }
        return containsFlow;
    }

    public int getFlowDefinitionCount() {
        return flowDefinitions.size();
    }

    public String[] getFlowDefinitionIds() {
        return flowDefinitions.keySet().toArray(new String[flowDefinitions.size()]);
    }

    public FlowDefinitionRegistry getParent() {
        return parent;
    }

    public void setParent(FlowDefinitionRegistry parent) {
        this.parent = parent;
    }

    public void registerFlowDefinition(FlowDefinitionHolder definitionHolder) {
        Assert.notNull(definitionHolder, "The holder of the flow definition to register is required");
        if (logger.isDebugEnabled()) {
            logger.debug("Registering flow definition '" + definitionHolder.getFlowDefinitionResourceString()
                         + "' under id '" + definitionHolder.getFlowDefinitionId() + "'");
        }
        flowDefinitions.put(definitionHolder.getFlowDefinitionId(), definitionHolder);
    }

    public void registerFlowDefinition(FlowDefinition definition) {
        registerFlowDefinition(new StaticFlowDefinitionHolder(definition));
    }

    public void destroy() {
        for (FlowDefinitionHolder holder : flowDefinitions.values()) {
            holder.destroy();
        }
    }

    // internal helpers

    public String toString() {
        return new ToStringCreator(this).append("flowIds", getFlowDefinitionIds()).append("parent", parent).toString();
    }

    /**
     * Returns the identified flow definition holder. Throws an exception if it cannot be found.
     */
    private FlowDefinitionHolder getFlowDefinitionHolder(String id) throws NoSuchFlowDefinitionException {
        FlowDefinitionHolder holder = flowDefinitions.get(id);
        if (holder == null) {
            throw new NoSuchFlowDefinitionException(id);
        }
        return holder;
    }
}
