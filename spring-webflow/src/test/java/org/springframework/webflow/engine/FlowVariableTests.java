package org.springframework.webflow.engine;

import org.junit.jupiter.api.Test;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.test.MockRequestContext;

import static org.junit.jupiter.api.Assertions.*;

public class FlowVariableTests {

    private boolean restoreCalled;

    @Test
    public void testCreateVariable() {
        FlowVariable var = new FlowVariable("foo", new VariableValueFactory() {
            public Object createInitialValue(RequestContext context) {
                return "bar";
            }

            public void restoreReferences(Object value, RequestContext context) {
            }
        });
        MockRequestContext context = new MockRequestContext();
        var.create(context);
        assertEquals("bar", context.getFlowScope().get("foo"));
    }

    @Test
    public void testDestroyVariable() {
        FlowVariable var = new FlowVariable("foo", new VariableValueFactory() {
            public Object createInitialValue(RequestContext context) {
                return "bar";
            }

            public void restoreReferences(Object value, RequestContext context) {
            }
        });
        MockRequestContext context = new MockRequestContext();
        var.create(context);
        assertEquals("bar", context.getFlowScope().get("foo"));
        var.destroy(context);
        assertFalse(context.getFlowScope().contains("foo"));
    }

    @Test
    public void testRestoreVariable() {
        FlowVariable var = new FlowVariable("foo", new VariableValueFactory() {
            public Object createInitialValue(RequestContext context) {
                return "bar";
            }

            public void restoreReferences(Object value, RequestContext context) {
                restoreCalled = true;
                assertEquals("bar", value);
            }
        });
        MockRequestContext context = new MockRequestContext();
        var.create(context);
        var.restore(context);
        assertEquals("bar", context.getFlowScope().get("foo"));
        assertTrue(restoreCalled);
    }

}
