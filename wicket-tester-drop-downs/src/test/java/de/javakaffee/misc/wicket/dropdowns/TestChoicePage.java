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
package de.javakaffee.misc.wicket.dropdowns;

import junit.framework.Assert;

import org.apache.wicket.markup.html.form.DropDownChoice;
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

    @SuppressWarnings( "unchecked" )
    @Test
    public void testSubmitChoice() {

        // start and render the test page
        _tester.startPage( ChoicePage.class );

        // assert rendered page class
        _tester.assertRenderedPage( ChoicePage.class );

        final FormTester formTester = _tester.newFormTester( "form", false );

        final int makeIdx = 1;
        final String make = getChoice( makeIdx, "form:makes" );
        formTester.select( "makes", makeIdx );
        _tester.executeAjaxEvent( "form:makes", "onchange" );

        final Object selectedMake = ( (DropDownChoice<String>) _tester.getComponentFromLastRenderedPage( "form:makes" ) ).getDefaultModelObject();
        Assert.assertEquals( make, selectedMake );

        // select model
        final int modelIdx = 1;
        final String model = getChoice( modelIdx, "form:models" );
        _tester.newFormTester( "form", false ).select( "models", modelIdx );

        _tester.newFormTester( "form", false ).submit( "submit" );

        _tester.assertRenderedPage( ChoicePage.class );

        final Object selectedMakeAfterSubmit = _tester.getComponentFromLastRenderedPage( "form:makes" ).getDefaultModelObject();
        Assert.assertEquals( make, selectedMakeAfterSubmit );

        // this fails with AssertionFailedError: expected:<ESCAPE> but was:<null>
        final Object selectedModelAfterSubmit = _tester.getComponentFromLastRenderedPage( "form:models" ).getDefaultModelObject();
        Assert.assertEquals( model, selectedModelAfterSubmit );
        

    }

    @SuppressWarnings( "unchecked" )
    private <T> T getChoice( final int idx, final String path ) {
        final DropDownChoice<T> makesDD =
                (DropDownChoice<T>) _tester.getComponentFromLastRenderedPage( path );
        return makesDD.getChoices().get( idx );
    }

}
