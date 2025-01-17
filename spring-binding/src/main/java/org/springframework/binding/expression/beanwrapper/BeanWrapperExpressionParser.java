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
package org.springframework.binding.expression.beanwrapper;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.binding.convert.ConversionExecutor;
import org.springframework.binding.convert.ConversionService;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.binding.expression.Expression;
import org.springframework.binding.expression.ParserContext;
import org.springframework.binding.expression.ParserException;
import org.springframework.binding.expression.support.AbstractExpressionParser;

/**
 * An expression parser that parses BeanWrapper property expressions.
 *
 * @author Keith Donald
 */
public class BeanWrapperExpressionParser extends AbstractExpressionParser {

    private ConversionService conversionService;

    private boolean autoGrowNestedPaths = false;

    private int autoGrowCollectionLimit = Integer.MAX_VALUE;

    /**
     * Creates a new expression parser that uses a {@link DefaultConversionService} to perform type conversion.
     */
    public BeanWrapperExpressionParser() {
        this.conversionService = new DefaultConversionService();
    }

    /**
     * Creates a new expression parser that uses the specified conversion service for type conversion.
     *
     * @param conversionService the conversion service to use
     */
    public BeanWrapperExpressionParser(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * The conversion service to use to obtain {@link ConversionExecutor conversion executors} that will be adapted to
     * {@link PropertiesEditor property editors} for use during a
     * {@link BeanWrapperImpl#setPropertyValue(String, Object) set value} call. The default if not specified is an
     * instance of {@link DefaultConversionService}.
     *
     * @return
     */
    public ConversionService getConversionService() {
        return conversionService;
    }

    /**
     * Sets the conversion service to use to obtain {@link ConversionExecutor conversion executors} that will be adapted
     * to {@link PropertiesEditor property editors} for use during a
     * {@link BeanWrapperImpl#setPropertyValue(String, Object) set value} call.
     *
     * @param conversionService the conversion service
     */
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * Set whether this BeanWrapper should attempt to "auto-grow" a nested path that contains a null value.
     * <p>If "true", a null path location will be populated with a default object value and traversed
     * instead of resulting in a {@link NullValueInNestedPathException}. Turning this flag on also
     * enables auto-growth of collection elements when accessing an out-of-bounds index.
     * <p>Default is "false" on a plain BeanWrapper.
     *
     * @param autoGrowNestedPaths
     * @param autoGrowNestedPaths
     */
    public void setAutoGrowNestedPaths(boolean autoGrowNestedPaths) {
        this.autoGrowNestedPaths = autoGrowNestedPaths;
    }

    /**
     * Specify a limit for array and collection auto-growing.
     * <p>Default is unlimited on a plain BeanWrapper.
     *
     * @param autoGrowCollectionLimit
     * @param autoGrowCollectionLimit
     */
    public void setAutoGrowCollectionLimit(int autoGrowCollectionLimit) {
        this.autoGrowCollectionLimit = autoGrowCollectionLimit;
    }

    protected Expression doParseExpression(String expressionString, ParserContext context) throws ParserException {
        BeanWrapperExpression expression = new BeanWrapperExpression(expressionString, conversionService);
        expression.setAutoGrowNestedPaths(autoGrowNestedPaths);
        expression.setAutoGrowCollectionLimit(autoGrowCollectionLimit);
        return expression;
    }
}
