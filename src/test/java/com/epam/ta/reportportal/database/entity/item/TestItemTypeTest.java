/*
 * Copyright 2016 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-dao
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal.database.entity.item;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.nullValue;

/**
 * @author Andrei Varabyeu
 */
public class TestItemTypeTest {

	private static final String ERROR_MESSAGE = "Incorrect test item level comparator";

	@Test
	public void checkHigherThan() {
		Assert.assertThat(ERROR_MESSAGE, TestItemType.TEST.higherThan(TestItemType.STEP), CoreMatchers.is(true));
		Assert.assertThat(ERROR_MESSAGE, TestItemType.SUITE.higherThan(TestItemType.TEST), CoreMatchers.is(true));
	}

	@Test
	public void checkEquals() {
		Assert.assertThat(ERROR_MESSAGE, TestItemType.TEST.higherThan(TestItemType.TEST), CoreMatchers.is(false));
		Assert.assertThat(ERROR_MESSAGE, TestItemType.STEP.higherThan(TestItemType.STEP), CoreMatchers.is(false));
	}

	@Test
	public void checkSameLevel() {
		Assert.assertThat(ERROR_MESSAGE, TestItemType.TEST.sameLevel(TestItemType.TEST), CoreMatchers.is(true));
		Assert.assertThat(ERROR_MESSAGE, TestItemType.STEP.sameLevel(TestItemType.STEP), CoreMatchers.is(true));
	}

	@Test
	public void checkLowerThan() {
		Assert.assertThat(ERROR_MESSAGE, TestItemType.TEST.lowerThan(TestItemType.SUITE), CoreMatchers.is(true));
		Assert.assertThat(ERROR_MESSAGE, TestItemType.STEP.lowerThan(TestItemType.TEST), CoreMatchers.is(true));
	}

	@Test
	public void checkNullable() throws IntrospectionException {
		TestItem item = TestItem.nullable();

		Arrays.stream(Introspector.getBeanInfo(TestItem.class).getPropertyDescriptors())
				.filter(pd -> Objects.nonNull(pd.getReadMethod()))
				.filter(pd -> !"getClass".equals(pd.getReadMethod().getName()))
				.map(PropertyDescriptor::getReadMethod)
				.forEach(m -> {
					try {
						Object obj = m.invoke(item);
						Assert.assertThat("Method " + m.getName() + " does not return null", obj, nullValue());

					} catch (IllegalAccessException | InvocationTargetException e) {
						Assert.fail("Cannot invoke method");
					}
				});

	}
}