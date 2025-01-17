package org.springframework.webflow.engine.support;

import org.junit.jupiter.api.Test;
import org.springframework.binding.expression.support.StaticExpression;
import org.springframework.webflow.engine.Transition;
import org.springframework.webflow.test.MockRequestContext;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultTargetResolverTests {
    @Test
    public void testResolveState() {
        DefaultTargetStateResolver resolver = new DefaultTargetStateResolver("mockState");
        MockRequestContext context = new MockRequestContext();
        Transition transition = new Transition();
        assertEquals("mockState", resolver.resolveTargetState(transition, null, context).getId());
    }

    @Test
    public void testResolveStateExpression() {
        DefaultTargetStateResolver resolver = new DefaultTargetStateResolver(new StaticExpression("mockState"));
        MockRequestContext context = new MockRequestContext();
        Transition transition = new Transition();
        assertEquals("mockState", resolver.resolveTargetState(transition, null, context).getId());
    }

    @Test
    public void testResolveStateNull() {
        DefaultTargetStateResolver resolver = new DefaultTargetStateResolver((String) null);
        MockRequestContext context = new MockRequestContext();
        Transition transition = new Transition();
        assertEquals(null, resolver.resolveTargetState(transition, null, context));
    }

}
