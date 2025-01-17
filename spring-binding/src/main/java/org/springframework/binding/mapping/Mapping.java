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
package org.springframework.binding.mapping;

import org.springframework.binding.expression.Expression;

/**
 * Information about a single mapping.
 *
 * @author Keith Donald
 */
public interface Mapping {

    /**
     * The source of the mapping.
     *
     * @return
     */
    Expression getSourceExpression();

    /**
     * The target of the mapping.
     *
     * @return
     */
    Expression getTargetExpression();

    /**
     * Whether this is a required mapping.
     *
     * @return
     */
    boolean isRequired();
}
