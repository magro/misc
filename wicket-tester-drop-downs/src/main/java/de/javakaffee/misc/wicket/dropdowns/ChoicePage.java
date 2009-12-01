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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * Copied from http://www.wicket-library.com/wicket-examples/ajax/choice
 */
public class ChoicePage extends BasePage {
    
    private String _selectedMake;

    private final Map<String, List<String>> modelsMap = new HashMap<String, List<String>>(); // map:company->model

    /**
     * @return Currently selected make
     */
    public String getSelectedMake() {
        return _selectedMake;
    }

    /**
     * @param selectedMake
     *            The make that is currently selected
     */
    public void setSelectedMake( String selectedMake ) {
        _selectedMake = selectedMake;
    }

    /**
     * Constructor.
     */
    public ChoicePage() {
        modelsMap.put( "AUDI", Arrays.asList( new String[] { "A4", "A6", "TT" } ) );
        modelsMap.put( "CADILLAC", Arrays.asList( new String[] { "CTS", "DTS", "ESCALADE", "SRX", "DEVILLE" } ) );
        modelsMap.put( "FORD", Arrays.asList( new String[] { "CROWN", "ESCAPE", "EXPEDITION", "EXPLORER", "F-150" } ) );

        IModel<List<? extends String>> makeChoices = new AbstractReadOnlyModel<List<? extends String>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<String> getObject() {
                Set<String> keys = modelsMap.keySet();
                List<String> list = new ArrayList<String>( keys );
                return list;
            }

        };

        IModel<List<? extends String>> modelChoices = new AbstractReadOnlyModel<List<? extends String>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<String> getObject() {
                List<String> models = modelsMap.get( _selectedMake );
                if ( models == null ) {
                    models = Collections.emptyList();
                }
                return models;
            }

        };

        Form<?> form = new Form<Object>( "form" );
        add( form );

        final DropDownChoice<String> makes =
                new DropDownChoice<String>( "makes", new PropertyModel<String>( this, "selectedMake" ), makeChoices );

        final DropDownChoice<String> models = new DropDownChoice<String>( "models", new Model<String>(), modelChoices );
        models.setOutputMarkupId( true );

        form.add( makes );
        form.add( models );
        form.add( new Button( "submit", new Model<String>( "Submit" ) ) );

        final Label submittedValuesLabel = new Label( "submittedValues", new Model<String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public String getObject() {
                return "Selected: " + getSelectedMake() + ", " + models.getModelObject();
            }
        });
        submittedValuesLabel.setOutputMarkupId( true );
        add( submittedValuesLabel );

        makes.add( new AjaxFormComponentUpdatingBehavior( "onchange" ) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate( AjaxRequestTarget target ) {
                target.addComponent( submittedValuesLabel );
                target.addComponent( models );
            }
            
        } );
        
    }

}
