/*
 * Copyright (c) 2022-2023, GÉANT
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.geant.shibboleth.plugin.userprofile.event.impl;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Unit tests for {@link LoginEvents}.
 */
public class LoginEventsTest {

    @Test
    public void test() throws JsonMappingException, JsonProcessingException {
        LoginEvents loginEvents = LoginEvents.parse(
                " [{\"rpId\":\"id\",\"attributes\":[{\"id\":\"id\",\"name\":\"name\",\"description\":\"desc\",\"values\":[\"foo\",\"bar\"]},{\"id\":\"id\",\"name\":\"name\",\"description\":\"desc\",\"values\":[\"foo\",\"bar\"]}],\"time\":500}, "
                        + "  {\"rpId\":\"id\",\"attributes\":[{\"id\":\"id\",\"name\":\"name\",\"description\":\"desc\",\"values\":[\"foo\",\"bar\"]},{\"id\":\"id\",\"name\":\"name\",\"description\":\"desc\",\"values\":[\"foo\",\"bar\"]}],\"time\":500}] ");
        loginEvents = LoginEvents.parse(loginEvents.serialize());
        Assert.assertEquals(loginEvents.getLoginEvents().size(), 2);
        AttributeImpl attribute = new AttributeImpl("id", "name", "descr", Arrays.asList("foo"));
        loginEvents.getLoginEvents()
                .add(new LoginEventImpl("rpIdNew1", 2010104, Arrays.asList(attribute, attribute, attribute)));
        loginEvents.getLoginEvents()
                .add(new LoginEventImpl("rpIdNew2", 2010104, Arrays.asList(attribute, attribute, attribute)));
        loginEvents.getLoginEvents()
                .add(new LoginEventImpl("rpIdNew3", 2010104, Arrays.asList(attribute, attribute, attribute)));
        loginEvents.getLoginEvents()
                .add(new LoginEventImpl("rpIdNew4", 2010104, Arrays.asList(attribute, attribute, attribute)));
        loginEvents.getLoginEvents()
                .add(new LoginEventImpl("rpIdNew5", 2010104, Arrays.asList(attribute, attribute, attribute)));
        loginEvents.setMaxEntries(5);
        loginEvents = LoginEvents.parse(loginEvents.serialize());
        Assert.assertEquals(loginEvents.getLoginEvents().size(), 5);
        Assert.assertTrue(loginEvents.getLoginEvents().get(0).getRpId().equals("rpIdNew1"));
    }

}
