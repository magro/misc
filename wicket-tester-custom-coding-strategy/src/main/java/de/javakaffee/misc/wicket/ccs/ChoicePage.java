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

import static de.javakaffee.misc.wicket.ccs.WicketApplication.MAKES_DB;

import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Submit a chosen value via a stateless form and display this value.
 */
public class ChoicePage extends BasePage {

    /**
     * Constructor.
     */
    public ChoicePage( PageParameters params ) {
        super( params );

        String submittedMake = getMakeFrom( params );
        if ( submittedMake != null && !MAKES_DB.contains( submittedMake ) ) {
            submittedMake = null;
        }

        setStatelessHint( true );

        final IModel<List<? extends String>> makeChoices = Model.ofList( MAKES_DB );

        final Form<?> form = new StatelessForm<Object>( "form" );
        add( form );

        final IModel<String> selectedMake = new Model<String>( submittedMake );
        final DropDownChoice<String> makes = new DropDownChoice<String>( "makes", selectedMake, makeChoices );

        form.add( makes );
        form.add( new Button( "submit", new Model<String>( "Show Details!" ) ) {
            private static final long serialVersionUID = 1L;

            public void onSubmit() {
                final PageParameters params = DetailsPage.pageParametersFor( selectedMake.getObject() );
                setResponsePage( DetailsPage.class, params );
            };
        } );

        final Label submittedMakeLabel = new Label( "submittedMake", selectedMake );
        add( submittedMakeLabel );

    }

    public static PageParameters pageParametersFor( final String make ) {
        final PageParameters params = new PageParameters();
        params.put( "m", make == null
            ? ""
            : make );
        return params;
    }

    public static String getMakeFrom( final PageParameters params ) {
        return params.getString( "m" );
    }

}
