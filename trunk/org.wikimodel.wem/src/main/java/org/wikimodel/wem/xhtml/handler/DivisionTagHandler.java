/*******************************************************************************
 * Copyright (c) 2005,2006 Cognium Systems SA and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Cognium Systems SA - initial API and implementation
 *******************************************************************************/
package org.wikimodel.wem.xhtml.handler;

import java.util.Arrays;

import org.wikimodel.wem.WikiParameter;
import org.wikimodel.wem.xhtml.impl.XhtmlHandler.TagStack.TagContext;

/**
 * @author kotelnikov
 * @author vmassol
 * @author thomas.mortagne
 */
public class DivisionTagHandler extends TagHandler
{

    public DivisionTagHandler()
    {
        super(true, false, true);
    }

    /**
     * @return the class used to indicate the division block is an embedded document.
     */
    protected String getDocumentClass()
    {
        return "doc";
    }

    @Override
    protected void begin(TagContext context)
    {
        WikiParameter param = context.getParams().getParameter("class");
        if (param != null) {
            // Check if we have a div meaning an empty line between block
            if (Arrays.asList(param.getValue().split(" ")).contains("wikimodel-emptyline")) {
                int value = (Integer) context.getTagStack().getStackParameter("emptyLinesCount");
                value++;
                context.getTagStack().setStackParameter("emptyLinesCount", value);
            }

            // Check if we have a div meaning start of embedded document
            if (Arrays.asList(param.getValue().split(" ")).contains(getDocumentClass())) {
                context.getScannerContext().beginDocument();
            }
        }
    }

    @Override
    protected void end(TagContext context)
    {
        WikiParameter param = context.getParams().getParameter("class");
        if (param != null) {
            if (Arrays.asList(param.getValue().split(" ")).contains(getDocumentClass())) {
                context.getScannerContext().endDocument();
            }
        }
    }
}
