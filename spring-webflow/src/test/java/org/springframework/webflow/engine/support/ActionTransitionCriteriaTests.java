/*
 * Copyright 2004-2008 the original author or authors.
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
package org.springframework.webflow.engine.support;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.webflow.test.MockAction;
import org.springframework.webflow.test.MockRequestContext;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTransitionCriteriaTests {

    private MockAction action;

    private ActionTransitionCriteria criteria;

    @BeforeEach
    public void setUp() throws Exception {
        action = new MockAction();
        criteria = new ActionTransitionCriteria(action);
    }

    @Test
    public void testExecuteSuccessResult() {
        MockRequestContext context = new MockRequestContext();
        assertTrue(criteria.test(context));
    }

    @Test
    public void testExecuteTrueResult() {
        action.setResultEventId("true");
        MockRequestContext context = new MockRequestContext();
        assertTrue(criteria.test(context));
    }

    @Test
    public void testExecuteYesResult() {
        action.setResultEventId("yes");
        MockRequestContext context = new MockRequestContext();
        assertTrue(criteria.test(context));
    }

    @Test
    public void testExecuteErrorResult() {
        action.setResultEventId("whatever");
        MockRequestContext context = new MockRequestContext();
        assertFalse(criteria.test(context));
    }

}
