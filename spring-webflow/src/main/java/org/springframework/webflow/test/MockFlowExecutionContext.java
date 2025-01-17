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
package org.springframework.webflow.test;

import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.execution.FlowExecutionContext;
import org.springframework.webflow.execution.FlowExecutionKey;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.execution.FlowSession;

/**
 * A stub implementation of the flow execution context interface.
 *
 * @author Keith Donald
 * @see FlowExecutionContext
 */
public class MockFlowExecutionContext implements FlowExecutionContext {

    private boolean started;

    private FlowExecutionKey key;

    private FlowDefinition flow;

    private FlowSession activeSession;

    private MutableAttributeMap<Object> flashScope = new LocalAttributeMap<>();

    private MutableAttributeMap<Object> conversationScope = new LocalAttributeMap<>();

    private MutableAttributeMap<Object> attributes = new LocalAttributeMap<>();

    private FlowExecutionOutcome outcome;

    /**
     * Creates a new mock flow execution context -- automatically installs a root flow definition and active flow
     * session.
     */
    public MockFlowExecutionContext() {
        setActiveSession(new MockFlowSession());
        this.flow = activeSession.getDefinition();
    }

    /**
     * Creates a new mock flow execution context for the specified root flow definition.
     *
     * @param flow
     * @param flow
     */
    public MockFlowExecutionContext(Flow flow) {
        this(new MockFlowSession(flow));
    }

    /**
     * Creates a new mock flow execution context for the specified active flow session.
     *
     * @param flowSession
     * @param flowSession
     */
    public MockFlowExecutionContext(FlowSession flowSession) {
        setActiveSession(flowSession);
        this.flow = flowSession.getDefinition();
    }

    public FlowExecutionKey getKey() {
        return key;
    }

    /**
     * Sets the flow execution key
     *
     * @param key
     * @param key
     */
    public void setKey(FlowExecutionKey key) {
        this.key = key;
    }

    // implementing flow execution context

    public String getCaption() {
        return flow.getCaption();
    }

    public FlowDefinition getDefinition() {
        return flow;
    }

    public boolean hasStarted() {
        return started;
    }

    public boolean isActive() {
        return activeSession != null;
    }

    public boolean hasEnded() {
        return hasStarted() && !isActive();
    }

    public FlowSession getActiveSession() throws IllegalStateException {
        if (activeSession == null) {
            throw new IllegalStateException("No flow session is active");
        }
        return activeSession;
    }

    /**
     * Sets the mock session to be the <i>active session</i>.
     *
     * @param activeSession
     * @param activeSession
     */
    public void setActiveSession(FlowSession activeSession) {
        this.activeSession = activeSession;
        if (!started && activeSession != null) {
            started = true;
        }
    }

    public MutableAttributeMap<Object> getFlashScope() {
        return flashScope;
    }

    /**
     * Sets the flow execution flash scope.
     *
     * @param scope
     * @param scope
     */
    public void setFlashScope(MutableAttributeMap<Object> scope) {
        this.flashScope = scope;
    }

    // convenience mock accessors

    public MutableAttributeMap<Object> getConversationScope() {
        return conversationScope;
    }

    // mutators

    /**
     * Sets the flow execution conversation scope.
     *
     * @param scope
     * @param scope
     */
    public void setConversationScope(MutableAttributeMap<Object> scope) {
        this.conversationScope = scope;
    }

    public AttributeMap<Object> getAttributes() {
        return attributes;
    }

    public FlowExecutionOutcome getOutcome() {
        return outcome;
    }

    /**
     * Sets the result of this flow ending.
     *
     * @param outcome the ended outcome
     */
    public void setOutcome(FlowExecutionOutcome outcome) {
        this.outcome = outcome;
    }

    public Flow getDefinitionInternal() {
        return (Flow) getDefinition();
    }

    /**
     * Sets the top-level flow definition.
     *
     * @param rootFlow
     * @param rootFlow
     */
    public void setFlow(FlowDefinition rootFlow) {
        this.flow = rootFlow;
    }

    // convenience accessors

    /**
     * Returns the mock active flow session.
     *
     * @return
     */
    public MockFlowSession getMockActiveSession() {
        return (MockFlowSession) activeSession;
    }

    /**
     * Returns the mutable execution attribute map.
     *
     * @return the execution attribute map
     */
    public MutableAttributeMap<Object> getAttributeMap() {
        return attributes;
    }

    /**
     * Puts a execution attribute into the context.
     *
     * @param attributeName  the attribute name
     * @param attributeValue the attribute value
     */
    public void putAttribute(String attributeName, Object attributeValue) {
        attributes.put(attributeName, attributeValue);
    }

}
