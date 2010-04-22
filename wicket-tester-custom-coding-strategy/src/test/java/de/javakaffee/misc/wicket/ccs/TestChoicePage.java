/*
 * Copyright 2009 Martin Grotzke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package de.javakaffee.misc.wicket.ccs;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test using the WicketTester, {@link #testSubmitChoice()} fails as HomePage is
 * returned after the form is submitted.
 */
public class TestChoicePage {

    private WicketTester _tester;

    @BeforeMethod
    public void setUp() {
        _tester = new WicketTester( new WicketApplication() );
    }

    @Test
    public void testSubmitChoice() {

        // start and render the test page
        _tester.startPage( ChoicePage.class );

        // assert rendered page class
        _tester.assertRenderedPage( ChoicePage.class );

        // select make
        final FormTester formTester = _tester.newFormTester( "form", false );

        formTester.select( "makes", 1 );
        formTester.submit( "submit" );

        _tester.assertRenderedPage( DetailsPage.class );

    }

}
