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

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@link BeanDefinitionParser} for the <code>&lt;flow-executor&gt;</code> tag.
 *
 * @author Keith Donald
 */
class FlowExecutorBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    private static final String FLOW_EXECUTOR_FACTORY_BEAN_CLASS_NAME = "org.springframework.webflow.config.FlowExecutorFactoryBean";

    protected String getBeanClassName(Element element) {
        return FLOW_EXECUTOR_FACTORY_BEAN_CLASS_NAME;
    }

    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder definitionBuilder) {
        definitionBuilder.addPropertyReference("flowDefinitionLocator", getFlowRegistry(element, parserContext));
        addFlowExecutionRepositoryProperties(element, definitionBuilder, parserContext);
        definitionBuilder.addPropertyValue("flowExecutionAttributes", parseFlowExecutionAttributes(element));
        addFlowExecutionListenerLoader(element, parserContext, definitionBuilder);
    }

    private String getFlowRegistry(Element element, ParserContext parserContext) {
        String registry = element.getAttribute("flow-registry");
        if (!StringUtils.hasText(registry)) {
            return "flowRegistry";
        } else {
            return registry;
        }
    }

    private void addFlowExecutionRepositoryProperties(Element element, BeanDefinitionBuilder definitionBuilder,
                                                      ParserContext parserContext) {
        Element repositoryElement = DomUtils.getChildElementByTagName(element, "flow-execution-repository");
        if (repositoryElement != null) {
            addMaxExecutions(repositoryElement, definitionBuilder, parserContext);
            addConversationManager(repositoryElement, definitionBuilder, parserContext);
            addMaxSnapshots(repositoryElement, definitionBuilder, parserContext);
        }
    }

    private void addMaxExecutions(Element element, BeanDefinitionBuilder definitionBuilder, ParserContext parserContext) {
        String maxConversations = element.getAttribute("max-executions");
        if (StringUtils.hasText(maxConversations)) {
            definitionBuilder.addPropertyValue("maxFlowExecutions", maxConversations);
        }
    }

    private void addConversationManager(Element element, BeanDefinitionBuilder definitionBuilder,
                                        ParserContext parserContext) {
        String conversationManager = element.getAttribute("conversation-manager");
        if (StringUtils.hasText(conversationManager)) {
            definitionBuilder.addPropertyReference("conversationManager", conversationManager);
        }
    }

    private void addMaxSnapshots(Element element, BeanDefinitionBuilder definitionBuilder, ParserContext parserContext) {
        String maxSnapshots = element.getAttribute("max-execution-snapshots");
        if (StringUtils.hasText(maxSnapshots)) {
            definitionBuilder.addPropertyValue("maxFlowExecutionSnapshots", maxSnapshots);
        }
    }

    private Set<Object> parseFlowExecutionAttributes(Element element) {
        Element executionAttributesElement = DomUtils.getChildElementByTagName(element, "flow-execution-attributes");
        if (executionAttributesElement != null) {
            HashSet<Object> attributes = new HashSet<>();
            Element redirectElement = DomUtils.getChildElementByTagName(executionAttributesElement,
                "always-redirect-on-pause");
            if (redirectElement != null) {
                String value = redirectElement.getAttribute("value");
                attributes.add(new FlowElementAttribute("alwaysRedirectOnPause", value, "boolean"));
            }
            Element redirectInSameStateElement = DomUtils.getChildElementByTagName(executionAttributesElement,
                "redirect-in-same-state");
            if (redirectInSameStateElement != null) {
                String value = redirectInSameStateElement.getAttribute("value");
                attributes.add(new FlowElementAttribute("redirectInSameState", value, "boolean"));
            }
            List<Element> attributeElements = DomUtils.getChildElementsByTagName(executionAttributesElement,
                "attribute");
            for (Element attributeElement : attributeElements) {
                String name = attributeElement.getAttribute("name");
                String value = attributeElement.getAttribute("value");
                String type = attributeElement.getAttribute("type");
                attributes.add(new FlowElementAttribute(name, value, type));
            }
            return attributes;
        } else {
            return null;
        }
    }

    private void addFlowExecutionListenerLoader(Element element, ParserContext parserContext,
                                                BeanDefinitionBuilder definitionBuilder) {
        Element listenersElement = DomUtils.getChildElementByTagName(element, "flow-execution-listeners");
        if (listenersElement != null) {
            definitionBuilder.addPropertyValue("flowExecutionListenerLoader", parserContext.getDelegate()
                .parseCustomElement(listenersElement, definitionBuilder.getBeanDefinition()));
        }
    }
}
