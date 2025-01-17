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
package org.springframework.webflow.engine.impl;

import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.definition.TransitionDefinition;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.FlowExecutionException;
import org.springframework.webflow.execution.FlowExecutionListener;
import org.springframework.webflow.execution.FlowSession;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.View;

/**
 * A helper that aids in publishing events to an array of <code>FlowExecutionListener</code> objects.
 *
 * @author Keith Donald
 * @author Erwin Vervaet
 * @author Scott Andrews
 * @see org.springframework.webflow.execution.FlowExecutionListener
 */
class FlowExecutionListeners {

    private FlowExecutionListener[] EMPTY_LISTENER_ARRAY = new FlowExecutionListener[0];

    /**
     * The list of listeners that should receive event callbacks during managed flow executions.
     */
    private FlowExecutionListener[] listeners;

    /**
     * Create a flow execution listener helper that wraps an empty listener array.
     */
    public FlowExecutionListeners() {
        this((FlowExecutionListener[]) null);
    }

    /**
     * Create a flow execution listener helper that wraps the specified listener array.
     *
     * @param listeners the listener array
     */
    public FlowExecutionListeners(FlowExecutionListener... listeners) {
        if (listeners != null) {
            this.listeners = listeners;
        } else {
            this.listeners = EMPTY_LISTENER_ARRAY;
        }
    }

    /**
     * Returns the wrapped listener array.
     *
     * @return the listener array
     */
    public FlowExecutionListener[] getArray() {
        return listeners;
    }

    /**
     * Returns the size of the listener array.
     */
    public int size() {
        return listeners.length;
    }

    // methods to fire events to all listeners

    /**
     * Notify all interested listeners that a request was submitted to the flow execution.
     */
    public void fireRequestSubmitted(RequestContext context) {
        for (FlowExecutionListener listener : listeners) {
            listener.requestSubmitted(context);
        }
    }

    /**
     * Notify all interested listeners that the flow execution finished processing a request.
     */
    public void fireRequestProcessed(RequestContext context) {
        for (FlowExecutionListener listener : listeners) {
            listener.requestProcessed(context);
        }
    }

    /**
     * Notify all interested listeners that a flow execution session is starting (about to be created).
     */
    public void fireSessionCreating(RequestContext context, FlowDefinition flow) {
        for (FlowExecutionListener listener : listeners) {
            listener.sessionCreating(context, flow);
        }
    }

    /**
     * Notify all interested listeners that a flow execution session has been activated (created, on the stack and about
     * to start).
     */
    public void fireSessionStarting(RequestContext context, FlowSession session, MutableAttributeMap<?> input) {
        for (FlowExecutionListener listener : listeners) {
            listener.sessionStarting(context, session, input);
        }
    }

    /**
     * Notify all interested listeners that a flow execution session has started (has entered its start state).
     */
    public void fireSessionStarted(RequestContext context, FlowSession session) {
        for (FlowExecutionListener listener : listeners) {
            listener.sessionStarted(context, session);
        }
    }

    /**
     * Notify all interested listeners that an event was signaled in the flow execution.
     */
    public void fireEventSignaled(RequestContext context, Event event) {
        for (FlowExecutionListener listener : listeners) {
            listener.eventSignaled(context, event);
        }
    }

    /**
     * Notify all interested listeners that a state is being entered in the flow execution.
     */
    public void fireStateEntering(RequestContext context, StateDefinition nextState) {
        for (FlowExecutionListener listener : listeners) {
            listener.stateEntering(context, nextState);
        }
    }

    /**
     * Notify all interested listeners that a state was entered in the flow execution.
     */
    public void fireStateEntered(RequestContext context, StateDefinition previousState) {
        for (FlowExecutionListener listener : listeners) {
            listener.stateEntered(context, previousState, context.getCurrentState());
        }
    }

    /**
     * Notify all interested listeners that a flow execution view is rendering.
     */
    public void fireViewRendering(RequestContext context, View view) {
        for (FlowExecutionListener listener : listeners) {
            listener.viewRendering(context, view, context.getCurrentState());
        }
    }

    /**
     * Notify all interested listeners that a flow execution has rendered.
     */
    public void fireViewRendered(RequestContext context, View view) {
        for (FlowExecutionListener listener : listeners) {
            listener.viewRendered(context, view, context.getCurrentState());
        }
    }

    /**
     * Notify all interested listeners that a transition is being entered in the flow execution.
     */
    public void fireTransitionExecuting(RequestContext context, TransitionDefinition transition) {
        for (FlowExecutionListener listener : listeners) {
            listener.transitionExecuting(context, transition);
        }
    }

    /**
     * Notify all interested listeners that a flow session was paused in the flow execution.
     */
    public void firePaused(RequestContext context) {
        for (FlowExecutionListener listener : listeners) {
            listener.paused(context);
        }
    }

    /**
     * Notify all interested listeners that the flow execution was resumed.
     */
    public void fireResuming(RequestContext context) {
        for (FlowExecutionListener listener : listeners) {
            listener.resuming(context);
        }
    }

    /**
     * Notify all interested listeners that the active flow execution session is ending.
     */
    public void fireSessionEnding(RequestContext context, FlowSession session, String outcomeId,
                                  MutableAttributeMap<?> output) {
        for (FlowExecutionListener listener : listeners) {
            listener.sessionEnding(context, session, outcomeId, output);
        }
    }

    /**
     * Notify all interested listeners that a flow execution session has ended.
     */
    public void fireSessionEnded(RequestContext context, FlowSession session, String outcomeId, AttributeMap<?> output) {
        for (FlowExecutionListener listener : listeners) {
            listener.sessionEnded(context, session, outcomeId, output);
        }
    }

    /**
     * Notify all interested listeners that a flow execution threw an exception.
     */
    public void fireExceptionThrown(RequestContext context, FlowExecutionException exception) {
        for (FlowExecutionListener listener : listeners) {
            listener.exceptionThrown(context, exception);
        }
    }
}
