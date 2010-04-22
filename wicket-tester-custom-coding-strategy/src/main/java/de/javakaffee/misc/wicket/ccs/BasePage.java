/*
 * $Id$
 * (c) Copyright 2009 freiheit.com technologies GmbH
 *
 * Created on Nov 30, 2009 by Martin Grotzke (martin.grotzke@freiheit.com)
 *
 * This file contains unpublished, proprietary trade secret information of
 * freiheit.com technologies GmbH. Use, transcription, duplication and
 * modification are strictly prohibited without prior written consent of
 * freiheit.com technologies GmbH.
 */
package de.javakaffee.misc.wicket.ccs;

import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import de.javakaffee.misc.wicket.ccs.WicketApplication.MySession;

public class BasePage extends WebPage {
    /**
     * Constructor
     */
    public BasePage() {
        this( null );
    }

    /**
     * @param params
     */
    @SuppressWarnings( "serial" )
    public BasePage( PageParameters params ) {
        super( params );
        List<String> items = MySession.get().getItems();
        if ( items == null || items.isEmpty() ) {
            add( new InvisibleContainer( "items" ) );
        }
        else {
            
            add( new ListView<String>( "items", items ) {

                @Override
                protected void populateItem( ListItem<String> item ) {
                    item.add( new Label( "item", item.getModelObject() ) );
                }
            } );
        }
    }
    
    static class InvisibleContainer extends WebMarkupContainer {
        
        private static final long serialVersionUID = 1L;

        public InvisibleContainer( final String id ) {
            super( id );
            setVisible( false );
        }

    }

}
