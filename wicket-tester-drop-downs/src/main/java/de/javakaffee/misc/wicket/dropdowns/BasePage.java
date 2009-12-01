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
package de.javakaffee.misc.wicket.dropdowns;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class BasePage extends WebPage {
    /**
     * Constructor
     */
    public BasePage() {
        add( new BookmarkablePageLink<Void>( "back", HomePage.class ).setAutoEnable( true ) );
    }

}
