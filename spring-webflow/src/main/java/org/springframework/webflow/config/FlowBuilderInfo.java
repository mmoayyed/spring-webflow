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
package org.springframework.webflow.config;

import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Set;

/**
 * A low-level pointer to a flow definition that will be registered in a flow registry and built by a concrete flow
 * builder implementation class.
 *
 * @author Keith Donald
 */
class FlowBuilderInfo {

    /**
     * The id to assign to the flow definition.
     */
    private String id;

    /**
     * The fully-qualified flow builder implementation class.
     */
    private String className;

    /**
     * Attributes to assign to the flow definition.
     */
    private Set<FlowElementAttribute> attributes;

    public FlowBuilderInfo(String id, String className, Set<FlowElementAttribute> attributes) {
        Assert.hasText(className, "The fully-qualified FlowBuilder class name is required");
        this.className = className;
        setId(id);
        this.attributes = (attributes != null ? attributes : Collections.emptySet());
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        if (StringUtils.hasText(id)) {
            this.id = id;
        } else {
            this.id = StringUtils.uncapitalize(StringUtils.delete(ClassUtils.getShortName(className), "FlowBuilder"));
        }
    }

    public String getClassName() {
        return className;
    }

    public Set<FlowElementAttribute> getAttributes() {
        return attributes;
    }
}
