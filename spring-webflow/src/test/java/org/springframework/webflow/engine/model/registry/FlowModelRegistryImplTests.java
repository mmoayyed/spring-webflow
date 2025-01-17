package org.springframework.webflow.engine.model.registry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.webflow.engine.model.FlowModel;

import static org.junit.jupiter.api.Assertions.*;

public class FlowModelRegistryImplTests {

    private FlowModelRegistryImpl registry = new FlowModelRegistryImpl();

    private FlowModel fooFlow;

    private FlowModel barFlow;

    @BeforeEach
    public void setUp() {
        fooFlow = new FlowModel();
        barFlow = new FlowModel();
    }

    @Test
    public void testNoSuchFlowDefinition() {
        try {
            registry.getFlowModel("bogus");
            fail("Should've bombed with NoSuchFlow");
        } catch (NoSuchFlowModelException e) {

        }
    }

    @Test
    public void testRegisterFlow() {
        registry.registerFlowModel("foo", new StaticFlowModelHolder(fooFlow));
        assertEquals(fooFlow, registry.getFlowModel("foo"));
    }

    @Test
    public void testRegisterFlowSameIds() {
        registry.registerFlowModel("foo", new StaticFlowModelHolder(fooFlow));
        FlowModel newFlow = new FlowModel();
        registry.registerFlowModel("foo", new StaticFlowModelHolder(newFlow));
        assertSame(newFlow, registry.getFlowModel("foo"));
    }

    @Test
    public void testRegisterMultipleFlows() {
        registry.registerFlowModel("foo", new StaticFlowModelHolder(fooFlow));
        registry.registerFlowModel("bar", new StaticFlowModelHolder(barFlow));
        assertEquals(fooFlow, registry.getFlowModel("foo"));
        assertEquals(barFlow, registry.getFlowModel("bar"));
    }

    @Test
    public void testParentHierarchy() {
        testRegisterMultipleFlows();
        FlowModelRegistryImpl child = new FlowModelRegistryImpl();
        child.setParent(registry);
        FlowModel fooFlow = new FlowModel();
        child.registerFlowModel("foo", new StaticFlowModelHolder(fooFlow));
        assertSame(fooFlow, child.getFlowModel("foo"));
        assertEquals(barFlow, child.getFlowModel("bar"));
    }

    private static class StaticFlowModelHolder implements FlowModelHolder {

        private FlowModel model;

        public StaticFlowModelHolder(FlowModel model) {
            this.model = model;
        }

        public FlowModel getFlowModel() {
            return model;
        }

        public Resource getFlowModelResource() {
            return null;
        }

        public boolean hasFlowModelChanged() {
            return false;
        }

        public void refresh() {
        }

    }
}
